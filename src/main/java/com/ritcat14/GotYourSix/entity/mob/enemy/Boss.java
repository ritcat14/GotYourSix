package com.ritcat14.GotYourSix.entity.mob.enemy;

import java.util.List;

import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.Screen;

public class Boss extends Enemy {

    private int            time       = 0;
    private double         speed      = 0.2;

    double                 xa         = 0, ya = 0;

    public Boss(int x, int y, int health, String sheet, String type) {
        this.type = type;
        this.sheet = sheet;
        this.x = x << 4;
        this.y = y << 4;
        this.health = health;
        initSheets();
        animSprite = down;
        sprite = animSprite.getSprite();
        collidable = true;
    }

    public void update() {
        time++;
        xa = 0;
        ya = 0;
        List<Player> players = level.getPlayers(this, 200);
            if (players.size() > 0) {
                Player player = players.get(0);
                if (x < player.getX())
                    xa += speed;
                if (x > player.getX())
                    xa -= speed;
                if (y < player.getY())
                    ya += speed;
                if (y > player.getY())
                    ya -= speed;
            } else {
                super.move(xa, ya);
            }
        if (!collision(xa, ya)) {
            if (xa != 0 || ya != 0) {
                move(xa, ya);
                walking = true;
            } else
                walking = false;
        } else {
            if (xa > 0) xa = -xa;
            else if (xa < 0) xa *= -1;
            if (ya > 0) ya = -ya;
            else if (ya < 0) ya *= -1;
            ya *= -1;
            move(xa, ya);
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
    }

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x - 16), (int)(y - 16), this);
    }
}
