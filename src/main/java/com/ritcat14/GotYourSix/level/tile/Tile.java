package com.ritcat14.GotYourSix.level.tile;

import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;

public class Tile {
    
    public int x = 0, y = 0;
    public Sprite sprite = null;
    
    public static Tile grass = new SpawnGrassTile(Sprite.grass), flower = new SpawnGrassTile(Sprite.flower), water = new SpawnWaterTile(Sprite.water), 
  							  gravel = new SpawnGrassTile(Sprite.gravel), woodFloor = new SpawnFloorTile(Sprite.woodFloor), mossWall = new SpawnWallTile(Sprite.mossWall), 
  							  brickWall = new SpawnWallTile(Sprite.brickWall), portal = new SpawnPortalTile(Sprite.portal), door = new SpawnDoorTile(Sprite.doorClosed), 
  							  bush = new SpawnWallTile(Sprite.bush), mossWallShad = new SpawnWallTile(Sprite.mossWallShad), brickWallShad = new SpawnWallTile(Sprite.brickWallShad),
  
  							  wallGrassTL = new SpawnEdgeTile(Sprite.wallGrassTL), wallGrassTM = new SpawnEdgeTile(Sprite.wallGrassTM), wallGrassTR = new SpawnEdgeTile(Sprite.wallGrassTR),
    						  wallGrassLM = new SpawnEdgeTile(Sprite.wallGrassLM), wallGrassRM = new SpawnEdgeTile(Sprite.wallGrassRM), wallGrassBL = new SpawnEdgeTile(Sprite.wallGrassBL),
  							  wallGrassBM = new SpawnEdgeTile(Sprite.wallGrassBM), wallGrassBR = new SpawnEdgeTile(Sprite.wallGrassBR),
  							  
  							  wallWaterTL = new SpawnEdgeTile(Sprite.wallWaterTL), wallWaterTM = new SpawnEdgeTile(Sprite.wallWaterTM), wallWaterTR = new SpawnEdgeTile(Sprite.wallWaterTR),
  						     wallWaterLM = new SpawnEdgeTile(Sprite.wallWaterLM), wallWaterRM = new SpawnEdgeTile(Sprite.wallWaterRM), wallWaterBL = new SpawnEdgeTile(Sprite.wallWaterBL),
  							  wallWaterBM = new SpawnEdgeTile(Sprite.wallWaterBM), wallWaterBR = new SpawnEdgeTile(Sprite.wallWaterBR);
    
    public static final int col_grass = 0xff01ff01, col_flower = 0xffffd800, col_water = 0xff0094ff, col_gravel = 0xff303030, 
  	 								 col_woodFloor = 0xff7f0000, col_mossWall = 0xff606060, col_brickWall = 0xff808080, col_portal = 0xff2FFCFF, 
    								 col_door = 0xff460037, col_mossWallShad = 0xff707070, col_brickWallShad = 0xff0A0A0A;
  
    public static final int col_wallGrassTL = 0xff000000, col_wallGrassTM = 0xff00FF90, col_wallGrassTR = 0xffFFFFFF, col_wallGrassLM = 0xffB200FF, 
    								 col_wallGrassRM = 0xffFF006E,  col_wallGrassBL = 0xffFF6A00, col_wallGrassBM = 0xffFF0000, col_wallGrassBR = 0xff7F3300;
  
    public static final int col_wallWaterTL = 0xff0046FF, col_wallWaterTM = 0xff0061FF, col_wallWaterTR = 0xff0078FF, col_wallWaterLM = 0xff0026FF,
  									 col_wallWaterRM = 0xff7CFF21, col_wallWaterBL = 0xff7C466D, col_wallWaterBM = 0xff7C4621, col_wallWaterBR = 0xffB6FF21;
    
  
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
