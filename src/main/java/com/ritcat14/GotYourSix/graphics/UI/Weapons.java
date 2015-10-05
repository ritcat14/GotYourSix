package com.ritcat14.GotYourSix.graphics.UI;

import java.util.ArrayList;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.items.FireBall;
import com.ritcat14.GotYourSix.items.Weapon;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Weapons extends Inventory {

    private int                 wepNum    = 1;
    private int                 fireBalls = 0;
    private ArrayList<FireBall> fireballs = new ArrayList<FireBall>();
    private Vector2i            position;

    public Weapons() {
        super(new Vector2i(Game.getWindowWidth() - 220, Game.getWindowHeight() - 100), "weaponBarBack.png");
        this.position = new Vector2i(Game.getWindowWidth() - 200, Game.getWindowHeight() - 20);
    }

    public void add(Weapon wep) {
        if (fireballs.size() == 0)
            wepNum++;
        if (wep instanceof FireBall)
            fireballs.add((FireBall)wep);
        fireBalls = fireballs.size();
        UIPanel itemButton = new UIPanel(position.add(new Vector2i(0, -30)), new Vector2i((16 * 5),(16 * 5)), wep.getImage());
        addComponent(itemButton);
    }
  //TODO: Add particle effect for wall projectile, fix xp bonus, and fix weapon changing/selecting in the weapon rack bar
}
