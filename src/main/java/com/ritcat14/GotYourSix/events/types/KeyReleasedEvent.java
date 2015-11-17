package com.ritcat14.GotYourSix.events.types;

import com.ritcat14.GotYourSix.events.Event;

public class KeyReleasedEvent extends Event {
  
  protected int key = 0;
  
    protected KeyReleasedEvent(int key, Type type){
      super(type);
      this.key = key;
    }
  
    public int getKey(){
      return key;
    }
  
}
