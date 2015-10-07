package com.ritcat14.GotYourSix.graphics.UI;

import java.awt.Graphics;

import java.awt.Color;
import java.awt.Font;
import com.ritcat14.GotYourSix.graphics.UI.UIComponent;
import com.ritcat14.GotYourSix.util.Vector2i;

public class UILabel extends UIComponent {
  
    public String text;
    private Font font;
  
    public UILabel(Vector2i position, String text) {
        super(position);
        font = new Font("Helvetica",Font.BOLD, 24);
        this.text = text;
        colour = new Color(0xff00ff);
    }
  
    public UILabel setFont(Font font){
        this.font = font;
        return this;
    }
  
    public void setText(String text){
        this.text = text;
    }
  
    public String getText(){
        return text;
    }
  
    public void render(Graphics g) {
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(text, position.x + offset.x + 1, position.y + offset.y + 2);
        g.setColor(colour);
        g.drawString(text, position.x + offset.x, position.y + offset.y);
    }
}
