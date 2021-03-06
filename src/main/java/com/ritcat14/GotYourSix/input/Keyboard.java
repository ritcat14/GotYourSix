package com.ritcat14.GotYourSix.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	private boolean[] keys = new boolean[1000];
	public boolean up = false, down = false, left = false, right = false, sprint = false,
   	sel1 = false, sel2 = false, sel3 = false, sel4 = false, sel5 = false, sel6 = false,
  		invnt = false, map = false, paused = false, home = false;
	
	public void update(){
		up = keys[KeyEvent.VK_UP] ||keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] ||keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] ||keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] ||keys[KeyEvent.VK_D];
      sprint = keys[KeyEvent.VK_SHIFT];
      sel1 = keys[KeyEvent.VK_1];
      sel2 = keys[KeyEvent.VK_2];
      sel3 = keys[KeyEvent.VK_3];
      sel4 = keys[KeyEvent.VK_4];
      sel5 = keys[KeyEvent.VK_5];
      sel6 = keys[KeyEvent.VK_6];
      invnt = keys[KeyEvent.VK_Q];
      map = keys[KeyEvent.VK_M];
      paused = keys[KeyEvent.VK_ESCAPE];
      home = keys[KeyEvent.VK_HOME] || keys[KeyEvent.VK_H];
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
}
