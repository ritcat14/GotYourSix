package com.ritcat14.GotYourSix.graphics.UI.menus;


import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.ritcat14.GotYourSix.graphics.UI.UILabel;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.items.Item;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.input.Mouse;

public class Slot extends UIPanel {
  
    private List<Item> items = new ArrayList<Item>();
    private UILabel counter = null;
  
    public Slot(Vector2i position){
      super(position, new Vector2i(50,50), ImageUtil.getImage("/ui/panels/inventory/slot.png"));
      counter = new UILabel(new Vector2i(5,5), "" + items.size());
      counter.setFont(new Font("Magneto",Font.BOLD, 10));
      addComponent(counter);
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
  
    public void updateLabel(){
        counter.text = "" + items.size();
    }
  
    public void remove(){
        items.remove(items.size()-1);
    }
  
    public void update(){
      super.update();
      Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, 50, 50);
      Point p = new Point(Mouse.getX(), Mouse.getY());
      if (Mouse.getButton() == MouseEvent.BUTTON1 && rect.contains(p)) {
          System.out.println("Left");
      } else if (Mouse.getButton() == MouseEvent.BUTTON3 && rect.contains(p)){
          System.out.println("Right");
      }
    }
  
}
