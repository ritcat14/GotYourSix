package com.ritcat14.GotYourSix.entity.mob;

import java.util.List;

/*This class follows the A* search algorithm, 
 making it able to track the player across the map*/

import com.ritcat14.GotYourSix.graphics.AnimatedObject;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;
import com.ritcat14.GotYourSix.level.Node;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.Game;

public class Wizard extends Enemy {

    private int            time       = 0;
    private double         speed      = 1;
    private AnimatedObject down       = new AnimatedObject(SpriteSheet.wizard_down, 32, 32, 3);
    private AnimatedObject up         = new AnimatedObject(SpriteSheet.wizard_up, 32, 32, 3);
    private AnimatedObject left       = new AnimatedObject(SpriteSheet.wizard_left, 32, 32, 3);
    private AnimatedObject right      = new AnimatedObject(SpriteSheet.wizard_right, 32, 32, 3);

    private AnimatedObject animSprite = down;

    double                 xa         = 0, ya = 0;
    private List<Node>     path       = null;

    public Wizard(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        sprite = animSprite.getSprite();
        health = 20;
        XPBonus = 1;
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
        if (level.getTile((int)x, (int)y).solid()) {
            x += 16;
            y += 16;
        }
        screen.renderMob((int)(x - 16), (int)(y - 16), this);
    }

}
