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
        String type = "";
        for (int i = 0; i < amount; i++) {
            int xx = ran.nextInt(8) - 4;
            int yy = ran.nextInt(8) - 4;
            if (enemy == "Zombie") {
                int j = ran.nextInt(3);
                if (j == 0) type = "baby";
                else if (j == 1) type = "blue";
                else if (j == 2) type = "female";
                level.add(new Zombie(x + xx, y + yy, "/textures/sheets/mob/enemy/zombie/"+ type + enemy + ".png"));
            } else if (enemy == "Goblin"){
                int j = ran.nextInt(4);
                switch (j){
                  case 0: type = "hooded";
                  break;
                  case 1: type = "knight";
                  break;
                  case 2: type = "minion";
                  break;
                  case 3: type = "naked";
                  break;
                }
                level.add(new Zombie(x + xx, y + yy, "/textures/sheets/mob/enemy/goblin/"+ type + enemy + ".png"));
            } else if (enemy == "Mummy"){
                int j = ran.nextInt(2);
                switch (j){
                  case 0: type = "blind";
                  break;
                  case 1: type = "eye";
                  break;
                }
                level.add(new Zombie(x + xx, y + yy, "/textures/sheets/mob/enemy/mummy/"+ type + enemy + ".png"));
          } else if (enemy == "Person"){
                int j = ran.nextInt(4);
                switch (j){
                  case 0: type = "cat";
                  break;
                  case 1: type = "emo";
                  break;
                  case 2: type = "knight";
                  break;
                  case 3: type = "viking";
                  break;
                }
                level.add(new Zombie(x + xx, y + yy, "/textures/sheets/mob/enemy/goblin/"+ type + enemy + ".png"));
            }
      }
    }
}
