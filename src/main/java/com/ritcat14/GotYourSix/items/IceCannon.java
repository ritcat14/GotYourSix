package com.ritcat14.GotYourSix.items;

import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.util.Vector2i;

public class IceCannon extends Weapon {
  
    public IceCannon(){
        sprite = Sprite.icedCannon;
    }
  
    public IceCannon(Vector2i position) {
        super(position);
        sprite = Sprite.icedCannon;
    }
}
