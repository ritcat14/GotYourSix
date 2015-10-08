package com.ritcat14.GotYourSix.entity.projectile;

import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class FirCannon extends Projectile {
  
    public FirCannon(){}
  
    public FirCannon(double x, double y, double dir, Mob mob) {
        super(x, y, dir, mob);
        FIRERATE = 25;
        range = 130;
        angle = dir;
        speed = 2;
        damage = 20;
        sprite = Sprite.rotate(Sprite.firedCannon, angle);

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
        weapon = Weapon.FIREDCANNON;
    }
  
}
