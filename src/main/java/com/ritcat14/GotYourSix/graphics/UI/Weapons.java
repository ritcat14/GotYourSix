package com.ritcat14.GotYourSix.graphics.UI;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.items.FireBall;
import com.ritcat14.GotYourSix.items.Weapon;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.graphics.UI.UILabel;

public class Weapons extends UIPanel {

    private int                 wepNum    = 1;
    private int                 fireBalls = 0;
    private int selectedWep = 5;
    private int x, y;
    private ArrayList<FireBall> fireballs = new ArrayList<FireBall>();
    private ArrayList<UILabel> labels = new ArrayList<UILabel>();
    private ArrayList<UIPanel> weps = new ArrayList<UIPanel>();
    private ArrayList<ArrayList> lists = new ArrayList<ArrayList>();
    private UIPanel weapon;
    private UILabel amount;
    private BufferedImage weaponImg;
  

    public Weapons() {
        super(new Vector2i(Game.getWindowWidth() - (6 * 80), Game.getWindowHeight() - 100), new Vector2i(6 * 80, 80),ImageUtil.getImage("/ui/weaponBarBack.png" ));
        this.x = Game.getWindowWidth() - (6 * 80);
        this.y = Game.getWindowHeight() - 100;
        for (int i = 0; i < 6; i++){
            int num = (i + 1);
            if (Player.type == Player.Type.FIRE || Player.type == Player.Type.FIREKING){
              weaponImg = ImageUtil.getImage("/ui/panels/weapons/fire/fireWeapon" + num + ".png");
            } else if (Player.type == Player.Type.ICE || Player.type == Player.Type.ICEKING){
              weaponImg = ImageUtil.getImage("/ui/panels/weapons/ice/iceWeapon" + num + ".png");
            }
              weapon = new UIPanel(new Vector2i(x + (i * 80), y), new Vector2i(weaponImg.getWidth(), weaponImg.getHeight()), weaponImg);
              addComponent(weapon);
              weps.add(weapon);
        }
        for (int i = 0; i < lists.size(); i++){
            amount = new UILabel(weapon.position.add(new Vector2i(2, 75)), "" + lists.get(i).size());
            addComponent(amount);
        }
    }

    public void add(Weapon wep) {
        if (fireballs.size() == 0)
            wepNum++;
        if (wep instanceof FireBall) fireballs.add((FireBall)wep);
    }
  
    public void changeWeapon(int index){
        for (int i = 0; i < weps.size(); i++){
          if(Player.type == Player.Type.FIRE || Player.type == Player.Type.FIREKING){
            weps.get(selectedWep - 1).setBackgroundImage(ImageUtil.getImage("/ui/panels/weapons/fire/fireWeapon" + selectedWep + ".png"));
            weps.get(index - 1).setBackgroundImage(ImageUtil.getImage("/ui/panels/weapons/fire/fireWeaponSelected" + index + ".png"));
          }else if(Player.type == Player.Type.ICE || Player.type == Player.Type.ICEKING){
            weps.get(selectedWep - 1).setBackgroundImage(ImageUtil.getImage("/ui/panels/weapons/ice/iceWeapon" + selectedWep + ".png"));
            weps.get(index - 1).setBackgroundImage(ImageUtil.getImage("/ui/panels/weapons/ice/iceWeaponSelected" + index + ".png"));
          }
        }
        selectedWep = index;
    }
  
    public void update(){
        super.update();
        lists.add(fireballs);
    }
  //TODO: Add particle effect for wall projectile, fix xp bonus, and fix weapon changing/selecting in the weapon rack bar
}
