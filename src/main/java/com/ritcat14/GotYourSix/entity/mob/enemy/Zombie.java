package com.ritcat14.GotYourSix.entity.mob.enemy;

import java.util.List;

import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.AnimatedObject;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;
import com.ritcat14.GotYourSix.level.tile.Tile;
import com.ritcat14.GotYourSix.util.Debug;

public class Zombie extends Enemy {

    private int            time       = 0;
    private double         speed      = 0.2;
    private AnimatedObject down       = null;
    private AnimatedObject up         = null;
    private AnimatedObject left       = null;
    private AnimatedObject right      = null;
    private String         sheet      = "";

    private AnimatedObject animSprite = null;

    double                 xa         = 0, ya = 0;

    public Zombie(int x, int y, String sheet) {
        this.sheet = sheet;
        this.x = x << 4;
        this.y = y << 4;
        initSheets();
        animSprite = down;
        sprite = animSprite.getSprite();
        health = 10;
        XPBonus = 20;
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

    private void initSheets() {
        SpriteSheet zombie = new SpriteSheet(sheet, 96, 128);
        down = new AnimatedObject(new SpriteSheet(zombie, 0, 0, 3, 1, 32), 32, 32, 3);
        up = new AnimatedObject(new SpriteSheet(zombie, 0, 3, 3, 1, 32), 32, 32, 3);
        left = new AnimatedObject(new SpriteSheet(zombie, 0, 1, 3, 1, 32), 32, 32, 3);
        right = new AnimatedObject(new SpriteSheet(zombie, 0, 2, 3, 1, 32), 32, 32, 3);
    }

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x - 16), (int)(y - 16), this);
        Debug.drawRect(screen, ((int)x) - 16, (int)y, 32, 16, true);
    }

}
