package com.ritcat14.GotYourSix.items;

import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.util.Vector2i;

public class IceBall extends Weapon {
  
    public IceBall(Vector2i position) {
        super(position);
        sprite = Sprite.iceBall;
    }
}
