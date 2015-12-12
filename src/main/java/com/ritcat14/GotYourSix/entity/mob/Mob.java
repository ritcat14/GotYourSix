package com.ritcat14.GotYourSix.entity.mob;


import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;

import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.entity.mob.enemy.*;
import com.ritcat14.GotYourSix.entity.projectile.*;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;
import com.ritcat14.GotYourSix.level.worlds.LavaLevel;
import com.ritcat14.GotYourSix.level.tile.SpawnEdgeTile;
import com.ritcat14.GotYourSix.level.tile.SpawnLavaTile;
import com.ritcat14.GotYourSix.level.tile.SpawnWaterTile;

public abstract class Mob extends Entity {

    public List<Projectile> avShots = new ArrayList<Projectile>();
    protected boolean moving  = false;
    protected boolean walking = false;

    public int     health = 0;

    protected enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    };

    protected Direction dir;

    protected void move(double xa, double ya) {
        if (xa != 0 && ya != 0) {
            move(xa, 0);
            move(0, ya);
            return;
        }
        if (xa > 0)
            dir = Direction.RIGHT;
        if (xa < 0)
            dir = Direction.LEFT;
        if (ya > 0)
            dir = Direction.DOWN;
        if (ya < 0)
            dir = Direction.UP;

        while (xa != 0) {
            if (Math.abs(xa) > 1) {
                if (!collision(abs(xa), ya)) {
                    this.x += abs(xa);
                }
                xa -= abs(xa);
            } else {
                if (!collision(abs(xa), ya)) {
                    this.x += xa;
                }
                xa = 0;
            }
        }
        while (ya != 0) {
            if (Math.abs(ya) > 1) {
                if (!collision(xa, abs(ya))) {
                    this.y += abs(ya);
                }
                ya -= abs(ya);
            } else {
                if (!collision(xa, abs(ya))) {
                    this.y += ya;
                }
                ya = 0;
            }
        }
    }

    private int abs(double value) {
        if (value < 0)
            return -1;
        else
            return 1;
    }

    public abstract void update();

    public abstract void render(Screen screen);

    protected void shoot(double x, double y, double dir) {
        Projectile p;
        if (this instanceof Player) {
            if (Player.getLevel() == 1){
                p = new Projectile(x,y,dir,new Sprite(16, 0,0,SpriteSheet.projectiles),this,1,90,1);
                level.add(p);
            } else if (Player.getLevel() == 2){
                p = new Projectile(x,y,dir,new Sprite(16,1,0,SpriteSheet.projectiles), this,2,60,20);
                level.add(p);
            } else if (Player.getLevel() == 3){
                p = new Projectile(x,y,dir,new Sprite(16,2,0,SpriteSheet.projectiles),this,3,70,15);
                level.add(p);
            } else if (Player.getLevel() == 4) {
                p = new Projectile(x,y,dir,new Sprite(16,0,1,SpriteSheet.projectiles),this,4,180,20);
                level.add(p);
            } else if (Player.getLevel() == 5) {
                p = new Projectile(x,y,dir,new Sprite(16,1,1,SpriteSheet.projectiles),this,5,120,25);
                level.add(p);
            } else if (Player.getLevel() == 6){
                p = new Projectile(x,y,dir,new Sprite(16,2,1,SpriteSheet.projectiles),this,6,140,30);
                level.add(p);
            }
        } else {
            p = new Projectile(x,y,dir,new Sprite(16, 0,0,SpriteSheet.projectiles),this,1,90,Player.getLevel());
            level.add(p);
        }
    }

    protected boolean entityCollided(Entity e) {
        boolean collided = false;
        /*for (int c = 0; c < 4; c++) {
            double xt = ((x + e.getX()) - (c % 2) * 15) / 16;
            double yt = ((y + e.getY()) - (c / 2) * 15) / 16;
            int ix = (int)Math.ceil(xt);
            int iy = (int)Math.ceil(yt);
            int width = e.getSprite().getWidth();
            int height = e.getSprite().getHeight();
            if (ix >= x || ix <= x + width || iy >= y || iy <= y + height)
                collided = true;
        }*/
        Rectangle r1 = new Rectangle((int)x - 16, (int)y, 32, 16);
        Rectangle r2 = new Rectangle((int)e.getX() - 16, (int)e.getY(), 32, 16);
        collided = (r1.intersects(r2));
        return collided;
    }

    protected boolean collision(double xa, double ya) {
        boolean solid = false;
        for (int c = 0; c < 4; c++) {
            double xt, yt;
            if (this instanceof Wizard) {
                xt = ((x + xa) - (c % 2) * 15) / 16;
                yt = ((y + ya) - (c / 2) * 15) / 16;
            } else {
                xt = (int)(((x + xa) + c % 2 * 14 - 7) / 16);
                yt = (int)(((y + ya) + c / 2 * 12 + 3) / 16);
            }
            int ix = (int)Math.ceil(xt);
            int iy = (int)Math.ceil(yt);
            if (c % 2 == 0)
                ix = (int)Math.floor(xt);
            if (c / 2 == 0)
                iy = (int)Math.floor(yt);
            if (this instanceof Enemy && level.getTile(ix, iy) instanceof SpawnEdgeTile && !(level instanceof LavaLevel))
                return true;
            /*if (this instanceof Enemy){
              int index = level.getEnemies().indexOf(this);
              for (int i = 0; i < level.getEnemies().size(); i++){
                if (entityCollided(level.getEnemies().get(i)) && i != index){
                    return true;
                }
              }
            }*/
            if (this instanceof Player) {
                if (level.getTile(ix, iy) instanceof SpawnWaterTile) {
                } else if (level.getTile(ix,iy) instanceof SpawnLavaTile){
                    level.getClientPlayer().setSpeed(1.0);
                    level.getClientPlayer().loseHealth(1);
                } else {
                  level.getClientPlayer().setSpeed(1.5);
                }
            }
            if (level.getTile(ix, iy).solid())
                solid = true;
        }
        return solid;
    }
}
