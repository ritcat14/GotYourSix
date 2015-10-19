package com.ritcat14.GotYourSix.graphics.UI.menus;

import java.util.ArrayList;
import java.util.List;

import com.ritcat14.GotYourSix.graphics.UI.UIActionListener;
import com.ritcat14.GotYourSix.graphics.UI.UIButton;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.input.Mouse;
import com.ritcat14.GotYourSix.items.Item;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Inventory extends UIPanel{
  
    private boolean dragged = false;
    
    protected List<Item> items = new ArrayList<Item>();
  
    public Inventory(Vector2i position, String image) {
        super(position, new Vector2i(250, 250), ImageUtil.getImage("/ui/" + image));
    }
  
    public void add(Item item){
        items.add(item);
        UIButton itemButton = new UIButton(new Vector2i(0,0), item.getImage(), new UIActionListener(){
            public void perform(){
                if (!dragged){
                  position = new Vector2i(Mouse.getX(), Mouse.getY());
                  dragged = true;
                } else{
                    
                }
            }
        },"");
        addComponent(itemButton);
    }
  
    public void remove(Item item){
        items.remove(item);
    }
  
}
