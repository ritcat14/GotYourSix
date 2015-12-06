package com.ritcat14.GotYourSix.util;
import com.ritcat14.GotYourSix.Game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.Color;
import java.awt.Font;


public class Console implements ActionListener{
  JFrame frame = null;
  JTextPane textPane = null;
  public ArrayList<String> text = new ArrayList<String>();
  public boolean hasInputText = false;
  
  public Console(){
    this.frame = new JFrame("Server Console");
    this.frame.setSize(600, 600);
    JFrame gameFrame = Game.getGame().getFrame();
    this.frame.setLocation(gameFrame.getX() + gameFrame.getWidth(), gameFrame.getY());
    this.frame.setUndecorated(false);
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    this.textPane = new JTextPane();
    this.textPane.setEditable(false);
    this.textPane.setBackground(Color.black);
    this.textPane.setForeground(Color.green);
    this.textPane.setFont(new Font("Lucida Console", Font.PLAIN, 12));
    this.textPane.addKeyListener(new KeyAdapter(){
      
      public void keyPressed(KeyEvent e)
        {
        
      }
      
    });
    
    this.frame.getContentPane().setLayout(new BorderLayout());
    this.frame.getContentPane().add(new JScrollPane(this.textPane), "Center");
    this.frame.setVisible(true);
    redirectSystemStreams();
  }
  
  private void updateTextPane(final String text) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        Document doc = textPane.getDocument();
        try {
          doc.insertString(doc.getLength(), text, null);
        } catch (BadLocationException e) {
          throw new RuntimeException(e);
        }
        textPane.setCaretPosition(doc.getLength() - 1);
      }
    });
  }

  private void redirectSystemStreams() {
    OutputStream out = new OutputStream() {
      @Override
      public void write(final int b) throws IOException {
        updateTextPane(String.valueOf((char) b));
      }

      @Override
      public void write(byte[] b, int off, int len) throws IOException {
        updateTextPane(new String(b, off, len));
      }

      @Override
      public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
      }
    };

    System.setOut(new PrintStream(out, true));
    System.setErr(new PrintStream(out, true));
  }
  
  public void setVisible(boolean arg){
      frame.setVisible(arg);
  }
  
  public ArrayList<String> getText(){
    hasInputText = false;
    ArrayList<String> text2 = new ArrayList<String>();
    text2.addAll(this.text);
    this.text.clear();
    return text2;
  }
  
  public void clearTextArea(){
    this.textPane.setText("");
  }
  
  public void addText(String line){
    try{
      Document doc = textPane.getDocument();
      doc.insertString(doc.getLength(),line + "\n", null);
    }catch(Exception e){}
  }
  
  public void actionPerformed(ActionEvent e){
  }
}