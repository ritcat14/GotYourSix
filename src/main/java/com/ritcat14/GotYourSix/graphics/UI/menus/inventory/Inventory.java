package com.ritcat14.GotYourSix.graphics.UI.menus.inventory;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.events.*;
import com.ritcat14.GotYourSix.events.types.*;
import com.ritcat14.GotYourSix.graphics.UI.UILabel;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.items.Item;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.input.Mouse;

public class Inventory extends UIPanel {
  
    private Slot slot = null, s = null;
    private ArmourSlot as = null;
    private ResSlot rs = null;
    private int slotSize = 50, defence = 0;
    private UILabel def = null;
    public Player p;
    
    private List<Slot> slots = new ArrayList<Slot>();
  
    public Inventory(Player p) {
        super(new Vector2i(50,50), new Vector2i(Game.getAbsoluteWidth() - 100, Game.getAbsoluteHeight() - 100), ImageUtil.getImage("/ui/panels/inventory/inventory.png"));
        s = new Slot(new Vector2i(Mouse.getX(), Mouse.getY()), false, p);
        this.p = p;
        for (int x =5; x < 14; x++){
          for (int y = 0; y < 7; y++){
            slot = new Slot(new Vector2i((x * (slotSize + 6)) + 50, (y * (slotSize + 6)) + 50), false, p);
            slots.add(slot);
            addComponent(slot);
          }
        }
        as = new ArmourSlot(new Vector2i((1 * (slotSize + 6)) + 50, (1 * (slotSize + 6)) + 50), "Head", p);
        slots.add(as);
        addComponent(as);
        as.add(p.getArmour("Head"));
        as = new ArmourSlot(new Vector2i((1 * (slotSize + 6)) + 50, (2 * (slotSize + 6)) + 50), "Chest", p);
        slots.add(as);
        addComponent(as);
        as.add(p.getArmour("Chest"));
        as = new ArmourSlot(new Vector2i((1 * (slotSize + 6)) + 50, (3 * (slotSize + 6)) + 50), "Legs", p);
        slots.add(as);
        addComponent(as);
        as.add(p.getArmour("Legs"));
        def = new UILabel(new Vector2i((1 * (slotSize + 6)) + 50, (1 * (slotSize + 6))), "Defence: " + getDefence());
        addComponent(def);
        for (int x = 1; x < 3; x++){
          for (int y = 7; y < 8; y++){
            rs = new ResSlot(new Vector2i((x * (slotSize + 6)) + 50, (y * (slotSize + 6)) + 50), p);
            slots.add(rs);
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
                    currSlot.select(true);
                    s.add(items.get(0));
                    currSlot.remove(items.get(items.size() - 1));
                } else if (right && currSlot.isSelected()){
                  if (s.getItems().size() > 0){
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
                } else if (left){
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
  
   public List<Slot> getSlots(){
       return slots;
   }
  
    public void add(Item item){
        for (int i = 0; i < slots.size(); i++){
            if (slots.get(i).isArmour()) break;
            else if (slots.get(i).add(item)) break;
        }      
    }
  
    public void remove(Item item){
        for (int i = 0; i < slots.size(); i++){
            if (slots.get(i).remove(item)) break;
        }
    }
  
    public int getDefence(){
      defence = 0;
      for (int i = 0; i < slots.size(); i ++){
          if (slots.get(i) instanceof ArmourSlot && slots.get(i).getItems().size() > 0){
             defence += ((ArmourSlot)(slots.get(i))).getDefence();
          }
      }
      return defence;
    }
  
    public void render(Graphics g){
        super.render(g);
        s.render(g);
        def.render(g);
        for (int i = 0; i < slots.size(); i++){
          slots.get(i).render(g);
        }
    }
  
    public void update(){
        super.update();
        s.update();
        def.setText("Defence: " + getDefence());
        def.update();
        for (int i = 0; i < slots.size(); i++){
            slots.get(i).update();
        }
    }
  
}