package com.ritcat14.GotYourSix.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.ritcat14.GotYourSix.entity.mob.Wizard;
import com.ritcat14.GotYourSix.entity.mob.Zombie;
import com.ritcat14.GotYourSix.entity.mob.Dummy;
import com.ritcat14.GotYourSix.entity.mob.Shooter;
import com.ritcat14.GotYourSix.entity.mob.SoulEater;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.util.Vector2i;

public class TestLevel extends Level {
	
	public TestLevel(String path) {
		super(path);
	}
	
	public void loadLevel(String path){
		try{
			BufferedImage image = ImageIO.read(TestLevel.class.getResource(path));
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
          getPlayers().get(i).setLocation(new Vector2i(6 * 16, 60 * 16));
      }
   }
	
	protected void generateLevel(){
		for (int i = 0; i < 10; i++){
			Random random = new Random();
			add(new Zombie(random.nextInt(20) + 3, random.nextInt(60) + 3));
		}
	}

}
