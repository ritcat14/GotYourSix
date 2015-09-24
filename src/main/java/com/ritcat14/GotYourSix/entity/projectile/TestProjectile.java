package com.ritcat14.GotYourSix.entity.projectile;

import java.util.List;

import com.ritcat14.GotYourSix.entity.mob.Enemy;
import com.ritcat14.GotYourSix.entity.mob.Mob;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.entity.spawner.ParticleSpawner;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class TestProjectile extends Projectile{

	public TestProjectile(double x, double y, double dir, Mob mob) {
		super(x, y, dir, mob);
		//range = random.nextInt(100);
		range = 130;
		angle = dir;
		speed = 3;
		damage = 1;
		sprite = Sprite.test;

		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update(){
      projectileCollision((int)(x + nx), (int)(y + ny));
		if(level.tileCollision((int)(x + nx), (int)(y + ny), 8, 4, 4)){
			level.add(new ParticleSpawner((int)x, (int) y, 100, 15, level, Sprite.particle_normal));
			remove();
		} else if(collided){
			level.add(new ParticleSpawner((int)x, (int) y, 50, 15, level, Sprite.particle_blood));
         collided = false;
      }
      if(Player.getLevel() > 1){
        sprite = Sprite.test;
        damage = 1;
      }else{
        sprite = Sprite.rotate(Sprite.testArrow, angle);
        damage = Player.getLevel();
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
      List<Enemy> enemies = level.getEnemies();
	}

}
