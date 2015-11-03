package com.ritcat14.GotYourSix.entity.projectile;

import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class Arrow extends Projectile {
  
    public Arrow(){}
  
    public Arrow(double x, double y, double dir, Mob mob) {
        super(x, y, dir, mob);
        FIRERATE = 10;
        range = 50;
        angle = dir;
        speed = 3;
        damage = 1;
        sprite = Sprite.rotate(Sprite.arrow, angle);

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
        Projectile.weapon = Weapon.ARROW;
    }
  
}
