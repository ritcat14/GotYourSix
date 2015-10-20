package com.ritcat14.GotYourSix.entity.mob;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.projectile.*;
import com.ritcat14.GotYourSix.graphics.*;
import com.ritcat14.GotYourSix.graphics.UI.*;
import com.ritcat14.GotYourSix.graphics.UI.menus.Inventory;
import com.ritcat14.GotYourSix.graphics.UI.menus.Minimap;
import com.ritcat14.GotYourSix.graphics.UI.menus.Weapons;
import com.ritcat14.GotYourSix.input.*;
import com.ritcat14.GotYourSix.items.*;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Player extends Mob {
	
   private static String name;
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
  
	private AnimatedObject upShoot;
	private AnimatedObject downShoot;
	private AnimatedObject leftShoot;
	private AnimatedObject rightShoot;
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
   private double invin = 1.5;
   private boolean hit = false;
   private boolean dying = false;
   private boolean shooting = false;
  
   private UIManager ui;
   private UIManager mui;
   private UIProgressBar UIHealthBar;
   private UIProgressBar UILevelBar;
   private UIProgressBar UIHungerBar;
   private UIProgressBar UIThirstBar;
   private UIProgressBar UIStaminaBar;
   private UIPanel weapons;
   private Inventory invent;
   private static Weapons w;
   private UILabel xpLabel;
   private UIButton button;
   private UIButton face;
   private BufferedImage image;
   private Minimap map;
   private Game game = Game.getGame();
  
   private static boolean madeItem = false;
   private boolean changePlayer = true;
   private List<Item> items;
   private List<Projectile> shots;
  
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
      this.name = name;
     
      // Player default attributes
      health = 100;
      XP = 0;
      hunger = 0;
      thirst = 0;
      stamina = 100;
      staminaDec = 15;
      staminaInc = 3;
     
      Arrow a = new Arrow();
      avShots.add(a);
      Cannon c = new Cannon();
      avShots.add(c);
      if (type == Type.FIRE || type == Type.FIREKING){
        FirArrow fa = new FirArrow();
        avShots.add(fa);
        FirCannon fc = new FirCannon();
        avShots.add(fc);
        FirBall fb = new FirBall();
        avShots.add(fb);
        FirWall fw = new FirWall();
        avShots.add(fw);
      } else if (type == Type.ICE || type == Type.ICEKING){
        IcArrow ia = new IcArrow();
        avShots.add(ia);
        IcCannon ic = new IcCannon();
        avShots.add(ic);
        IcBall ib = new IcBall();
        avShots.add(ib);
        IcWall iw = new IcWall();
        avShots.add(iw);
      }
      
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
      UIPanel panel = (UIPanel) new UIPanel(new Vector2i(Game.getWindowWidth(), 0), new Vector2i(60 * 5, Game.getWindowHeight())).setColor(0xff464646);
      ui.addPanel(panel);
      map = new Minimap(new Vector2i(Game.getWindowWidth(), 0));
      mui.addPanel(map);
      panel.addComponent(((UILabel)new UILabel(new Vector2i(250, 330), name).setColor(0xff464646)).setFont(new Font("Veranda", Font.BOLD, 24)));
     
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
      }, "ENTER");
      panel.addComponent(button);     
     
      checkSprite();
		animSprite = down;
      sprite = animSprite.getSprite();
     
      if (image != null){
      face = new UIButton(new Vector2i(200, 330 - 20),image, new UIActionListener() {
          public void perform() {
              Game.STATE = Game.State.PAUSE;
          }
      },"");
     panel.addComponent(face);
      }
      //invent = new Inventory(new Vector2i((Game.getWindowWidth() + (60 * 5)) - 275, Game.getWindowHeight() - 200), "inventoryBack.png");
      //ui.addPanel(invent);
      if (this != null){
          w = new Weapons();
          ui.addPanel(w);
      }
		if (w != null) fireRate = avShots.get(w.getSelected() - 1).FIRERATE;
	}
  
   public static String getName(){
       return name;
   }
  
   public static String getGroup(){
     return "Clan";
   }
  
   public static String getStats(){
     return "";
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
  
   private void updateStats(int time){
      if (hit && time % 30 == 0){
        invin-= 0.5;
       if (invin <= 0){
         invin = 3;
         hit = false;
       }
      }
      if (time % 180 == 0 && thirst < 100) thirst += 2;
      if (time % 180 == 0 && hunger < 100) hunger += 1;
      if (time % 180 == 0 && thirst>= 100){
        loseHealth(4);
        dying = true;
      } else if(time % 180 == 0 && hunger >= 100){
        loseHealth(2);
        dying = true;
      } else {
        dying = false;
      }
      if (time % 60 == 0 && health < 100 && !hit && !dying) health ++;
      
      UIHealthBar.setProgress(health / 100.0);
      UILevelBar.setProgress(XP / 100.0);
      UIHungerBar.setProgress(hunger / 100.0);
      UIThirstBar.setProgress(thirst / 100.0);
      UIStaminaBar.setProgress(stamina / 100.0);
      xpLabel.setText("LVL "+XPLevel);
      
      if (input.sprint && time % 100 == 0 && stamina > staminaDec && walking) stamina -= staminaDec;
      else if (input.sprint && stamina > staminaDec && walking) speed = 2.5;
      else speed = 1.5;
      
      if (stamina < 100 && !input.sprint && time % 60 == 0) stamina += staminaInc;
      if (stamina > 100 - staminaInc) stamina = 100;
   }
  
   private static int time = 0;
	public void update() {
      if (level != null) shots = level.getProjectiles();
      items = level.getItems();
      if (level != null) map.setLevel(level);
      
      checkSprite();
      sprite = animSprite.getSprite();
      time++;
     
      updateStats(time);
      
		if (walking || shooting) animSprite.update(); 
      else animSprite.setFrame(0);
      
		if (fireRate > 0) fireRate --;
      
		double xa = 0, ya = 0;
      if (walking && !shooting){
        if (input.up) animSprite = up;
        if (input.down) animSprite = down;
        if (input.left) animSprite = left;
        if (input.right) animSprite = right;
      }
		if(input.up) {
			ya -= speed;
		}else if(input.down) {
			ya += speed;
		}
		if(input.left) {
			xa -= speed;
		} else if(input.right){
			xa += speed;
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
      itemCollision();
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
          Projectile.weapon = Projectile.Weapon.ARROW;
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
                if (pl.intersects(en) &&!hit){
                  players.get(j).loseHealth(3);
                  hit = true;
                }
            }
        }
   }

	private void updateShooting() {
		if(Mouse.getButton() == 1 && canShoot && w.canShoot()){
           shooting = true;
			double dx = Mouse.getX() - (Game.getWindowWidth()/2);
			double dy = Mouse.getY() - (Game.getWindowHeight()/2);
			double dir = Math.atan2(dy, dx);
         System.out.println(dir);
           if (dir >= -2.35619449 && dir <= -0.7853981634){
             animSprite = upShoot;
           } else if (dir <= 2.35619449 && dir <= 0.7853981634){
             animSprite = leftShoot;//
           } else if (dir <= 2.35619449 && dir >= -2.35619449){
             animSprite = downShoot;//
           } else if (dir >= -0.7853981634 && dir <= 0.7853981634){
             animSprite = rightShoot;
           }
           if (fireRate <= 0){
           		shoot(x, y, dir);
           		w.removeWep();
               if (w != null) fireRate = avShots.get(w.getSelected() - 1).FIRERATE;
           }
		} else shooting = false;
	}
  
   public void loseHealth(int damage){
        health -= damage;
        if (health <= 0){
            Game.STATE = Game.State.PAUSE;
            health = 1;
        }
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
           upShoot = AnimatedObject.fireSpriteUpShoot;
           downShoot = AnimatedObject.fireSpriteDownShoot;
           leftShoot = AnimatedObject.fireSpriteLeftShoot;
           rightShoot = AnimatedObject.fireSpriteRightShoot;
           image = ImageUtil.getImage("/ui/buttons/playerFire.png");
     }
       changePlayer = false;
   }
   }
  
   public Inventory getInvent(){
       return invent;
   }
  
   public static Weapons getWepInvent(){
       return w;
   }
  
  
   private void itemCollision(){
       for (int i = 0; i < items.size(); i++){
           if (time % 60 == 0) items.get(i).inLife();
           Rectangle r = new Rectangle(items.get(i).position.x, items.get(i).position.y, items.get(i).sprite.getWidth(), items.get(i).sprite.getHeight());
           if (r.x >= getBounds().x && r.x <= getBounds().x + getBounds().width
               && r.y >= getBounds().y && r.y <= getBounds().y + getBounds().height){
               if (items.get(i) instanceof Weapon) w.add((Weapon)items.get(i));
               else invent.add(items.get(i));
               items.get(i).remove();
           }
       }
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