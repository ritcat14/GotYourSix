package com.ritcat14.GotYourSix.entity.projectile;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.entity.mob.Enemy;
import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.entity.spawner.ParticleSpawner;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;

public abstract class Projectile extends Entity {
  
    public static enum Weapon{
      ARROW,
      CANNON,
      ICEDARROW, FIREDARROW,
      ICEDCANNON, FIREDCANNON,
      ICEBALL, FIREBALL,
      ICEWALL, FIREWALL
    }
  
    public static Weapon weapon = Weapon.ARROW;

    public static int FIRERATE;           //Higher is slower

    protected final double  xOrigin, yOrigin;
    protected double        angle;
    protected Sprite        sprite;
    protected double        x, y;
    protected double        nx, ny;
    protected double        distance;
    protected int           speed, range, damage;
    private Mob             mob;
    protected boolean collided = false;

    protected final Random  random   = new Random();
  
    public Projectile(){
      xOrigin = 0;
      yOrigin = 0;
    }

    public Projectile(double x, double y, double dir, Mob m) {
        xOrigin = x;
        yOrigin = y;
        angle = dir;
        this.x = x;
        this.y = y;
        this.mob = m;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getSpriteSize() {
        return sprite.SIZE;
    }

    public Entity getOrigin() {
        return mob;
    }

    protected void move(double nx, double ny, int range) {
        x += nx;
        y += ny;
        if (distance() > range)
            remove();
    }
  
    public void update(){
        super.update();
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
        move(nx, ny, range);
    }

    protected double distance() {
        double dist = Math.sqrt(Math.abs(((xOrigin - x) * (xOrigin - x)) + ((yOrigin - y) * (yOrigin - y))));
        return dist;
    }

    public boolean projectileCollision(int x, int y) {
        Point ploc = new Point(x, y);
        List<Enemy> enemies = level.getEnemies();
        List<Player> players = level.getPlayers();
        for (int j = 0; j < players.size(); j++) {
            for (int i = 0; i < enemies.size(); i++) {
                Rectangle en = new Rectangle((int)(enemies.get(i).getX()) - 10, (int)(enemies.get(i).getY()) - 16, enemies.get(i).getSprite().getWidth() - 12, enemies.get(i).getSprite().getHeight());
                Rectangle pl = new Rectangle((int)(players.get(j).getX()) - 10, (int)(players.get(j).getY()) - 16, players.get(j).getSprite().getWidth() - 12, players.get(j).getSprite().getHeight());
                if (mob.equals(players.get(j))) {
                    if (en.contains(ploc)) {
                        enemies.get(i).loseHealth(damage, players.get(j));
                        collided = true;
                    }
                } else if (mob.equals(enemies.get(i))){
                    if (pl.contains(ploc)) {
                        players.get(j).loseHealth(damage);
                    }
                }
            }
        }
      return collided;
    }

    public void render(Screen screen) {
        screen.renderProjectile((int)x - 7, (int)y - 2, this);
    }
}
