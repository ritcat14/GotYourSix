package com.ritcat14.GotYourSix.util;

import com.ritcat14.GotYourSix.Game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;


public class Installer implements ActionListener {
  JFrame frame = null;
<<<<<<< HEAD
  public static boolean installed = FileHandler.fileExists(FileHandler.startDir);
=======
  public static boolean installed = FileHandler.fileExists(FileHandler.localUserFile);
>>>>>>> be10df84ef0068172709da65b942c285d33a0083
  private JButton install = null;
  public Installer(){
    
    this.frame = new JFrame("Install Got Your Six");
    this.frame.setSize(300, 400);
    this.frame.setLocationRelativeTo(null);
    this.frame.setUndecorated(true);
    this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    this.install = new JButton("Install");
    this.install.setVerticalTextPosition(AbstractButton.CENTER);
    this.install.setHorizontalTextPosition(AbstractButton.CENTER);
    this.install.addActionListener(this);
    this.install.setToolTipText("Click to install");
    this.install.setVisible(true);
    
    this.frame.getContentPane().setLayout(new BorderLayout());
    this.frame.getContentPane().add(this.install, "Center");
    this.frame.setVisible(true);
  }
  
  public void setVisible(boolean arg){
      frame.setVisible(arg);
  }

public void actionPerformed(ActionEvent arg0) {
    FileHandler.setupGame();
    this.install.setText(System.getenv("userprofile"));
  try {
    createInternetShortcut("Got Your Six", FileHandler.netDir + "Current Version/GotYourSix-" + Game.getVersion(this) + ".jar");
  } catch (IOException e){}
  this.install.setVisible(false);
  this.install.enable(false);
  Game.startGame();
  this.frame.dispose();
}
  public static String getWindowsCurrentUserPath() {
    return FileHandler.startDir;
  }
  
  public static void createInternetShortcut(String name, String target) throws IOException {
     String path = getWindowsCurrentUserPath() + "/"+ name + ".URL";
     createInternetShortcut(name, path, target, "");
  }
  
  public static void createInternetShortcut
      (String name, String target, String icon) throws IOException {
    String path = getWindowsCurrentUserPath() + "/"+ name + ".URL";
    createInternetShortcut(name, path, target, icon);
  }
  
  public static void createInternetShortcut (String name, String where, String target, String icon) throws IOException {
    FileWriter fw = new FileWriter(where);
    fw.write("[InternetShortcut]\n");
    fw.write("URL=" + target + "\n");
    if (!icon.equals(""))  {
      fw.write("IconFile=" + icon + "\n");  
    }
    fw.flush();
    fw.close();
  }
}