package com.ritcat14.GotYourSix.entity.mob;

import com.ritcat14.GotYourSix.graphics.Screen;

public abstract class Enemy extends Mob{

    public void render(Screen screen) {
        
    }

    public abstract void update();
  
    public void loseHealth(int damage){
        health -= damage;
        if (health <= 0){
            remove();
        }
    }
  
    public int getHealth(){
        return health;
    }
}
