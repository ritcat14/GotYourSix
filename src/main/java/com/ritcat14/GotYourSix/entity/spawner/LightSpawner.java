package com.ritcat14.GotYourSix.entity.spawner;

import java.awt.Color;

import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.entity.particle.Particle;
import com.ritcat14.GotYourSix.level.Level;

public class LightSpawner extends Spawner {
  
    private int life = 0;
    private static Color c = new Color(255, 255, 255, 150);
    private static Sprite light = new Sprite(3, c);
  
    public LightSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
      remove();
		for(int i = 0; i < amount; i ++){
			level.add(new Particle(x, y, life, light));
		}
    }  
}
