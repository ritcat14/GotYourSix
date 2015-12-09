package com.ritcat14.GotYourSix.entity.portal;

import java.awt.Rectangle;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.level.Level;

public class Portal extends Entity {
  
    private Level currLevel = null;
    private boolean locked = false;
  
  
    public Portal(int x, int y, Level currLevel){
        this.x = x;
        this.y = y;
        this.currLevel = currLevel;
        sprite = Sprite.portal;
    }
  
    public void update(){
        if (isLocked()){
          sprite = Sprite.portalClosed;
        } else {
          sprite = Sprite.portal;
        }
        Rectangle bounds = new Rectangle((int)(x * 16), (int)(y * 16), sprite.getWidth(), sprite.getHeight());
        /*switch (Player.getLevel()) {
            case 1: Level1.unLock();
            break;
            case 2: Level2.unLock();
            break;
            case 3: Level3.unLock();
            break;
            case 4: Level4.unLock();
            break;
            case 5: Level5.unLock();
            break;
            case 6: Level6.unLock();
            break;
        }*/
        if (bounds.intersects(currLevel.getClientPlayer().getBounds()) && !isLocked()) {
          Game.getGame().changeLevel(Level.createLevel(Player.getLevel()));
        }
    }
  
    public void render(Screen screen){
        screen.renderSprite((int)(x * 16), (int)(y * 16), sprite, true);
    }
  
    public boolean isLocked(){
      return locked;
    }
  
    public void lock(){
      locked = true;
    }
  
    public void unLock(){
      locked = false;
    }
  
}
