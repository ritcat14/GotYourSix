package com.ritcat14.GotYourSix.graphics.UI;

import java.awt.Color;
import java.awt.Graphics;

import com.ritcat14.GotYourSix.util.Vector2i;

public class UIComponent {
  
    public Vector2i position, size;
    protected Vector2i offset;
    public Color colour;
    protected UIPanel panel;
  
    public boolean active = true;
  
    public UIComponent(Vector2i position) {
        this.position = position;
        offset = new Vector2i();
    }
  
    public UIComponent(Vector2i position, Vector2i size) {
        this.position = position;
        this.size = size;
        offset = new Vector2i();
    }
  
    void init(UIPanel panel){
        this.panel = panel;
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
