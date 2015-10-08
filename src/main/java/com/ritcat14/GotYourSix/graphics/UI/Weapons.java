package com.ritcat14.GotYourSix.graphics.UI;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Font;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.projectile.Projectile;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.input.Keyboard;
import com.ritcat14.GotYourSix.items.CannonBall;
import com.ritcat14.GotYourSix.items.FireArrow;
import com.ritcat14.GotYourSix.items.IceArrow;
import com.ritcat14.GotYourSix.items.IceBall;
import com.ritcat14.GotYourSix.items.IceCannon;
import com.ritcat14.GotYourSix.items.IceWall;
import com.ritcat14.GotYourSix.items.FireBall;
import com.ritcat14.GotYourSix.items.FireCannon;
import com.ritcat14.GotYourSix.items.FireWall;
import com.ritcat14.GotYourSix.items.Weapon;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.graphics.UI.UILabel;

public class Weapons extends UIPanel {

    private int                   wepNum      = 1;
    private int                   fireBalls   = 0;
    private int                   selectedWep = 5;
    private int                   x, y;
    private ArrayList<CannonBall> cannons     = new ArrayList<CannonBall>();
    private ArrayList<FireArrow>  firearrows  = new ArrayList<FireArrow>();
    private ArrayList<FireBall>   fireballs   = new ArrayList<FireBall>();
    private ArrayList<FireCannon> firecannons = new ArrayList<FireCannon>();
    private ArrayList<FireWall>   firewalls   = new ArrayList<FireWall>();
    private ArrayList<IceArrow>   icearrows   = new ArrayList<IceArrow>();
    private ArrayList<IceBall>    iceballs    = new ArrayList<IceBall>();
    private ArrayList<IceCannon>  icecannons  = new ArrayList<IceCannon>();
    private ArrayList<IceWall>    icewalls    = new ArrayList<IceWall>();
    private ArrayList<UILabel>    labels      = new ArrayList<UILabel>();
    private ArrayList<UIPanel>    weps        = new ArrayList<UIPanel>();
    private UIPanel               weapon;
    private UILabel               amount;
    private UIManager				 ui;
    private BufferedImage         weaponImg;
    private Keyboard key = Game.getKeyboard();

    public Weapons() {
        super(new Vector2i((Game.getWindowWidth()-180), Game.getWindowHeight() - 80), new Vector2i(6 * 80, 80),
              ImageUtil.getImage("/ui/weaponBarBack.png"));
        this.x = (Game.getWindowWidth()-180);
        this.y = Game.getWindowHeight() - 80;
        ui = new UIManager();
        ui.addPanel(this);
        for (int i = 0; i < 6; i++) {
            int num = (i + 1);
            if (Player.type == Player.Type.FIRE || Player.type == Player.Type.FIREKING) {
                weaponImg = ImageUtil.getImage("/ui/panels/weapons/fire/fireWeapon" + num + ".png");
            } else if (Player.type == Player.Type.ICE || Player.type == Player.Type.ICEKING) {
                weaponImg = ImageUtil.getImage("/ui/panels/weapons/ice/iceWeapon" + num + ".png");
            }
            weapon = new UIPanel(new Vector2i(x + (i * 80), y), new Vector2i(weaponImg.getWidth(), weaponImg.getHeight()), weaponImg);
            addComponent(weapon);
            if (num == 1) amount = new UILabel(weapon.position, "∞");
            else if (num == 2) amount = new UILabel(weapon.position, "" + cannons.size());
            if (Player.type == Player.Type.FIRE || Player.type == Player.Type.FIREKING) {
                if (num == 3) amount = new UILabel(weapon.position, "" + firearrows.size());
                else if (num == 4) amount = new UILabel(weapon.position, "" + firecannons.size());
                else if (num == 5) amount = new UILabel(weapon.position, "" + fireballs.size());
                else if (num == 6) amount = new UILabel(weapon.position, "" + firewalls.size());
            } else if (Player.type == Player.Type.ICE || Player.type == Player.Type.ICEKING) {
                if (num == 3) amount = new UILabel(weapon.position, "" + icearrows.size());
                else if (num == 4) amount = new UILabel(weapon.position, "" + icecannons.size());
                else if (num == 5) amount = new UILabel(weapon.position, "" + iceballs.size());
                else if (num == 6) amount = new UILabel(weapon.position, "" + icewalls.size());
            }
            labels.add(amount);
            weapon.addComponent(amount);
            amount.setFont(new Font("Helvetica", Font.BOLD, 15)).setColor(0xff7F7F7F);
            weps.add(weapon);
        }
        changeWeapon(1);
    }

    public void add(Weapon wep) {
        if (wep instanceof FireBall) fireballs.add((FireBall)wep);
        else if (wep instanceof CannonBall) cannons.add((CannonBall)wep);
        else if (wep instanceof FireArrow) firearrows.add((FireArrow)wep);
        else if (wep instanceof FireCannon) firecannons.add((FireCannon)wep);
        else if (wep instanceof FireWall) firewalls.add((FireWall)wep);
        else if (wep instanceof IceArrow) icearrows.add((IceArrow)wep);
        else if (wep instanceof IceBall) iceballs.add((IceBall)wep);
        else if (wep instanceof IceCannon) icecannons.add((IceCannon)wep);
        else if (wep instanceof IceWall) icewalls.add((IceWall)wep);
        updateLabels();
    }

