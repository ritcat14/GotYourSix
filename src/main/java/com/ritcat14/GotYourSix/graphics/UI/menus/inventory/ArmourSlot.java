package com.ritcat14.GotYourSix.graphics.UI.menus.inventory;

import java.awt.Font;

import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.UI.UILabel;
import com.ritcat14.GotYourSix.items.Item;
import com.ritcat14.GotYourSix.items.armour.Armour;
import com.ritcat14.GotYourSix.util.Vector2i;

public class ArmourSlot extends Slot {
  
    private String type = "";
    private int defence = 0;
  
    public ArmourSlot(Vector2i position, String type, Player p){
      super(position, true, p);
      this.type = type;
      UILabel typeLabel = new UILabel(new Vector2i(-30, 5), type);
      typeLabel.setFont(new Font("Magneto",Font.BOLD, 20));
      addComponent(typeLabel);
    }
  
    public String getType(){
        return type;
    }
  
    public boolean add(Item item){
        if (item instanceof Armour){
          System.out.println("Item is armour");
            if (((Armour)item).getType() == type){
                items.add(item);
                setBackgroundImage(item.getImage());
                return true;
            }
        }
        return false;
    }
  
    public boolean remove(Item item){
        if (item instanceof Armour){
            if (((Armour) item).getType() == type){
                items.remove(item);
                updateBack();
                return true;
            }
        }
        return false;
    }
  
    public int getDefence(){
      defence = ((Armour)(getItems().get(0))).getDefence();
      return defence;
    }
  
}
