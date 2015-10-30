package com.ritcat14.GotYourSix.graphics.UI;
import com.ritcat14.GotYourSix.util.Vector2i;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class UIPanel extends UIComponent{
  
    private List<UIComponent> components = new ArrayList<UIComponent>();
    private Vector2i size;
    private BufferedImage image;
  
    public UIPanel(Vector2i position, Vector2i size){
        super(position);
        this.position = position;
        this.size = size;
        colour = new Color(0xff00ff);
    }
    public UIPanel(Vector2i position, Vector2i size, int colour){
        super(position);
        this.position = position;
        this.size = size;
        this.colour = new Color(colour);
    }
  
    public UIPanel(Vector2i position, Vector2i size, BufferedImage image){
        super(position);
        this.position = position;
        this.size = size;
        this.image = image;
    }
  
    public UIPanel(Vector2i position, BufferedImage image){
        super(position);
        this.position = position;
        this.image = image;
    }
  
    public void setBackgroundImage(BufferedImage image){
        this.image = image;
    }
  
    public void removeComponent(UIComponent component){
      components.remove(component);
    }

    public void addComponent(UIComponent component) {
        component.init(this);
        components.add(component);
    }
  
    public UIComponent getComponent(int index){
        return components.get(index);
    }
  
    public void update() {
        for (UIComponent component : components){
            component.setOffset(position);
            component.update();
        }
    }
  
    public Rectangle getBounds(){
      return new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);
    }
  
    public void render(Graphics g) {
        if (image != null) {
          if (size != null) {
              g.drawImage(image, position.x, position.y, size.x, size.y, null);
          } else {
              System.out.println("Creating size");
              this.size = new Vector2i(image.getWidth(), image.getHeight());
              g.drawImage(image, position.x, position.y, size.x, size.y, null);
          }
        } else {
          g.setColor(colour);
          g.fillRect(position.x, position.y, size.x, size.y);
        }
        for (UIComponent component : components){
            component.render(g);
        }
    }
}
