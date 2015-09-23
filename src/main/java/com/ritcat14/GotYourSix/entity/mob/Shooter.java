package com.ritcat14.GotYourSix.entity.mob;

import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.graphics.AnimatedObject;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.util.Debug;
import java.util.List;

public class Shooter extends Enemy {
    private AnimatedObject down       = new AnimatedObject(SpriteSheet.dummy_down, 32, 32, 3);
    private AnimatedObject up         = new AnimatedObject(SpriteSheet.dummy_up, 32, 32, 3);
    private AnimatedObject left       = new AnimatedObject(SpriteSheet.dummy_left, 32, 32, 3);
    private AnimatedObject right      = new AnimatedObject(SpriteSheet.dummy_right, 32, 32, 3);
    private Entity         rand       = null;

    private AnimatedObject animSprite = down;

    private int            time       = 0;
    int                    xa         = 0, ya = 0;

    public Shooter(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        sprite = down.getSprite();
        health = 10;
        XPBonus = 1;
    }

    public void update() {
        if (walking)
            animSprite.update();
        else
            animSprite.setFrame(0);
        time++;
        if (time % (random.nextInt(50) + 30) == 0) {
            xa = random.nextInt(3) - 1;
            ya = random.nextInt(3) - 1;
            if (random.nextInt(4) == 0) {
                xa = 0;
                ya = 0;
            }
        }
        if (walking)
            animSprite.update();
        else
            animSprite.setFrameRate(0);
        if (ya < 0) {
            animSprite = up;
            dir = Direction.UP;
        } else if (ya > 0) {
            animSprite = down;
            dir = Direction.DOWN;
        }
        if (xa < 0) {
            animSprite = left;
            dir = Direction.LEFT;
        } else if (xa > 0) {
            animSprite = right;
            dir = Direction.RIGHT;
        }
        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking = false;
        }
        shootRandom();
    }

    private void shootRandom() {
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

    private void shootClosest() {
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

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x - 16), (int)(y - 16), this);
    }
}
