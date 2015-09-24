package com.ritcat14.GotYourSix.entity.mob;

import com.ritcat14.GotYourSix.graphics.AnimatedObject;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;

public class SoulEater extends Enemy {
	private int time = 0;

	private AnimatedObject down = new AnimatedObject(SpriteSheet.soul_down, 32, 32, 3);
	private AnimatedObject up = new AnimatedObject(SpriteSheet.soul_up, 32, 32, 3);
	private AnimatedObject left = new AnimatedObject(SpriteSheet.soul_left, 32, 32, 3);
	private AnimatedObject right = new AnimatedObject(SpriteSheet.soul_right, 32, 32, 3);

	private AnimatedObject animSprite = down;

	int xa = 0, ya = 0;

	public SoulEater(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = animSprite.getSprite();
        health = 15;
        XPBonus = 1;
	}

	private void move() {
		xa = 0;
		ya = 0;
		Player player = level.getClientPlayer();
		if (x < player.getX()) xa ++;
		if (x > player.getX()) xa --;
		if (y < player.getY()) ya ++;
		if (y > player.getY()) ya --;
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else walking = false;
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
     shootRandom();
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x - 16), (int)(y - 16), this);
	}

}
