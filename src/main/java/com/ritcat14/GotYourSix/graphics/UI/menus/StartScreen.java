package com.ritcat14.GotYourSix.graphics.UI.menus;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.UI.*;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.FileHandler;
import com.ritcat14.GotYourSix.entity.mob.Player;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class StartScreen extends UIPanel implements KeyListener{
  
    private boolean menuActive = false;
    private boolean activateMenu = false;
    private boolean keyAdded = false;
    private boolean[] keys      = new boolean[1000];
    private List<UIComponent> menuItems = new ArrayList<UIComponent>();
  
    private UILabel options = new UILabel(new Vector2i((Game.getAbsoluteWidth() / 2) - 50, (Game.getAbsoluteHeight() - 200)), "START");
    private UIButton left = new UIButton(new Vector2i(Game.getAbsoluteWidth() / 12, Game.getAbsoluteHeight() / 2), ImageUtil.getImage("/ui/buttons/arrowLeft.png"), new UIActionListener() {
      public void perform() {
          if (state == playerViewState.MF) state = playerViewState.MI;
          else if (state == playerViewState.MI) state = playerViewState.FF;
          else if (state == playerViewState.FF) state = playerViewState.FI;
          else if (state == playerViewState.FI) state = playerViewState.MF;
      }
    }, "");
    private UIButton right = new UIButton(new Vector2i((Game.getAbsoluteWidth() / 12) + 450, Game.getAbsoluteHeight() / 2), ImageUtil.getImage("/ui/buttons/arrowRight.png"), new UIActionListener() {
        public void perform() {
          if (state == playerViewState.MF) state = playerViewState.FI;
          else if (state == playerViewState.FI) state = playerViewState.FF;
          else if (state == playerViewState.FF) state = playerViewState.MI;
          else if (state == playerViewState.MI) state = playerViewState.MF;
        }
    }, "");
    private UIButton start = new UIButton(new Vector2i(Game.getAbsoluteWidth() - 232, Game.getAbsoluteHeight() - 100), ImageUtil.getImage("/ui/buttons/startBtn.png"), new UIActionListener() {
        public void perform() {
            Game.STATE = Game.State.GAME;
        }
    }, "");
    private UIPanel playerView = new UIPanel(new Vector2i((Game.getAbsoluteWidth() / 12) + 115, (Game.getAbsoluteHeight() / 2) - 180), ImageUtil.getImage("/ui/panels/characterView.png"));
    private UIPanel character = new UIPanel(new Vector2i((Game.getAbsoluteWidth() / 12) + 164, (Game.getAbsoluteHeight() / 2) - 141), ImageUtil.getImage("/ui/panels/characters/MF.png"));
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

    public StartScreen() {
        super(new Vector2i(0, 0), new Vector2i(Game.getAbsoluteWidth(), Game.getAbsoluteHeight()), ImageUtil.getImage("/ui/panels/background.png"));
        addComponent(options);
        menuItems.add(left);
        menuItems.add(right);
        menuItems.add(start);
        menuItems.add(playerView);
        menuItems.add(character);
        menuItems.add(nameBox);
        /*menuItems.add(groupNameBox);
        menuItems.add(groupPassBox);*/
        List<UILabel> labels = new ArrayList<UILabel>();
        for (int i = 0; i < menuItems.size(); i++){
          if (menuItems.get(i) instanceof UITextBox){
            UITextBox x = (UITextBox) menuItems.get(i);
            if (x.getText() != ""){
              UILabel l = new UILabel(x.getAbsolutePosition().add(new Vector2i(-150, -1)), x.getText());
              labels.add(l);
            }
          }
        }
          for (int i = 0; i < labels.size(); i++){
            menuItems.add(labels.get(i));
        }
    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        if (keys[KeyEvent.VK_ENTER]){
          if (!menuActive) {
              activateMenu = true;
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
        options.setPosition(new Vector2i((Game.getAbsoluteWidth() / 2) - ((options.getFontMetrics().stringWidth(options.getText()))), (Game.getAbsoluteHeight() - 200)));
      }
    }  
}