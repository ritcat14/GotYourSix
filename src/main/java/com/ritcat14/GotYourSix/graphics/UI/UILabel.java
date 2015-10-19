package com.ritcat14.GotYourSix.graphics.UI;

import com.ritcat14.GotYourSix.util.Vector2i;

import java.awt.Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class UILabel extends UIComponent {
  
    public String text;
    private Font font;
    private FontMetrics fm;
  
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
  
    public FontMetrics getFontMetrics(){
        return fm;
    }
  
    public void setPosition(Vector2i position){
      this.position = position;
    }
  
    public String getText(){
        return text;
    }
  
    public void setText(String text){
      this.text = text;
    }
  
    public void render(Graphics g) {
        fm = g.getFontMetrics();
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(text, position.x + offset.x + 1, position.y + offset.y + 2);
        g.setColor(colour);
        g.drawString(text, position.x + offset.x, position.y + offset.y);
    }
}