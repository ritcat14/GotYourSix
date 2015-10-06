package com.ritcat14.GotYourSix.items;

import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.util.Vector2i;

public class FireCannon extends Weapon {
  
    public FireCannon(Vector2i position) {
        super(position);
        sprite = Sprite.firedCannon;
    }
}
