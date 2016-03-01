package com.ritcat14.GotYourSix;

import java.util.ArrayList;
import java.util.List;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.InputStream;

import java.awt.Font;
import java.awt.FontFormatException;

import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.events.Event;
import com.ritcat14.GotYourSix.events.EventListener;
import com.ritcat14.GotYourSix.graphics.*;
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
import com.ritcat14.GotYourSix.sfx.Sound;
import com.ritcat14.GotYourSix.sfx.SoundManager;
import com.ritcat14.GotYourSix.util.*;

public class Game extends Canvas implements Runnable, EventListener { //Set up game class as Canvas, and allow this class to be a thread and handle key events
    private static final long serialVersionUID = 1L; //Required by Canvas superclass
    private static int        scale            = 3; //Image rendering factor, used to speed up rendering time by scaling the BufferedImage

    private static Dimension  imgDim           = new Dimension(ImageUtil.getImage("/ui/panels/background.png").getWidth(), ImageUtil.getImage("/ui/panels/background.png").getHeight()), 
															   boundary = new Dimension(900, 700), size = Game.getScaledDimension(imgDim, boundary); //Scales the background image to 900x700 pixels, with a ratio
																																					 //the same as that of the screen resolution
    private static int        width            = size.width / scale, height = size.height / scale, absoluteWidth = width, absoluteHeight = height; //Scaled and unscaled window width/height

	/*
	* Enum state used to determinging what is currently being displayed to the user
	*/
    public static enum State {
        START,
        GAME,
        OPTION,
        WAITING,
        PAUSE
    }

    public static State      STATE        = State.START; // Instance of the state, which is static, so is accessible to any other class.
	/* 
	* Standard variable declerations.
	* Each variable is set to null to ensure that "floating" variables don't occur
	*/
    private Thread           thread       = null; //Create main thread
    private JFrame           frame        = null; //Create frame object
    private static Keyboard  key          = null; //Create keyboard for handling key events
    private static Level     level        = null; //Create an instance of the level class, so we can access level-based variables
    private static Player    player       = null; //Create an instance of the player
    private boolean          running      = false;//Determines if the thread is running or not

    private static UIManager uiManager    = null, minimapManager = null; //Create the required managers for the minimap and User Interface (UI)). These control rendering and updating of the minimap and UI
    private StartScreen      sc           = null; //Create a startscreen object
    private Pause            pause        = null; //Create a pause menu object

