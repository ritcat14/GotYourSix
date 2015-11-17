package com.ritcat14.GotYourSix.graphics.UI.menus;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.UI.*;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.FileHandler;
import com.ritcat14.GotYourSix.entity.mob.Player;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class StartScreen extends UIPanel implements KeyListener{
  
    private boolean menuActive = false;
    private boolean activateMenu = false;
    private boolean keyAdded = false;
    private boolean[] keys      = new boolean[1000];
    private List<UIComponent> menuItems = new ArrayList<UIComponent>();
  
    private UILabel options = new UILabel(new Vector2i((Game.getAbsoluteWidth() / 2) - 50, (Game.getAbsoluteHeight() - 200)), "START");
    private UIButton left = new UIButton(new Vector2i((Game.getAbsoluteWidth() / 12) - 50, (Game.getAbsoluteHeight() / 2) - 50), ImageUtil.getImage("/ui/buttons/arrowLeft.png"), new UIActionListener() {
      public void perform() {
          if (state == playerViewState.MF) state = playerViewState.MI;
          else if (state == playerViewState.MI) state = playerViewState.FF;
          else if (state == playerViewState.FF) state = playerViewState.FI;
          else if (state == playerViewState.FI) state = playerViewState.MF;
      }
    }, "");
  
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
            if (FileHandler.fileExists(FileHandler.netUserDir + getPlayerName() + ".txt")) {
              System.out.println("player exists");
              if (FileHandler.fileExists(FileHandler.netGroupDir + getGroupName() + ".txt")){
                System.out.println("group exists");
                if (FileHandler.playerInGroup(getGroupName(), getPlayerName()) && FileHandler.password(getGroupName(), getGroupPass())){
                  System.out.println("group & pass right");
                  Game.STATE = Game.State.GAME;
                } else {
                  System.out.println("Player not in group/password wrong" + FileHandler.playerInGroup(getGroupName(), getPlayerName()));
                }
              } else {
                System.out.println("Group doesnt exist");
                Game.infoBox("Player exists in another group. Please use correct player/group information","Error");
              }
            } else {
              int confirm =
                              JOptionPane.showOptionDialog(null, "Player not found in group " + "'" + getGroupName() + "'" + ". Add player?", "Player not in group",
                                                           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                  //yes
                  FileHandler.createPlayer(getPlayerName());
              if (FileHandler.fileExists(FileHandler.netGroupDir + getGroupName() + ".txt")){
                //check if player is in group & check password
                if (FileHandler.playerInGroup(getGroupName(), getPlayerName()) && FileHandler.password(getGroupName(), getGroupPass())){
                  Game.STATE = Game.State.GAME;
                } else if (!FileHandler.password(getGroupName(), getGroupPass())){
                  Game.infoBox("Password incorrect.","Error in pass");
                } else if (!FileHandler.playerInGroup(getGroupName(), getPlayerName())){
                  confirm =
                              JOptionPane.showOptionDialog(null, "Player not found in group " + "'" + getGroupName() + "'" + ". Add player?", "Player not in group",
                                                           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                  //yes
                  FileHandler.addPlayerToGroup(getGroupName(), getPlayerName());
                  Game.STATE = Game.State.GAME;
                }
                }
              } else {
                confirm =
                              JOptionPane.showOptionDialog(null, "Group doesn't exist. Create a new group?", "Group not found",
                                                           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                  //yes
                  FileHandler.createGroup(getGroupName(), getGroupPass());
                  FileHandler.addPlayerToGroup(getGroupName(), getPlayerName());
                  Game.STATE = Game.State.GAME;
                }
              }
                }
            }
        }
    }, "");
  
    private UIPanel character = new UIPanel(new Vector2i((Game.getAbsoluteWidth() / 12) + 125, (Game.getAbsoluteHeight() / 2) - 140), ImageUtil.getImage("/ui/panels/characters/MF.png"));
    public static enum playerViewState {
      MF, MI, FF, FI;
    }
    public static playerViewState state = playerViewState.MF;
    private enum optionState {
      START, MAINTENANCE;
    }
    private optionState opState = optionState.START;
    private boolean setupCreated = false;
    private UITextBox nameBox = new UITextBox(new Vector2i((Game.getAbsoluteWidth() / 12) + 115, ((Game.getAbsoluteHeight() / 2) - 180) + 406), "NAME: ");
    private UITextBox groupNameBox = new UITextBox(new Vector2i((Game.getAbsoluteWidth() / 2) + 200, ((Game.getAbsoluteHeight() / 2) - 150)), "GROUP: ");
    private UITextBox groupPassBox = new UITextBox(new Vector2i((Game.getAbsoluteWidth() / 2) + 200, ((Game.getAbsoluteHeight() / 2) - 80)), "PASS: ", "*");

    public StartScreen() {
        super(new Vector2i(0, 0), new Vector2i(Game.getAbsoluteWidth(), Game.getAbsoluteHeight()), ImageUtil.getImage("/ui/panels/background.png"));
        BufferedImage image = ImageUtil.getImage("/ui/panels/background.png");
        Dimension imgSize = new Dimension(image.getWidth(), image.getHeight());
		  Dimension boundary = new Dimension(Game.getAbsoluteWidth(), Game.getAbsoluteHeight());
        Dimension newDimension = Game.getScaledDimension(imgSize, boundary);
        setSize(new Vector2i(newDimension.width, newDimension.height));
        addComponent(options);
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

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        if (keys[KeyEvent.VK_ENTER]){
          if (!menuActive) {
              activateMenu = true;
              if (FileHandler.fileExists(FileHandler.localUserFile)) nameBox.setText(FileHandler.getPlayerName());
              removeComponent(options);
          }
        } else if (keys[KeyEvent.VK_LEFT]) {
            if (opState == optionState.START) opState = optionState.MAINTENANCE;
          else if (opState == optionState.MAINTENANCE) opState = optionState.START;
        } else if (keys[KeyEvent.VK_RIGHT]) {
            if (opState == optionState.MAINTENANCE) opState = optionState.START;
            else if (opState == optionState.START) opState = optionState.MAINTENANCE;
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
      super.update();
      if (!menuActive && activateMenu) {
        //initiate menu
        for (int i = 0; i < menuItems.size(); i++){
          addComponent(menuItems.get(i));
        }
        state = playerViewState.MF;
        SpriteSheet.init();
        menuActive = true;
        activateMenu = false;
      } else if (menuActive){
        //update menu
        String charName = state.toString();
        character.setBackgroundImage(ImageUtil.getImage("/ui/panels/characters/" + charName + ".png"));
      } else if (!menuActive){
        //update start
        if (opState == optionState.START) options.setText("START");
        else if (opState == optionState.MAINTENANCE) options.setText("MAINTENANCE");
        if (options.getFontMetrics() != null) options.setPosition(new Vector2i((Game.getAbsoluteWidth() / 2) - ((options.getFontMetrics().stringWidth(options.getText()))), (Game.getAbsoluteHeight() - 200)));
      }
    }
}