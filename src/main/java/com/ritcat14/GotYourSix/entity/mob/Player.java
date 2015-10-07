package com.ritcat14.GotYourSix.entity.mob;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.projectile.Projectile;
import com.ritcat14.GotYourSix.entity.projectile.TestProjectile;
import com.ritcat14.GotYourSix.graphics.AnimatedObject;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.graphics.UI.Inventory;
import com.ritcat14.GotYourSix.graphics.UI.Weapons;
import com.ritcat14.GotYourSix.graphics.UI.Minimap;
import com.ritcat14.GotYourSix.graphics.UI.UIActionListener;
import com.ritcat14.GotYourSix.graphics.UI.UIButton;
import com.ritcat14.GotYourSix.graphics.UI.UILabel;
import com.ritcat14.GotYourSix.graphics.UI.UIManager;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.graphics.UI.UIProgressBar;
import com.ritcat14.GotYourSix.input.Keyboard;
import com.ritcat14.GotYourSix.input.Mouse;
import com.ritcat14.GotYourSix.items.*;
import com.ritcat14.GotYourSix.items.Item;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Player extends Mob {
	
   private String name;
	private Keyboard input;
	private double speed = 1.5;
   public static boolean swimming = false;
   public static boolean canShoot = true;
   public static boolean changeLevel = false;
   public static Level levelToGo;
   
	private AnimatedObject down;
	private AnimatedObject up;
	private AnimatedObject left;
	private AnimatedObject right;
  
	private AnimatedObject upSwim;
	private AnimatedObject downSwim;
	private AnimatedObject leftSwim;
	private AnimatedObject rightSwim;
   private Sprite weapon;
	
	private AnimatedObject animSprite;
	
	private int fireRate = 0;
   private static int XPLevel = 1;
   private int XP = 0;
   private int hunger = 0;
   private int thirst = 0;
   private int stamina = 100;
   private int staminaDec = 15;
   private int staminaInc = 3;
  
   private UIManager ui;
   private UIManager mui;
   private UIProgressBar UIHealthBar;
   private UIProgressBar UILevelBar;
   private UIProgressBar UIHungerBar;
   private UIProgressBar UIThirstBar;
   private UIProgressBar UIStaminaBar;
   private UIPanel weapons;
   private Inventory invent;
   private Weapons w;
   private UILabel xpLabel;
   private UIButton button;
   private UIButton face;
   private BufferedImage image;
   private Minimap map;
   private Game game = Game.getGame();
  
   private static boolean madeItem = false;
   private boolean changePlayer = true;
  
   public static enum Type {
     FIRE, FIREKING, ICE, ICEKING
   }
  
   public static Type type;
	
  @Deprecated
	public Player(String name, Keyboard input) {
      this.name = name;
		this.input = input;
      checkSprite();
		animSprite = down;
      sprite = animSprite.getSprite();
     
      // Player default attributes
      health = 100;
      XP = 0;
      hunger = 0;
      thirst = 0;
      stamina = 100;
      staminaDec = 15;
      staminaInc = 3;
	}
	
	public Player(String name, double x, double y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
     
      // Player default attributes
      health = 100;
      XP = 0;
      hunger = 0;
      thirst = 0;
      stamina = 100;
      staminaDec = 15;
      staminaInc = 3;
      
      ui = Game.getUIManager();
      mui = Game.getMapManager();
      BufferedImage back = ImageUtil.getImage("/ui/bars/back.png");
      int fontCol = 0xFFE7EF;
      Font font = new Font("Serif", Font.BOLD + Font.ITALIC, 24);
      if (type == Type.FIRE || type == Type.FIREKING){
          back = ImageUtil.getImage("/ui/bars/back.png");
          fontCol = 0xE83E44;
      }else if (type == Type.ICE || type == Type.ICEKING){
          back = ImageUtil.getImage("/ui/bars/back.png");
          fontCol = 0x417FEA;
      }
      UIPanel panel = (UIPanel) new UIPanel(new Vector2i(Game.getWindowWidth(), 0), new Vector2i(60 * 5, Game.getWindowHeight())).setColor(0x363636);
      ui.addPanel(panel);
      map = new Minimap(new Vector2i(Game.getWindowWidth(), 0));
      mui.addPanel(map);
      panel.addComponent(((UILabel)new UILabel(new Vector2i(250, 330), name).setColor(0xB3B3B3)).setFont(new Font("Veranda", Font.BOLD, 24)));
     
      UIHealthBar = new UIProgressBar(new Vector2i(20, 345), back, ImageUtil.getImage("/ui/bars/healthFront.png"));
      panel.addComponent(UIHealthBar);
      
      UIStaminaBar = new UIProgressBar(new Vector2i(20, 405), back, ImageUtil.getImage("/ui/bars/staminaFront.png"));
      panel.addComponent(UIStaminaBar);
      
      UILevelBar = new UIProgressBar(new Vector2i(20, 465), back, ImageUtil.getImage("/ui/bars/xpFront.png"));
      panel.addComponent(UILevelBar);
      
      UIHungerBar = new UIProgressBar(new Vector2i(20, 525), back, ImageUtil.getImage("/ui/bars/foodFront.png"));
      panel.addComponent(UIHungerBar);
      
      UIThirstBar = new UIProgressBar(new Vector2i(20, 585), back, ImageUtil.getImage("/ui/bars/waterFront.png"));
      UIThirstBar.setColor(0x545454);
      UIThirstBar.setForegroundColour(0x0094FF);
      panel.addComponent(UIThirstBar);
      
      UILabel hpLabel = ((UILabel)new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(-5, -16)),"HP").setColor(fontCol)).setFont(font);
      panel.addComponent(hpLabel);
     
      UILabel staminaLabel = ((UILabel)new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(-5, 46)),"STAMINA").setColor(fontCol)).setFont(font);
      panel.addComponent(staminaLabel);
     
      xpLabel = ((UILabel)new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(-5, 106)),"LVL " + XPLevel).setColor(fontCol)).setFont(font);
      panel.addComponent(xpLabel);
     
      UILabel foodLabel = ((UILabel)new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(-5, 166)),"HUNGER").setColor(fontCol)).setFont(font);
      panel.addComponent(foodLabel);
     
      UILabel waterLabel = ((UILabel)new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(-5, 226)),"THIRST").setColor(fontCol)).setFont(font);
      panel.addComponent(waterLabel);
     
      button= new UIButton(new Vector2i(UIThirstBar.position).add(new Vector2i(2, 60)), new Vector2i(100, 30), new UIActionListener(){
          public void perform() {
              //Change level
              if (changeLevel) Game.changeLevel(levelToGo);
          }
      });
      button.setText("Enter");
      panel.addComponent(button);     
     
      checkSprite();
		animSprite = down;
      sprite = animSprite.getSprite();
		fireRate = TestProjectile.FIRERATE;
     
      if (image != null){
      face = new UIButton(new Vector2i(200, 330 - 20),image, new UIActionListener() {
          public void perform() {
              Game.STATE = Game.State.PAUSE;
          }
      });
     panel.addComponent(face);
      }
      //invent = new Inventory(new Vector2i((Game.getWindowWidth() + (60 * 5)) - 275, Game.getWindowHeight() - 200), "inventoryBack.png");
      //ui.addPanel(invent);
      if (this != null){
          w = new Weapons();
          ui.addPanel(w);
      }
	}
  
   public String getName(){
       return name;
   }
  
   public void inXP(int xp){
       XP += xp;
       if (XP >= 100){
           levelIn();
           XP = 0;
           health = 100;
       }
   }
  
   private void levelIn(){
       XPLevel ++;
       checkWep();
   }
  
   public int getXP(){
       return XP;
   }
  
   public static int getLevel(){
       return XPLevel;
   }
  
   public void setLocation(Vector2i p){
       this.x = p.x;
       this.y = p.y;
   }
  
   private static int time = 0;
	public void update() {
      makeItem("FireBall");
      if (level != null) map.setLevel(level);
      checkSprite();
      sprite = animSprite.getSprite();
      time++;
      if (time % 180 == 0 && thirst < 100 && hunger < 100){
          thirst += 2;
          hunger ++;
      }
      if (time % 180 == 0 && thirst>= 100) loseHealth(2);
      else if(time % 180 == 0 && hunger >= 100) loseHealth(1);
      if (time % 60 == 0 && health < 100) health += 1;
      
      UIHealthBar.setProgress(health / 100.0);
      UILevelBar.setProgress(XP / 100.0);
      UIHungerBar.setProgress(hunger / 100.0);
      UIThirstBar.setProgress(thirst / 100.0);
      UIStaminaBar.setProgress(stamina / 100.0);
      xpLabel.setText("LVL "+XPLevel);
      
		if (walking) animSprite.update(); 
      else animSprite.setFrame(0);
      
		if (fireRate > 0) fireRate --;
      
		double xa = 0, ya = 0;
      if (input.sprint && time % 100 == 0 && stamina > staminaDec && walking) stamina -= staminaDec;
      else if (input.sprint && stamina > staminaDec && walking) speed = 2.5;
      else speed = 1.5;
      
      if (stamina < 100 && !input.sprint && time % 60 == 0) stamina += staminaInc;
      if (stamina > 100 - staminaInc) stamina = 100;
      
		if(input.up) {
			ya -= speed;
         if (swimming) animSprite = upSwim;
			else animSprite = up;
		}else if(input.down) {
			ya += speed;
         if (swimming) animSprite = downSwim;
			else animSprite = down;
		}
		if(input.left) {
			xa -= speed;
         if (swimming) animSprite = leftSwim;
			else animSprite = left;
		} else if(input.right){
			xa += speed;
         if (swimming) animSprite = rightSwim;
			else animSprite = right;
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
      if(XPLevel >= 100){
          if (type == Type.FIRE) type = Type.FIREKING;
          else if (type == Type.ICE) type = Type.ICEKING;
          checkSprite();
      }
	}
  
  private void checkWep(){
      if (type == Type.FIRE || type == Type.FIREKING){
        if (XPLevel == 50){
          for (int i = 0; i < XPLevel / 5; i++){
            FireWall ib = new FireWall();
            w.add(ib);
          }
        } else if (XPLevel == 40){
          for (int i = 0; i < XPLevel / 5; i++){
            FireBall ib = new FireBall();
            w.add(ib);
          }
        } else if (XPLevel == 30){
          for (int i = 0; i < XPLevel / 5; i++){
            FireCannon ib = new FireCannon();
            w.add(ib);
          }
        } else if (XPLevel == 20){
          for (int i = 0; i < XPLevel / 5; i++){
            FireArrow ib = new FireArrow();
            w.add(ib);
          }
        }
      } else if (type == Type.ICE || type == Type.ICEKING){
        if (XPLevel == 50){
          for (int i = 0; i < XPLevel / 5; i++){
            IceWall ib = new IceWall();
            w.add(ib);
          }
        } else if (XPLevel == 40){
          for (int i = 0; i < XPLevel / 5; i++){
            IceBall ib = new IceBall();
            w.add(ib);
          }
        } else if (XPLevel == 30){
          for (int i = 0; i < XPLevel / 5; i++){
            IceCannon ib = new IceCannon();
            w.add(ib);
          }
        } else if (XPLevel == 20){
          for (int i = 0; i < XPLevel / 5; i++){
            IceArrow ib = new IceArrow();
            w.add(ib);
          }
        }
      }
      if (XPLevel == 10){
          for (int i = 0; i < XPLevel / 5; i++){
            CannonBall ib = new CannonBall();
            w.add(ib);
          }
        } else if (XPLevel == 1){
          TestProjectile.weapon = TestProjectile.Weapon.ARROW;
        }
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
		if(Mouse.getButton() == 1 && fireRate <= 0 && canShoot){
			double dx = Mouse.getX() - (Game.getWindowWidth()/2);
			double dy = Mouse.getY() - (Game.getWindowHeight()/2);
			double dir = Math.atan2(dy, dx);
         if (w.canShoot()){
           shoot(x, y, dir);
           w.removeWep();
         }
			fireRate = TestProjectile.FIRERATE;
		}
	}
  
   public void loseHealth(int damage){
        health -= damage;
        if (health <= 0){
            Game.changeLevel(Level.spawn);
            health = 1;
        }
   }
   public static Item getItem(){
     Random ran = new Random();
     int i;
     if (type == Type.FIRE || type == Type.FIREKING){
         i = ran.nextInt(4);
         if (i == 0){
           CannonBall cb = new CannonBall();
           return cb;
         } else if (i == 1){
           FireArrow fa = new FireArrow();
           return fa;
         } else if (i == 2){
           FireCannon fc = new FireCannon();
           return fc;
         } else if (i == 3){
           FireBall fb = new FireBall();
           return fb;
         } else if (i == 4){
           FireWall fw = new FireWall();
           return fw;
         }
     } else if (type == Type.ICE || type == Type.FIREKING){
         i = ran.nextInt(4);
         if (i == 0){
           CannonBall cb = new CannonBall();
           return cb;
         } else if (i == 1){
           IceArrow ia = new IceArrow();
           return ia;
         } else if (i == 2){
           IceCannon ic = new IceCannon();
           return ic;
         } else if (i == 3){
           IceBall ib = new IceBall();
           return ib;
         } else if (i == 4){
           IceWall iw = new IceWall();
           return iw;
         }
     }
     CannonBall c = new CannonBall();
     return c;
   }
   
   private void checkSprite(){
     if(changePlayer){
       if (type == Type.FIREKING){
           up = AnimatedObject.fireKingUp;
           down = AnimatedObject.fireKingDown;
           left = AnimatedObject.fireKingLeft;
           right = AnimatedObject.fireKingRight;
           upSwim = AnimatedObject.fireKingUpSwim;
           downSwim = AnimatedObject.fireKingDownSwim;
           leftSwim = AnimatedObject.fireKingLeftSwim;
           rightSwim = AnimatedObject.fireKingRightSwim;
           image = ImageUtil.getImage("/ui/buttons/playerFireKing.png");
       } else if (type == Type.ICEKING){
           up = AnimatedObject.iceKingUp;
           down = AnimatedObject.iceKingDown;
           left = AnimatedObject.iceKingLeft;
           right = AnimatedObject.iceKingRight;
           upSwim = AnimatedObject.iceKingUpSwim;
           downSwim = AnimatedObject.iceKingDownSwim;
           leftSwim = AnimatedObject.iceKingLeftSwim;
           rightSwim = AnimatedObject.iceKingRightSwim;
           image = ImageUtil.getImage("/ui/buttons/playerIceKing.png");
     } else if (type == Type.ICE){
           up = AnimatedObject.iceSpriteUp;
           down = AnimatedObject.iceSpriteDown;
           left = AnimatedObject.iceSpriteLeft;
           right = AnimatedObject.iceSpriteRight;
           upSwim = AnimatedObject.iceSpriteUpSwim;
           downSwim = AnimatedObject.iceSpriteDownSwim;
           leftSwim = AnimatedObject.iceSpriteLeftSwim;
           rightSwim = AnimatedObject.iceSpriteRightSwim;
           image = ImageUtil.getImage("/ui/buttons/playerIce.png");
     } else if (type == Type.FIRE){
           up = AnimatedObject.fireSpriteUp;
           down = AnimatedObject.fireSpriteDown;
           left = AnimatedObject.fireSpriteLeft;
           right = AnimatedObject.fireSpriteRight;
           upSwim = AnimatedObject.fireSpriteUpSwim;
           downSwim = AnimatedObject.fireSpriteDownSwim;
           leftSwim = AnimatedObject.fireSpriteLeftSwim;
           rightSwim = AnimatedObject.fireSpriteRightSwim;
           image = ImageUtil.getImage("/ui/buttons/playerFire.png");
     }
       changePlayer = false;
   }
   }
  
   public Inventory getInvent(){
       return invent;
   }
  
   public Weapons getWepInvent(){
       return w;
   }
  
   public static void makeItem(String item){
      if(!madeItem){
          if (item == "FireBall"){
            FireBall f = new FireBall(new Vector2i(5 * 16, 5 * 16));
            if (level != null) level.add(f);
          }
          madeItem = true;
      }
   }

	public void render(Screen screen) {
		screen.renderMob((int)(x - 16), (int)(y - 16), sprite);
	}
}