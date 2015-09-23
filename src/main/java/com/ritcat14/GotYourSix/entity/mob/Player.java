package com.ritcat14.GotYourSix.entity.mob;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.List;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.projectile.Projectile;
import com.ritcat14.GotYourSix.entity.projectile.TestProjectile;
import com.ritcat14.GotYourSix.graphics.AnimatedObject;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.SpriteSheet;
import com.ritcat14.GotYourSix.graphics.UI.UIActionListener;
import com.ritcat14.GotYourSix.graphics.UI.UIButton;
import com.ritcat14.GotYourSix.graphics.UI.UILabel;
import com.ritcat14.GotYourSix.graphics.UI.UIManager;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.graphics.UI.UIProgressBar;
import com.ritcat14.GotYourSix.input.Keyboard;
import com.ritcat14.GotYourSix.input.Mouse;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Player extends Mob {
	
   private String name;
	private Keyboard input;
	private double speed = 2.5;
	private AnimatedObject down = new AnimatedObject(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedObject up = new AnimatedObject(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedObject left = new AnimatedObject(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedObject right = new AnimatedObject(SpriteSheet.player_right, 32, 32, 3);
	
	private AnimatedObject animSprite = down;
	
	private int fireRate = 0;
   private int XPLevel = 0;
   private int XP = 0;
   private int hunger = 0;
   private int thirst = 0;
  
   private UIManager ui;
   private UIProgressBar UIHealthBar;
   private UIProgressBar UILevelBar;
   private UIProgressBar UIHungerBar;
   private UIProgressBar UIThirstBar;
   private UILabel xpLabel;
   private UIButton button;
	
  @Deprecated
	public Player(String name, Keyboard input) {
      this.name = name;
		this.input = input;
		animSprite = down;
      sprite = animSprite.getSprite();
      health = 100;
      XPLevel =1;
      XP = 0;
      hunger = 0;
      thirst = 0;
	}
	
	public Player(String name, double x, double y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		animSprite = down;
      sprite = animSprite.getSprite();
      System.out.println(sprite);
		fireRate = TestProjectile.FIRERATE;
     
      // Player default attributes
      health = 100;
      XPLevel =1;
      XP = 0;
      hunger = 0;
      thirst = 0;
      
      ui = Game.getUIManager();
      UIPanel panel = (UIPanel) new UIPanel(new Vector2i(Game.getWindowWidth(), 0), new Vector2i(60 * 5, Game.getWindowHeight())).setColor(0x363636);
      ui.addPanel(panel);
      panel.addComponent(((UILabel)new UILabel(new Vector2i(40, 300), name).setColor(0xB3B3B3)).setFont(new Font("Veranda", Font.BOLD, 24)));
     
      UIHealthBar = new UIProgressBar(new Vector2i(10, 315), new Vector2i((60 * 5) - 20, 20));
      UIHealthBar.setColor(0x545454);
      UIHealthBar.setForegroundColour(0xE03434);
      panel.addComponent(UIHealthBar);
      
      UILevelBar = new UIProgressBar(new Vector2i(10, 345), new Vector2i((60 * 5) - 20, 20));
      UILevelBar.setColor(0x545454);
      UILevelBar.setForegroundColour(0x548915);
      panel.addComponent(UILevelBar);
      
      UIHungerBar = new UIProgressBar(new Vector2i(10, 375), new Vector2i((60 * 5) - 20, 20));
      UIHungerBar.setColor(0x545454);
      UIHungerBar.setForegroundColour(0x511F16);
      panel.addComponent(UIHungerBar);
      
      UIThirstBar = new UIProgressBar(new Vector2i(10, 405), new Vector2i((60 * 5) - 20, 20));
      UIThirstBar.setColor(0x545454);
      UIThirstBar.setForegroundColour(0x0094FF);
      panel.addComponent(UIThirstBar);
      
      UILabel hpLabel = new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(2, 16)),"HP");
      hpLabel.setColor(0xFFE7EF);
      hpLabel.setFont(new Font("Veranda", Font.BOLD, 18));
      panel.addComponent(hpLabel);
     
      xpLabel = new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(2, 46)),"LVL " + XPLevel);
      xpLabel.setColor(0xFFE7EF);
      xpLabel.setFont(new Font("Veranda", Font.BOLD, 18));
      panel.addComponent(xpLabel);
     
      UILabel foodLabel = new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(2, 76)),"FOOD");
      foodLabel.setColor(0xFFE7EF);
      foodLabel.setFont(new Font("Veranda", Font.BOLD, 18));
      panel.addComponent(foodLabel);
     
      UILabel waterLabel = new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(2, 106)),"WATER");
      waterLabel.setColor(0xFFE7EF);
      waterLabel.setFont(new Font("Veranda", Font.BOLD, 18));
      panel.addComponent(waterLabel);
     
      button= new UIButton(new Vector2i(UIHealthBar.position).add(new Vector2i(2, 136)), new Vector2i(100, 30), new UIActionListener(){
          public void perform() {
              System.out.println("Button pressed!");
          }
      });
      button.setText("Hello");
      panel.addComponent(button);
	}
  
   public String getName(){
       return name;
   }
  
   public void inXP(int xp){
       XP += xp;
       if (XP >= 100){
           XPLevel += 1;
           XP = 0;
       }
   }
  
   public int getXP(){
       return XP;
   }
  
   public int getLevel(){
       return XPLevel;
   }
  
   private static int time = 0;
	public void update() {
      sprite = animSprite.getSprite();
      time++;
      if (time % 180 == 0 && thirst < 100 && hunger < 100){
          thirst += 2;
          hunger ++;
          //Game.loadBar.stop();
      }
      if (time % 180 == 0 && thirst>= 100) loseHealth(2);
      else if(time % 180 == 0 && hunger >= 100) loseHealth(1);
      if (time % 360 == 0 && health < 100) health += 1;
      UIHealthBar.setProgress(health / 100.0);
      UILevelBar.setProgress(XP / 100.0);
      UIHungerBar.setProgress(hunger / 100.0);
      UIThirstBar.setProgress(thirst / 100.0);
      xpLabel.setText("LVL "+XPLevel);
     
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if (fireRate > 0) fireRate --;
		double xa = 0, ya = 0;
		if(input.up) {
			ya -= speed;
			animSprite = up;
		}else if(input.down) {
			ya += speed;
			animSprite = down;
		}
		if(input.left) {
			xa -= speed;
			animSprite = left;
		} else if(input.right){
			xa += speed;
			animSprite = right;
		}
		
		if(xa != 0 || ya != 0){
			move(xa,ya);
			walking = true;
		}else{
			walking = false;
		}
		clear();
		updateShooting();
      enemyCollision();
	}
	
	private void clear() {
		for(int i = 0; i < level.getProjectiles().size(); i++){
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}
  
   private void enemyCollision(){
        List<Enemy> enemies = level.getEnemies();
        List<Player> players = level.getPlayers();
        for (int j = 0; j < players.size(); j++) {
            for (int i = 0; i < enemies.size(); i++) {
                Rectangle en = new Rectangle((int)(enemies.get(i).getX()) - 10, (int)(enemies.get(i).getY()) - 16, enemies.get(i).getSprite().getWidth() - 12, enemies.get(i).getSprite().getHeight());
                Rectangle pl = new Rectangle((int)(players.get(j).getX()) - 10, (int)(players.get(j).getY()) - 16, players.get(j).getSprite().getWidth() - 12, players.get(j).getSprite().getHeight());
                if (pl.intersects(en)) players.get(j).loseHealth(3);
            }
        }
   }

	private void updateShooting() {
		if(Mouse.getButton() == 1 && fireRate <= 0){
			double dx = Mouse.getX() - (Game.getWindowWidth()/2);
			double dy = Mouse.getY() - (Game.getWindowHeight()/2);
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = TestProjectile.FIRERATE;
		}
	}
  
   public void loseHealth(int damage){
     if(time % 180 == 0){
         if(health >= damage) health -= damage;
     }
        if (health <= 0){
            System.exit(0);
            remove();
        }
   }

	public void render(Screen screen) {
		screen.renderMob((int)(x - 16), (int)(y - 16), sprite);
	}
}