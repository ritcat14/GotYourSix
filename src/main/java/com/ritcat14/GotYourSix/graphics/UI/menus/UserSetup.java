package com.ritcat14.GotYourSix.graphics.UI.menus;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.UI.UIActionListener;
import com.ritcat14.GotYourSix.graphics.UI.UIButton;
import com.ritcat14.GotYourSix.graphics.UI.UIDropDown;
import com.ritcat14.GotYourSix.graphics.UI.UILabel;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.graphics.UI.UITextBox;
import com.ritcat14.GotYourSix.util.FileHandler;
import com.ritcat14.GotYourSix.util.Vector2i;

public class UserSetup extends UIPanel {
    private static String name;
    private UITextBox nameBox = new UITextBox(new Vector2i(80,30));
    private UITextBox ageBox = new UITextBox(new Vector2i(80,70));
    private UIPanel panel = new UIPanel(new Vector2i((Game.getWindowWidth() + (60 * 5)) / 2 - 100, (Game.getWindowHeight() / 2) - 100), new Vector2i(200, 200), 0xffc0c0c0);
  
    private UIButton         saveButton = new UIButton(new Vector2i((Game.getWindowWidth() + (60 * 5)) / 2 - 100, ((Game.getWindowHeight() / 2) - 100) + 170),
                                  new Vector2i(100, 30), new UIActionListener() {
                                      public void perform() {
                                        //FileHandler.save();
                                      }
                                  }, "SAVE");
  
    private UIDropDown         selectGroup = new UIDropDown(new Vector2i((Game.getWindowWidth() + (60 * 5)) / 2 - 100, ((Game.getWindowHeight() / 2) - 100) + 170),
                                  new Vector2i(100, 30), new UIActionListener() {
                                      public void perform() {
                                        //FileHandler.save();
                                      }
                                  }, "SAVE");
  
  public UserSetup(){
    super(new Vector2i((Game.getWindowWidth() + (60 * 5)) / 2 - 100, (Game.getWindowHeight() / 2) - 100), new Vector2i(200, 200), 0xffc0c0c0);
    panel.addComponent(this);
    addComponent(saveButton);
    addComponent(nameBox);
    addComponent(ageBox);
    UILabel l1 = new UILabel(new Vector2i(0, 30), "NAME:");
    UILabel l2 = new UILabel(new Vector2i(0, 70), "AGE:");
    addComponent(l1);
    addComponent(l2);
  }
  
  public void update(){
    super.update();
    this.name = nameBox.text;
  }
  
  public static String getName(){
    return name;
  }
  
}