    private Screen           screen       = null; //Create a screen object. Used for drawing all the pixels to the frame
    private BufferedImage    image        = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //Create the scaled down BufferedImage. I used a BufferedImage because it allows for transparency, and pixel-per-pixel
																										  //Control over each rendered object, further increasing rendering time.
    private int[]            pixels       = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); //Set up an array of pixels (integers) based on the number of pixels the BufferedImage can hold.

    private List<Layer>      layerStack   = new ArrayList<Layer>();  //Create a list of layers
    private List<UILayer>    UILayerStack = new ArrayList<UILayer>();//Create a list of UI layers

    private static Game      game         = null; //Create a static game object, so other classes can access non-static items in this class
    public static boolean    loaded       = false, paused = false, initPause = false; //Booleans for if the game is paused, and if we have created a pause menu yet.
    private int              time         = 0; //Actual time since the game was running
    private static Console   c            = new Console(); //Output console for debuggibng, and displaying tips to the user
    public static Font       font         = new Font("Magneto", Font.BOLD, 24); //Set up the default font for all text in the game
    private static Graphics g; //Create a graphics object that other classes can access
    public static SoundManager s = new SoundManager() { //Create a soubnd manager, and set up all the required game sounds ready for playing.
        public void initSounds() {
            sounds.add(new Sound("INTRO", Sound.getURL("Intro.wav")));
            //sounds.add(new Sound("WALK", Sound.getURL("footstepDirt.wav")));
            /*sounds.add(new Sound("FIRESHOOT", Sound.getURL("SpellFire.wav")));
            sounds.add(new Sound("ICESHOOT", Sound.getURL("SpellIce.wav")));
            sounds.add(new Sound("ACHIEVE", Sound.getURL("Achievement.wav")));
            sounds.add(new Sound("DOORCLOSE", Sound.getURL("DoorClose.wav")));*/
            sounds.add(new Sound("GOBLIN", Sound.getURL("Goblin1.wav")));
            /*sounds.add(new Sound("ENEMY2", Sound.getURL("Goblin2.wav")));
            sounds.add(new Sound("ENEMY3", Sound.getURL("Goblin3.wav")));
            sounds.add(new Sound("ENEMY4", Sound.getURL("Goblin4.wav")));
            sounds.add(new Sound("ENEMY5", Sound.getURL("Goblin5.wav")));
            sounds.add(new Sound("LOSE", Sound.getURL("Lose.wav")));
            sounds.add(new Sound("SELECT", Sound.getURL("Select.wav")));
            sounds.add(new Sound("WIN", Sound.getURL("Win.wav")));*/
        }
      };
	  
	  /*
	  * Game constructor
	  * Sets up the canvas ready to be drawn to the frame
	  */
	  
    public Game() {
        Dimension size = new Dimension((width * scale), height * scale); //Create unscaled size
        setPreferredSize(size); //Set the size of the canvas to the unscaled size
        Game.game = this; //Initiate the game instance

        screen = new Screen(width, height); //Initiate a new screeen object ready for rendering.
        uiManager = new UIManager(); //Create the UI Manager for handling the UI
        UILayerStack.add(uiManager); //Add the UI Manager to the UI layer stack
        minimapManager = new UIManager(); //Instantiate the minimap Manager
        frame = new JFrame(); //Instantiate the main frame/window
        key = new Keyboard(); //Instantiate the keyboard variable
        init(STATE); //Initiate the current game state

        addKeyListener(key); //Add the keyboard as the frame's default keyboard listener

        Mouse mouse = new Mouse(this); //Create an instance of the Mouse class for handling mouse click events
        addMouseListener(mouse); //Set the default mouse listener for the frame to the mouse object
        addMouseMotionListener(mouse); // Set the default mouse movement listener to the mouse object
    }
	
	/*
	* Method used for initiating the main game frame, seetting up the close strategy and starting the game
	*/
	
    public static void startGame() {
        game = new Game(); //Create an instance of the class
        game.frame.setResizable(false); //Prevent the window from being allowed to be resized
        game.frame.setUndecorated(true); //Removes the bar around the edge of the window. Change to false to add this back
        game.frame.setLayout(new GridBagLayout()); //Create a layout pane for items to be positioned in the frame correctly
        GridBagConstraints gbc = new GridBagConstraints(); //Initiate a callable object of this layout
        gbc.anchor = GridBagConstraints.CENTER; //Center the objects we add past this point
        game.frame.add(game, gbc); //Add the game object (Canvas) to the layout manager, and thus the frame.

        game.frame.pack(); //Tell the frame we are done adding items
        game.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Tell the frame not to close if the user tries to exit
		
		/*
		* Sets up a custom way of exiting the game
		*/
		
        WindowListener exitListener = new WindowAdapter() { //Default event class that triggers when the window is tried to be closed
            @Override //Declare we are overriding a method in the WindowListener class
            public void windowClosing(WindowEvent e) { //If the window is trying to close
                State preState = STATE; //Store the game state
                if (player != null) STATE = State.PAUSE; //If the player exists, pause the game
                int confirm = JOptionPane.showOptionDialog(null, "Are You Sure to Close Application?", "Exit Confirmation",
                                                           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null); //Open a yes/no message bos, asking if the user wants to exit
                if (confirm == 0 && player != null) { //If they want to exit and the player exists (They are in game)...
                    int confirm2 = JOptionPane.showOptionDialog(null, "Save Progress?", "Save Confirmation", JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE, null, null, null); //Open a message box asking if they want to save their progress
                    if (confirm2 == 0) { //If they do...
                        FileHandler.save(player); //Save
                        System.exit(0); //Close the window
                    } else {
                        System.exit(0); //Otherwise just exit.
                    }
                } else if (confirm == 0) { //If they want to exit while not in game...
                    System.exit(0);  //Close the window
                } else { //If they say no...
                    STATE = preState; //Set the state back to the previous state
                    paused = false; //Unpause the game
                }
            }
        };
        game.frame.addWindowListener(exitListener); //Set the windows exit listener to our custom one
        game.frame.setLocationRelativeTo(null); //Position the frame in the center of the screen
        game.frame.setVisible(true); //Set the frame to be visible
        game.frame.requestFocus(); //Set the game to have input focus
        game.start(); //Start the game thread
        s.loopSound("INTRO"); //Start the instroduction music
        game.frame.requestFocus(); //Request focus to ensure we haven't lost it
        System.out.println("Press Enter to start..."); //Tell the user the game has started
    }
	
	/*
	* A method for creating a font object from a font file
	*/
	
    public static Font createFont() {
        Font font2 = null; //The font we are going to returnb
        InputStream is = game.getClass().getResourceAsStream("/fonts/BOOTERZO.ttf"); //Load the font from the file
        try { //Attempt to...
            font2 = Font.createFont(Font.TRUETYPE_FONT, is); //set the font to the one from the file
        } catch (FontFormatException e) { //If there is an error, do nothing
        } catch (IOException e) { //If there is an error, do nothing
        }
        return font2.deriveFont(30f); //return the new font with size 30
    }
	
	/*
	* Returns the instance of the output console
	*/
	
    public static Console getConsole() {
        return c;
    }
	
	/*
	* Create a message box, setting the title and message to the title and message passed into the parameters
	*/

    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	
	/*
	* Return the version value stored in Maven's pom.xml. This ensures the game name is savd witht the correct version in the name.
	*/
	
    public static String getVersion(Object classRes) {
        return classRes.getClass().getPackage().getImplementationVersion();
    }
	
	/*
	* Initiate the game state. This loads the necessary game objects depending on the state
	*/
	
    private void init(State state) {
        if (state == State.GAME) { //If the state is ingame...
            level = Level.createLevel(0); //Load the spawn level (level 0)
            TileCoordinate playerSpawn = new TileCoordinate(15, 60); //Create player coodinates. This class creates coordinates from pixels and converts it to tile coordinates (16 pixels -> 1 tile)
            String playerName = ""; //Initiate the players name
            if (FileHandler.fileExists(FileHandler.localUserFile))
                playerName = FileHandler.getPlayerName(); //If the player already exists, set the players name to the stored name
            else
                playerName = sc.getPlayerName(); //Otherwise retrieve the name from the start screen that the user has typed into the name box
            player = new Player(playerName, playerSpawn.x(), playerSpawn.y(), key); //Create a new player. Pass in the name, spawn location (in tile coordinates), and the keyboard class.
            changeLevel(Level.activeLevel); //Set the level to the current level (0, as we set that in the beginning)
            FileHandler.save(player); //Save everything
            initPause = false; //Set pause to false, just in case it is true
            c.clearTextArea(); //Clear the console
            System.out.println("Controls: \n W/up -> Player up \n A/left -> Player left \n S/down -> Player down \n D/right -> Player right \n "
                               + "Q -> Inventory \n Shift -> Sprint \n HOME -> home level \n M -> Map \n numbers 1-6 -> Weapons"); //Output the controls so the user can see what to do
        } else if (state == State.START) { //If the state is the start screen...
            sc = new StartScreen(); //Initiate the start screen
            uiManager.addPanel(sc); //Add the startscreen to the UI manager, which will render and update it
        } else if (state == State.PAUSE) { //If the state is in pause...
            pause = new Pause(); //Instantiate a new pause menu
            uiManager.addPanel(pause); //Add the pause menu to the UI manager, ready for rendering and updating
            initPause = true; //Tell the game we have created a pause menu (prevent more than 1 opening)
            paused = true; //Tell the game we are paused
        }
        STATE = State.WAITING; //Once the correct state is set up, set the state wo WAITING, which tells the game we are waiting for the state to change.
    }
	
	/*
	* Return the current JFrame
	*/
	
    public JFrame getWindow() {
        return frame;
    }
	
	/*
	* A method used for changing the game's level, and transfering the game objects (e.g. player) to the new level
	*/
	
    public void changeLevel(Level lev) {
        if (level != null) { //If the level exists...
            layerStack.remove(level); //remove it from the layer stack (stops it being rendered, updated, and recieving key events)
            level = lev; //Set level to the new level we wish to change to
            level.add(player); //Add the player to the new level
            level.setPlayerLocation(); //Set the location to the levels chosen location
            addLayer(level); //Add the new level into the layerstack for updating, rendering and receiving key events
        }
    }
	
	/*
	* Returns the current UI manager so other classes can add items to it
	*/
	
    public static UIManager getUIManager() {
        return uiManager;
    }
	
	/*
	*  //Return the current UI map manager
	*/
	
    public static UIManager getMapManager() {
        return minimapManager;
    }
	
	/*
	* //Return the value of the window Width, unscaled
	*/
	
    public static int getWindowWidth() {
        return (width * scale);
    }
	
	/*
	* Return the value of the window Height unscaled
	*/
	
    public static int getWindowHeight() {
        return (height * scale);
    }
	
	/*
	* Return the absolute window width
	*/
	
    public static int getAbsoluteWidth() {
        return (absoluteWidth * scale);
    }
	
	/*
	* Return the absolute window height
	*/
	
    public static int getAbsoluteHeight() {
        return (absoluteHeight * scale);
    }
	
	/*
	* Return the scale variable
	*/
	
    public static int getScale() {
        return scale;
    }
	
	/*
	* Return the non-sttic instance of game
	*/
	
    public synchronized static Game getGame() {
		// synchronized means that this method can be accessed by more than 1 thread at once. If one thread accesses the variable, he other threads have to wait for the first thread
		// to finish before it can access the value. Otherwise the value may be changed by one thread, then changed again by another, and the value isn't what was intended.
        return game;
    }
	
	/*
	* Returnt he current level
	*/
	
    public static Level getLevel() {
        return level;
    }
	
	/*
	* Returns the current keyboard controller
	*/
	
    public static Keyboard getKeyboard() {
        return key;
    }
	
	/*
	* Allows other classes to add layers into the stack
	*/
	
    public void addLayer(Layer layer) {
        layerStack.add(layer);
    }
	
	/*
	* Allows other classes to remove layers from the stack
	*/
	
    public void removeLayer(Layer layer) {
        layerStack.remove(layer);
    }
	
	/*
	* Allows other classes to add UI ayers to the UI stack
	*/
	
    public void addUILayer(UILayer layer) {
        UILayerStack.add(layer);
    }
	
	/*
	* Alows other classes to remove UI layers from the UI stack
	*/
	
    public void removeUILayer(UILayer layer) {
        UILayerStack.remove(layer);
    }
	
	/*
	* //Returns the image used for drawing the game
	*/
	
    public BufferedImage getImage() {
        return image;
    }
	
	/*
	* Scales the dimensions given to have the same ratio of the screen width to the screen height
	*/
	
    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
		//Set up all the necessary variables
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
	
	/*
	* //synchronized method to start the main thread
	*/
	
    public synchronized void start() { //Starts the Thread running
        running = true;
        thread = new Thread(this, "Display"); //Initialises the Thread
        thread.start(); //Runs the Thread=
    }
	
	/*
	* Synchronized method to stop the main thread
	*/
	
    public synchronized void stop() { //Stops the Thread
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace(); //Catches any errors if the Thread crashes
        }
    }
	
	/*
	* This method is defualt. It is overriden from the thread superclass, and is automatically called when the thread is started
	*/
	
	@Override
    public void run() {
        long lastTime = System.nanoTime(); //Get the current system time that the thread has been awake in nanoseconds
        long timer = System.currentTimeMillis();  //Get the current time the thread has been running in miliseconds
        final double ns = 1000000000.0 / 60.0; //Set up a frame-per-second lock at 60
        double delta = 0; //initiate delta
        int frames = 0; //initiate frames couner
        int updates = 0; //initiate updates counter
        requestFocus(); //request focus to the window to ensue we are calculating live variables
        while (running) { //If the thread is running...
            long now = System.nanoTime(); //Get the current time the thread has been running in nanoseconds
            delta += (now - lastTime) / ns; //Increase delta by the time the last frame took to draw/, and divide it by the fps lock
            lastTime = now; //Set the last frame time to the current frame time ready for the next frame time to be calculated
            render(); //Call the render method
            frames++; //Increase the frame counter
            while (delta >= 1) { //While delta is greater than or equal to 1 (The render will be called 60 times a second. Each render cycle the update method will be called every other second)
                update(); //Update the game
                updates++; //Increase the update counter
                delta--; //Decrease delta
            }

            if (System.currentTimeMillis() - timer > 1000) { //Updates the fps and ups counter every time the thread has run for 1000 miliseconds (1 second)
                timer += 1000;
                frame.setTitle(updates + "UPS, " + frames + "FPS");
                updates = 0;
                frames = 0;
            }
        }
        stop(); //If we are no longer running, stop the thread
    }
	
	/*
	* The event method for EventListener
	*/
	
    public void onEvent(Event event) {
		/* Loop through each layer in both stacks and pass the events to them. 
		*  Chech the Ui first as it is on top. If the UI returns false
		*  (it hasn't handled the event), pass it to the game layers.
		*/
        for (int i = UILayerStack.size() - 1; i >= 0; i--) {
            UILayerStack.get(i).onEvent(event);
        }
        for (int i = layerStack.size() - 1; i >= 0; i--) {
            layerStack.get(i).onEvent(event);
        }
    }
	
	/*
	* The main game update method. Handles updating all game items
	*/
	
    public void update() {
        time++; //Increase the time value by one updated 60 times per second, so if (time % 60) == 0, one second has passed)
        if (key.paused && !initPause)
            STATE = State.PAUSE; //If the pause key is being pressed and pause menue hasn't been made, set the game state to pause
        else if (key.paused && initPause) //If we press the pause key and we have created pause, return to the game state
            STATE = State.GAME;
        if (time % 1800 == 0 && player != null) // If 30 seconds has passed, and the player exists, then save the game
            FileHandler.save(player);
        init(STATE); //Call the initiate state method, in case it is changed during the game
        key.update(); //Update the key detector

        //Update layers
        for (int i = 0; i < UILayerStack.size(); i++) {
            UILayerStack.get(i).update();
        }
        for (int i = 0; i < layerStack.size(); i++) {
            layerStack.get(i).update();
        }
    }
	
	/*
	* Main game render method. Handles rendering of all game objects to the image
	*/
	
    public void render() {
        BufferStrategy bs = getBufferStrategy(); //Create a buffer strategy (a way of drawing pixels to the image)
        if (bs == null) { //If it is null...
            createBufferStrategy(3); //Create a buffer strategy. Set it up for 3D as it has a more recise pixel resolution and more control
            return; //Exit the method
        }

        screen.clear(); //Clear the screen every render, ready for re-drawing

        Graphics g = bs.getDrawGraphics(); //Get the buffer strategy's default graphics object, used for drawing
        this.g = g; //Set the global graphics variable to the one we just fetched
        if (STATE != State.WAITING) { //If the game state isn't waiting...
            List<UIPanel> panels = uiManager.getPanels(); //Get the list of panels that are currently being drawn
            for (int i = 0; i < panels.size(); i++) { //For each one...
                uiManager.removePanel(panels.get(i)); // remove it.
            }
        }
        init(STATE); //Call initiate state to set up the game for if the state has changed during rendering.
        if (player != null) { //If the player exists...
            double xScroll = player.getX() - screen.width / 2; //Set the x scroll variable of the map
            double yScroll = player.getY() - screen.height / 2; //Set the y scroll variable of the map
            level.setScroll((int)xScroll, (int)yScroll); //Tell the level these values
        }

        //Loop through the stacks, rendering each layer
        for (int i = 0; i < layerStack.size(); i++) {
            layerStack.get(i).render(screen);
        }
		//Set the pixels in this class to all the pixels stored in the screen class, ready for rendering to the BufferedImage
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }
		
        g.setColor(new Color(0xff00ff)); //Set the background to a bright pink, so we know if anything isn't rendering correctly
        g.fillRect(0, 0, getWidth(), getHeight()); //Draw the rectangle to fill thr screen
        /* scale the image's size to fill the window, scaling the objects inside to also fit. This ensures the game will scale depending on
		*  scree resolution
		*/
		int newWidth = 0, newHeight = 0;
        if (image.getWidth() / image.getHeight() < getAbsoluteWidth() / getAbsoluteHeight()) {
            newWidth = getAbsoluteWidth();
            newHeight = (int)Math.floor((double)image.getHeight() * (double)getAbsoluteWidth() / (double)image.getWidth());
        } else {
            newHeight = getAbsoluteHeight();
            newWidth = (int)Math.floor((double)image.getWidth() * (double)getAbsoluteHeight() / (double)image.getHeight());
        }

        g.drawImage(image, 0, 0, newWidth, newHeight, null); //Draw the image over the rectangle, filling the window

        //Loop through the stacks, rendering each layer. Do the UI as the last thing to render, as it is on top of everything else
        for (int i = 0; i < UILayerStack.size(); i++) {
            UILayerStack.get(i).render(g);
        }
        g.dispose(); //Dispose of the graphics object as we no longer need it
        bs.show(); //Show the buffer strategy in the canvas
    }
	
	/*
	* The main mehod, which is a default method that is called when the jar is first run.
	*/
	
    public static void main(String[] args) {
        if (!Installer.installed) { // If the game isn't installed, run the installer
            new Installer();
        } else { // Otherwise create the game
            startGame();
        }
    }
}