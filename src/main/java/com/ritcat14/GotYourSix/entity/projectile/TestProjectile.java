package com.ritcat14.GotYourSix.entity.projectile;

import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.entity.spawner.ParticleSpawner;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class TestProjectile extends Projectile {

    public TestProjectile(double x, double y, double dir, Mob mob) {
        super(x, y, dir, mob);
        //range = random.nextInt(100);
        range = 130;
        angle = dir;
        speed = 3;
        damage = 1;
        sprite = Sprite.test;

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
    }

    public void update() {
        if (level.tileCollision((int)(x + nx), (int)(y + ny), 8, 4, 4)) {
            if (Player.getLevel() >= 25) {
                level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_blueFire));
            } else if (Player.getLevel() >= 20) {
                level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_fire));
            } else if (Player.getLevel() >= 15) {
                level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_fireCannon));
            } else if (Player.getLevel() >= 10) {
                level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_normal));
            } else if (Player.getLevel() >= 5) {
                level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_fireArrow));
            } else {
                level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_normal));
            }
            remove();
        }
        if (projectileCollision((int)(x + nx), (int)(y + ny))) {
            level.add(new ParticleSpawner((int)x, (int)y, 50, 6, level, Sprite.particle_blood));
            if (Player.getLevel() >= 25) {
                level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_blueFire));
            } else if (Player.getLevel() >= 20) {
                level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_fire));
            } else if (Player.getLevel() >= 15) {
                level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_fireCannon));
            } else if (Player.getLevel() >= 10) {
                level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_normal));
            } else if (Player.getLevel() >= 5) {
                level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_fireArrow));
            } else {
                level.add(new ParticleSpawner((int)x, (int)y, 100, 3, level, Sprite.particle_normal));
            }
            remove();
            collided = false;
        }
        damage = Player.getLevel();
        if (Player.getLevel() >= 25) {
            sprite = Sprite.rotate(Sprite.blueFireBall, angle);
        } else if (Player.getLevel() >= 20) {
            sprite = Sprite.rotate(Sprite.fireBall, angle);
        } else if (Player.getLevel() >= 15) {
            sprite = Sprite.rotate(Sprite.fireCannon, angle);
        } else if (Player.getLevel() >= 10) {
            sprite = Sprite.rotate(Sprite.test, angle);
        } else if (Player.getLevel() >= 5) {
            sprite = Sprite.rotate(Sprite.fireArrow, angle);
        } else {
            sprite = Sprite.rotate(Sprite.testArrow, angle);
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
