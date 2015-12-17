package com.ritcat14.GotYourSix.graphics.UI.menus;

import java.util.ArrayList;
import java.util.List;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.UI.UIActionListener;
import com.ritcat14.GotYourSix.graphics.UI.UIButton;
import com.ritcat14.GotYourSix.graphics.UI.UIComponent;
import com.ritcat14.GotYourSix.graphics.UI.UILabel;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Options extends UIPanel {
  
    private StartScreen sc = null;
  
    private enum tab {
        CONTROLS,
        CREDITS;
    };
    //Controls tab
    private List<UIComponent> conCont = new ArrayList<UIComponent>();
    private UILabel label01 = new UILabel(new Vector2i(20,25), "UP ARROW/W -> Up         Q -> Inventory");
    private UILabel label02 = new UILabel(new Vector2i(20,50), "DOWN ARROW/S -> Down     M -> Minimap");
    private UILabel label03 = new UILabel(new Vector2i(20,75), "LEFT ARROW/A -> Left     Shift -> Sprint");
    private UILabel label04 = new UILabel(new Vector2i(20,100),"RIGHT ARROW/D -> Right   Left Mouse -> Shoot");
    private UILabel label05 = new UILabel(new Vector2i(20,125), "HOME/H -> Teleport To Home Level");
    private UILabel label06 = new UILabel(new Vector2i(5,150),"Inventory:");
    private UILabel label07 = new UILabel(new Vector2i(20,170),"Left Click -> Drop Item");
    private UILabel label08 = new UILabel(new Vector2i(20,200),"Right Click ->Pick Up Item");
    //credits tab
    private List<UIComponent> credCont = new ArrayList<UIComponent>();
    private UILabel label0 = new UILabel(new Vector2i(5,25), "Developed and designed by: Kristian Rice");
    private UILabel label1 = new UILabel(new Vector2i(5,50), "Graphics designed and made by: Hannah Causebrook");
    private UILabel label2 = new UILabel(new Vector2i(5,75), "Made for: Mr Vowles");
    
    private tab tabState = tab.CONTROLS;
    private UIPanel content = new UIPanel(new Vector2i(30,120), new Vector2i(Game.getAbsoluteWidth() - 60, Game.getAbsoluteHeight() - 150), 0xccaaaaaa);
  
    private UIButton back = new UIButton(new Vector2i(content.getBounds().width - 100, content.getBounds().height - 50), new Vector2i(100,50), new UIActionListener(){
        public void perform(){
            sc.goToStart();
        }
    },"BACK");
  
    private UIButton controls = new UIButton(new Vector2i(30, 70), new Vector2i(110, 40), new UIActionListener(){
        public void perform(){
            if (!(tabState == tab.CONTROLS)){
                tabState = tab.CONTROLS;
            }
        }
    }, "CONTROLS");
  
    private UIButton credits = new UIButton(new Vector2i(140,70), new Vector2i(110,40), new UIActionListener(){
        public void perform(){
            if (!(tabState == tab.CREDITS)){
                tabState = tab.CREDITS;
            }
        }
    },"CREDITS");
  
    public Options(StartScreen sc){
        super(new Vector2i(0,0), new Vector2i(Game.getAbsoluteWidth(), Game.getAbsoluteHeight()), ImageUtil.getImage("/ui/panels/background.png"));
        this.sc = sc;
        addComponent(controls);
        addComponent(credits);
        addComponent(content);
        content.addComponent(back);
        //controls tab
        conCont.add(label01);
        conCont.add(label02);
        conCont.add(label03);
        conCont.add(label04);
        conCont.add(label05);
        conCont.add(label06);
        conCont.add(label07);
        conCont.add(label08);
        //credits tab
        credCont.add(label0);
        credCont.add(label1);
        credCont.add(label2);
    }
  
    public void update(){
        super.update();
        //initiate appropriate menu based on tabState
        if (tabState != tab.CONTROLS){
            controls.size = new Vector2i(110,50);
            controls.position = new Vector2i(30, 70);
            content.clear();
        } else if (tabState == tab.CONTROLS){
            controls.size = new Vector2i(110,70);
            controls.position = new Vector2i(30,50);
            content.clear();
            for (int i = 0; i < conCont.size(); i++){
                content.addComponent(conCont.get(i));
            }
            content.addComponent(back);
        }
        if (tabState != tab.CREDITS){
            credits.size = new Vector2i(110,50);
            credits.position = new Vector2i(140, 70);
        } else if (tabState == tab.CREDITS){
            credits.size = new Vector2i(110,70);
            credits.position = new Vector2i(140,50);
            content.clear();
            for (int i = 0; i < credCont.size(); i++){
                content.addComponent(credCont.get(i));
            }
            content.addComponent(back);
        }
    }
  
}
