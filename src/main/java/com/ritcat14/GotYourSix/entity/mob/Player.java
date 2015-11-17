package com.ritcat14.GotYourSix.entity.mob;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.awt.event.MouseEvent;
import java.lang.*;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.mob.enemy.Enemy;
import com.ritcat14.GotYourSix.entity.projectile.*;
import com.ritcat14.GotYourSix.entity.spawner.LightSpawner;
import com.ritcat14.GotYourSix.events.*;
import com.ritcat14.GotYourSix.events.types.*;
import com.ritcat14.GotYourSix.graphics.*;
import com.ritcat14.GotYourSix.graphics.UI.*;
import com.ritcat14.GotYourSix.graphics.UI.menus.*;
import com.ritcat14.GotYourSix.input.*;
import com.ritcat14.GotYourSix.items.*;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.util.*;

public class Player extends Mob implements EventListener {
	
   public static boolean swimming = false, canShoot = true, changeLevel = false;
   public static Level levelToGo = null;
   public static int XPLevel = 1;
  
   public int XP = 0, hunger = 0, thirst = 0, fireRate = 0, stamina = 100;
  
  
   private static boolean madeItem = false;
   private static String name = null;
   private static Weapons    w = null;
  
   private boolean hit = false, dying = false, shooting = false, changePlayer = true, inventOpen = false, mapOpen = false;
	private double speed = 1.5, invin = 1;
   private int staminaDec = 15, staminaInc = 3;
  
	private AnimatedObject down = null, up = null, left = null, right = null; // All the movement animatedObjects. These are set depending on the player you choose in the selection screen
   private Sprite weapon = null;
	private AnimatedObject animSprite = null;

   private BufferedImage image = null;
	private Keyboard input = null;  
   private Game game = Game.getGame();
  
   private List<Item> items = null;
   private List<Projectile> shots = null;
  
   private static Inventory invent = new Inventory();
   private Minimap m = new Minimap();
   private UIManager         ui = null;
   private CustomFont font = new CustomFont();
	
   @Deprecated
	public Player(String name, Keyboard input) {
      this.name = name;
		this.input = input;
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
      
      if (FileHandler.fileExists(FileHandler.netUserDir + name + ".txt")) setStats(FileHandler.getStats());
      AnimatedObject.init();
     
      Arrow a = new Arrow();
      avShots.add(a);
      Cannon c = new Cannon();
      avShots.add(c);
      if (StartScreen.state == StartScreen.playerViewState.MF || StartScreen.state == StartScreen.playerViewState.FF){
        FirArrow fa = new FirArrow();
        avShots.add(fa);
        FirCannon fc = new FirCannon();
        avShots.add(fc);
        FirBall fb = new FirBall();
        avShots.add(fb);
        FirWall fw = new FirWall();
        avShots.add(fw);
      } else if (StartScreen.state == StartScreen.playerViewState.MI || StartScreen.state == StartScreen.playerViewState.FI){
        IcArrow ia = new IcArrow();
        avShots.add(ia);
        IcCannon ic = new IcCannon();
        avShots.add(ic);
        IcBall ib = new IcBall();
        avShots.add(ib);
        IcWall iw = new IcWall();
        avShots.add(iw);
      }
      ui = game.getUIManager();
      w = new Weapons();
      ui.addPanel(w);
      if (w != null) fireRate = getShots().get(w.getSelected() - 1).FIRERATE;
      
      Stats s = new Stats();
      ui.addPanel(s);
      
      m.setLevel(Level.spawn);
     
      up = AnimatedObject.up;
      down = AnimatedObject.down;
      left = AnimatedObject.left;
      right = AnimatedObject.right;
		animSprite = down;
      sprite = animSprite.getSprite();
	}
  
   public List<Projectile> getShots(){
     return avShots;
   }
  
   public BufferedImage getFace(){
       return image;
   }
  
   public static String getName(){
       return name;
   }
  
   public static Inventory getInvent(){
     return invent;
   }
  
