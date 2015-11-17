package com.ritcat14.GotYourSix.graphics.UI.menus;

import java.awt.image.BufferedImage;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.graphics.UI.UIProgressBar;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.level.Level;

public class Stats extends UIPanel {
    
    private UIProgressBar health = null;
    private UIProgressBar food = null;
    private UIProgressBar stamina = null;
    private UIProgressBar water = null;
    
    public Stats(){
      super(new Vector2i(20,(Game.getAbsoluteHeight() - (ImageUtil.getImage("/ui/panels/stats/stats.png").getHeight()/2))), new Vector2i(ImageUtil.getImage("/ui/panels/stats/stats.png").getWidth() / 2, ImageUtil.getImage("/ui/panels/stats/stats.png").getHeight() / 2), ImageUtil.getImage("/ui/panels/stats/stats.png"));
      BufferedImage back = Level.resizeImage(ImageUtil.getImage("/ui/bars/barBack.png"), ImageUtil.getImage("/ui/bars/barBack.png").getWidth()/2, ImageUtil.getImage("/ui/bars/barBack.png").getHeight()/2);
      health = new UIProgressBar(new Vector2i(15,4), back, Level.resizeImage(ImageUtil.getImage("/ui/bars/healthFront.png"), ImageUtil.getImage("/ui/bars/healthFront.png").getWidth()/2, ImageUtil.getImage("/ui/bars/healthFront.png").getHeight()/2));
      food = new UIProgressBar(new Vector2i(15,19), back, Level.resizeImage(ImageUtil.getImage("/ui/bars/foodFront.png"), ImageUtil.getImage("/ui/bars/foodFront.png").getWidth()/2, ImageUtil.getImage("/ui/bars/foodFront.png").getHeight()/2));
      water = new UIProgressBar(new Vector2i(15,34), back, Level.resizeImage(ImageUtil.getImage("/ui/bars/waterFront.png"), ImageUtil.getImage("/ui/bars/waterFront.png").getWidth()/2, ImageUtil.getImage("/ui/bars/waterFront.png").getHeight()/2));
      stamina = new UIProgressBar(new Vector2i(15,49), back, Level.resizeImage(ImageUtil.getImage("/ui/bars/staminaFront.png"), ImageUtil.getImage("/ui/bars/staminaFront.png").getWidth()/2, ImageUtil.getImage("/ui/bars/staminaFront.png").getHeight()/2));
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
