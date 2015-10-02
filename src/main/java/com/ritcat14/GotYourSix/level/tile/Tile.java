package com.ritcat14.GotYourSix.level.tile;

import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnEdgeTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnFloorTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnGrassTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnPortalTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnWallTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnWaterTile;
import com.ritcat14.GotYourSix.level.tile.spawn_level.SpawnDoorTile;

public class Tile {
    
    public int x, y;
    public Sprite sprite;
    
    public static Tile grass = new SpawnGrassTile(Sprite.grass);
    public static Tile flower = new SpawnGrassTile(Sprite.flower);
    public static Tile water = new SpawnWaterTile(Sprite.water);
    public static Tile gravel = new SpawnGrassTile(Sprite.gravel);
    public static Tile woodFloor = new SpawnFloorTile(Sprite.woodFloor);
    public static Tile mossWall = new SpawnWallTile(Sprite.mossWall);
    public static Tile brickWall = new SpawnWallTile(Sprite.brickWall);
    public static Tile portal = new SpawnPortalTile(Sprite.portal);
    public static Tile door = new SpawnDoorTile(Sprite.doorClosed);
    public static Tile bush = new SpawnWallTile(Sprite.bush);
    public static Tile mossWallShad = new SpawnWallTile(Sprite.mossWallShad);
    public static Tile brickWallShad = new SpawnWallTile(Sprite.brickWallShad);
  
    public static Tile wallGrassTL = new SpawnEdgeTile(Sprite.wallGrassTL);
    public static Tile wallGrassTM = new SpawnEdgeTile(Sprite.wallGrassTM);
    public static Tile wallGrassTR = new SpawnEdgeTile(Sprite.wallGrassTR);
    public static Tile wallGrassLM = new SpawnEdgeTile(Sprite.wallGrassLM);
    public static Tile wallGrassRM = new SpawnEdgeTile(Sprite.wallGrassRM);
    public static Tile wallGrassBL = new SpawnEdgeTile(Sprite.wallGrassBL);
    public static Tile wallGrassBM = new SpawnEdgeTile(Sprite.wallGrassBM);
    public static Tile wallGrassBR = new SpawnEdgeTile(Sprite.wallGrassBR);
  
    public static Tile wallWaterTL = new SpawnEdgeTile(Sprite.wallWaterTL);
    public static Tile wallWaterTM = new SpawnEdgeTile(Sprite.wallWaterTM);
    public static Tile wallWaterTR = new SpawnEdgeTile(Sprite.wallWaterTR);
    public static Tile wallWaterLM = new SpawnEdgeTile(Sprite.wallWaterLM);
    public static Tile wallWaterRM = new SpawnEdgeTile(Sprite.wallWaterRM);
    public static Tile wallWaterBL = new SpawnEdgeTile(Sprite.wallWaterBL);
    public static Tile wallWaterBM = new SpawnEdgeTile(Sprite.wallWaterBM);
    public static Tile wallWaterBR = new SpawnEdgeTile(Sprite.wallWaterBR);
    
    public static final int col_grass = 0xff01ff01;
    public static final int col_flower = 0xffffd800;
    public static final int col_water = 0xff0094ff;
    public static final int col_gravel = 0xff303030;
    public static final int col_woodFloor = 0xff7f0000;
    public static final int col_mossWall = 0xff606060;
    public static final int col_brickWall = 0xff808080;
    public static final int col_portal = 0xff2FFCFF;
    public static final int col_door = 0xff460037;
    public static final int col_mossWallShad = 0xff707070;
    public static final int col_brickWallShad = 0xff0A0A0A;
  
    public static final int col_wallGrassTL = 0xff000000;
    public static final int col_wallGrassTM = 0xff00FF90;
    public static final int col_wallGrassTR = 0xffFFFFFF;
    public static final int col_wallGrassLM = 0xffB200FF;
    public static final int col_wallGrassRM = 0xffFF006E;
    public static final int col_wallGrassBL = 0xffFF6A00;
    public static final int col_wallGrassBM = 0xffFF0000;
    public static final int col_wallGrassBR = 0xff7F3300;
  
    public static final int col_wallWaterTL = 0xff0046FF;
    public static final int col_wallWaterTM = 0xff0061FF;
    public static final int col_wallWaterTR = 0xff0078FF;
    public static final int col_wallWaterLM = 0xff0026FF;
    public static final int col_wallWaterRM = 0xff7CFF21;
    public static final int col_wallWaterBL = 0xff7C466D;
    public static final int col_wallWaterBM = 0xff7C4621;
    public static final int col_wallWaterBR = 0xffB6FF21;
    
  
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
