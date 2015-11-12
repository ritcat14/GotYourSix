package com.ritcat14.GotYourSix.graphics.UI.menus;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.graphics.UI.UILabel;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class MessageBox extends UIPanel {
  
    public MessageBox(Vector2i position, String text){
      super(position, ImageUtil.getImage("/ui/panels/UI/messageBox.png"));
      UILabel label = new UILabel(position, text);
      addComponent(label);
    }
  
}
