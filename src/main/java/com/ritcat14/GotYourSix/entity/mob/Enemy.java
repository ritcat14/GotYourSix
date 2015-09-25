package com.ritcat14.GotYourSix.entity.mob;

import java.util.List;

import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.entity.mob.Player;

public abstract class Enemy extends Mob {
  
   public double XPBonus = 1;
   public int time = 0;
   private Entity         rand       = null;
   protected boolean collidable = false;

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

    public void shootRandom() {
        List<Entity> entities = level.getEntities(this, 5 * 16);
        if (time % (60 + random.nextInt(60)) == 0) {
            entities.add(level.getClientPlayer());

            int index = random.nextInt(entities.size());

            rand = entities.get(index);

            if (rand != null) {
                double dx = rand.getX() - x;
                double dy = rand.getY() - y;
                double dir = Math.atan2(dy, dx);
                shoot(x, y, dir);
            }
        }
    }

    public void shootClosest() {
        List<Entity> entities = level.getEntities(this, 500);
        entities.add(level.getClientPlayer());
        double min = 0;
        Entity closest = null;
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (!(e instanceof Mob))
                continue;
            double distance = Vector2i.getDistance(new Vector2i((int)x, (int)y), new Vector2i((int)e.getX(), (int)e.getY()));
            if (i == 0 || distance < min) {
                min = distance;
                closest = e;
            }
        }
        if (closest != null) {
            double dx = closest.getX() - x;
            double dy = closest.getY() - y;
            double dir = Math.atan2(dy, dx);
            shoot(x, y, dir);
        }
    }
  
}
