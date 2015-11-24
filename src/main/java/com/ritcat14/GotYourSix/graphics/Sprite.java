package com.ritcat14.GotYourSix.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.ritcat14.GotYourSix.graphics.SpriteSheet;

public class Sprite {

    public final int      SIZE;
    private int           x = 0, y = 0;
    private int           width = 0, height = 0;
    public int[]          pixels = null;
    protected SpriteSheet sheet = null;

    public static Sprite  voidSprite      = new Sprite(16, 6, 1, SpriteSheet.tiles);

    //sprites:
    public static Sprite  bush            = new Sprite(16, 0, 0, SpriteSheet.tiles);
    public static Sprite  flower          = new Sprite(16, 1, 0, SpriteSheet.tiles);
    public static Sprite  gravel          = new Sprite(16, 0, 1, SpriteSheet.tiles);
    public static Sprite  woodFloor       = new Sprite(16, 1, 1, SpriteSheet.tiles);
    public static Sprite  grass           = new Sprite(16, 3, 1, SpriteSheet.tiles);
    public static Sprite  water           = new Sprite(16, 9, 0, SpriteSheet.tiles);
    public static Sprite  brickWall       = new Sprite(16, 0, 2, SpriteSheet.tiles);
    public static Sprite  mossWall        = new Sprite(16, 1, 2, SpriteSheet.tiles);
    public static Sprite  doorClosed      = new Sprite(16, 0, 3, SpriteSheet.tiles);
    public static Sprite  doorOpen        = new Sprite(16, 1, 3, SpriteSheet.tiles);
    public static Sprite  portal          = new Sprite(16, 2, 3, SpriteSheet.tiles);
    public static Sprite  brickWallShad   = new Sprite(16, 3, 3, SpriteSheet.tiles);
    public static Sprite  mossWallShad    = new Sprite(16, 4, 3, SpriteSheet.tiles);

    public static Sprite  wallGrassTL     = new Sprite(16, 2, 0, SpriteSheet.tiles);
    public static Sprite  wallGrassTM     = new Sprite(16, 3, 0, SpriteSheet.tiles);
    public static Sprite  wallGrassTR     = new Sprite(16, 4, 0, SpriteSheet.tiles);
    public static Sprite  wallGrassLM     = new Sprite(16, 2, 1, SpriteSheet.tiles);
    public static Sprite  wallGrassRM     = new Sprite(16, 4, 1, SpriteSheet.tiles);
    public static Sprite  wallGrassBL     = new Sprite(16, 2, 2, SpriteSheet.tiles);
    public static Sprite  wallGrassBM     = new Sprite(16, 3, 2, SpriteSheet.tiles);
    public static Sprite  wallGrassBR     = new Sprite(16, 4, 2, SpriteSheet.tiles);

    public static Sprite  wallWaterTL     = new Sprite(16, 5, 0, SpriteSheet.tiles);
    public static Sprite  wallWaterTM     = new Sprite(16, 6, 0, SpriteSheet.tiles);
    public static Sprite  wallWaterTR     = new Sprite(16, 7, 0, SpriteSheet.tiles);
    public static Sprite  wallWaterLM     = new Sprite(16, 8, 0, SpriteSheet.tiles);
    public static Sprite  wallWaterRM     = new Sprite(16, 10, 0, SpriteSheet.tiles);
    public static Sprite  wallWaterBL     = new Sprite(16, 11, 0, SpriteSheet.tiles);
    public static Sprite  wallWaterBM     = new Sprite(16, 12, 0, SpriteSheet.tiles);
    public static Sprite  wallWaterBR     = new Sprite(16, 13, 0, SpriteSheet.tiles);

