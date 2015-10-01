package com.ritcat14.GotYourSix;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;

import javax.swing.JFrame;

import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.UI.LoadBar;
import com.ritcat14.GotYourSix.graphics.UI.UIManager;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.graphics.UI.UIActionListener;
import com.ritcat14.GotYourSix.graphics.UI.UIButton;
import com.ritcat14.GotYourSix.input.Keyboard;
import com.ritcat14.GotYourSix.input.Mouse;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.level.TileCoordinate;
import com.ritcat14.GotYourSix.util.Console;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    private static int        scale            = 5;

    private static int        width            = (Toolkit.getDefaultToolkit().getScreenSize().width / scale) - 60;
    private static int        height           = Toolkit.getDefaultToolkit().getScreenSize().height / scale;

    public static enum State {
        START,
        LOAD,
        GAME,
        PAUSE
    }

    public static State      STATE       = State.START;

    private Thread           thread;
    private JFrame           frame;
    private Keyboard         key;
    private static Level     level;
    private static Player    player;
    private boolean          running     = false;

    private static UIManager uiManager;

    private Screen           screen;
    private BufferedImage    image       = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[]            pixels      = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    public static LoadBar    loadBar;
    private static Game      game;
    public static boolean    loaded      = false;
    private boolean          loadedGame  = false;
    private static  boolean paused = false;

    //start menu items
    private boolean          loadedStart = false;
    private UIPanel          startPanel = (UIPanel)new UIPanel(new Vector2i(0, 0),
                                              new Vector2i(Game.getWindowWidth() + (60 * scale), Game.getWindowHeight()), ImageUtil.getImage("/textures/sheets/buttons/background.png")).setColor(0x363636);
    private UIButton         startFireButton = new UIButton(new Vector2i((getWindowWidth() + (60 * scale)) / 2, getWindowHeight() / 2).add(new Vector2i(2, 136)),
                                  new Vector2i(100, 30), new UIActionListener() {
                                      public void perform() {
                                          STATE = State.GAME;
                                          Player.type = Player.Type.FIRE;
                                      }
                                  });
    private UIButton         startIceButton = new UIButton(new Vector2i((getWindowWidth() + (60 * scale)) / 2, (getWindowHeight() / 2) + 40).add(new Vector2i(2, 136)),
                                  new Vector2i(100, 30), new UIActionListener() {
                                      public void perform() {
                                          STATE = State.GAME;
                                          Player.type = Player.Type.ICE;
                                      }
                                  });
  
    //pause menu items
    private UIPanel          pausePanel =
                         (UIPanel)new UIPanel(new Vector2i(((getWindowWidth() + (60 * scale))/2) - (40 * scale), (getWindowHeight()/2) - (40 * scale)),
                                              new Vector2i((80 * scale), (80 * scale))).setColor(0x363636);
    private UIButton         pauseButton = new UIButton(new Vector2i(130, 0),
                                  new Vector2i(140, 30), new UIActionListener() {
                                      public void perform() {
                                          paused = false;
                                          STATE = State.GAME;
                                      }
                                  });
    private UIButton         exitButton = new UIButton(new Vector2i(130, 40),
                                  new Vector2i(100, 30), new UIActionListener() {
                                      public void perform() {
                                        System.exit(0);
                                      }
                                  });

    public Game() {
        Dimension size = new Dimension((width * scale) + (60 * 5), height * scale);
        setPreferredSize(size);

        screen = new Screen(width, height);
        uiManager = new UIManager();
        frame = new JFrame();
        key = new Keyboard();
        if (STATE == State.GAME) {
            init(STATE);
        } else if (STATE == State.START) {
            init(STATE);
        }

        addKeyListener(key); //Add our key detector

        Mouse mouse = new Mouse(); //Create and instantiate our mouse detector
        addMouseListener(mouse); //Add the mouse detector
        addMouseMotionListener(mouse);
    }

    private void init(State state) {
        if (state == State.GAME) {
            TileCoordinate playerSpawn = new TileCoordinate(15, 60);
            player = new Player("Kris", playerSpawn.x(), playerSpawn.y(), key); //Initialise the player
            changeLevel(Level.spawn);
            loadedGame = true;
            loadedStart = false;
            paused = false;
        } else if (state == State.START) {
            //initiate start menu items here
            uiManager.addPanel(startPanel);            
            startFireButton.setText("FIRE");
            startFireButton.label.setColor(0XFFB44720);
            startPanel.addComponent(startFireButton);
            startIceButton.setText("ICE");
            startIceButton.label.setColor(0XFF2A7BCC);
            startPanel.addComponent(startIceButton);
            loadedStart = true;
            loadedGame = false;
            paused = false;
        } else if (state == State.PAUSE){
            uiManager.addPanel(pausePanel);
            pauseButton.setText("CONTINUE");
            pausePanel.addComponent(pauseButton);
            exitButton.setText("EXIT");
            pausePanel.addComponent(exitButton);
            loadedStart = false;
            paused = true;
        }
    }

    public static UIManager getUIManager() {
        return uiManager;
    }

    public static int getWindowWidth() {
        return (width * scale);
    }

    public static int getWindowHeight() {
        return (height * scale);
    }

    public synchronized static Game getGame() {
        return game;
    }

    public JFrame getFrame() {
        return frame;
    }

    public static Level getLevel() {
        return level;
    }

    public static void changeLevel(Level lev) {
        level = lev;
        level.add(player);
        level.setPlayerLocation();
    }
  
    public static boolean isPaused(){
      return paused;
    }

    public synchronized void start() { //Starts the Thread running
        running = true;
        thread = new Thread(this, "Display"); //Initialises the Thread
        thread.start(); //Runs the Thread
    }

    public synchronized void stop() { //Stops the Thread
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace(); //Catches any errors if the Thread crashes
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            render();
            frames++;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle(updates + "UPS, " + frames + "FPS");
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    public void update() {
        if (STATE == State.GAME) {
            if (!loadedGame)
                init(State.GAME);
            level.update();
        } else if (STATE == State.START) {
            if (!loadedStart)
                init(State.START);
        }
        key.update();
        uiManager.update();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();

        Graphics g = bs.getDrawGraphics();
        if (!loadedGame && STATE == State.GAME) {
            List<UIPanel> panels = uiManager.getPanels();
            for (int i = 0; i < panels.size(); i++) {
                uiManager.removePanel(panels.get(i));
            }
            init(STATE);
        } else if (loadedGame && STATE == State.GAME && !paused){
            uiManager.removePanel(pausePanel);
        } else if (!loadedStart && STATE == State.START) {
            List<UIPanel> panels = uiManager.getPanels();
            for (int i = 0; i < panels.size(); i++) {
                uiManager.removePanel(panels.get(i));
            }
            init(STATE);
        } else if (!paused && STATE == State.PAUSE){
            init(STATE);
        }
        if (player != null) {
            double xScroll = player.getX() - screen.width / 2;
            double yScroll = player.getY() - screen.height / 2;
            level.render((int)xScroll, (int)yScroll, screen);
        }

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }
        g.setColor(new Color(0xff00ff));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(image, 0, 0, getWindowWidth(), getWindowHeight(), null);
        uiManager.render(g);
        g.dispose();
        bs.show();
    }

    public Graphics getGrapics() {
        return getBufferStrategy().getDrawGraphics();
    }

    public static void main(String[] args) {
        new Console();
        game = new Game();
        game.frame.setResizable(false);
        game.frame.setUndecorated(true); //Enable for full screen
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);
        game.frame.requestFocus();
        game.start();
    }

}
