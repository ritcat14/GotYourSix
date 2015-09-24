package com.ritcat14.GotYourSix.graphics;

import com.ritcat14.GotYourSix.graphics.SpriteSheet;

public class Sprite {

    public final int      SIZE;
    private int           x, y;
    private int           width, height;
    public int[]          pixels;
    protected SpriteSheet sheet;

    public static Sprite  grass             = new Sprite(16, 0, 0, SpriteSheet.tiles);
    public static Sprite  flower            = new Sprite(16, 1, 0, SpriteSheet.tiles);
    public static Sprite  voidSprite        = new Sprite(16, 6, 1, SpriteSheet.spawn_level);

    //Spawn level sprites:
    public static Sprite  spawn_grass       = new Sprite(16, 3, 1, SpriteSheet.spawn_level);
    public static Sprite  spawn_flower      = new Sprite(16, 1, 0, SpriteSheet.spawn_level);
    public static Sprite  spawn_water       = new Sprite(16, 6, 1, SpriteSheet.spawn_level);
    public static Sprite  spawn_gravel      = new Sprite(16, 0, 1, SpriteSheet.spawn_level);
    public static Sprite  spawn_woodFloor   = new Sprite(16, 1, 1, SpriteSheet.spawn_level);
    public static Sprite  spawn_mossWall    = new Sprite(16, 1, 2, SpriteSheet.spawn_level);
    public static Sprite  spawn_brickWall   = new Sprite(16, 0, 2, SpriteSheet.spawn_level);
    public static Sprite  spawn_door_closed = new Sprite(16, 0, 3, SpriteSheet.spawn_level);
    public static Sprite  spawn_door_open   = new Sprite(16, 1, 3, SpriteSheet.spawn_level);

    public static Sprite  spawn_wallGrassTL = new Sprite(16, 2, 0, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallGrassTM = new Sprite(16, 3, 0, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallGrassTR = new Sprite(16, 4, 0, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallGrassLM = new Sprite(16, 2, 1, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallGrassRM = new Sprite(16, 4, 1, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallGrassBL = new Sprite(16, 2, 2, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallGrassBM = new Sprite(16, 3, 2, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallGrassBR = new Sprite(16, 4, 2, SpriteSheet.spawn_level);

    public static Sprite  spawn_wallWaterTL = new Sprite(16, 5, 0, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallWaterTM = new Sprite(16, 6, 0, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallWaterTR = new Sprite(16, 7, 0, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallWaterLM = new Sprite(16, 5, 1, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallWaterRM = new Sprite(16, 7, 1, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallWaterBL = new Sprite(16, 5, 2, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallWaterBM = new Sprite(16, 6, 2, SpriteSheet.spawn_level);
    public static Sprite  spawn_wallWaterBR = new Sprite(16, 7, 2, SpriteSheet.spawn_level);

    //Projectile sprites here:
    public static Sprite  test              = new Sprite(16, 0, 0, SpriteSheet.projectile_test);
    public static Sprite  testArrow              = new Sprite(16, 1, 0, SpriteSheet.projectile_test);

    //Particles
    public static Sprite  particle_normal   = new Sprite(2, 0xFFAAAAAA);
    public static Sprite  particle_blood   = new Sprite(2, 0xFFFF0000);

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
  
    public static Sprite rotate(Sprite sprite, double angle){
        return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
    }
  
   private static int[] rotate(int[] pixels, int width, int height, double angle){
       int[] result = new int[width * height];
       double nxx = rotX(-angle, 1.0, 0.0);
       double nxy = rotY(-angle, 1.0, 0.0);
       double nyx = rotX(-angle, 0.0, 1.0);
       double nyy = rotY(-angle, 0.0, 1.0);
       //initial value of pixel
       double x0 = rotX(-angle, -width/2.0, -height/2.0) + width / 2.0;
       double y0 = rotY(-angle, -width/2.0, -height/2.0) + height / 2.0;
       
       for (int y = 0; y < height; y++){
           double x1 = x0;
           double y1 = y0;
           for (int x = 0; x < width; x++){
               int xx = (int) x1; //convert to int for drawing
               int yy = (int) y1;
               int col = 0;
               if(xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xffff00ff;
               else col = pixels[xx + yy * width];
               result[x + y * width] = col;
               x1 += nxx;
               y1 += nxy;
           }
           x0 += nyx;
           y0 += nyy;
       }
       
       return result;
   }
	
   private static double rotX(double angle, double x, double y){
       double cos = Math.cos(angle - (Math.PI / 2));
       double sin = Math.sin(angle - (Math.PI / 2));
       return x * cos + y * -sin;
   }
	
   private static double rotY(double angle, double x, double y){
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


    private void setColour(int colour) {
        for (int i = 0; i < width * height; i++) {
            pixels[i] = colour;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void load() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
            }
        }
    }
}
