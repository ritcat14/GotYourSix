package com.ritcat14.GotYourSix.entity.spawner;

import java.util.Random;

import com.ritcat14.GotYourSix.entity.mob.Enemy;
import com.ritcat14.GotYourSix.entity.mob.Zombie;
import com.ritcat14.GotYourSix.entity.mob.Wizard;
import com.ritcat14.GotYourSix.level.Level;

public class EnemySpawner extends Spawner {
  
   private int life;
   private Random ran = new Random();
  
    public EnemySpawner(int x, int y, int life, int amount, Level level, Enemy e){
        super(x, y, Type.MOB, amount, level);
        this.life = life;
        remove();
        for(int i = 0; i < amount; i ++){
            if (e instanceof Zombie) level.add(new Zombie(ran.nextInt(level.getWidth() - 4), ran.nextInt(level.getWidth() - 4)));
            if (e instanceof Wizard) level.add(new Wizard(ran.nextInt(level.getWidth() - 4), ran.nextInt(level.getWidth() - 4)));
		  }
    }
  
}
