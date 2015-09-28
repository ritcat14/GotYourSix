package com.ritcat14.GotYourSix.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

    private String            path;
    public final int          SIZE;
    public final int          SPRITE_WIDTH, SPRITE_HEIGHT;
    private int               width, height;
    public int[]              pixels;

    public static SpriteSheet tiles           = new SpriteSheet("/textures/sheets/spritesheet.png", 256);
    public static SpriteSheet spawn_level     = new SpriteSheet("/textures/sheets/spawn_level.png", 128, 64);
    public static SpriteSheet projectile_test = new SpriteSheet("/textures/sheets/projectiles/test.png", 48);

    public static SpriteSheet fireKing          = new SpriteSheet("/textures/sheets/mob/player/fireKingSprite.png", 128, 64);
    public static SpriteSheet fireKing_down     = new SpriteSheet(fireKing, 0, 0, 1, 2, 32);
    public static SpriteSheet fireKing_up       = new SpriteSheet(fireKing, 1, 0, 1, 2, 32);
    public static SpriteSheet fireKing_left     = new SpriteSheet(fireKing, 2, 0, 1, 2, 32);
    public static SpriteSheet fireKing_right    = new SpriteSheet(fireKing, 3, 0, 1, 2, 32);

    public static SpriteSheet iceKing          = new SpriteSheet("/textures/sheets/mob/player/iceKingSprite.png", 128, 64);
    public static SpriteSheet iceKing_down     = new SpriteSheet(iceKing, 0, 0, 1, 2, 32);
    public static SpriteSheet iceKing_up       = new SpriteSheet(iceKing, 1, 0, 1, 2, 32);
    public static SpriteSheet iceKing_left     = new SpriteSheet(iceKing, 2, 0, 1, 2, 32);
    public static SpriteSheet iceKing_right    = new SpriteSheet(iceKing, 3, 0, 1, 2, 32);

    public static SpriteSheet fireSprite          = new SpriteSheet("/textures/sheets/mob/player/fireSprite.png", 128, 64);
    public static SpriteSheet fireSprite_down     = new SpriteSheet(fireSprite, 0, 0, 1, 2, 32);
    public static SpriteSheet fireSprite_up       = new SpriteSheet(fireSprite, 1, 0, 1, 2, 32);
    public static SpriteSheet fireSprite_left     = new SpriteSheet(fireSprite, 2, 0, 1, 2, 32);
    public static SpriteSheet fireSprite_right    = new SpriteSheet(fireSprite, 3, 0, 1, 2, 32);

    public static SpriteSheet iceSprite          = new SpriteSheet("/textures/sheets/mob/player/iceSprite.png", 128, 64);
    public static SpriteSheet iceSprite_down     = new SpriteSheet(iceSprite, 0, 0, 1, 2, 32);
    public static SpriteSheet iceSprite_up       = new SpriteSheet(iceSprite, 1, 0, 1, 2, 32);
    public static SpriteSheet iceSprite_left     = new SpriteSheet(iceSprite, 2, 0, 1, 2, 32);
    public static SpriteSheet iceSprite_right    = new SpriteSheet(iceSprite, 3, 0, 1, 2, 32);

    public static SpriteSheet iceSpriteShoot          = new SpriteSheet("/textures/sheets/mob/player/iceSpriteShooting.png", 128, 64);
    public static SpriteSheet iceSpriteShoot_down     = new SpriteSheet(iceSpriteShoot, 0, 0, 1, 2, 32);
    public static SpriteSheet iceSpriteShoot_up       = new SpriteSheet(iceSpriteShoot, 1, 0, 1, 2, 32);
    public static SpriteSheet iceSpriteShoot_left     = new SpriteSheet(iceSpriteShoot, 2, 0, 1, 2, 32);
    public static SpriteSheet iceSpriteShoot_right    = new SpriteSheet(iceSpriteShoot, 3, 0, 1, 2, 32);

    public static SpriteSheet fireSpriteShoot          = new SpriteSheet("/textures/sheets/mob/player/fireSpriteShooting.png", 128, 64);
    public static SpriteSheet fireSpriteShoot_down     = new SpriteSheet(fireSpriteShoot, 0, 0, 1, 2, 32);
    public static SpriteSheet fireSpriteShoot_up       = new SpriteSheet(fireSpriteShoot, 1, 0, 1, 2, 32);
    public static SpriteSheet fireSpriteShoot_left     = new SpriteSheet(fireSpriteShoot, 2, 0, 1, 2, 32);
    public static SpriteSheet fireSpriteShoot_right    = new SpriteSheet(fireSpriteShoot, 3, 0, 1, 2, 32);

    public static SpriteSheet iceKingShoot          = new SpriteSheet("/textures/sheets/mob/player/iceKingShooting.png", 128, 64);
    public static SpriteSheet iceKingShoot_down     = new SpriteSheet(iceKingShoot, 0, 0, 1, 2, 32);
    public static SpriteSheet iceKingShoot_up       = new SpriteSheet(iceKingShoot, 1, 0, 1, 2, 32);
    public static SpriteSheet iceKingShoot_left     = new SpriteSheet(iceKingShoot, 2, 0, 1, 2, 32);
    public static SpriteSheet iceKingShoot_right    = new SpriteSheet(iceKingShoot, 3, 0, 1, 2, 32);

    public static SpriteSheet fireKingShoot          = new SpriteSheet("/textures/sheets/mob/player/fireKingShooting.png", 128, 64);
    public static SpriteSheet fireKingShoot_down     = new SpriteSheet(fireKingShoot, 0, 0, 1, 2, 32);
    public static SpriteSheet fireKingShoot_up       = new SpriteSheet(fireKingShoot, 1, 0, 1, 2, 32);
    public static SpriteSheet fireKingShoot_left     = new SpriteSheet(fireKingShoot, 2, 0, 1, 2, 32);
    public static SpriteSheet fireKingShoot_right    = new SpriteSheet(fireKingShoot, 3, 0, 1, 2, 32);

    public static SpriteSheet playerSwim          = new SpriteSheet("/textures/sheets/mob/player/mainSpriteSwim.png", 128, 96);
    public static SpriteSheet player_downSwim     = new SpriteSheet(playerSwim, 0, 0, 1, 3, 32);
    public static SpriteSheet player_upSwim       = new SpriteSheet(playerSwim, 1, 0, 1, 3, 32);
    public static SpriteSheet player_leftSwim     = new SpriteSheet(playerSwim, 2, 0, 1, 3, 32);
    public static SpriteSheet player_rightSwim    = new SpriteSheet(playerSwim, 3, 0, 1, 3, 32);

    public static SpriteSheet soul            = new SpriteSheet("/textures/sheets/mob/enemy/soulEaterSprite.png", 128, 96);
    public static SpriteSheet soul_down       = new SpriteSheet(soul, 0, 0, 1, 3, 32);
    public static SpriteSheet soul_up         = new SpriteSheet(soul, 1, 0, 1, 3, 32);
    public static SpriteSheet soul_left       = new SpriteSheet(soul, 2, 0, 1, 3, 32);
    public static SpriteSheet soul_right      = new SpriteSheet(soul, 3, 0, 1, 3, 32);

    public static SpriteSheet dummy           = new SpriteSheet("/textures/sheets/mob/npc01.png", 128, 96);
    public static SpriteSheet dummy_down      = new SpriteSheet(dummy, 0, 0, 1, 3, 32);
    public static SpriteSheet dummy_up        = new SpriteSheet(dummy, 1, 0, 1, 3, 32);
    public static SpriteSheet dummy_left      = new SpriteSheet(dummy, 2, 0, 1, 3, 32);
    public static SpriteSheet dummy_right     = new SpriteSheet(dummy, 3, 0, 1, 3, 32);

    public static SpriteSheet zombie          = new SpriteSheet("/textures/sheets/mob/enemy/zombieSprite.png", 128, 96);
    public static SpriteSheet zombie_down     = new SpriteSheet(zombie, 0, 0, 1, 3, 32);
    public static SpriteSheet zombie_up       = new SpriteSheet(zombie, 1, 0, 1, 3, 32);
    public static SpriteSheet zombie_left     = new SpriteSheet(zombie, 2, 0, 1, 3, 32);
    public static SpriteSheet zombie_right    = new SpriteSheet(zombie, 3, 0, 1, 3, 32);

    public static SpriteSheet wizard          = new SpriteSheet("/textures/sheets/mob/enemy/wizardSprite.png", 128, 96);
    public static SpriteSheet wizard_down     = new SpriteSheet(wizard, 0, 0, 1, 3, 32);
    public static SpriteSheet wizard_up       = new SpriteSheet(wizard, 1, 0, 1, 3, 32);
    public static SpriteSheet wizard_left     = new SpriteSheet(wizard, 2, 0, 1, 3, 32);
    public static SpriteSheet wizard_right    = new SpriteSheet(wizard, 3, 0, 1, 3, 32);

    private Sprite[]          sprites;

    public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
        int xx = x * spriteSize;
        int yy = y * spriteSize;
        int w = width * spriteSize;
        int h = height * spriteSize;
        SPRITE_WIDTH = w;
        SPRITE_HEIGHT = h;
        if (width == height)
            SIZE = width;
        else
            SIZE = -1;
        pixels = new int[w * h];
        for (int y0 = 0; y0 < h; y0++) {
            int yp = yy + y0;
            for (int x0 = 0; x0 < w; x0++) {
                int xp = xx + x0;
                pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
            }
        }
        int frame = 0;
        sprites = new Sprite[width * height];
        for (int ya = 0; ya < height; ya++) {
            for (int xa = 0; xa < width; xa++) {
                int[] spritePixels = new int[spriteSize * spriteSize];
                for (int y0 = 0; y0 < spriteSize; y0++) {
                    for (int x0 = 0; x0 < spriteSize; x0++) {
                        spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
                sprites[frame++] = sprite;
            }
        }
    }

    public SpriteSheet(String path, int size) {
        this.path = path;
        this.SIZE = size;
        SPRITE_WIDTH = size;
        SPRITE_HEIGHT = size;
        pixels = new int[SIZE * SIZE];
        load();
    }

    public SpriteSheet(String path, int width, int height) {
        this.path = path;
        SIZE = -1;
        SPRITE_WIDTH = width;
        SPRITE_HEIGHT = height;
        pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
        load();
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
  
    public int[] getPixels(){
        return pixels;
    }

    private void load() {
        try {
            System.out.print("Trying to load " + path + "...");
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            System.out.println("Success!");
            this.width = image.getWidth();
            this.height = image.getHeight();
            pixels = new int[this.width * this.height];
            image.getRGB(0, 0, this.width, this.height, pixels, 0, this.width);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            System.err.println("Failed");
        }
    }

}
