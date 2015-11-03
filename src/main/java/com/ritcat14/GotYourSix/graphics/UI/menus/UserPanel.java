package com.ritcat14.GotYourSix.graphics.UI.menus;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.UI.*;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class UserPanel extends UIPanel {

    private UIManager         ui, mui;
    private UIProgressBar     UIHealthBar, UILevelBar, UIHungerBar, UIThirstBar, UIStaminaBar;
    private UIPanel           weapons;
    private Inventory         invent;
    private static Weapons    w;
    private UILabel           xpLabel;
    private UIButton          button, face;
    private BufferedImage     image;
    private Minimap           map;
    private Game              game       = Game.getGame();
    private Player            player;

    private List<UIComponent> components = new ArrayList<UIComponent>();

    public UserPanel(Player player) {
        super(new Vector2i(Game.getWindowWidth(), 0), new Vector2i(60 * 5, Game.getWindowHeight()));
        this.player = player;
        ui = game.getUIManager();
        ui.addPanel(this);
        //mui = game.getMapManager();
        setColor(0xff464646);
        //map = new Minimap(new Vector2i(Game.getWindowWidth(), 0));
        //mui.addPanel(map);

        BufferedImage back = ImageUtil.getImage("/ui/bars/back.png");
        int fontCol = 0xFFE7EF;
        Font font = new Font("Serif", Font.BOLD + Font.ITALIC, 24);
        if (StartScreen.state == StartScreen.playerViewState.MF || StartScreen.state == StartScreen.playerViewState.FF) {
            back = ImageUtil.getImage("/ui/bars/back.png");
            fontCol = 0xE83E44;
        } else if (StartScreen.state == StartScreen.playerViewState.MI || StartScreen.state == StartScreen.playerViewState.FI) {
            back = ImageUtil.getImage("/ui/bars/back.png");
            fontCol = 0x417FEA;
        }

        addComponent(((UILabel)new UILabel(new Vector2i(250, 330), player.getName()).setColor(0xff464646)).setFont(new Font("Veranda", Font.BOLD, 24)));

        UIHealthBar = new UIProgressBar(new Vector2i(20, 345), back, ImageUtil.getImage("/ui/bars/healthFront.png"));
        addComponent(UIHealthBar);
        components.add(UIHealthBar);

        UIStaminaBar = new UIProgressBar(new Vector2i(20, 405), back, ImageUtil.getImage("/ui/bars/staminaFront.png"));
        addComponent(UIStaminaBar);
        components.add(UIStaminaBar);

        UILevelBar = new UIProgressBar(new Vector2i(20, 465), back, ImageUtil.getImage("/ui/bars/xpFront.png"));
        addComponent(UILevelBar);
        components.add(UILevelBar);

        UIHungerBar = new UIProgressBar(new Vector2i(20, 525), back, ImageUtil.getImage("/ui/bars/foodFront.png"));
        addComponent(UIHungerBar);
        components.add(UIHungerBar);

        UIThirstBar = new UIProgressBar(new Vector2i(20, 585), back, ImageUtil.getImage("/ui/bars/waterFront.png"));
        UIThirstBar.setColor(0x545454);
        UIThirstBar.setForegroundColour(0x0094FF);
        addComponent(UIThirstBar);
        components.add(UIThirstBar);

        UILabel hpLabel =
                          ((UILabel)new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(-5, -16)), "HP").setColor(fontCol)).setFont(font);
        addComponent(hpLabel);

        UILabel staminaLabel =
                               ((UILabel)new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(-5, 46)), "STAMINA").setColor(fontCol)).setFont(font);
        addComponent(staminaLabel);

        xpLabel =
                  ((UILabel)new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(-5, 106)), "LVL " + player.XPLevel).setColor(fontCol)).setFont(font);
        addComponent(xpLabel);
        components.add(xpLabel);

        UILabel foodLabel =
                            ((UILabel)new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(-5, 166)), "HUNGER").setColor(fontCol)).setFont(font);
        addComponent(foodLabel);

        UILabel waterLabel =
                             ((UILabel)new UILabel(new Vector2i(UIHealthBar.position).add(new Vector2i(-5, 226)), "THIRST").setColor(fontCol)).setFont(font);
        addComponent(waterLabel);

        button = new UIButton(new Vector2i(UIThirstBar.position).add(new Vector2i(2, 60)), new Vector2i(100, 30), new UIActionListener() {
            public void perform() {
                //Change level
                System.out.println("Changed level");
            }
        }, "ENTER");
        addComponent(button);

        if (player.getFace() != null) {
            face = new UIButton(new Vector2i(200, 330 - 20), image, new UIActionListener() {
                public void perform() {
                    Game.STATE = Game.State.PAUSE;
                }
            }, "");
            addComponent(face);
        }
        //invent = new Inventory(new Vector2i((Game.getWindowWidth() + (60 * 5)) - 275, Game.getWindowHeight() - 200), "inventoryBack.png");
        //ui.addPanel(invent);
        if (player != null) {
            w = new Weapons();
            ui.addPanel(w);
        }
        if (w != null)
            player.fireRate = player.getShots().get(w.getSelected() - 1).FIRERATE;

    }

    public List<UIComponent> getUpdateables() {
        return components;
    }
  
    public Weapons getWeapon(){
      if (w != null) return w;
      else return null;
    }
  
    public Inventory getInvent() {
      return invent;
    }

    public void update() {
        super.update();
        if (player != null) {
            UIHealthBar.setProgress(player.health / 100.0);
            UILevelBar.setProgress(player.XP / 100.0);
            UIHungerBar.setProgress(player.hunger / 100.0);
            UIThirstBar.setProgress(player.thirst / 100.0);
            UIStaminaBar.setProgress(player.stamina / 100.0);
            xpLabel.setText("LVL " + player.XPLevel);
        }
    }

}
