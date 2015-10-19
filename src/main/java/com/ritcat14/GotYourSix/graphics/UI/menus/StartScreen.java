package com.ritcat14.GotYourSix.graphics.UI.menus;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.UI.UIActionListener;
import com.ritcat14.GotYourSix.graphics.UI.UIButton;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.FileHandler;
import com.ritcat14.GotYourSix.entity.mob.Player;

public class StartScreen extends UIPanel {

    private UIButton startFireButton   = new UIButton(new Vector2i(0, 0), new Vector2i(70, 30), new UIActionListener() {
                                                          public void perform() {
                                                            Game.STATE = Game.State.GAME;
                                                            Player.type = Player.Type.FIRE;
                                                          }
                                                      }, "FIRE");
    private UIButton startIceButton    = new UIButton(new Vector2i((Game.getWindowWidth() + (60 * 5)) - 100, 0), new Vector2i(50, 30), new UIActionListener() {
                                                          public void perform() {
                                                            Game.STATE = Game.State.GAME;
                                                            Player.type = Player.Type.ICE;
                                                          }
                                                      }, "ICE");
    private UIButton maintenanceButton = new UIButton(new Vector2i(0, Game.getWindowHeight() - 30), new Vector2i(200, 30),
                                                      new UIActionListener() {
                                                          public void perform() {
                                                              Game.STATE = Game.State.MAINTENANCE;
                                                          }
                                                      }, "MAINTENANCE");
    private UserSetup setup;
    private boolean setupCreated = false;

    public StartScreen() {
        super(new Vector2i(0, 0), new Vector2i(Game.getWindowWidth() + (60 * 5), Game.getWindowHeight()), ImageUtil.getImage("/textures/sheets/buttons/background.png"));
        startFireButton.label.setColor(0XFFB44720);
        addComponent(startFireButton);
      
        startIceButton.label.setColor(0XFF2A7BCC);
        addComponent(startIceButton);
      
        maintenanceButton.label.setColor(0xff33CCCC);
        addComponent(maintenanceButton);
    }
  
    public void update(){
      super.update();
      if (FileHandler.UserExists() && setup != null){
        removeComponent(setup);
      }
      if (!FileHandler.UserExists() && !setupCreated){
        setup = new UserSetup();
        addComponent(setup);
        setupCreated = true;
      }
    }  
}