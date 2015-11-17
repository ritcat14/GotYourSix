package com.ritcat14.GotYourSix.graphics.UI.menus;

import java.util.ArrayList;
import java.util.List;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.items.Item;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Inventory extends UIPanel {

    private Slot       slot     = null;
    private int        slotSize = 50;

    private static List<Slot> slots    = new ArrayList<Slot>();

    public Inventory() {
        super(new Vector2i(50, 50), new Vector2i(Game.getAbsoluteWidth() - 100, Game.getAbsoluteHeight() - 100),
              ImageUtil.getImage("/ui/panels/inventory/inventory.png"));
        for (int x = 5; x < 14; x++) {
            for (int y = 0; y < 7; y++) {
                slot = new Slot(new Vector2i((x * (slotSize + 6)) + 50, ((y * (slotSize + 6)) + 50) + 12));
                slots.add(slot);
                addComponent(slot);
            }
        }
    }

    public void add(Item item) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).add(item))
                break;
        }
    }
  
    public static List<Slot> getSlots(){
      return slots;
    }

    public void remove(Item item) {

    }

}
