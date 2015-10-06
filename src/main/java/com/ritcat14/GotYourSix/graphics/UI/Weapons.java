package com.ritcat14.GotYourSix.graphics.UI;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Font;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.mob.Player;
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
    private BufferedImage         weaponImg;


    public Weapons() {
        super(new Vector2i(Game.getWindowWidth() - (6 * 80), Game.getWindowHeight() - 100), new Vector2i(6 * 80, 80),
              ImageUtil.getImage("/ui/weaponBarBack.png"));
        this.x = Game.getWindowWidth() - (6 * 80);
        this.y = Game.getWindowHeight() - 100;
        for (int i = 0; i < 6; i++) {
            int num = (i + 1);
            if (Player.type == Player.Type.FIRE || Player.type == Player.Type.FIREKING) {
                weaponImg = ImageUtil.getImage("/ui/panels/weapons/fire/fireWeapon" + num + ".png");
            } else if (Player.type == Player.Type.ICE || Player.type == Player.Type.ICEKING) {
                weaponImg = ImageUtil.getImage("/ui/panels/weapons/ice/iceWeapon" + num + ".png");
            }
            weapon = new UIPanel(new Vector2i(x + (i * 80), y), new Vector2i(weaponImg.getWidth(), weaponImg.getHeight()), weaponImg);
            addComponent(weapon);
            weps.add(weapon);
        }
        changeWeapon(1);
    }

    public void add(Weapon wep) {
        if (wep instanceof FireBall) {
            if (fireballs.size() == 0)
                wepNum++; //change icon to active icon
            fireballs.add((FireBall)wep);
        } else if (wep instanceof CannonBall) {
            if (cannons.size() == 0)
                wepNum++;
            cannons.add((CannonBall)wep);
        } else if (wep instanceof FireArrow) {
            if (firearrows.size() == 0)
                wepNum++;
            firearrows.add((FireArrow)wep);
        } else if (wep instanceof FireCannon) {
            if (firecannons.size() == 0)
                wepNum++;
            firecannons.add((FireCannon)wep);
        } else if (wep instanceof FireWall) {
            if (firewalls.size() == 0)
                wepNum++;
            firewalls.add((FireWall)wep);
        } else if (wep instanceof IceArrow) {
            if (icearrows.size() == 0)
                wepNum++;
            icearrows.add((IceArrow)wep);
        } else if (wep instanceof IceBall) {
            if (iceballs.size() == 0)
                wepNum++;
            iceballs.add((IceBall)wep);
        } else if (wep instanceof IceCannon) {
            if (icecannons.size() == 0)
                wepNum++;
            icecannons.add((IceCannon)wep);
        } else if (wep instanceof IceWall) {
            if (icewalls.size() == 0)
                wepNum++;
            icewalls.add((IceWall)wep);
        }
    }

    public void changeWeapon(int index) {
        for (int i = 0; i < weps.size(); i++) {
            if (Player.type == Player.Type.FIRE || Player.type == Player.Type.FIREKING) {
                weps.get(selectedWep - 1)
                    .setBackgroundImage(ImageUtil.getImage("/ui/panels/weapons/fire/fireWeapon" + selectedWep + ".png"));
                weps.get(index - 1).setBackgroundImage(ImageUtil.getImage("/ui/panels/weapons/fire/fireWeaponSelected" + index + ".png"));
            } else if (Player.type == Player.Type.ICE || Player.type == Player.Type.ICEKING) {
                weps.get(selectedWep - 1).setBackgroundImage(ImageUtil.getImage("/ui/panels/weapons/ice/iceWeapon" + selectedWep + ".png"));
                weps.get(index - 1).setBackgroundImage(ImageUtil.getImage("/ui/panels/weapons/ice/iceWeaponSelected" + index + ".png"));
            }
        }
        selectedWep = index;
    }

    public void update() {
        super.update();
        for (int i = 0; i < weps.size(); i++){
            int num = i + 1;
            if (num == 1)
                amount = new UILabel(weps.get(i).position, "∞");
            else if (num == 2)
                amount = new UILabel(weps.get(i).position, "" + cannons.size());
            if (Player.type == Player.Type.FIRE || Player.type == Player.Type.FIREKING) {
                if (num == 3)
                    amount = new UILabel(weps.get(i).position, "" + firearrows.size());
                else if (num == 4)
                    amount = new UILabel(weps.get(i).position, "" + firecannons.size());
                else if (num == 5)
                    amount = new UILabel(weps.get(i).position, "" + fireballs.size());
                else if (num == 6)
                    amount = new UILabel(weps.get(i).position, "" + firewalls.size());
            }else if (Player.type == Player.Type.ICE || Player.type == Player.Type.ICEKING) {
                if (num == 3)
                    amount = new UILabel(weps.get(i).position, "" + icearrows.size());
                else if (num == 4)
                    amount = new UILabel(weps.get(i).position, "" + icecannons.size());
                else if (num == 5)
                    amount = new UILabel(weps.get(i).position, "" + iceballs.size());
                else if (num == 6)
                    amount = new UILabel(weps.get(i).position, "" + icewalls.size());
            }
            amount.setFont(new Font("Helvetica", Font.BOLD, 15)).setColor(0xff7F7F7F);
            weps.get(i).addComponent(amount);
          
        }
    }
    //TODO: Add particle effect for wall projectile, fix xp bonus, and fix weapon changing/selecting in the weapon rack bar
}
