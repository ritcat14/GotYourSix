package com.ritcat14.GotYourSix.graphics.UI.menus;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.UI.UIActionListener;
import com.ritcat14.GotYourSix.graphics.UI.UIButton;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.util.FileHandler;

public class Maintenance extends UIPanel {
  
    private UIButton         exitButton = new UIButton(new Vector2i(0, Game.getWindowHeight() - 30),
                                  new Vector2i(100, 30), new UIActionListener() {
                                      public void perform() {
                                        Game.STATE = Game.State.START;
                                      }
                                  }, "EXIT");
  
    private UIButton         saveButton = new UIButton(new Vector2i(0, Game.getWindowHeight() - 30).add(new Vector2i(300, 0)),
                                  new Vector2i(100, 30), new UIActionListener() {
                                      public void perform() {
                                        FileHandler.openFile("FANG", "FANG.txt");
                                      }
                                  }, "SAVE");
  
    public Maintenance(){
      super(new Vector2i(0, 0), new Vector2i(Game.getWindowWidth() + (60 * 5), Game.getWindowHeight()), 0xffC0C0C0);
      addComponent(saveButton);
      addComponent(exitButton);
    }

}
