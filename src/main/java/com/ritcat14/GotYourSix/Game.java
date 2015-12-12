package com.ritcat14.GotYourSix;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.Point;
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
import com.ritcat14.GotYourSix.graphics.UI.menus.Pause;
import com.ritcat14.GotYourSix.graphics.UI.menus.StartScreen;
import com.ritcat14.GotYourSix.graphics.layers.Layer;
import com.ritcat14.GotYourSix.graphics.layers.UILayer;
import com.ritcat14.GotYourSix.input.Keyboard;
import com.ritcat14.GotYourSix.input.Mouse;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.level.TileCoordinate;
import com.ritcat14.GotYourSix.level.worlds.LavaLevel;
import com.ritcat14.GotYourSix.util.Console;
import com.ritcat14.GotYourSix.util.FileHandler;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Installer;

public class Game extends Canvas implements Runnable, EventListener {
    private static final long serialVersionUID = 1L;
    private static int        scale            = 3;

    private static Dimension  imgDim           = new Dimension(ImageUtil.getImage("/ui/panels/background.png").getWidth(),
                                                               ImageUtil.getImage("/ui/panels/background.png").getHeight()),
        boundary = new Dimension(900, 700), size = Game.getScaledDimension(imgDim, boundary);
    private static int        width            = size.width / scale, height = size.height / scale, absoluteWidth = width,
        absoluteHeight = height;

    private static int        frameWidth       = Toolkit.getDefaultToolkit().getScreenSize().width / scale;
    private static int        frameHeight      = Toolkit.getDefaultToolkit().getScreenSize().height / scale;

    public static enum State {
        START,
        GAME,
        WAITING,
        PAUSE
    }

    public static State      STATE        = State.START;

    private Thread           thread       = null;
    private JFrame           frame        = null;
    private static Keyboard  key          = null;
    private static Level     level        = null;
    private static Player    player       = null;
    private boolean          running      = false;

    private static UIManager uiManager    = null, minimapManager = null;
    private StartScreen      sc           = null;
    private Pause            pause        = null;

    private Screen           screen       = null;
    private BufferedImage    image        = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[]            pixels       = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    private List<Layer>      layerStack   = new ArrayList<Layer>();
    private List<UILayer>    UILayerStack = new ArrayList<UILayer>();

    private static Game      game         = null;
    public static boolean    loaded       = false, paused = false, initPause = false;;
    private int              time         = 0;
    private static Console   c            = null;

