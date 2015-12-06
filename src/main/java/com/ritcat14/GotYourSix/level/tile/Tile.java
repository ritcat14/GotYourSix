package com.ritcat14.GotYourSix.level.tile;

import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class Tile {
    
    public int x = 0, y = 0;
    public Sprite sprite = null;
    
    public static Tile sand = new SpawnGrassTile(Sprite.sand), stone = new SpawnGrassTile(Sprite.stone), wall = new SpawnWallTile(Sprite.wall), wallShad = new SpawnWallTile(Sprite.wallShad),
  							  crust = new SpawnGrassTile(Sprite.crust), wood = new SpawnGrassTile(Sprite.wood), moss = new SpawnWallTile(Sprite.moss), mossShad = new SpawnWallTile(Sprite.mossShad),
  							  grassTL = new SpawnEdgeTile(Sprite.grassTL), grassML = new SpawnEdgeTile(Sprite.grassML), grassBL = new SpawnEdgeTile(Sprite.grassBL), grassTM = new SpawnEdgeTile(Sprite.grassTM),
  							  grass = new SpawnGrassTile(Sprite.grass), grassBM = new SpawnEdgeTile(Sprite.grassBM), grassTR = new SpawnEdgeTile(Sprite.grassTR), grassMR = new SpawnEdgeTile(Sprite.grassMR),
    						  grassBR = new SpawnEdgeTile(Sprite.grassBR), enemy = new SpawnGrassTile(Sprite.enemy), waterTL = new SpawnEdgeTile(Sprite.waterTL), waterML = new SpawnEdgeTile(Sprite.waterML),
  							  waterBL = new SpawnEdgeTile(Sprite.waterBL), boss = new SpawnGrassTile(Sprite.boss), waterTM = new SpawnEdgeTile(Sprite.waterTM), water = new SpawnWaterTile(Sprite.water),
  							  waterBM = new SpawnEdgeTile(Sprite.waterBM), waterTR = new SpawnEdgeTile(Sprite.waterTR), waterMR = new SpawnEdgeTile(Sprite.waterMR), waterBR = new SpawnEdgeTile(Sprite.waterBR),
  							  lavaTL = new SpawnLavaTile(Sprite.lavaTL), lavaML = new SpawnLavaTile(Sprite.lavaML), lavaBL = new SpawnLavaTile(Sprite.lavaBL), lavaTM = new SpawnLavaTile(Sprite.lavaTM),
  							  lava = new SpawnLavaTile(Sprite.lava), lavaBM = new SpawnLavaTile(Sprite.lavaBM), lavaTR = new SpawnLavaTile(Sprite.lavaTR), lavaMR = new SpawnLavaTile(Sprite.lavaMR),
  							  lavaBR = new SpawnLavaTile(Sprite.lavaBR), stump = new SpawnWallTile(Sprite.stump), reed = new SpawnGrassTile(Sprite.reed), bush01 = new SpawnWallTile(Sprite.bush01),
  							  bush02 = new SpawnWallTile(Sprite.bush02), rocks = new SpawnWallTile(Sprite.rocks), grassGrave = new SpawnWallTile(Sprite.grassGrave), lavaGrave = new SpawnWallTile(Sprite.lavaGrave);
    
    public static final int col_sand = 0xffFF0000, col_stone = 0xffFF00AA, col_wall = 0xffFFAA00, col_wallShad = 0xffAA0000, col_crust = 0xffAAAA00, col_wood = 0xffAABB00, col_moss = 0xffAACC00,
  									 col_mossShad = 0xffAADD00, col_grassTL = 0xffAAEE00, col_grassML = 0xffAAFF00, col_grassBL = 0xffAAAAAA,  col_grassTM = 0xffBBAA00, col_grass = 0xffCCAA00,
  									 col_grassBM = 0xffDDAA00, col_grassTR = 0xff00AA00, col_grassMR = 0xff00BB00, col_grassBR = 0xff00CC00, col_enemy = 0xff00DD00, col_waterTL = 0xff00EE00,
  									 col_waterML = 0xff00FF00, col_waterBL = 0xff0000AA, col_boss = 0xff0000BB, col_waterTM = 0xff0000CC, col_water = 0xff0000DD, col_waterBM = 0xff0000EE,
  									 col_waterTR = 0xff0000FF, col_waterMR = 0xffCCCCAA, col_waterBR = 0xffCCAAAA, col_lavaTL = 0xff00CCAA, col_lavaML = 0xff00DDBB, col_lavaBL = 0xff00EECC, 
  								 	 col_lavaTM = 0xff00FFDD, col_lava = 0xffAABBBB, col_lavaBM = 0xffBBCC00, col_lavaTR = 0xffCCDD00, col_lavaMR = 0xffDDEE00, col_lavaBR = 0xffEEFF00, col_stump = 0xff422719,
  									 col_reed = 0xff432820, col_bush01 = 0xff503527, col_bush02 = 0xff574234, col_rocks = 0xff644941, col_grassGrave = 0xff706C6A, col_lavaGrave = 0xff565452;
    
  
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
