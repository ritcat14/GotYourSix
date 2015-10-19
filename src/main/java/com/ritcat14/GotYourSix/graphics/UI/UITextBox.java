package com.ritcat14.GotYourSix.graphics.UI;
import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.input.Mouse;
import com.ritcat14.GotYourSix.util.Vector2i;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class UITextBox extends UILabel implements KeyListener {

    private boolean   selected;
    private boolean[] keys      = new boolean[1000];
    private Cursor    c;

    private Vector2i  size      = new Vector2i(150, 30);
    private String    keyString = "";

    public UITextBox(Vector2i position, String text) {
        super(position.add(new Vector2i(0, 30)), text);
        Game.getGame().addKeyListener(this);
    }

    public UITextBox(Vector2i position) {
        super(position, "");
        Game.getGame().addKeyListener(this);
    }

    private int time = 0;

    public void update() {
        time++;
        super.update();
        Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y - 30, size.x, size.y);
        boolean leftMouseDown = Mouse.getButton() == MouseEvent.BUTTON1;
        if (rect.contains(new Point(Mouse.getX(), Mouse.getY()))) {
            c = new Cursor(Cursor.TEXT_CURSOR);
            Game.getGame().setCursor(c);
            if (leftMouseDown) {
                selected = true;
            }
        } else if (!rect.contains(new Point(Mouse.getX(), Mouse.getY()))) {
            c = new Cursor(Cursor.DEFAULT_CURSOR);
            Game.getGame().setCursor(c);
            if (leftMouseDown){
              selected = false;
            }
        }
        if (selected && time % 120 == 0) {
            text = text + "|";
        } else if (!selected || time % 60 == 0) {
            if (text.length() > 0 && text.charAt(text.length() - 1) == '|') {
                text = text.substring(0, text.length() - 1);
            }
        }
    }
  
    public String getTypedText(){
      return keyString;
    }
  
    private void addText(KeyEvent e){
        if (selected) {
            if (text.length() > 0 && text.charAt(text.length() - 1) == '|') {
                text = text.substring(0, text.length() - 1);
            }
            int id = e.getID();
            if (id == KeyEvent.KEY_PRESSED) {
                char c = e.getKeyChar();
                keyString = "" + c;
            } else {
                int keyCode = e.getKeyCode();
                keyString = KeyEvent.getKeyText(keyCode);
            }
            if (keys[KeyEvent.VK_BACK_SPACE]) {
                if (text.length() > 0)
                    text = text.substring(0, text.length() - 1);
                keyString = "";
            } else if (keys[KeyEvent.VK_SPACE])
                keyString = "";
          text = text + keyString;
        }
    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        addText(e);
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) {}
  
    public void render(Graphics g){
      super.render(g);
      
    }

}
