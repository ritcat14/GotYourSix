package com.ritcat14.GotYourSix.graphics.UI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.util.Vector2i;

public class LoadBar implements Runnable{
  
    private Thread thread;
    private boolean running = false;
    private Graphics g;
  
    private int loaded = 100;
   
    public LoadBar(){
      System.out.println("Created");
    }
    public synchronized void start() {
      System.out.println("Starting...");
        running = true;
        thread = new Thread(this, "Load Bar"); //Initialises the Thread
        thread.start(); //Runs the Thread
    }
  
    public synchronized void stop(){
        System.out.println("Stopped");
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace(); //Catches any errors if the Thread crashes
        }
    }
  
    public void run() {
      System.out.println("running");
      //render(Game.getGame().getFrame());
        /*long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                delta--;
            }
            render(Game.getGame().getGraphics());

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }*/
    }
  
    public boolean isRunning(){
        return running;
    }
  
    private void render(Graphics g){
        this.g = g;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(50, 50, 150, 20);
        //g.dispose();
    }
  
    private void update(){
      
    }
  
}
