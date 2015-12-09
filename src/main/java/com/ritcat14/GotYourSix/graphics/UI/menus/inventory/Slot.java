package com.ritcat14.GotYourSix.graphics.UI.menus.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.ritcat14.GotYourSix.graphics.UI.UILabel;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.items.Item;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Slot extends UIPanel {
  
    private List<Item> items = new ArrayList<Item>();
    private UILabel counter = null;
    private boolean selected = false;
  
    public Slot(Vector2i position){
      super(position, new Vector2i(50,50), ImageUtil.getImage("/ui/panels/inventory/slot.png"));
      counter = new UILabel(new Vector2i(5,5), "" + items.size());
      counter.setFont(new Font("Magneto",Font.BOLD, 10));
      addComponent(counter);
    }
  
    public void updateBack(){
        if (items.size() == 0){
          setBackgroundImage(ImageUtil.getImage("/ui/panels/inventory/slot.png"));
        }
    }
  
    public boolean add(Item item){
        if (items.size() == 99) return false;
        if (items.size() < 1){
          setBackgroundImage(item.getImage());
          items.add(item);
          updateLabel();
          return true;
        } else if (item.getClass().equals(items.get(0).getClass())){
          items.add(item);
          updateLabel();
          return true;
        }
      return false;
    }
  
    public Rectangle getBounds(){
      return new Rectangle(getAbsolutePosition().x - 50, getAbsolutePosition().y - 50, 50, 50);
    }
  
    public boolean isSelected(){
      return selected;
    }
  
    public void select(boolean selected){
      this.selected = selected;
    }
  
    public List<Item> getItems(){
      return items;
    }
  
    public void updateLabel(){
        counter.text = "" + items.size();
    }
  
    public void update(){
      super.update();
    }
  
    public boolean remove(Item item){
      if (item.getClass().equals(items.get(0).getClass())){
        items.remove(items.size()-1);
        updateLabel();
        return true;
      }
      return false;
    }
  
    public void render(Graphics g) {
        super.render(g);
    }
  
}