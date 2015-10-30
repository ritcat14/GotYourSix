package com.ritcat14.GotYourSix.entity.projectile;

import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class FirArrow extends Projectile {
  
    public FirArrow(){}
  
    public FirArrow(double x, double y, double dir, Mob mob) {
        super(x, y, dir, mob);
        FIRERATE = 5;
        range = 100;
        angle = dir;
        speed = 4;
        damage = 4;
        sprite = Sprite.rotate(Sprite.firedArrow, angle);

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
        weapon = Weapon.FIREDARROW;
    }
  
}