package com.ritcat14.GotYourSix.graphics.UI;

import java.util.ArrayList;
import java.util.List;

import com.ritcat14.GotYourSix.items.Item;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Inventory extends UIPanel{
  
    private List<Item> items = new ArrayList<Item>();
  
    public Inventory(Vector2i position) {
        super(position, new Vector2i(250, 250), ImageUtil.getImage("/ui/inventoryBack.png"));
    }
  
    public void add(Item item){
        items.add(item);
        UIButton itemButton = new UIButton(new Vector2i(0,0), item.getImage(), new UIActionListener(){
            public void perform(){
              
            }
        });
        addComponent(itemButton);
    }
  
    public void remove(Item item){
        items.remove(item);
    }
  
}
