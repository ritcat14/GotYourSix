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
    public static Sprite sand = new Sprite(16,0,0,SpriteSheet.tiles);
    public static Sprite stone = new Sprite(16,0,1,SpriteSheet.tiles);
    public static Sprite wall = new Sprite(16,0,2,SpriteSheet.tiles);
    public static Sprite wallShad = new Sprite(16,0,3,SpriteSheet.tiles);
    public static Sprite crust = new Sprite(16,1,0,SpriteSheet.tiles);
    public static Sprite wood = new Sprite(16,1,1,SpriteSheet.tiles);
    public static Sprite moss = new Sprite(16,1,2,SpriteSheet.tiles);
    public static Sprite mossShad = new Sprite(16,1,3,SpriteSheet.tiles);
    public static Sprite grassTL = new Sprite(16,2,0,SpriteSheet.tiles);
    public static Sprite grassML = new Sprite(16,2,1,SpriteSheet.tiles);
    public static Sprite grassBL = new Sprite(16,2,2,SpriteSheet.tiles);
    public static Sprite portal = new Sprite(16,2,3,SpriteSheet.tiles);
    public static Sprite grassTM = new Sprite(16,3,0,SpriteSheet.tiles);
    public static Sprite grass = new Sprite(16,3,1,SpriteSheet.tiles);
    public static Sprite grassBM = new Sprite(16,3,2,SpriteSheet.tiles);
    public static Sprite portalClosed = new Sprite(16,3,3,SpriteSheet.tiles);
    public static Sprite grassTR = new Sprite(16,4,0,SpriteSheet.tiles);
    public static Sprite grassMR = new Sprite(16,4,1,SpriteSheet.tiles);
    public static Sprite grassBR = new Sprite(16,4,2,SpriteSheet.tiles);
    public static Sprite enemy = new Sprite(16,4,3,SpriteSheet.tiles);
    public static Sprite waterTL = new Sprite(16,5,0,SpriteSheet.tiles);
    public static Sprite  waterML = new Sprite(16,5,1,SpriteSheet.tiles);
    public static Sprite waterBL = new Sprite(16,5,2,SpriteSheet.tiles);
    public static Sprite boss = new Sprite(16,5,3,SpriteSheet.tiles);
    public static Sprite waterTM = new Sprite(16,6,0,SpriteSheet.tiles);
    public static Sprite water = new Sprite(16,6,1,SpriteSheet.tiles);
    public static Sprite waterBM = new Sprite(16,6,2,SpriteSheet.tiles);
    public static Sprite waterTR = new Sprite(16,7,0,SpriteSheet.tiles);
    public static Sprite waterMR = new Sprite(16,7,1,SpriteSheet.tiles);
    public static Sprite waterBR = new Sprite(16,7,2,SpriteSheet.tiles);
    public static Sprite lavaTL = new Sprite(16,8,0,SpriteSheet.tiles);
    public static Sprite lavaML = new Sprite(16,8,1,SpriteSheet.tiles);
    public static Sprite lavaBL = new Sprite(16,8,2,SpriteSheet.tiles);
    public static Sprite lavaTM = new Sprite(16,9,0,SpriteSheet.tiles);
    public static Sprite lava = new Sprite(16,9,1,SpriteSheet.tiles);
    public static Sprite lavaBM = new Sprite(16,9,2,SpriteSheet.tiles);
    public static Sprite lavaTR = new Sprite(16,10,0,SpriteSheet.tiles);
    public static Sprite lavaMR = new Sprite(16,10,1,SpriteSheet.tiles);
    public static Sprite lavaBR = new Sprite(16,10,2,SpriteSheet.tiles);
  	 public static Sprite stump = new Sprite(16,6,3,SpriteSheet.tiles);
    public static Sprite reed = new Sprite(16,7,3,SpriteSheet.tiles);
  	 public static Sprite bush01 = new Sprite(16,8,3,SpriteSheet.tiles);
  	 public static Sprite bush02 = new Sprite(16,9,3,SpriteSheet.tiles);
  	 public static Sprite rocks = new Sprite(16,10,3,SpriteSheet.tiles);
    public static Sprite grassGrave = new Sprite(16,11,3,SpriteSheet.tiles);
    public static Sprite lavaGrave = new Sprite(16,12,3,SpriteSheet.tiles);

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