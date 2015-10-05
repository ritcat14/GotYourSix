package com.ritcat14.GotYourSix.entity;

import java.awt.Rectangle;
import java.util.Random;

import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.level.Level;

public class Entity{
	protected double x, y;
	protected Sprite sprite;
	private boolean removed = false;
	protected static Level level;
	protected final Random random = new Random();
	
	public Entity(){}
	
	public Entity(int x, int y, Sprite sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void update() {}
	
	public void render(Screen screen){
		if (sprite != null) screen.renderSprite((int)x, (int)y, sprite, true);
	}
	
	public void remove() {
		removed = true;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public boolean isRemoved() {
		return removed;
	}
  
    public Rectangle getBounds(){
      Rectangle r = new Rectangle((int)(x-16), (int)(y-16), sprite.getWidth(), sprite.getHeight());
      return r;
    }
	
	public void init(Level level){
		this.level = level;
	}
	
}