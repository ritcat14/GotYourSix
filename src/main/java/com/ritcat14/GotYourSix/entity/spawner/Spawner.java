package com.ritcat14.GotYourSix.entity.spawner;

import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.level.Level;

public abstract class Spawner extends Entity{
	
	public enum Type {
		MOB, PARTICLE
	}
	
	protected Type type = null;
	
	public Spawner(int x, int y, Type type, int amount, Level level){
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
}
