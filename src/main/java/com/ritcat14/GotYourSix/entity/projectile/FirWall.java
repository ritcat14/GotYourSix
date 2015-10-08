package com.ritcat14.GotYourSix.entity.projectile;

import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class FirWall extends Projectile {
  
    public FirWall(){}
  
    public FirWall(double x, double y, double dir, Mob mob) {
        super(x, y, dir, mob);
        FIRERATE = 5;
        range = 100;
        angle = dir;
        speed = 2;
        damage = 50;
        sprite = Sprite.rotate(Sprite.fireWall, angle);

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
        weapon = Weapon.FIREWALL;
    }
  
}
