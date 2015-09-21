package com.ritcat14.GotYourSix.graphics.UI;

import java.awt.Color;
import java.awt.Graphics;

import com.ritcat14.GotYourSix.util.Vector2i;

public class UIComponent {
  
    public Vector2i position;
    protected Vector2i offset;
    public Color colour;
  
    public UIComponent(Vector2i position) {
        this.position = position;
        offset = new Vector2i();
    }
  
    public UIComponent setColor(int colour){
        this.colour = new Color(colour);
        return this;
    }
  
    public void update() {
      
    }
  
    public void render(Graphics g) {
        
    }
  
    void setOffset(Vector2i offset){
        this.offset = offset;
    }
   
}
