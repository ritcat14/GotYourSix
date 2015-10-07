package com.ritcat14.GotYourSix.items;

import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.util.Vector2i;

public class FireWall extends Weapon {
  
    public FireWall(){
        sprite = Sprite.fireWall;
    }
  
    public FireWall(Vector2i position) {
        super(position);
        sprite = Sprite.fireWall;
    }
}