    public Game() {
        Dimension size = new Dimension((frameWidth * scale), frameHeight * scale);
        setPreferredSize(size);
        Game.game = this;

        screen = new Screen(width, height);
        //setLocation(new Point((frameWidth / 2) - (width / 2), (frameHeight / 2) - (height / 2)));
        uiManager = new UIManager();
        UILayerStack.add(uiManager);
        minimapManager = new UIManager();
        frame = new JFrame();
        key = new Keyboard();
        init(STATE);

        addKeyListener(key);

        Mouse mouse = new Mouse(this);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public static void startGame() {
        game = new Game();
        game.frame.setResizable(false);
        game.frame.setUndecorated(true); //Enable for full screen
        game.frame.setLayout(new GridBagLayout());
        game.frame.add(game, new GridBagConstraints());
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //save game
                State preState = STATE;
                if (player != null)
                    STATE = State.PAUSE;
                int confirm =
                              JOptionPane.showOptionDialog(null, "Are You Sure to Close Application?", "Exit Confirmation",
                                                           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0 && player != null) {
                    int confirm2 =
                                   JOptionPane.showOptionDialog(null, "Save Progress?", "Save Confirmation", JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm2 == 0) {
                        FileHandler.save(player);
                        System.exit(0);
                    } else {
                        System.exit(0);
                    }
                } else if (confirm == 0) {
                    System.exit(0);
                } else {
                    STATE = preState;
                    paused = false;
                }
            }
        };
        game.frame.addWindowListener(exitListener);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);
        game.frame.requestFocus();
        game.start();
        c = new Console();
        game.frame.requestFocus();
        System.out.println("Press Enter to start...");
    }

    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public static String getVersion(Object classRes) {
        return classRes.getClass().getPackage().getImplementationVersion();
    }

    private void init(State state) {
        if (state == State.GAME) {
            level = Level.createLevel(0);
            TileCoordinate playerSpawn = new TileCoordinate(15, 60);
            String playerName = "";
            if (FileHandler.fileExists(FileHandler.localUserFile))
                playerName = FileHandler.getPlayerName();
            else
                playerName = sc.getPlayerName();
            player = new Player(playerName, playerSpawn.x(), playerSpawn.y(), key);
            changeLevel(Level.activeLevel);
            FileHandler.save(player);
            initPause = false;
            c.clearTextArea();
            System.out.println("Controls: \n W/up -> Player up \n A/left -> Player left \n S/down -> Player down \n D/right -> Player right \n "
                               + "Q -> Inventory \n Shift -> Sprint \n HOME -> home level \n M -> Map \n numbers 1-6 -> Weapons");
        } else if (state == State.START) {
            sc = new StartScreen();
            uiManager.addPanel(sc);
        } else if (state == State.PAUSE) {
            pause = new Pause();
            uiManager.addPanel(pause);
            initPause = true;
            paused = true;
        }
        STATE = State.WAITING;
    }

    public JFrame getWindow() {
        return frame;
    }

    public void changeLevel(Level lev) {
        if (lev instanceof LavaLevel) {
            System.out.println();
        }
        if (level != null) {
            layerStack.remove(level);
            level = lev;
            level.add(player);
            level.setPlayerLocation();
            addLayer(level);
        }
    }

    public static UIManager getUIManager() {
        return uiManager;
    }

    public static Console getConsole() {
        return c;
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

    public void removeLayer(Layer layer) {
        layerStack.remove(layer);
    }

    public void addUILayer(UILayer layer) {
        UILayerStack.add(layer);
    }

    public void removeUILayer(UILayer layer) {
        UILayerStack.remove(layer);
    }

    public BufferedImage getImage() {
        return image;
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
        int originalWidth = imgSize.width;
        int originalHeight = imgSize.height;
        int boundWidth = boundary.width;
        int boundHeight = boundary.height;
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        // first check if we need to scale width
        if (originalWidth > boundWidth) {
            //scale width to fit
            newWidth = boundWidth;
            //scale height to maintain aspect ratio
            newHeight = (newWidth * originalHeight) / originalWidth;
        }

        // then check if we need to scale even with the new height
        if (newHeight > boundHeight) {
            //scale height to fit instead
            newHeight = boundHeight;
            //scale width to maintain aspect ratio
            newWidth = (newHeight * originalWidth) / originalHeight;
        }
        return new Dimension(newWidth, newHeight);
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
        for (int i = UILayerStack.size() - 1; i >= 0; i--) {
            UILayerStack.get(i).onEvent(event);
        }
        for (int i = layerStack.size() - 1; i >= 0; i--) {
            layerStack.get(i).onEvent(event);
        }
    }

    public void update() {
        time++;
        if (key.paused && !initPause)
            STATE = State.PAUSE;
        else if (key.paused && initPause)
            STATE = State.GAME;
        if (time % 1800 == 0 && player != null)
            FileHandler.save(player);
        init(STATE);
        key.update();
        //uiManager.update();

        //Update layers
        for (int i = 0; i < UILayerStack.size(); i++) {
            UILayerStack.get(i).update();
        }
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
        int newWidth = 0, newHeight = 0;
        if (image.getWidth() / image.getHeight() < getAbsoluteWidth() / getAbsoluteHeight()) {
            newWidth = getAbsoluteWidth();
            newHeight = (int)Math.floor((double)image.getHeight() * (double)getAbsoluteWidth() / (double)image.getWidth());
        } else {
            newHeight = getAbsoluteHeight();
            newWidth = (int)Math.floor((double)image.getWidth() * (double)getAbsoluteHeight() / (double)image.getHeight());
        }

        g.drawImage(image, 0, 0, newWidth, newHeight, null);


        //uiManager.render(g);

        for (int i = 0; i < UILayerStack.size(); i++) {
            UILayerStack.get(i).render(g);
        }
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        if (!Installer.installed) {
            new Installer();
        } else {
            startGame();
        }
    }
}
