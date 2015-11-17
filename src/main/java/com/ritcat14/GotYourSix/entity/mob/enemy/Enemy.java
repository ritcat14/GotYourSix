package com.ritcat14.GotYourSix.entity.mob.enemy;

import java.util.List;
import java.util.Random;

import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.UI.menus.StartScreen;
import com.ritcat14.GotYourSix.items.*;
import com.ritcat14.GotYourSix.level.tile.Tile;
import com.ritcat14.GotYourSix.level.TileCoordinate;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.entity.mob.Player;

public abstract class Enemy extends Mob {

    public double     XPBonus    = 1;
    public int        time       = 0;
    private Entity    rand       = null;
    protected boolean collidable = false;

    public abstract void update();

    public abstract void render(Screen screen);

    public static Item getItem() {
        Random ran = new Random();
        int i = ran.nextInt(4);
        if (StartScreen.state == StartScreen.playerViewState.MF || StartScreen.state == StartScreen.playerViewState.FF) {
            if (i == 0) {
                CannonBall cb = new CannonBall();
                return cb;
            } else if (i == 1) {
                FireArrow fa = new FireArrow();
                return fa;
            } else if (i == 2) {
                FireCannon fc = new FireCannon();
                return fc;
            } else if (i == 3) {
                FireBall fb = new FireBall();
                return fb;
            } else if (i == 4) {
                FireWall fw = new FireWall();
                return fw;
            }
        } else if (StartScreen.state == StartScreen.playerViewState.MI || StartScreen.state == StartScreen.playerViewState.FI) {
            if (i == 0) {
                CannonBall cb = new CannonBall();
                return cb;
            } else if (i == 1) {
                IceArrow ia = new IceArrow();
                return ia;
            } else if (i == 2) {
                IceCannon ic = new IceCannon();
                return ic;
            } else if (i == 3) {
                IceBall ib = new IceBall();
                return ib;
            } else if (i == 4) {
                IceWall iw = new IceWall();
                return iw;
            }
        }
        CannonBall c = new CannonBall();
        return c;
    }

    public void loseHealth(int damage, Player player) {
        health -= damage;
        if (health <= 0) {
            remove();
            player.inXP((int)Math.ceil(XPBonus / (Player.getLevel() * 2)));
            Random ran = new Random();
            int j = ran.nextInt(3);
            for (int g = 0; g <= j; g++) {
                Item e = getItem();
                e.setPosition(new Vector2i((int)getX() + ((ran.nextInt(40) - 20)), (int)getY() + ((ran.nextInt(40) - 20))));
                level.add(e);
            }
        }
    }

    public void checkLocation() {
        TileCoordinate loc = new TileCoordinate((int)x,(int)y);
        Tile at = Game.getLevel().getTile(loc.x(), loc.y());
        if (at.solid()) {
            x = random.nextInt(3);
            y = random.nextInt(3);
        }
    }

    public int getHealth() {
        return health;
    }

    public void shootRandom() {
        List<Entity> entities = level.getEntities(this, 5 * 16);
        if (time % (60 + random.nextInt(60)) == 0) {
            entities.add(level.getClientPlayer());

            int index = random.nextInt(entities.size());

            rand = entities.get(index);

            if (rand != null) {
                double dx = rand.getX() - x;
                double dy = rand.getY() - y;
                double dir = Math.atan2(dy, dx);
                shoot(x, y, dir);
            }
        }
    }

    public void shootClosest() {
        List<Entity> entities = level.getEntities(this, 500);
        entities.add(level.getClientPlayer());
        double min = 0;
        Entity closest = null;
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (!(e instanceof Mob))
                continue;
            double distance = Vector2i.getDistance(new Vector2i((int)x, (int)y), new Vector2i((int)e.getX(), (int)e.getY()));
            if (i == 0 || distance < min) {
                min = distance;
                closest = e;
            }
        }
        if (closest != null) {
            double dx = closest.getX() - x;
            double dy = closest.getY() - y;
            double dir = Math.atan2(dy, dx);
            shoot(x, y, dir);
        }
    }
}
