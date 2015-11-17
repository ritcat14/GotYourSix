package com.ritcat14.GotYourSix.items;

import java.awt.image.BufferedImage;

import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Item {
  
    public Sprite sprite = null;
    protected int amount = 0;
    public Vector2i position = null;
    private boolean removed = false;
    int life = 10;
  
    public Item(){
    }
  
    public Item(Vector2i position){
        this.position = position;
    }
  
   public Sprite getSprite(){
       return sprite;
   }
  
   public void setPosition(Vector2i position){
       this.position = position;
   }
  
   public void inLife(){
     life--;
     if (life <= 0){
       remove();
     }
   }
  
   public void render(Screen screen){
       screen.renderSprite(position.x, position.y, sprite, true);
   }
  
   public BufferedImage getImage(){ //converts sprite to image and returns it to the inventory for storing the item
       int[] pixels = new int[sprite.getWidth() * sprite.getHeight()];
       for (int x = 0; x < sprite.getWidth(); x++){
           for(int y = 0; y < sprite.getHeight(); y++){
               if (sprite.getPixels()[x + y * sprite.getWidth()] == 0xffff00ff) continue;
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
