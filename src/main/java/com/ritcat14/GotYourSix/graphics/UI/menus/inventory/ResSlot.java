package com.ritcat14.GotYourSix.graphics.UI.menus.inventory;

import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.util.Vector2i;

public class ResSlot extends Slot {
  
    public ResSlot(Vector2i position, Player p){
      super(position, true, p);
    }
  
    public void update(){
        /*for (int i = 0; i < items.size(); i++){
            if (items.get(i).getType().equals("Water")){
                p.water(10);
            } else if (items.get(i).getType().equals("Food")){
                p.food(5);
            }
            items.remove(i);
        }
        super.update();*/
    }  
}
