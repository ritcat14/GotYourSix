package com.ritcat14.GotYourSix.graphics.UI.menus;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.UI.*;
import com.ritcat14.GotYourSix.graphics.UI.menus.Options;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;
import com.ritcat14.GotYourSix.util.*;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.input.Mouse;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class StartScreen extends UIPanel implements KeyListener {
  
    private boolean menuActive = false, activateMenu = false, keyAdded = false, activateOps = false, opsActive = false, startActive = false, activeStart = true;
    private boolean[] keys      = new boolean[1000];
    private List<UIComponent> menuItems = new ArrayList<UIComponent>();
    
    //Start screen items
    private UILabel options = new UILabel(new Vector2i((Game.getAbsoluteWidth() / 2) - 50, (Game.getAbsoluteHeight() - 200)), "START");
    private UIPanel leftArrow = new UIPanel(new Vector2i((Game.getAbsoluteWidth() / 2) - 65, (Game.getAbsoluteHeight() - 215)), new Vector2i(15,15), ImageUtil.getImage("/ui/buttons/leftArrowStart.png"));
    private UIPanel rightArrow = new UIPanel(new Vector2i(((Game.getAbsoluteWidth() / 2) + 30), (Game.getAbsoluteHeight() - 215)), new Vector2i(15,15), ImageUtil.getImage("/ui/buttons/rightArrowStart.png"));
    private UIButton left = new UIButton(new Vector2i((Game.getAbsoluteWidth() / 12) - 50, (Game.getAbsoluteHeight() / 2) - 50), ImageUtil.getImage("/ui/buttons/arrowLeft.png"), new UIActionListener() {
      public void perform() {
          if (state == playerViewState.MF) state = playerViewState.MI;
          else if (state == playerViewState.MI) state = playerViewState.FF;
          else if (state == playerViewState.FF) state = playerViewState.FI;
          else if (state == playerViewState.FI) state = playerViewState.MF;
      }
    }, "");
    
    //Menu items
    private UIButton right = new UIButton(new Vector2i((Game.getAbsoluteWidth() / 12) + 350, (Game.getAbsoluteHeight() / 2) - 50), ImageUtil.getImage("/ui/buttons/arrowRight.png"), new UIActionListener() {
        public void perform() {
          if (state == playerViewState.MF) state = playerViewState.FI;
          else if (state == playerViewState.FI) state = playerViewState.FF;
          else if (state == playerViewState.FF) state = playerViewState.MI;
          else if (state == playerViewState.MI) state = playerViewState.MF;
        }
    }, "");
  
    private UIButton start = new UIButton(new Vector2i(Game.getAbsoluteWidth() - 240, Game.getAbsoluteHeight() - 100), ImageUtil.getImage("/ui/buttons/startBtn.png"), new UIActionListener() {
        public void perform() {
            boolean groupExists = FileHandler.fileExists(FileHandler.netGroupDir + getGroupName() + ".txt");
            boolean playerExists = FileHandler.fileExists(FileHandler.netUserDir + getPlayerName() + ".txt");
            boolean password = FileHandler.password(getGroupName(), getGroupPass());
            boolean playerInGroup = FileHandler.playerInGroup(getGroupName(), getPlayerName());
            if (FileHandler.fileExists(FileHandler.localUserFile) && !FileHandler.getPlayerName().equals(getPlayerName())){
                Game.infoBox("This is not the correct name. Please use the name you last created. Name: " + FileHandler.getPlayerName() + ".", "Wrong name");
            } else {
                if (playerExists){
                    if (groupExists){
                        if (password){
                            Game.STATE = Game.State.GAME; // start game
                        } else {
                            Game.infoBox("Incorrect Password. Please try again", "Password Error");
                        }
                    } else {
                        // Group not found. Player belongs to another group. Please use correct information
                        Game.infoBox("Player either to different group. Please use correct information", "Group not found");
                    }
                } else {    
                    int confirm = JOptionPane.showOptionDialog(null, "Create user?", "Create user",
                                                               JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);  // create?
                    if (confirm == 0){
                        if (groupExists){
                            if (password){
                                FileHandler.createPlayer(getPlayerName());
                                FileHandler.addPlayerToGroup(getGroupName(), getPlayerName()); // add player to group
                                Game.STATE = Game.State.GAME; // start game
                            } else {
                                Game.infoBox("Incorrect Password. Please try again", "Password Error");
                            }
                        } else {
                            // group not found. create group?
                            int confirm1 = JOptionPane.showOptionDialog(null, "Group not found. Create group?", "Create group",
                                                               JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                            if (confirm1 == 0){
                                // yes
                                FileHandler.createGroup(getGroupName(), getGroupPass());
                                FileHandler.createPlayer(getPlayerName());
                                FileHandler.addPlayerToGroup(getGroupName(), getPlayerName());
                                Game.STATE = Game.State.GAME; // Start the game
                            }
                        }
                    }
                }
            }
        }
    }, "");
  
    private Options o = null;
  
    private UIPanel character = new UIPanel(new Vector2i((Game.getAbsoluteWidth() / 12) + 125, (Game.getAbsoluteHeight() / 2) - 140), ImageUtil.getImage("/ui/panels/characters/MF.png"));
    public static enum playerViewState {
      MF, MI, FF, FI;
    }
    public static playerViewState state = playerViewState.MI;
    public static enum optionState {
      START, OPTIONS;
    }
    public static optionState opState = optionState.START;
    private boolean setupCreated = false;
    private UITextBox nameBox = new UITextBox(new Vector2i((Game.getAbsoluteWidth() / 12) + 115, ((Game.getAbsoluteHeight() / 2) - 180) + 406), "NAME: ");
    private UITextBox groupNameBox = new UITextBox(new Vector2i((Game.getAbsoluteWidth() / 2) + 200, ((Game.getAbsoluteHeight() / 2) - 150)), "GROUP: ");
    private UITextBox groupPassBox = new UITextBox(new Vector2i((Game.getAbsoluteWidth() / 2) + 200, ((Game.getAbsoluteHeight() / 2) - 80)), "PASS: ", "*");
    private MessageBox mb = null;
    private boolean messageBoxAdded = false;
  
    public StartScreen() {
        super(new Vector2i(0, 0), new Vector2i(Game.getAbsoluteWidth(), Game.getAbsoluteHeight()), ImageUtil.getImage("/ui/panels/background.png"));
        BufferedImage image = ImageUtil.getImage("/ui/panels/background.png");
        Dimension imgSize = new Dimension(image.getWidth(), image.getHeight());
		  Dimension boundary = new Dimension(Game.getAbsoluteWidth(), Game.getAbsoluteHeight());
        Dimension newDimension = Game.getScaledDimension(imgSize, boundary);
        setSize(new Vector2i(newDimension.width, newDimension.height));
        addComponent(options);
        addComponent(leftArrow);
        addComponent(rightArrow);
        menuItems.add(left);
        menuItems.add(right);
        menuItems.add(start);
        menuItems.add(character);
        menuItems.add(nameBox);
        menuItems.add(groupNameBox);
        menuItems.add(groupPassBox);
        List<UILabel> labels = new ArrayList<UILabel>();
        for (int i = 0; i < menuItems.size(); i++){
          if (menuItems.get(i) instanceof UIPanel){
              UIPanel currPanel = (UIPanel)menuItems.get(i);
              if (currPanel.getImage() != null){
                Dimension imageDim = new Dimension(currPanel.getImage().getWidth(), currPanel.getImage().getHeight());
                Dimension bound = new Dimension(Game.getAbsoluteWidth() / 2, Game.getAbsoluteHeight()/2);
                Dimension finalDim = Game.getScaledDimension(imageDim, bound);
                currPanel.setSize(new Vector2i(finalDim.width, finalDim.height));
              }
          }
          if (menuItems.get(i) instanceof UITextBox){
            UITextBox x = (UITextBox) menuItems.get(i);
            if (x.getName() != ""){
              UILabel l = new UILabel(x.getAbsolutePosition().add(new Vector2i(-150, -1)), x.getName());
              labels.add(l);
            }
          }
        }
          for (int i = 0; i < labels.size(); i++){
            menuItems.add(labels.get(i));
        }
    }
  
    public String getPlayerName(){
        return nameBox.text;
    }
  
    public String getGroupName(){
      return groupNameBox.text;
    }
  
    public String getGroupPass(){
      return groupPassBox.getTypedText();
    }
  
    public void goToStart(){
            menuActive = false;
            activateMenu = false;
            activateOps = false;
            opsActive = false;
            clear();
            addComponent(options);
            addComponent(leftArrow);
            addComponent(rightArrow);
            activeStart = false;
            startActive = true;
    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        if (keys[KeyEvent.VK_ENTER]){
            if (!menuActive && opState == optionState.START) {
                activateMenu = true;
                if (FileHandler.fileExists(FileHandler.localUserFile)){
                    nameBox.setText(FileHandler.getPlayerName());
                    state = playerViewState.valueOf(FileHandler.getStats().split("\n")[4]);
                }
            } else if (!opsActive && opState == optionState.OPTIONS){
                activateOps = true;
            }
            removeComponent(options);
        } else if (keys[KeyEvent.VK_LEFT]) {
            if (opState == optionState.START) opState = optionState.OPTIONS;
          else if (opState == optionState.OPTIONS) opState = optionState.START;
        } else if (keys[KeyEvent.VK_RIGHT]) {
            if (opState == optionState.OPTIONS) opState = optionState.START;
            else if (opState == optionState.START) opState = optionState.OPTIONS;
        }
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) {}
  
    public void update(){
        if (Game.getGame() != null && !keyAdded){
          Game.getGame().addKeyListener(this);
          keyAdded = true;
        }
      if (opState == optionState.START){
          if (!menuActive && activateMenu) {
              //initiate menu
              clear();
              for (int i = 0; i < menuItems.size(); i++){
                  addComponent(menuItems.get(i));
              }
              state = playerViewState.MF;
              SpriteSheet.init();
              menuActive = true;
              activateMenu = false;
              Game.getConsole().clearTextArea();
              System.out.println("About: ");
              System.out.println("Please fill out name and group. If you'd like to create a group, enter a name and password, or enter an existing one to join a group. Use the arrow buttons to select a character");
          } else if (menuActive){
              //update menu
              String charName = state.toString();
              character.setBackgroundImage(ImageUtil.getImage("/ui/panels/characters/" + charName + ".png"));
          }
      } else if (opState == optionState.OPTIONS){
          if (!opsActive && activateOps){
              clear();
              //initiate ops menu
              o = new Options(this);
              addComponent(o);
              System.out.println("Added option panel");
              opsActive = true;
              activateOps = false;
          } else if (opsActive){
              o.update();
          }
      }
      if (!menuActive && !opsActive){
          //update start
          if (opState == optionState.START) options.setText("START");
          else if (opState == optionState.OPTIONS) options.setText("OPTION");
          if (options.getFontMetrics() != null) options.setPosition(new Vector2i((Game.getAbsoluteWidth() / 2) - ((options.getFontMetrics().stringWidth(options.getText()))), (Game.getAbsoluteHeight() - 200)));
      }
      super.update();
    }
}