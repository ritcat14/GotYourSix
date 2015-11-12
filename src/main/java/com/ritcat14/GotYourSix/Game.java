package com.ritcat14.GotYourSix;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.events.Event;
import com.ritcat14.GotYourSix.events.EventListener;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.UI.UIManager;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.graphics.UI.menus.StartScreen;
import com.ritcat14.GotYourSix.graphics.layers.Layer;
import com.ritcat14.GotYourSix.input.Keyboard;
import com.ritcat14.GotYourSix.input.Mouse;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.level.TileCoordinate;
import com.ritcat14.GotYourSix.util.Console;

public class Game extends Canvas implements Runnable, EventListener {
    private static final long serialVersionUID = 1L;
    private static int        scale            = 5;

    private static int        width            = Toolkit.getDefaultToolkit().getScreenSize().width / scale;
    private static int        height           = Toolkit.getDefaultToolkit().getScreenSize().height / scale;
    private static int        absoluteWidth    = width;
    private static int        absoluteHeight   = height;

    public static enum State {
        START,
        GAME,
        MAINTENANCE,
        WAITING,
        PAUSE
    }

    public static State      STATE      = State.START;

    private Thread           thread = null;
    private JFrame           frame = null;
    private static Keyboard  key = null;
    private static Level     level = null;
    private static Player    player = null;
    private boolean          running    = false;

    private static UIManager uiManager = null;
    private static UIManager minimapManager = null;
    private StartScreen      sc = null;

    private Screen           screen = null;
    private BufferedImage    image      = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[]            pixels     = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    private List<Layer>      layerStack = new ArrayList<Layer>();

    private static Game      game = null;
    public static boolean    loaded     = false;
    public static boolean paused = false;

    public Game() {
        Dimension size = new Dimension((width * scale), height * scale);
        setPreferredSize(size);
        Game.game = this;

        screen = new Screen(width, height);
        uiManager = new UIManager();
        minimapManager = new UIManager();
        frame = new JFrame();
        key = new Keyboard();
        init(STATE);

        addKeyListener(key);

        Mouse mouse = new Mouse(this);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    private void init(State state) {
        if (state == State.GAME) {
            level = Level.spawn;
            TileCoordinate playerSpawn = new TileCoordinate(15, 60);
            player = new Player("Kris", playerSpawn.x(), playerSpawn.y(), key);
            changeLevel(Level.spawn);
        } else if (state == State.START) {
            sc = new StartScreen();
            uiManager.addPanel(sc);
        }
        STATE = State.WAITING;
    }

    public void changeLevel(Level lev) {
        level = lev;
        level.add(player);
        level.setPlayerLocation();
        addLayer(level);
    }

    public static UIManager getUIManager() {
        return uiManager;
    }

    public static UIManager getMapManager() {
        return minimapManager;
    }

    public static int getWindowWidth() {
        return (width * scale);
    }

    public static int getWindowHeight() {
        return (height * scale);
    }

    public static int getAbsoluteWidth() {
        return (absoluteWidth * scale);
    }

    public static int getAbsoluteHeight() {
        return (absoluteHeight * scale);
    }

    public static int getScale() {
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

    public static Keyboard getKeyboard() {
        return key;
    }

    public void addLayer(Layer layer) {
        layerStack.add(layer);
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

    public void onEvent(Event event) {
        for (int i = layerStack.size() - 1; i >= 0; i--) {
            layerStack.get(i).onEvent(event);
        }
    }

    public void update() {
        init(STATE);
        key.update();
        uiManager.update();

        //Update layers
        for (int i = 0; i < layerStack.size(); i++) {
            layerStack.get(i).update();
        }
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
            level.setScroll((int)xScroll, (int)yScroll);
        }

        //Render layers
        for (int i = 0; i < layerStack.size(); i++) {
            layerStack.get(i).render(screen);
        }

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }
        g.setColor(new Color(0xff00ff));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(image, 0, 0, getWindowWidth(), getWindowHeight(), null);
        //g.setColor(new Color(0, 0, 0, 150));
        //g.fillRect(0, 0, getWindowWidth(), getWindowHeight());
        

        uiManager.render(g);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Console();
        game = new Game();
        game.frame.setResizable(false);
        game.frame.setUndecorated(true); //Enable for full screen
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                //save game
                int confirm =
                              JOptionPane.showOptionDialog(null, "Are You Sure to Close Application?", "Exit Confirmation",
                                                           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    System.exit(0);
                }
            }
        };
        game.frame.addWindowListener(exitListener);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);
        game.frame.requestFocus();
        game.start();
    }
}
