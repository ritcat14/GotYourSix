package com.ritcat14.GotYourSix.items;

import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.util.Vector2i;

public class FireArrow extends Weapon {
  
    public FireArrow(Vector2i position) {
        super(position);
        sprite = Sprite.firedArrow;
    }
}
