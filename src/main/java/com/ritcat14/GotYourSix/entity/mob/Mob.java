package com.ritcat14.GotYourSix.entity.mob;


import java.util.ArrayList;
import java.util.List;

import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.entity.projectile.*;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnDoorTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnEdgeTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnPortalTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnWaterTile;

public abstract class Mob extends Entity {

    protected List<Projectile> avShots = new ArrayList<Projectile>();
    protected boolean moving  = false;
    protected boolean walking = false;

    protected int     health;

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
            if (avShots.get(Player.getWepInvent().getSelected() - 1) instanceof Arrow) {
					p = new Arrow(x, y, dir, this);
               level.add(p);
            } else if (avShots.get(Player.getWepInvent().getSelected() - 1) instanceof Cannon){
              p = new Cannon(x, y, dir, this);
              level.add(p);
            } else if (avShots.get(Player.getWepInvent().getSelected() - 1) instanceof  FirArrow){
              p = new FirArrow(x, y, dir, this);
              level.add(p);
            } else if (avShots.get(Player.getWepInvent().getSelected() - 1) instanceof FirCannon){
              p = new FirCannon(x, y, dir, this);
              level.add(p);
            } else if (avShots.get(Player.getWepInvent().getSelected() - 1) instanceof  FirBall){
              p = new FirBall(x, y, dir, this);
              level.add(p);
            } else if (avShots.get(Player.getWepInvent().getSelected() - 1) instanceof FirWall){
              p = new FirWall(x, y, dir, this);
              level.add(p);
            } else if (avShots.get(Player.getWepInvent().getSelected() - 1) instanceof IcArrow){
              p = new IcArrow(x, y, dir, this);
              level.add(p);
            } else if (avShots.get(Player.getWepInvent().getSelected() - 1) instanceof IcCannon){
              p = new IcCannon(x, y, dir, this);
              level.add(p);
            } else if (avShots.get(Player.getWepInvent().getSelected() - 1) instanceof IcBall){
              p = new IcBall(x, y, dir, this);
              level.add(p);
            } else if (avShots.get(Player.getWepInvent().getSelected() - 1) instanceof IcWall){
              p = new IcWall(x, y, dir, this);
              level.add(p);
            }
        }
        else{
          p = new Arrow(x, y, dir, this);
          level.add(p);
        }
    }

    protected boolean entityCollided(Entity e) {
        boolean collided = false;
        for (int c = 0; c < 4; c++) {
            double xt = ((x + e.getX()) - (c % 2) * 15) / 16;
            double yt = ((y + e.getY()) - (c / 2) * 15) / 16;
            int ix = (int)Math.ceil(xt);
            int iy = (int)Math.ceil(yt);
            int width = e.getSprite().getWidth();
            int height = e.getSprite().getHeight();
            if (ix >= x || ix <= x + width || iy >= y || iy <= y + height)
                collided = true;
        }
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
            if (level.getTile(ix, iy) instanceof SpawnDoorTile && Door.doorOpen == false)
                return false;
            else if (level.getTile(ix, iy) instanceof SpawnDoorTile && Door.doorOpen == true)
                return false;
            if (this instanceof Enemy && level.getTile(ix, iy) instanceof SpawnEdgeTile)
                return true;
            if (this instanceof Player) {
                if (level.getTile(ix, iy) instanceof SpawnWaterTile) {
                    Player.swimming = true;
                    Player.canShoot = false;
                } else {
                    Player.swimming = false;
                    Player.canShoot = true;
                }
                if (level.getTile(ix, iy) instanceof SpawnPortalTile) {
                    Player.changeLevel = true;
                    if (level == Level.test)
                        Player.levelToGo = Level.spawn;
                    else if (level == Level.spawn)
                        Player.levelToGo = Level.level1;
                } else {
                    Player.changeLevel = false;
                }
            }
            if (level.getTile(ix, iy).solid())
                solid = true;
        }
        return solid;
    }
}
