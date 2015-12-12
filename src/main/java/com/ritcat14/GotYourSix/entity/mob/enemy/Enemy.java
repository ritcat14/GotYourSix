package com.ritcat14.GotYourSix.entity.mob.enemy;

import java.util.List;
import java.util.Random;

import com.ritcat14.GotYourSix.graphics.AnimatedObject;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.items.*;
import com.ritcat14.GotYourSix.items.armour.*;
import com.ritcat14.GotYourSix.level.tile.Tile;
import com.ritcat14.GotYourSix.level.TileCoordinate;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;

public abstract class Enemy extends Mob {

    public int        time       = 0;
    private Entity    rand       = null;
    protected boolean collidable = false;
    /* AnimSprite is used to change the players direction. While each of the directional animated objects
    update, animSprite is used as a blank animatedObject to allow the sprite image of the animated object to be set depending on the enemies 
    direction of movement.*/
    protected AnimatedObject down       = null;
    protected AnimatedObject up         = null;
    protected AnimatedObject left       = null;
    protected AnimatedObject right      = null;
    protected String         sheet      = "";
    protected double speed = 0;

    // Animated Objects for each direction of the enemies movement.
    protected AnimatedObject animSprite = null;
  
    public abstract void update();

    public abstract void render(Screen screen);

    public Item getItem() {
        Random ran = new Random();
        int i = ran.nextInt(3);
        switch(i){
            case 0:
                return new Water();
            case 1: return new Food();
        }
        if (this instanceof Boss){
            i = ran.nextInt(10);
            switch(i){
                case 0:
                    int j = ran.nextInt(4);
                    switch(j){
                        case 0:
                            return level.getClientPlayer().getArmour("Head");
                        case 1:
                            return level.getClientPlayer().getArmour("Chest");
                        case 2:
                            return level.getClientPlayer().getArmour("Legs");
                        default:
                            break;
                    }
                    break;
            }
        }
        //return appropriate item
        return new Water();
    }

    public void loseHealth(int damage, Player player) {
        Random ran = new Random();
        health -= damage;
        int chance = ran.nextInt(5);
        if (chance == 2 || chance == 3){
          speed -= 0.1;
        }
        if (speed <= 0) speed = 0.1;
        if (health <= 0) {
            if (this instanceof Boss) {
                Player.XPLevel += 1;
            }
            remove();
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

    public void initSheets() {
        SpriteSheet mainSheet = new SpriteSheet(sheet, 96, 128);
        down = new AnimatedObject(new SpriteSheet(mainSheet, 0, 0, 3, 1, 32), 32, 32, 3);
        up = new AnimatedObject(new SpriteSheet(mainSheet, 0, 3, 3, 1, 32), 32, 32, 3);
        left = new AnimatedObject(new SpriteSheet(mainSheet, 0, 1, 3, 1, 32), 32, 32, 3);
        right = new AnimatedObject(new SpriteSheet(mainSheet, 0, 2, 3, 1, 32), 32, 32, 3);
    }
}
