package com.ritcat14.GotYourSix.entity.mob;

import java.util.List;

import com.ritcat14.GotYourSix.graphics.AnimatedObject;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;
import com.ritcat14.GotYourSix.level.tile.Tile;

public class Zombie extends Enemy{

	private int time = 0;
	private double speed = 0.2;
	private AnimatedObject down = new AnimatedObject(SpriteSheet.zombie_down, 32, 32, 3);
	private AnimatedObject up = new AnimatedObject(SpriteSheet.zombie_up, 32, 32, 3);
	private AnimatedObject left = new AnimatedObject(SpriteSheet.zombie_left, 32, 32, 3);
	private AnimatedObject right = new AnimatedObject(SpriteSheet.zombie_right, 32, 32, 3);
	
	private AnimatedObject animSprite = down;

	double xa = 0, ya = 0;

	public Zombie(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = animSprite.getSprite();
      health = 10;
      XPBonus = 20;
      collidable = true;
	}

	private void move() {
		xa = 0;
		ya = 0;
		List<Player> players = level.getPlayers(this, 200);
		if (players.size() > 0) {
			Player player = players.get(0);
			if (x < player.getX()) xa+=speed;
			if (x > player.getX()) xa-=speed;
			if (y < player.getY()) ya+=speed;
			if (y > player.getY()) ya-=speed;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else
			walking = false;
	}

	public void update() {
		if (!collision(xa, ya)){
			move();
		}
		move();
		if (walking) animSprite.update();
		else
			animSprite.setFrameRate(0);
		if (ya < 0) {
			animSprite = up;
			dir = Direction.UP;
		} else if (ya > 0) {
			animSprite = down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			animSprite = left;
			dir = Direction.LEFT;
		} else if (xa > 0) {
			animSprite = right;
			dir = Direction.RIGHT;
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x - 16), (int)(y - 16), this);
	}

}
