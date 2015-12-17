package com.ritcat14.GotYourSix.entity.mob.enemy;

import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.AnimatedObject;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;

public class SoulEater extends Enemy {
  
    private int            time       = 0;
    int                    xa         = 0, ya = 0;

    public SoulEater(int x, int y, String type) {
        this.type = type;
        this.x = x << 4;
        this.y = y << 4;
        initSheets();
        animSprite = down;
        sprite = animSprite.getSprite();
        health = 15;
        collidable = false;
    }

    private void move() {
        xa = 0;
        ya = 0;
        Player player = level.getClientPlayer();
        if (x < player.getX())
            xa++;
        if (x > player.getX())
            xa--;
        if (y < player.getY())
            ya++;
        if (y > player.getY())
            ya--;

        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else
            walking = false;
    }

    public void update() {
        move();
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
    }

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x - 16), (int)(y - 16), this);
    }

}
