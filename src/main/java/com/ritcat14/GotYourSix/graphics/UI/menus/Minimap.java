package com.ritcat14.GotYourSix.graphics.UI.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Minimap extends UIPanel {

    private Game          game  = Game.getGame();
    private BufferedImage image = null;
    private Level level = null;

    public Minimap() {
        super(new Vector2i(50,50), new Vector2i(Game.getAbsoluteWidth() - 100, Game.getAbsoluteHeight() - 100),
              ImageUtil.getImage("/ui/panels/map/background.png"));
    }
  
    public void setLevel(Level level){
        image = level.getMapImage();
        this.level = level;
    }

    public void render(Graphics g) {
        super.render(g);
        int x1 = (position.x + (getBounds().width/2)) - (image.getWidth()/2);
        int y1 = (position.y + (getBounds().height/2)) - (image.getHeight()/2);
        if (image != null) g.drawImage(image, x1, y1, null);
        if (level != null){
          for (int i = 0; i < level.getPlayers().size(); i++){
            int x2 = (int)(((level.getPlayers().get(i).getX() - 16) / 16));
            int y2 = (int)(((level.getPlayers().get(i).getY() - 16) / 16));
            g.setColor(Color.ORANGE);
            g.fillRect(x1 + x2, y1 + y2, 5, 5);
        }
          for (int i = 0; i < level.getEnemies().size(); i++){
            int x2 = (int)(((level.getEnemies().get(i).getX() - 16) / 16));
            int y2 = (int)(((level.getEnemies().get(i).getY() - 16) / 16));
            g.setColor(Color.RED);
            g.fillRect(x1 + x2, y1 + y2, 5, 5);
          }
        }
    }
  
  public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();

    return dimg;
}  
 
 
}