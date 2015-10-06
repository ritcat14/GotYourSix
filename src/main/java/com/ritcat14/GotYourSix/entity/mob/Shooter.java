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

    private AnimatedObject animSprite = down;
  
    int                    xa         = 0, ya = 0;

    public Shooter(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        sprite = down.getSprite();
        health = 10;
        XPBonus = health;
        collidable = true;
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

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x - 16), (int)(y - 16), this);
    }
}
