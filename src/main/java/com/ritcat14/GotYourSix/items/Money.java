package com.ritcat14.GotYourSix.items;

import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;

public class Money extends Item {
  
    public Money(){
        super("Money");
        sprite = new Sprite(8,4,0,SpriteSheet.items);
    }
  
}
