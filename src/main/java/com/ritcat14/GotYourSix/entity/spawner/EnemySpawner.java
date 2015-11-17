package com.ritcat14.GotYourSix.entity.spawner;

import java.util.Random;

import com.ritcat14.GotYourSix.entity.mob.enemy.Enemy;
import com.ritcat14.GotYourSix.entity.mob.enemy.Zombie;
import com.ritcat14.GotYourSix.entity.mob.enemy.Wizard;
import com.ritcat14.GotYourSix.level.Level;

/* Categories of enemies include:
 @ Goblin
 @ Mummy
 @ Person
 @ Zombie
 */

public class EnemySpawner extends Spawner {

    private int    life = 0;
    private Random ran  = new Random();

    public EnemySpawner(int x, int y, int life, int amount, Level level, String enemy) {
        super(x, y, Type.MOB, amount, level);
        this.life = life;
        remove();
        for (int i = 0; i < amount; i++) {
            int xx = ran.nextInt(10) - 5;
            int yy = ran.nextInt(10) - 5;
            if (enemy == "Zombie") {
                int j = ran.nextInt(3);
                String type = "";
                if (j == 0) type = "baby";
                else if (j == 1) type = "blue";
                else if (j == 2) type = "female";
                level.add(new Zombie(x + xx, y + yy, "/textures/sheets/mob/enemy/zombie/"+ type + enemy + ".png"));
            }
        }
    }

}
