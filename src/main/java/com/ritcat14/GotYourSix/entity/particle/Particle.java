package com.ritcat14.GotYourSix.entity.particle;

import java.util.Random;

import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.entity.projectile.Projectile;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class Particle extends Entity {

    protected Sprite   sprite = null;

    protected int      life = 0;
    protected int      time = 0;

    protected double xx = 0, yy = 0, zz = 0;
    protected double xa = 0, ya = 0, za = 0;
    private Random   ran = new Random();

    public Particle(int x, int y, int life, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.xx = x;
        this.yy = y;
        this.life = life + (random.nextInt(50) - 25);
        this.sprite = sprite;
        this.xa = random.nextGaussian();
        this.ya = random.nextGaussian();
        this.zz = random.nextFloat() + 2.0;
        int col = ran.nextInt(4);
        if (sprite == Sprite.particle_normal) {
            if (Projectile.weapon == Projectile.Weapon.FIREDCANNON || Projectile.weapon == Projectile.Weapon.FIREDARROW) {
                if (col == 0)
                    this.sprite = new Sprite(2, 0xff000000);
                if (col == 1)
                    this.sprite = new Sprite(2, 0xffF8A800);
                if (col == 2)
                    this.sprite = new Sprite(2, 0xffF8F8C8);
                if (col == 3)
                    this.sprite = new Sprite(2, 0xffF8A800);
            } else if (Projectile.weapon == Projectile.Weapon.FIREBALL || Projectile.weapon == Projectile.Weapon.FIREWALL) {
                if (col == 0)
                    this.sprite = new Sprite(2, 0xffF86800);
                if (col == 1)
                    this.sprite = new Sprite(2, 0xffF8A800);
                if (col == 2)
                    this.sprite = new Sprite(2, 0xffF8F8C8);
                if (col == 3)
                    this.sprite = new Sprite(2, 0xffF8D840);
            } else if (Projectile.weapon == Projectile.Weapon.ICEDCANNON || Projectile.weapon == Projectile.Weapon.ICEDARROW) {
                if (col == 0)
                    this.sprite = new Sprite(2, 0xff427AFF);
                if (col == 1)
                    this.sprite = new Sprite(2, 0xff607BFF);
                if (col == 2)
                    this.sprite = new Sprite(2, 0xff7C9BFF);
                if (col == 3)
                    this.sprite = new Sprite(2, 0xff000000);
            } else if (Projectile.weapon == Projectile.Weapon.ICEBALL || Projectile.weapon == Projectile.Weapon.ICEWALL) {
                if (col == 0)
                    this.sprite = new Sprite(2, 0xff427AFF);
                if (col == 1)
                    this.sprite = new Sprite(2, 0xff607BFF);
                if (col == 2)
                    this.sprite = new Sprite(2, 0xff7C9BFF);
                if (col == 3)
                    this.sprite = new Sprite(2, 0xffD3DEFF);
            }
        }
    }

    public void update() {
        time++;
        if (time >= Integer.MAX_VALUE - 100)
            time = 0; //Safety check
        if (time > life)
            remove();
        za -= 0.1;

        if (zz < 0) {
            zz = 0;
            za *= -0.55;
            xa *= 0.4;
            ya *= 0.4;
        }

        move(xx + xa, (yy + ya) + (zz + za));
    }

    private void move(double x, double y) {
        if (collision(x, y)) {
            this.xa *= -0.5;
            this.ya *= -0.5;
            this.za *= -0.5;
        }
        this.xx += xa;
        this.yy += ya;
        this.zz += za;
    }

    public boolean collision(double x, double y) {
        boolean solid = false;
        for (int c = 0; c < 4; c++) {
            double xt = (x - c % 2 * 16) / 16;
            double yt = (y - c / 2 * 16) / 16;
            int ix = (int)Math.ceil(xt);
            int iy = (int)Math.ceil(yt);
            if (c % 2 == 0)
                ix = (int)Math.floor(xt);
            if (c / 2 == 0)
                iy = (int)Math.floor(yt);
            if (level.getTile(ix, iy).solid())
                solid = true;
        }
        return solid;
    }

    public void render(Screen screen) {
        screen.renderSprite((int)xx - 1, (int)yy - (int)zz - 1, sprite, true);
    }

}
