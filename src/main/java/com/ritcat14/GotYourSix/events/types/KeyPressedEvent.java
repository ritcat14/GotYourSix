package com.ritcat14.GotYourSix.events.types;

import com.ritcat14.GotYourSix.events.Event;

public class KeyPressedEvent extends Event {
  
  protected int key = 0;
  
    protected KeyPressedEvent(int key, Type type){
      super(type);
      this.key = key;
    }
  
    public int getKey(){
      return key;
    }
  
}
