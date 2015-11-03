package com.ritcat14.GotYourSix.entity.spawner;

import com.ritcat14.GotYourSix.entity.particle.Particle;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.level.Level;

public class ParticleSpawner extends Spawner{
	
	private int life = 0;

	public ParticleSpawner(int x, int y, int life, int amount, Level level, Sprite sprite) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
      remove();
		for(int i = 0; i < amount; i ++){
			level.add(new Particle(x, y, life, sprite));
		}
	}
  
   

}
