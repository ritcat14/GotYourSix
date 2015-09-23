package com.ritcat14.GotYourSix.entity.mob;

import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.entity.mob.Player;

public abstract class Enemy extends Mob {
  
   public double XPBonus = 1;

	public abstract void update();

	public abstract void render(Screen screen);

    public void loseHealth(int damage, Player player) {
        health -= damage;
        if (health <= 0) {
            remove();
            player.inXP((int)Math.ceil(XPBonus / player.getLevel()));
        }
    }

    public int getHealth() {
        return health;
    }
  
}
