package com.ritcat14.GotYourSix.graphics.UI;

import com.ritcat14.GotYourSix.graphics.UI.UIComponent;
import com.ritcat14.GotYourSix.util.Vector2i;

import java.awt.Graphics;
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
  
    public UIPanel(Vector2i position, Vector2i size, BufferedImage image){
        super(position);
        this.position = position;
        this.size = size;
        colour = new Color(0xff00ff);
        this.image = image;
    }
  
    public void setBackgroundImage(BufferedImage image){
        this.image = image;
    }

    public void addComponent(UIComponent component) {
        component.init(this);
        components.add(component);
    }
  
    public void update() {
        for (UIComponent component : components){
            component.setOffset(position);
            component.update();
        }
    }
  
    public void render(Graphics g) {
        g.setColor(colour);
        g.fillRect(position.x, position.y, size.x, size.y);
        if (image != null) g.drawImage(image, 0,0,size.x, size.y, null);
        for (UIComponent component : components){
            component.render(g);
        }
    }  
}
