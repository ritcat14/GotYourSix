package com.ritcat14.GotYourSix.items.armour;

import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.items.Item;

public class Armour extends Item {
  
    private int defence = 0, pLevel = 0;
    private String type = "";
    
    public Armour(int defence, String type, Sprite sprite, int pLevel){
        this.defence = defence;
        this.type = type;
        this.sprite = sprite;
        this.pLevel = pLevel;
    }
  
    public int getDefence() {
      return defence;
    }
    
    public String getType() {
      return type;
    }
  
    public int getLevel() {
      return pLevel;
    } 
}