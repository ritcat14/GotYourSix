package com.ritcat14.GotYourSix.graphics.UI;

import java.awt.Color;
import java.awt.Graphics;

import com.ritcat14.GotYourSix.graphics.UI.UIButtonListener;
import com.ritcat14.GotYourSix.graphics.UI.UIComponent;
import com.ritcat14.GotYourSix.util.Vector2i;

public class UIButton extends UIComponent{
  
    private UIButtonListener buttonListener;
    public UILabel label;
  
    public UIButton(Vector2i position, Vector2i size) {
        super(position, size);
        Vector2i lp = new Vector2i(position);
        lp.x += 4;
        lp.y += size.y - 2;
        label = new UILabel(lp,"");
        label.setColor(0x444444);
        label.active = false;
      
        setColor(0xAAAAAA);
    }
  
    void init(UIPanel panel){
        super.init(panel);
        panel.addComponent(label);
    }
  
    public void setText(String text){
        if (text == "") label.active = false;
        else label.text = text;
    }

    public void update() {
        
    }

    public void render(Graphics g) {
        g.setColor(colour);
        g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
        if (label != null) label.render(g);
    }
  
}
