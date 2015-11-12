package com.ritcat14.GotYourSix.graphics.UI;
import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.input.Mouse;
import com.ritcat14.GotYourSix.util.Vector2i;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Color;
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
    private String boxName = "";

    public UITextBox(Vector2i position, String text) {
        super(position.add(new Vector2i(0, 30)), "");
        this.boxName = text;
        Game.getGame().addKeyListener(this); //Adds the listener to the game
    }

    public UITextBox(Vector2i position) {
        super(position.add(new Vector2i(0, 30)), "");
        Game.getGame().addKeyListener(this);
    }
  
    public String getText(){
      return boxName;
    }

    private int time = 0;

    public void update() {
        time++;
        super.update();
        Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y - 30, size.x, size.y);
        boolean leftMouseDown = Mouse.getButton() == MouseEvent.BUTTON1;
        if (rect.contains(new Point(Mouse.getX(), Mouse.getY()))) {
            c = new Cursor(Cursor.TEXT_CURSOR); //<- changes the cursor to the text cursor
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
            text = text + "|"; //adds | to the end of the text
        } else if (!selected || time % 60 == 0) { //Every second
            if (text.length() > 0 && text.charAt(text.length() - 1) == '|') {
                text = text.substring(0, text.length() - 1); //removes | from the end of the text
            }
        }
    }
  
    public String getTypedText(){
      return keyString;
    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        if (selected) {
            if (text.length() > 0 && text.charAt(text.length() - 1) == '|') {
                text = text.substring(0, text.length() - 1);
            }
            int id = e.getID(); //gets int ID of the event
            if (id == KeyEvent.KEY_PRESSED) {
                char c = e.getKeyChar(); //converts the key's character value to a character, and adds it to the string
                if (Character.isLetter(c) || Character.isDigit(c)) keyString = "" + c;
            } else {
                int keyCode = e.getKeyCode();
                keyString = KeyEvent.getKeyText(keyCode);
            }
            if (keys[KeyEvent.VK_BACK_SPACE]) {
                if (text.length() > 0)
                    text = text.substring(0, text.length() - 1); //removes a character if you press backspace
                keyString = "";
            } else if (keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_SHIFT]) keyString = "";
          text = text + keyString; //sets the labels text value equal to the string it was before plus the string/character you've typed.
        }
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) {}
  
    public void render(Graphics g){
      super.render(g);
      g.setColor(Color.BLACK);
      g.drawRect(position.x, position.y - (size.y - 5), size.x, size.y);
    }

}
