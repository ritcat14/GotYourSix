package com.ritcat14.GotYourSix.items;

import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.util.Vector2i;

public class IceArrow extends Weapon {
  
    public IceArrow(Vector2i position) {
        super(position);
        sprite = Sprite.icedArrow;
    }
}
