package com.ritcat14.GotYourSix.level.worlds;

import java.awt.image.BufferedImage;

import com.ritcat14.GotYourSix.entity.mob.enemy.Boss;
import com.ritcat14.GotYourSix.entity.spawner.EnemySpawner;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.level.tile.Tile;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class BossLevel extends Level {
  
    private String bossName = "";
    private int health = 0;
   
    public BossLevel(String path, String bossName, int health) {
        super(path, true);
        this.bossName = bossName;
        this.health = health;
        generateLevel();
    }
	
	protected void loadLevel(String path){
		BufferedImage image = ImageUtil.getImage(path);
		int w = width = image.getWidth();
		int h = height = image.getHeight();
		tiles = new int[w * h];
		image.getRGB(0, 0, w, h, tiles, 0, w);
	}
  
     public void setPlayerLocation(){
      for (int i = 0; i < getPlayers().size(); i ++){
          getPlayers().get(i).setLocation(new Vector2i(5 * 16, 5 * 16));
      }
   }
	
  protected void generateLevel(){
    for (int x = 0; x < width; x++){
      for (int y = 0; y < height; y++){
        if (tiles[x + y * width] == Tile.col_enemy) {
          EnemySpawner es = new EnemySpawner(x, y, 100, 5, this, bossName);
          add(es);
        } else if (tiles[x + y * width] == Tile.col_boss){
            Boss bs = new Boss(x, y,health, "/textures/sheets/mob/enemy/" + bossName.toLowerCase() + "/king" + bossName + ".png");
            add(bs);
        }
      }
    }
  }
}
