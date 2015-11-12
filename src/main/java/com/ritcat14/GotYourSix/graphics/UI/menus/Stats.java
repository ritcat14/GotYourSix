package com.ritcat14.GotYourSix.graphics.UI.menus;

import java.awt.image.BufferedImage;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.graphics.UI.UIProgressBar;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Stats extends UIPanel {
  
    private UIProgressBar health = null;
    private UIProgressBar food = null;
    private UIProgressBar stamina = null;
    private UIProgressBar water = null;
  
    public Stats(){
      super(new Vector2i(20, (Game.getAbsoluteHeight() / 8) * 7), ImageUtil.getImage("/ui/panels/stats/stats.png"));
      BufferedImage back = ImageUtil.getImage("/ui/bars/barBack.png");
      health = new UIProgressBar(new Vector2i(30,8), back, ImageUtil.getImage("/ui/bars/healthFront.png"));
      food = new UIProgressBar(new Vector2i(30,38), back, ImageUtil.getImage("/ui/bars/foodFront.png"));
      water = new UIProgressBar(new Vector2i(30,68), back, ImageUtil.getImage("/ui/bars/waterFront.png"));
      stamina = new UIProgressBar(new Vector2i(30,98), back, ImageUtil.getImage("/ui/bars/staminaFront.png"));
      addComponent(health);
      addComponent(food);
      addComponent(water);
      addComponent(stamina);
    }
  
    public void update(){
      super.update();
      health.setProgress(Game.getLevel().getClientPlayer().getHealth() / 100.0);
      food.setProgress(Game.getLevel().getClientPlayer().getHunger() / 100.0);
      water.setProgress(Game.getLevel().getClientPlayer().getThirst() / 100.0);
      stamina.setProgress(Game.getLevel().getClientPlayer().getStamina() / 100.0);
    }
  
}
