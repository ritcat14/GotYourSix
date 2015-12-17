package com.ritcat14.GotYourSix.graphics.UI;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.events.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;



public class UIPanel extends UIComponent implements EventListener {
  
    public List<UIComponent> components = new ArrayList<UIComponent>();
    private Vector2i size = null;
    private BufferedImage image = null;
    public boolean imageSet = false;
  
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

    public void onEvent(Event event) {
      for (UIComponent panel : components) {
        if (panel instanceof UIPanel) panel.onEvent(event);
      }
    }
  
    public void setBackgroundImage(BufferedImage image){
        this.image = image;
        imageSet = true;
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
  
    public List<UIComponent> getComponents(){
        return components;
    }
  
    public void update() {
        for (int i = components.size() - 1; i > -1; i--){
            components.get(i).setOffset(position);
            components.get(i).update();
        }
    }
  
    public void clear(){
        for (int i = components.size() - 1; i > -1; i--){
            components.remove(i);
        }
    }
  
    public BufferedImage getImage(){
      if (image != null) return image;
      return null;
    }
  
    public void setSize(Vector2i size){
      this.size = size;
    }
  
    public Rectangle getBounds(){
      return new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);
    }
  
    public void render(Graphics g) {
        if (image != null) {
          if (size != null) {
              g.drawImage(image, position.x, position.y, size.x, size.y, null);
          } else {
              this.size = new Vector2i(image.getWidth(), image.getHeight());
              g.drawImage(image, position.x, position.y, size.x, size.y, null);
          }
        } else {
          g.setColor(colour);
          g.fillRect(position.x, position.y, size.x, size.y);
        }
        for (int i = components.size() - 1; i > -1; i--){
            components.get(i).render(g);
        }
    }
}
