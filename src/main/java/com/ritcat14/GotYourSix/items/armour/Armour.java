package com.ritcat14.GotYourSix.items.armour;

import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.items.Item;

public class Armour extends Item {
  
    protected int defence = 0;
    protected int strength = 0;
    
    public Armour(int defence, int strength, Sprite sprite){
        this.defence = defence;
        this.strength = strength;
        this.sprite = sprite;
    }
  
    public int getDefence(){
      return defence;
    }
  
    public void reduceStrength(){
      strength --;
    }
  
    public int getStrrength(){
      return strength;
    }
  
}
