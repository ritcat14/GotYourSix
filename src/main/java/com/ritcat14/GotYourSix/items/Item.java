package com.ritcat14.GotYourSix.items;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Item {
  
    protected Sprite sprite;
    protected int amount;
    private Vector2i position;
    private boolean removed = false;
    private Level level;
  
    public Item(Vector2i position){
        this.position = position;
        this.level = Game.getLevel();
    }
  
   public Sprite getSprite(){
       return sprite;
   }
  
   public void render(Screen screen){
       screen.renderSprite(position.x, position.y, sprite, true);
   }
  
   public void update(){
       collision();
   }
  
   private void collision(){
       List<Player> players = level.getPlayers();
       for (int i = 0; i < players.size(); i++){
           Rectangle r = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
           if (r.x >= players.get(i).getBounds().x && r.x <= players.get(i).getBounds().x + players.get(i).getBounds().width
               && r.y >= players.get(i).getBounds().y && r.y <= players.get(i).getBounds().y + players.get(i).getBounds().height){
               if (this instanceof Weapon) players.get(i).getWepInvent().add((Weapon)this);
               else players.get(i).getInvent().add(this);
               remove();
           }
       }
   }
  
   public BufferedImage getImage(){ //converts sprite to image and returns it to the inventory for storing the item
       int[] pixels = new int[sprite.getWidth() * sprite.getHeight()];
       for (int x = 0; x < sprite.getWidth(); x++){
           for(int y = 0; y < sprite.getHeight(); y++){
               if (sprite.getPixels()[x + y * sprite.getWidth()] == 0xff00ff) continue;
               pixels[x + y * sprite.getWidth()] = sprite.getPixels()[x + y * sprite.getWidth()];
           }
       }
       BufferedImage image = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_RGB);
       image.setRGB(0, 0, sprite.getWidth(), sprite.getHeight(), pixels, 0, sprite.getWidth());
       return image;
   }
  
    public void remove(){
        removed = true;
    }
  
    public boolean isRemoved(){
        return removed;
    }
  
}
