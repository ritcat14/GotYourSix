package com.ritcat14.GotYourSix.graphics.UI.menus.inventory;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.events.*;
import com.ritcat14.GotYourSix.events.types.*;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.items.Item;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.input.Mouse;

public class Inventory extends UIPanel {
  
    private Slot slot = null, s = new Slot(new Vector2i(Mouse.getX(), Mouse.getY()));
    private ArmourSlot as = null;
    private ResSlot rs = null;
    private int slotSize = 50;
    
    private List<Slot> slots = new ArrayList<Slot>();
    private List<ArmourSlot> armourSlots = new ArrayList<ArmourSlot>();
    private List<ResSlot> resSlots = new ArrayList<ResSlot>();
  
    public Inventory() {
        super(new Vector2i(50,50), new Vector2i(Game.getAbsoluteWidth() - 100, Game.getAbsoluteHeight() - 100), ImageUtil.getImage("/ui/panels/inventory/inventory.png"));
        for (int x =5; x < 14; x++){
          for (int y = 0; y < 7; y++){
            slot = new Slot(new Vector2i((x * (slotSize + 6)) + 50, (y * (slotSize + 6)) + 50));
            slots.add(slot);
            addComponent(slot);
          }
        }
        for (int x = 1; x < 3; x++){
          for (int y = 1; y < 5; y++){
            as = new ArmourSlot(new Vector2i((x * (slotSize + 6)) + 50, (y * (slotSize + 6)) + 50));
            armourSlots.add(as);
            addComponent(as);
          }
        }
        for (int x = 1; x < 3; x++){
          for (int y = 7; y < 8; y++){
            rs = new ResSlot(new Vector2i((x * (slotSize + 6)) + 50, (y * (slotSize + 6)) + 50));
            resSlots.add(rs);
            addComponent(rs);
          }
        }
        addComponent(s);
    }
  
   public void onEvent(Event event) {
       EventDispatcher dispatcher = new EventDispatcher(event);
       dispatcher.dispatch(Event.Type.MOUSE_PRESSED, new EventHandler() {
           public boolean onEvent(Event event) {
             return onMousePressed((MousePressedEvent) event);
           }
       });
       dispatcher.dispatch(Event.Type.MOUSE_RELEASED, new EventHandler() {
           public boolean onEvent(Event event) {
             return onMouseReleased((MouseReleasedEvent) event);
           }
       });
       dispatcher.dispatch(Event.Type.MOUSE_MOVED, new EventHandler() {
         public boolean onEvent(Event event){
           return onMouseMoved((MouseMovedEvent) event);
         }
       });
   }
  
    public boolean onMousePressed(MousePressedEvent e) {
        Point p = new Point(Mouse.getX(), Mouse.getY());
        if (!this.getBounds().contains(p)) return false;
        boolean left = (e.getButton() == MouseEvent.BUTTON1);
        boolean right = (e.getButton() == MouseEvent.BUTTON3);
        for (int i = 0; i < slots.size(); i++){
            Slot currSlot = slots.get(i);
            List<Item> items = currSlot.getItems();
            boolean hasItems = (items.size() > 0);
            if (currSlot.getBounds().contains(p)){
                if (right && !currSlot.isSelected() && hasItems && s.getItems().size() == 0){
                    System.out.println("creating temp & selecting slot");
                    currSlot.select(true);
                    s.add(items.get(0));
                    currSlot.remove(items.get(items.size() - 1));
                } else if (right && currSlot.isSelected()){
                  if (s.getItems().size() > 0){
                      System.out.println("adding item to temp and removing from slot");
                      if (s.getItems().get(0).getClass().equals(items.get(0).getClass())){
                          s.add(items.get(0));
                          currSlot.remove(items.get(items.size() - 1));
                          currSlot.updateBack();
                      }
                  } else if (s.getItems().size() == 0){
                      s.add(items.get(0));
                      currSlot.remove(items.get(items.size() - 1));
                      currSlot.updateBack();
                  }
                } else if ((left || right) && s.getItems().size() == 0) {
                    System.out.println("handled event, but temp is empty");
                } else if (left){
                    System.out.println("adding item to clicked slot from temp");
                  if (currSlot.getItems().size() > 0){
                    if (s.getItems().get(0).getClass().equals(items.get(0).getClass())){
                      currSlot.add(s.getItems().get(0));
                      s.remove(s.getItems().get(s.getItems().size() - 1));
                    }
                  } else if (currSlot.getItems().size() == 0){
                    currSlot.add(s.getItems().get(0));
                    s.remove(s.getItems().get(s.getItems().size() - 1));
                  }
                    if (s.getItems().size() == 0){
                        s.setBackgroundImage(ImageUtil.getImage("/ui/panels/inventory/slot.png"));
                        currSlot.select(false);
                    }
                }
                currSlot.updateBack();
            }
        }
        return true;
    }
  
   public boolean onMouseReleased(MouseReleasedEvent e) {
       return true;
   }
  
   public boolean onMouseMoved(MouseMovedEvent e) {
       s.position = new Vector2i(Mouse.getX(), Mouse.getY());
       return true;
   }
  
    public void add(Item item){
        for (int i = 0; i < slots.size(); i++){
            if (slots.get(i).add(item)) break;
        }
    }
  
    public void remove(Item item){
        for (int i = 0; i < slots.size(); i++){
            if (slots.get(i).remove(item)) break;
        }
    }
  
    public void render(Graphics g){
        super.render(g);
    }
  
    public void update(){
      super.update();
      for (int i = 0; i < slots.size(); i++){
        slots.get(i).update();
      }
    }
  
}