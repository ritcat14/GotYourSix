package com.ritcat14.GotYourSix.graphics.UI.menus.inventory;

import java.awt.Font;

import com.ritcat14.GotYourSix.graphics.UI.UILabel;
import com.ritcat14.GotYourSix.items.Item;
import com.ritcat14.GotYourSix.items.armour.Armour;
import com.ritcat14.GotYourSix.util.Vector2i;

public class ArmourSlot extends Slot {
  
    private String type = "";
    private int defence = 0;
  
    public ArmourSlot(Vector2i position, String type){
      super(position, true);
      this.type = type;
      UILabel typeLabel = new UILabel(new Vector2i(-30, 5), type);
      typeLabel.setFont(new Font("Magneto",Font.BOLD, 20));
      addComponent(typeLabel);
    }
  
    public boolean add(Item item){
        if (item instanceof Armour){
            if (((Armour)item).getType() == this.type){
                return true;
            }
        }
        return false;
    }
  
    public boolean remove(Item item){
        if (item instanceof Armour){
            if (((Armour) item).getType() == this.type){
                return true;
            }
        }
        return false;
    }
  
    public int getdefence(){
      defence = ((Armour)(getItems().get(0))).getDefence();
      return defence;
    }
  
}