    public void changeWeapon(int index) {
        if (index == 1) Projectile.weapon = Projectile.Weapon.ARROW;
        else if (index == 2) Projectile.weapon = Projectile.Weapon.CANNON;
        if (Player.type == Player.Type.FIRE || Player.type == Player.Type.FIREKING) {
            weps.get(selectedWep - 1).setBackgroundImage(ImageUtil.getImage("/ui/panels/weapons/fire/fireWeapon" + selectedWep + ".png"));
            weps.get(index - 1).setBackgroundImage(ImageUtil.getImage("/ui/panels/weapons/fire/fireWeaponSelected" + index + ".png"));
            if (index == 3) Projectile.weapon = Projectile.Weapon.FIREDARROW;
            if (index == 4) Projectile.weapon = Projectile.Weapon.FIREDCANNON;
            if (index == 5) Projectile.weapon = Projectile.Weapon.FIREBALL;
            if (index == 6) Projectile.weapon = Projectile.Weapon.FIREWALL;
        } else if (Player.type == Player.Type.ICE || Player.type == Player.Type.ICEKING) {
            weps.get(selectedWep - 1).setBackgroundImage(ImageUtil.getImage("/ui/panels/weapons/ice/iceWeapon" + selectedWep + ".png"));
            weps.get(index - 1).setBackgroundImage(ImageUtil.getImage("/ui/panels/weapons/ice/iceWeaponSelected" + index + ".png"));
            if (index == 3) Projectile.weapon = Projectile.Weapon.ICEDARROW;
            if (index == 4) Projectile.weapon = Projectile.Weapon.ICEDCANNON;
            if (index == 5) Projectile.weapon = Projectile.Weapon.ICEBALL;
            if (index == 6) Projectile.weapon = Projectile.Weapon.ICEWALL;
        }
        selectedWep = index;
    }
  
   public int getSelected(){
       return selectedWep;
   }
  
   public int getAmount(){
       switch(selectedWep){
           case 2: return cannons.size();
       }
         if (Player.type == Player.Type.FIRE || Player.type == Player.Type.FIREKING){
           switch(selectedWep){
             case 3: return firearrows.size();
             case 4: return firecannons.size();
             case 5: return fireballs.size();
             case 6: return firewalls.size();
           }
         } else if (Player.type == Player.Type.ICE || Player.type == Player.Type.ICEKING) {
           switch(selectedWep){
             case 3: return icearrows.size();
             case 4: return icecannons.size();
             case 5: return iceballs.size();
             case 6: return icewalls.size();
           }
         }
     return 1;
   }
  
   public void removeWep(){
     try{
       if(selectedWep == 2){
           cannons.remove(cannons.size() - 1);
       }
         if (Player.type == Player.Type.FIRE || Player.type == Player.Type.FIREKING){
             if(selectedWep == 3) firearrows.remove(firearrows.size() - 1);
             if(selectedWep == 4) firecannons.remove(firecannons.size() - 1);
             if(selectedWep == 5) fireballs.remove(fireballs.size() - 1);
             if(selectedWep == 6) firewalls.remove(firewalls.size() - 1);
         } else if (Player.type == Player.Type.ICE || Player.type == Player.Type.ICEKING) {
             if(selectedWep == 3)icearrows.remove(icearrows.size() - 1);
             if(selectedWep == 4) icecannons.remove(icecannons.size() - 1);
             if(selectedWep == 5) iceballs.remove(iceballs.size() - 1);
             if(selectedWep == 6) icewalls.remove(icewalls.size() - 1);
         }
     }catch(Exception e){
       System.out.println("no weapons to remove");
     }
     updateLabels();
   }
  
   public boolean canShoot(){
       if (getAmount() > 0) return true;
       return false;
   }
  
    private void updateLabels(){
        for (int i = 0; i < labels.size(); i++){
          if (i == 0) continue;
          if (i == 1) labels.get(i).setText("" + cannons.size());
          if (Player.type == Player.Type.FIRE || Player.type == Player.Type.FIREKING){
            if (i == 2) labels.get(i).setText("" + firearrows.size());
            if (i == 3) labels.get(i).setText("" + firecannons.size());
            if (i == 4) labels.get(i).setText("" + fireballs.size());
            if (i == 5) labels.get(i).setText("" + firewalls.size());
          } else if (Player.type == Player.Type.ICE || Player.type == Player.Type.ICEKING) {
            if (i == 2) labels.get(i).setText("" + icearrows.size());
            if (i == 3) labels.get(i).setText("" + icecannons.size());
            if (i == 4) labels.get(i).setText("" + iceballs.size());
            if (i == 5) labels.get(i).setText("" + icewalls.size());
          }
        }
    }

    public void update() {
        if (key.sel1) changeWeapon(1);
        if (key.sel2) changeWeapon(2);
        if (key.sel3) changeWeapon(3);
        if (key.sel4) changeWeapon(4);
        if (key.sel5) changeWeapon(5);
        if (key.sel6) changeWeapon(6);
    }
    //TODO: Add particle effect for wall projectile
}