    //Projectile sprites here:
    public static Sprite  arrow           = new Sprite(16, 0, 0, SpriteSheet.projectiles);
    public static Sprite  cannon          = new Sprite(16, 1, 0, SpriteSheet.projectiles);
    //Ice
    public static Sprite  icedArrow       = new Sprite(16, 0, 1, SpriteSheet.projectiles);
    public static Sprite  icedCannon      = new Sprite(16, 1, 1, SpriteSheet.projectiles);
    public static Sprite  iceBall         = new Sprite(16, 2, 0, SpriteSheet.projectiles);
    public static Sprite  iceWall         = new Sprite(16, 2, 1, SpriteSheet.projectiles);
    //Fire
    public static Sprite  firedArrow      = new Sprite(16, 0, 3, SpriteSheet.projectiles);
    public static Sprite  firedCannon     = new Sprite(16, 1, 3, SpriteSheet.projectiles);
    public static Sprite  fireBall        = new Sprite(16, 2, 2, SpriteSheet.projectiles);
    public static Sprite  fireWall         = new Sprite(16, 2, 3, SpriteSheet.projectiles);

    //Particles
    public static Sprite  particle_normal = new Sprite(2, 0xFF000000);
    public static Sprite  particle_blood  = new Sprite(2, 0xFFFF0000);


    protected Sprite(SpriteSheet sheet, int width, int height) {
        if (width == height)
            SIZE = width;
        else
            SIZE = -1;
        this.width = width;
        this.height = height;
        this.sheet = sheet;
    }

    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE * SIZE];
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        load();
    }

    public Sprite(int width, int height, int colour) {
        SIZE = -1;
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        setColour(colour);
    }

    public Sprite(int size, int colour) {
        SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE * SIZE];
        setColour(colour);
    }

    public Sprite(int size, Color colour) {
        SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE * SIZE];
        setColour(colour);
    }

    public Sprite(int[] pixels, int width, int height) {
        if (width == height)
            SIZE = width;
        else
            SIZE = -1;
        this.width = width;
        this.height = height;
        this.pixels = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            this.pixels[i] = pixels[i];
        }
    }

    public static Sprite rotate(Sprite sprite, double angle) {
        return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
    }

    private static int[] rotate(int[] pixels, int width, int height, double angle) {
        int[] result = new int[width * height];
        double nxx = rotX(-angle, 1.0, 0.0);
        double nxy = rotY(-angle, 1.0, 0.0);
        double nyx = rotX(-angle, 0.0, 1.0);
        double nyy = rotY(-angle, 0.0, 1.0);
        //initial value of pixel
        double x0 = rotX(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
        double y0 = rotY(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

        for (int y = 0; y < height; y++) {
            double x1 = x0;
            double y1 = y0;
            for (int x = 0; x < width; x++) {
                int xx = (int)x1; //convert to int for drawing
                int yy = (int)y1;
                int col = 0;
                if (xx < 0 || xx >= width || yy < 0 || yy >= height)
                    col = 0xffff00ff;
                else
                    col = pixels[xx + yy * width];
                result[x + y * width] = col;
                x1 += nxx;
                y1 += nxy;
            }
            x0 += nyx;
            y0 += nyy;
        }

        return result;
    }

    private static double rotX(double angle, double x, double y) {
        double cos = Math.cos(angle - (Math.PI / 2));
        double sin = Math.sin(angle - (Math.PI / 2));
        return x * cos + y * -sin;
    }

    private static double rotY(double angle, double x, double y) {
        double cos = Math.cos(angle - (Math.PI / 2));
        double sin = Math.sin(angle - (Math.PI / 2));
        return x * sin + y * cos;
    }

    public static Sprite[] split(SpriteSheet sheet) {
        int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
        Sprite[] sprites = new Sprite[amount];
        int current = 0;
        int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
        for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
            for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {
                for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
                    for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
                        int xo = x + xp * sheet.SPRITE_WIDTH;
                        int yo = y + yp * sheet.SPRITE_HEIGHT;
                        pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
                    }
                }
                sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
            }
        }
        return sprites;
    }

    public Sprite(int[] pixels, int size) {
        SIZE = width = height = size;
        this.pixels = pixels;
    }
  
   public BufferedImage getImage(){
     BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
     for (int x = 0; x < width; x++){
       for (int y = 0; y < height; y++){
         bufferedImage.setRGB(x, y, pixels[x + y * width]);
       }
     }
     return bufferedImage;
   }


    public void setColour(int colour) {
        for (int i = 0; i < width * height; i++) {
            pixels[i] = colour;
        }
    }


    public void setColour(Color colour) {
        for (int i = 0; i < width * height; i++) {
            pixels[i] = colour.getRGB();
        }
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
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
            }
        }
    }
}