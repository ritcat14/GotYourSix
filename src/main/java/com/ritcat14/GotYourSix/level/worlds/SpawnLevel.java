package com.ritcat14.GotYourSix.level.worlds;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ritcat14.GotYourSix.entity.spawner.EnemySpawner;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.util.Vector2i;

public class SpawnLevel extends Level {
	
	public SpawnLevel(String path) {
		super(path);
	}
	
	protected void loadLevel(String path){
		try{
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Failed to load level file.");
		}
     generateLevel();
	}
  
     public void setPlayerLocation(){
      for (int i = 0; i < getPlayers().size(); i ++){
          getPlayers().get(i).setLocation(new Vector2i(5 * 16, 70 * 16));
      }
   }
	
  protected void generateLevel(){
    //no enemies!
    EnemySpawner es = new EnemySpawner(10, 15, 100, 5, this, "Zombie");
    add(es);
  }
}