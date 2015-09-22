package com.ritcat14.GotYourSix.entity.projectile;

import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.entity.spawner.ParticleSpawner;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class TestProjectile extends Projectile{

	public TestProjectile(double x, double y, double dir, Mob mob) {
		super(x, y, dir, mob);
		//range = random.nextInt(100);
		range = 200;
		angle = dir;
		speed = 3;
		damage = 20;
		sprite = Sprite.rotate(Sprite.testArrow, angle);

		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update(){
		if(level.tileCollision((int)(x + nx), (int)(y + ny), 8, 4, 4)){
			level.add(new ParticleSpawner((int)x, (int) y, 100, 52, level));
			remove();
		}
		move();
	}
	
	protected void move(){
		x += nx;
			y += ny;
			if(distance() > range) remove();
	}
	
	private double distance() {
		double dist = Math.sqrt(Math.abs(((xOrigin - x) * (xOrigin - x)) + ((yOrigin - y) * (yOrigin - y))));
		return dist;
	}

	public void render(Screen screen){
		screen.renderProjectile((int)x - 7, (int)y - 2, this);
	}

}
