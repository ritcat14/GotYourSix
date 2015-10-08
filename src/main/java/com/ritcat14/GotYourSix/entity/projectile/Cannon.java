package com.ritcat14.GotYourSix.entity.projectile;

import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class Cannon extends Projectile {
  
    public Cannon(){
      
    }
  
    public Cannon(double x, double y, double dir, Mob mob) {
        super(x, y, dir, mob);
        FIRERATE = 50;
        range = 90;
        angle = dir;
        speed = 1;
        damage = 10;
        sprite = Sprite.rotate(Sprite.cannon, angle);

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
        weapon = Weapon.CANNON;
    }
}
