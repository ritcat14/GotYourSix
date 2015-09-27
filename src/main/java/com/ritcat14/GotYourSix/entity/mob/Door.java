package com.ritcat14.GotYourSix.entity.mob;

import java.awt.Point;
import java.awt.Rectangle;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.AnimatedObject;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;
import com.ritcat14.GotYourSix.input.Mouse;
import com.ritcat14.GotYourSix.util.Debug;

public class Door extends Mob {

    private AnimatedObject door       = new AnimatedObject(SpriteSheet.zombie_down, 32, 32, 3);

    public static boolean  doorOpen   = false;

    private AnimatedObject animSprite = door;

    double                 xa         = 0, ya = 0;

    public Door(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        sprite = animSprite.getSprite();
    }

    public void update() {
        double dx = Mouse.getX();
        double dy = Mouse.getY();
        Rectangle r = new Rectangle((int)(x - 16), (int)(y - 16), 16, 16);
        if (Mouse.getButton() == 1) {
            if (r.contains(new Point((int)dx, (int)dy))) {
                if (doorOpen) {
                    doorOpen = false;
                    animSprite.setFrame(0);
                    System.out.println("Door open");
                } else {
                    doorOpen = true;
                    animSprite = door;
                    System.out.println("Door closed");
                }
            }
        }
    }

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x - 16), (int)(y - 16), this);
        Debug.drawRect(screen, (int)(x - 16), (int)(y - 16), 16, 16, true);
    }

}