   public String getStats(){
     String nl = "\n";
     return health + nl + hunger + nl + thirst + nl + XPLevel + nl + XP + nl + StartScreen.state.toString() + nl;
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
 
   public void setStats(String stats){
       String[] parts = stats.split("\n");
       health = Integer.parseInt(parts[0]);
       hunger = Integer.parseInt(parts[1]);
       thirst = Integer.parseInt(parts[2]);
       XPLevel = Integer.parseInt(parts[3]);
       XP = Integer.parseInt(parts[4]);
       StartScreen.state = StartScreen.playerViewState.valueOf(parts[5]);
   }
  
   public static int getLevel(){
       return XPLevel;
   }
  
   public int getStamina(){
     return stamina;
   }
  
   public int getHealth(){
     return health;
   }
  
   public int getHunger(){
     return hunger;
   }
  
   public int getThirst(){
     return thirst;
   }
  
   public void setLocation(Vector2i p){
       this.x = p.x;
       this.y = p.y;
   }
  
   public void onEvent(Event event) {
       EventDispatcher dispatcher = new EventDispatcher(event);
       dispatcher.dispatch(Event.Type.MOUSE_PRESSED, new EventHandler() {
           public boolean onEvent(Event event) {
             return onMousePressed((MousePressedEvent) event);
           }
       });
       dispatcher.dispatch(Event.Type.MOUSE_RELEASED, new EventHandler() {
           public boolean onEvent(Event event) {
             return onMouseReleased((MouseReleasedEvent) event);
           }
       });
       dispatcher.dispatch(Event.Type.MOUSE_MOVED, new EventHandler() {
         public boolean onEvent(Event event){
           return onMouseMoved((MouseMovedEvent) event);
         }
       });
   }
   private int timeOpen = 0;
   private int timeClosed = 0;
   public void updateInvent(){
       if (input.invnt && !inventOpen && timeClosed >= 30 && !mapOpen){
          //open invent
          ui.addPanel(invent);
          Game.paused = true;
          inventOpen = true;
          timeClosed = 0;
      } else if (input.invnt && inventOpen && timeOpen >= 30){
          //remove invent
          ui.removePanel(invent);
          Game.paused = false;
          inventOpen = false;
          timeOpen = 0;
      } else if (inventOpen) timeOpen++;
        else if (!inventOpen) timeClosed++;
   }
   private int mapTimeClosed = 0;
   public void updateMap(){
     if (input.map && !mapOpen && mapTimeClosed >= 30 && !inventOpen){
       //open map
       ui.addPanel(m);
       m.setLevel(level);
       //Game.paused = true;
       mapOpen = true;
       mapTimeClosed = 0;
     } else if (input.map && mapOpen && timeOpen >= 30){
       //remove map
       ui.removePanel(m);
       //Game.paused = false;
       mapOpen = false;
       timeOpen = 0;
     } else if (mapOpen) timeOpen ++;
       else if (!mapOpen) mapTimeClosed ++;
   }
  
   private void updateStats(int time){
      if (hit && time % 5 == 0){
        invin-= 0.5;
       if (invin <= 0){
         invin = 1;
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
     
      w.update();
      
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
	}
  
   private void updateShooting(){
       if (!shooting || fireRate > 0) return;
       double dx = Mouse.getX() - (Game.getWindowWidth()/2);
       double dy = Mouse.getY() - (Game.getWindowHeight()/2);
       double dir = Math.atan2(dy, dx);
       shoot(x, y, dir);
       w.removeWep();
       if (w != null) fireRate = avShots.get(w.getSelected() - 1).FIRERATE;
   }
  
   public boolean onMousePressed(MousePressedEvent e) {
      if (Mouse.getX() > Game.getWindowWidth()) return false;
		if(e.getButton() == MouseEvent.BUTTON1 && canShoot && w.canShoot()){
           shooting = true;
           return true;
		} else return true;
   }
  
   public boolean onMouseReleased(MouseReleasedEvent e) {
       if (e.getButton() == MouseEvent.BUTTON1) {
         shooting = false;
         return true;
       } else return false;
   }
  
   public boolean onMouseMoved(MouseMovedEvent e) {
     if (Mouse.getX() > Game.getWindowWidth()) {
       shooting = false;
       return true;
     } else {
       if (e.getDragged()) {
         shooting = true;
         return true;
       }
       return false;
     }
   }
  
  private void checkWep(){
      if (StartScreen.state == StartScreen.playerViewState.MF || StartScreen.state == StartScreen.playerViewState.FF){
        if (XPLevel == 25){
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
      } else if (StartScreen.state == StartScreen.playerViewState.MI || StartScreen.state == StartScreen.playerViewState.FI){
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
  
   public void loseHealth(int damage){
        health -= damage;
        if (health <= 0){
            Game.STATE = Game.State.PAUSE;
            health = 1;
        }
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
               if (items.get(i) instanceof Weapon) invent.add((Item)items.get(i));
               //else panel.getInvent().add(items.get(i));
               items.get(i).remove();
           }
       }
   }

	public void render(Screen screen) {
		screen.renderMob((int)(x - 16), (int)(y - 16), sprite);
      font.render(0,0,0xffaaaaaa,name + " Level " + XPLevel,screen);
	}
}