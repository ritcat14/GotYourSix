package com.ritcat14.GotYourSix.graphics.UI.menus;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Pause extends UIPanel {
  
    public Pause(){
        super(new Vector2i((Game.getAbsoluteWidth()/2) - 50, (Game.getAbsoluteHeight()/2) - 100), ImageUtil.getImage("/ui/panels/pause/background.png"));
    }
  
}
