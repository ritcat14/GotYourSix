package com.ritcat14.GotYourSix.entity.mob.enemy;

import java.util.List;

import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.AnimatedObject;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;
import com.ritcat14.GotYourSix.level.tile.Tile;

public class Chaser extends Enemy { // Extends enemy class

    private int            time       = 0; // Used to check how much time has passed. If (time % 60 == ) -> one second has passed

    int                    xa         = 0, ya = 0; // X and Y values to ba added to the X and Y coordinate of the sprites location

    public Chaser(int x, int y, String type) {
        this.type = type;
        this.x = x << 4; // mulitplies the value of X by 4^2 (4x4) by shifting the bits to the left 4 spaces.
        this.y = y << 4;
        initSheets();
        animSprite = down;
        sprite = animSprite.getSprite(); // Sets the sprite of the enemy to the current directions sprite
        health = 10; // Sets the health value to 10
        collidable = true; // Tells the collision detection that the enemy is collibale so that it does damage to the plaer on collision
    }

    private void move() {
        // Moves the player, but doesn't override the main move method in the base enemy class.
        List<Player> players = level.getPlayers(this, 130); /* Returns a list of players that are in the predetermined range of the enemy. 
        The range is determined by the 130 value. So any players in a range of 130 pixels of the enemy will be returned */
        if (players.size() > 0) { // If there is a player in the range, move towards it.
            Player player = players.get(0);
            if (x < player.getX())
                xa++;
            if (x > player.getX())
                xa--;
            if (y < player.getY())
                ya++;
            if (y > player.getY())
                ya--;
        } else {
          super.move(xa, ya); // If there is no player, call the default move method.
        }

        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else
            walking = false;
    }

    public void update() {
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

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x - 16), (int)(y - 16), this);
    }

}
