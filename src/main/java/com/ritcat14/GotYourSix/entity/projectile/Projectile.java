package com.ritcat14.GotYourSix.entity.projectile;

import java.util.Random;

import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.graphics.Sprite;

public abstract class Projectile extends Entity {
	
	public static final int FIRERATE = 5; //Higher is slower
	
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nx, ny;
	protected double distance;
	protected int speed, range, damage;
	
	protected final Random random = new Random();
	
	public Projectile(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public int getSpriteSize(){
		return sprite.SIZE;
	}
	
	protected void move(){
	}

}
