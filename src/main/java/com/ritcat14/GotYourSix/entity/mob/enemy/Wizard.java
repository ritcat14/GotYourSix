package com.ritcat14.GotYourSix.entity.mob.enemy;

import java.util.List;
import java.util.Random;

/*This class follows the A* search algorithm, 
 making it able to track the player across the map*/

import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.AnimatedObject;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;
import com.ritcat14.GotYourSix.level.Node;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.Game;

public class Wizard extends Enemy {

    private int            time       = 0;
    protected double         speed      = 0.8;

    double                 xa         = 0, ya = 0;
    private List<Node>     path       = null;

    public Wizard(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        initSheets();
        animSprite = down;
        sprite = animSprite.getSprite();
        health = 20;
        Random r = new Random();
        speed = 0 + (0.8 - 0) * r.nextDouble();
        collidable = true;
    }

    private void move() {
        xa = 0;
        ya = 0;
        List<Player> players = level.getPlayers(this, 120);
        if (players.size() > 0) {
            int px = (int)level.getPlayerAt(0).getX();
            int py = (int)level.getPlayerAt(0).getY();
            Vector2i start = new Vector2i((int)getX() >> 4, (int)getY() >> 4);
            Vector2i destination = new Vector2i(px >> 4, py >> 4);
            if (time % 3 == 0)
                path = level.findPath(start, destination);
            if (path != null) {
                if (path.size() > 0) {
                    Vector2i vec = path.get(path.size() - 1).tile;
                    if (x < vec.getX() << 4)
                        xa += speed;
                    if (x > vec.getX() << 4)
                        xa -= speed;
                    if (y < vec.getY() << 4)
                        ya += speed;
                    if (y > vec.getY() << 4)
                        ya -= speed;
                }
            }
        }
        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else
            walking = false;
    }

    public void update() {
        if (Game.loaded) {
            time++;
            if (!collision(xa, ya)) {
                move();
            }
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
    }

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x - 16), (int)(y - 16), this);
    }

}
