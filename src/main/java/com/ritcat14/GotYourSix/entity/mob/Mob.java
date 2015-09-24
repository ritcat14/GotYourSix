package com.ritcat14.GotYourSix.entity.mob;

import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.entity.projectile.Projectile;
import com.ritcat14.GotYourSix.entity.projectile.TestProjectile;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnEdgeTile;

public abstract class Mob extends Entity {

	protected boolean moving = false;
	protected boolean walking = false;
  
   protected int health;

	/*
	 * protected enum Type { DUMMY, PLAYER, SOULEATER, ZOMBIE }
	 * 
	 * protected Type type;
	 */

	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	};

	protected Direction dir;

	protected void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		if (xa > 0) dir = Direction.RIGHT;
		if (xa < 0) dir = Direction.LEFT;
		if (ya > 0) dir = Direction.DOWN;
		if (ya < 0) dir = Direction.UP;
		
		while(xa != 0){
			if (Math.abs(xa) > 1) {
				if (!collision(abs(xa), ya)) {
					this.x += abs(xa);
				}
				xa -= abs(xa);
			}else{
				if (!collision(abs(xa), ya)) {
					this.x += xa;
				}
				xa = 0;
			}
		}
		while(ya != 0){
			if (Math.abs(ya) > 1) {
				if (!collision(xa, abs(ya))) {
					this.y += abs(ya);
				}
				ya -= abs(ya);
			}else{
				if (!collision(xa, abs(ya))) {
					this.y += ya;
				}
				ya = 0;
			}
		}
	}

	private int abs(double value) {
		if (value < 0) return -1;
		else
			return 1;
	}

	public abstract void update();

	public abstract void render(Screen screen);

	protected void shoot(double x, double y, double dir) {
		Projectile p = new TestProjectile(x, y, dir, this);
		level.add(p);
	}
  
   protected boolean entityCollided(Entity e){
       boolean collided = false;
       for (int c = 0; c < 4; c++){
           double xt = ((x + e.getX() ) - (c % 2) * 15) / 16;
           double yt = ((y + e.getY() ) - (c / 2) * 15) / 16;
           int ix = (int) Math.ceil(xt);
           int iy = (int) Math.ceil(yt);
           int width = e.getSprite().getWidth();
           int height = e.getSprite().getHeight();
           if (ix >= x || ix <= x+width || iy >= y || iy <= y + height) collided = true;
       }
       return collided;
   }

	protected boolean collision(double xa, double ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) - (c % 2) * 15) / 16; //Fix colliion in y, 1 pixel off x
			double yt = ((y + ya) - (c / 2) * 15) / 16;
         int ix = (int) Math.ceil(xt);
         int iy = (int) Math.ceil(yt);
         if (c % 2 == 0) ix = (int) Math.floor(xt);
         if (c / 2 == 0) iy = (int) Math.floor(yt);
         if (this instanceof Enemy && level.getTile(ix, iy) instanceof SpawnEdgeTile) return true;
			if (level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}
}
