package com.ritcat14.GotYourSix.graphics.UI;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.util.Vector2i;

import java.awt.Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class UILabel extends UIComponent {
  
    public String text = "";
    protected Font font = null;
    private FontMetrics fm = null;
  
    public UILabel(Vector2i position, String text) {
        super(position);
        font = Game.createFont();
        this.text = text;
        colour = new Color(0xfaf572);
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