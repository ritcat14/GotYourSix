package com.ritcat14.GotYourSix.entity.projectile;

import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.entity.spawner.ParticleSpawner;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class TestProjectile extends Projectile {
  
    public static enum Weapon{
      ARROW,
      CANNON,
      ICEDARROW, FIREDARROW,
      ICEDCANNON, FIREDCANNON,
      ICEBALL, FIREBALL,
      ICEWALL, FIREWALL
    }
  
    public static Weapon weapon = Weapon.ARROW;

    public TestProjectile(double x, double y, double dir, Mob mob) {
        super(x, y, dir, mob);
        //range = random.nextInt(100);
        range = 130;
        angle = dir;
        speed = 3;
        damage = 1;
        sprite = Sprite.arrow;

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
    }

    public void update() {
        if (level.tileCollision((int)(x + nx), (int)(y + ny), 8, 4, 4)) {
            level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_normal));
            remove();
        }
        if (projectileCollision((int)(x + nx), (int)(y + ny))) {
            level.add(new ParticleSpawner((int)x, (int)y, 50, 6, level, Sprite.particle_blood));
            level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_normal));
            remove();
            collided = false;
        }
        damage = Player.getLevel();
        if (weapon == Weapon.ARROW) {
            sprite = Sprite.rotate(Sprite.arrow, angle);
        } else if (weapon == Weapon.ICEDARROW) {
            sprite = Sprite.rotate(Sprite.icedArrow, angle);
        } else if (weapon == Weapon.FIREDARROW) {
            sprite = Sprite.rotate(Sprite.firedArrow, angle);
        } else if (weapon == Weapon.CANNON) {
            sprite = Sprite.rotate(Sprite.cannon, angle);
        } else if (weapon == Weapon.ICEDCANNON) {
            sprite = Sprite.rotate(Sprite.icedCannon, angle);
        } else if (weapon == Weapon.FIREDCANNON) {
            sprite = Sprite.rotate(Sprite.firedCannon, angle);
        } else if (weapon == Weapon.ICEBALL) {
            sprite = Sprite.rotate(Sprite.iceBall, angle);
        } else if (weapon == Weapon.FIREBALL) {
            sprite = Sprite.rotate(Sprite.fireBall, angle);
        } else if (weapon == Weapon.ICEWALL) {
            sprite = Sprite.rotate(Sprite.iceWall, angle);
        } else if (weapon == Weapon.FIREWALL) {
            sprite = Sprite.rotate(Sprite.fireWall, angle);
        }
        move();
    }

    protected void move() {
        x += nx;
        y += ny;
        if (distance() > range)
            remove();
    }

    private double distance() {
        double dist = Math.sqrt(Math.abs(((xOrigin - x) * (xOrigin - x)) + ((yOrigin - y) * (yOrigin - y))));
        return dist;
    }

    public void render(Screen screen) {
        screen.renderProjectile((int)x - 7, (int)y - 2, this);
    }

}
