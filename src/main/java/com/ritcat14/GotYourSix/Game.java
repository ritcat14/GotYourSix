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
import com.ritcat14.GotYourSix.graphics.UI.UIManager;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.graphics.UI.menus.Maintenance;
import com.ritcat14.GotYourSix.graphics.UI.menus.StartScreen;
import com.ritcat14.GotYourSix.input.Keyboard;
import com.ritcat14.GotYourSix.input.Mouse;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.level.TileCoordinate;
import com.ritcat14.GotYourSix.util.Console;
import com.ritcat14.GotYourSix.util.FileHandler;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    private static int        scale            = 5;

    private static int        width            = (Toolkit.getDefaultToolkit().getScreenSize().width / scale) - 60;
    private static int        height           = Toolkit.getDefaultToolkit().getScreenSize().height / scale;

    public static enum State {
        START,
        GAME,
        MAINTENANCE,
        WAITING,
        PAUSE
    }

    public static State      STATE       = State.START;

    private Thread           thread;
    private JFrame           frame;
    private static Keyboard         key;
    private static Level     level;
    private static Player    player;
    private boolean          running     = false;

    private static UIManager uiManager;
    private static UIManager minimapManager;

    private Screen           screen;
    private BufferedImage    image       = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[]            pixels      = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    private static Game      game;
    public static boolean    loaded      = false;
    private boolean gameLoaded = false;
    public static  boolean paused = false;
    
    private StartScreen startScreen;
    private Maintenance maintenance;

    public Game() {
        Dimension size = new Dimension((width * scale) + (60 * 5), height * scale);
        setPreferredSize(size);

        screen = new Screen(width, height);
        uiManager = new UIManager();
        minimapManager = new UIManager();
        frame = new JFrame();
        key = new Keyboard();
        FileHandler.setupGameFiles();
        init(STATE);

        addKeyListener(key);

        Mouse mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    private void init(State state) {
        if (state == State.GAME) {
            TileCoordinate playerSpawn = new TileCoordinate(15, 60);
            player = new Player(FileHandler.getPlayerName(), playerSpawn.x(), playerSpawn.y(), key);
            changeLevel(Level.spawn);
            gameLoaded = true;
        } else if (state == State.START) {
            startScreen = new StartScreen();
            uiManager.addPanel(startScreen);
        } else if (state == State.MAINTENANCE){
            maintenance = new Maintenance();
            uiManager.addPanel(maintenance);
        }
        STATE = State.WAITING;
    }

    public static UIManager getUIManager() {
        return uiManager;
    }
  
    public static UIManager getMapManager(){
        return minimapManager;
    }

    public static int getWindowWidth() {
        return (width * scale);
    }

    public static int getWindowHeight() {
        return (height * scale);
    }
  
    public static int getScale(){
       return scale;
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
  
    public static Keyboard getKeyboard(){
        return key;
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
        init(STATE);
        if (gameLoaded) level.update();
        key.update();
        uiManager.update();
        minimapManager.update();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();

        Graphics g = bs.getDrawGraphics();
        if (STATE != State.WAITING) {
            List<UIPanel> panels = uiManager.getPanels();
            for (int i = 0; i < panels.size(); i++) {
                uiManager.removePanel(panels.get(i));
            }
        }
        init(STATE);
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
        minimapManager.render(g);
        g.dispose();
        bs.show();
    }
  
    public BufferedImage getMapImage(){
        return image;
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
