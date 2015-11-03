package com.ritcat14.GotYourSix.entity.projectile;

import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class IcBall extends Projectile {
  
    public IcBall(){}
  
    public IcBall(double x, double y, double dir, Mob mob) {
        super(x, y, dir, mob);
        FIRERATE = 10;
        range = 80;
        angle = dir;
        speed = 3;
        damage = 35;
        sprite = Sprite.rotate(Sprite.iceBall, angle);

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
        Projectile.weapon = Weapon.ICEBALL;
    }
  
}
