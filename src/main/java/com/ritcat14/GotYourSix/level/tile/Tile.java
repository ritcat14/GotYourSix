package com.ritcat14.GotYourSix.level.tile;

import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnEdgeTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnFloorTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnGrassTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnPortalTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnWallTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnWaterTile;

public class Tile {
    
    public int x, y;
    public Sprite sprite;
  
    public static Tile grass = new GrassTile(Sprite.grass);
    public static Tile flower = new FlowerTile(Sprite.flower);
    
    public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
    public static Tile spawn_flower = new SpawnGrassTile(Sprite.spawn_flower);
    public static Tile spawn_water = new SpawnWaterTile(Sprite.spawn_water);
    public static Tile spawn_gravel = new SpawnGrassTile(Sprite.spawn_gravel);
    public static Tile spawn_woodFloor = new SpawnFloorTile(Sprite.spawn_woodFloor);
    public static Tile spawn_mossWall = new SpawnWallTile(Sprite.spawn_mossWall);
    public static Tile spawn_brickWall = new SpawnWallTile(Sprite.spawn_brickWall);
    public static Tile spawn_portal = new SpawnPortalTile(Sprite.spawn_portal);
  
    public static Tile spawn_wallGrassTL = new SpawnEdgeTile(Sprite.spawn_wallGrassTL);
    public static Tile spawn_wallGrassTM = new SpawnEdgeTile(Sprite.spawn_wallGrassTM);
    public static Tile spawn_wallGrassTR = new SpawnEdgeTile(Sprite.spawn_wallGrassTR);
    public static Tile spawn_wallGrassLM = new SpawnEdgeTile(Sprite.spawn_wallGrassLM);
    public static Tile spawn_wallGrassRM = new SpawnEdgeTile(Sprite.spawn_wallGrassRM);
    public static Tile spawn_wallGrassBL = new SpawnEdgeTile(Sprite.spawn_wallGrassBL);
    public static Tile spawn_wallGrassBM = new SpawnEdgeTile(Sprite.spawn_wallGrassBM);
    public static Tile spawn_wallGrassBR = new SpawnEdgeTile(Sprite.spawn_wallGrassBR);
  
    public static Tile spawn_wallWaterTL = new SpawnEdgeTile(Sprite.spawn_wallWaterTL);
    public static Tile spawn_wallWaterTM = new SpawnEdgeTile(Sprite.spawn_wallWaterTM);
    public static Tile spawn_wallWaterTR = new SpawnEdgeTile(Sprite.spawn_wallWaterTR);
    public static Tile spawn_wallWaterLM = new SpawnEdgeTile(Sprite.spawn_wallWaterLM);
    public static Tile spawn_wallWaterRM = new SpawnEdgeTile(Sprite.spawn_wallWaterRM);
    public static Tile spawn_wallWaterBL = new SpawnEdgeTile(Sprite.spawn_wallWaterBL);
    public static Tile spawn_wallWaterBM = new SpawnEdgeTile(Sprite.spawn_wallWaterBM);
    public static Tile spawn_wallWaterBR = new SpawnEdgeTile(Sprite.spawn_wallWaterBR);
    
    public static final int col_spawn_grass = 0xff01ff01;
    public static final int col_spawn_flower = 0xffffd800;
    public static final int col_spawn_water = 0xff0094ff;
    public static final int col_spawn_gravel = 0xff303030;
    public static final int col_spawn_woodFloor = 0xff7f0000;
    public static final int col_spawn_mossWall = 0xff606060;
    public static final int col_spawn_brickWall = 0xff808080;
    public static final int col_spawn_portal = 0xff2FFCFF;
  
    public static final int col_spawn_wallGrassTL = 0xff000000;
    public static final int col_spawn_wallGrassTM = 0xff00FF90;
    public static final int col_spawn_wallGrassTR = 0xffFFFFFF;
    public static final int col_spawn_wallGrassLM = 0xffB200FF;
    public static final int col_spawn_wallGrassRM = 0xffFF006E;
    public static final int col_spawn_wallGrassBL = 0xffFF6A00;
    public static final int col_spawn_wallGrassBM = 0xffFF0000;
    public static final int col_spawn_wallGrassBR = 0xff7F3300;
  
    public static final int col_spawn_wallWaterTL = 0xff0046FF;
    public static final int col_spawn_wallWaterTM = 0xff0061FF;
    public static final int col_spawn_wallWaterTR = 0xff0078FF;
    public static final int col_spawn_wallWaterLM = 0xff0026FF;
    public static final int col_spawn_wallWaterRM = 0xff7CFF21;
    public static final int col_spawn_wallWaterBL = 0xff7C466D;
    public static final int col_spawn_wallWaterBM = 0xff7C4621;
    public static final int col_spawn_wallWaterBR = 0xffB6FF21;
    
  
    public Tile(Sprite sprite){
        this.sprite = sprite;
    }
  
    public void update(){
    }
    
    public void render(int x, int y, Screen screen) {
    }
  
    public boolean solid(){
        return false;
    }
}
