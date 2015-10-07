package com.ritcat14.GotYourSix.items;

import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.util.Vector2i;

public class IceWall extends Weapon {
  
    public IceWall(){
        sprite = Sprite.iceWall;
    }
  
    public IceWall(Vector2i position) {
        super(position);
        sprite = Sprite.iceWall;
    }
}
