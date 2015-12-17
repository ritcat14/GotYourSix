package com.ritcat14.GotYourSix.items;

import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;

public class Food extends Item {
  
    public Food() {
        super("Food");
        sprite = new Sprite(16,1,0,SpriteSheet.items);
    }
  
}
