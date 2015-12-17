package com.ritcat14.GotYourSix.graphics;

import com.ritcat14.GotYourSix.graphics.UI.menus.StartScreen;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

    private String            path = null;
    public final int          SIZE;
    public final int          SPRITE_WIDTH, SPRITE_HEIGHT;
    private int               width = 0, height = 0;
    public int[]              pixels = null;
    private BufferedImage image = null;

    public static SpriteSheet tiles                 = new SpriteSheet("/textures/sheets/spritesheet.png", 256, 256);
    public static SpriteSheet items = new SpriteSheet("/textures/sheets/items/items.png", 48, 16);
    public static SpriteSheet playerSheet, playerViewSheet, up, down, left, right, armour, projectiles;

    private Sprite[]          sprites = null;

    public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteWidth, int spriteHeight) {
        int xx = x * spriteWidth;
        int yy = y * spriteHeight;
        int w = width * spriteWidth;
        int h = height * spriteHeight;
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
                int[] spritePixels = new int[spriteWidth * spriteHeight];
                for (int y0 = 0; y0 < spriteHeight; y0++) {
                    for (int x0 = 0; x0 < spriteWidth; x0++) {
                        spritePixels[x0 + y0 * spriteWidth] = pixels[(x0 + xa * spriteWidth) + (y0 + ya * spriteHeight) * SPRITE_WIDTH];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteWidth, spriteHeight);
                sprites[frame++] = sprite;
            }
        }
    }

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

    public int[] getPixels() {
        return pixels;
    }

    private void load() {
        try {
            image = ImageIO.read(SpriteSheet.class.getResource(path));
            this.width = image.getWidth();
            this.height = image.getHeight();
            pixels = new int[this.width * this.height];
            image.getRGB(0, 0, this.width, this.height, pixels, 0, this.width);
        } catch (IOException e) {} catch (Exception e) {}
    }
  
    public static void init(){
      String plyer = StartScreen.state.toString() + ".png";
      playerSheet = new SpriteSheet("/textures/sheets/mob/player/" + plyer, 128, 64);
      armour = new SpriteSheet("/textures/sheets/mob/player/armour/" + plyer, 192, 96);
      projectiles = new SpriteSheet("/textures/sheets/projectiles/" + plyer, 48, 32);
      playerViewSheet = new SpriteSheet(playerSheet, 0, 0, 4, 1, 32);
      up = new SpriteSheet(playerSheet, 1, 0, 1, 2, 32);
      down = new SpriteSheet(playerSheet, 0, 0, 1, 2, 32);
      left = new SpriteSheet(playerSheet, 2, 0, 1, 2, 32);
      right = new SpriteSheet(playerSheet, 3, 0, 1, 2, 32);
    }

}
