package com.ritcat14.GotYourSix.graphics.UI.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.level.Level;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Minimap extends UIPanel{
  
    private Game game = Game.getGame();
    private int[] tiles;
    private Level level;
    private BufferedImage    image       = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
    private int[] pixels= ((DataBufferInt)image.getRaster().getDataBuffer()).getData();;
    private Screen screen;
  
    public Minimap(Vector2i position) {
        super(position, new Vector2i(300, 300), ImageUtil.getImage("/ui/mapBack.png"));
        screen = new Screen(image.getWidth(), image.getHeight());
    }
  
    public void setLevel(Level level){
        this.level = level;
        this.tiles = level.getTiles();
    }
  
    public void render(Graphics g){
        super.render(g);
        BufferStrategy bs = game.getBufferStrategy();
        if (bs == null) {
            game.createBufferStrategy(3);
            return;
        }

        screen.clear();

        g = bs.getDrawGraphics();
        if (level != null){
            Player player = level.getPlayerAt(0);
            if (player != null) {
              int xScroll = (int)(player.getX() - screen.width / 2);
              int yScroll = (int)(player.getY() - screen.height / 2);
              screen.setOffset(xScroll, yScroll);
              int x0 = xScroll >> 4;
              int x1 = (xScroll + screen.width + 16) >> 4;
              int y0 = yScroll >> 4;
              int y1 = (yScroll + screen.height + 16) >> 4;
              for (int y = y0; y < y1; y++) {
                for (int x = x0; x < x1; x++) {
                  level.getTile(x, y).render(x, y, screen);
                }
              }
              level.renderEntities(screen);
              //TODO: render icons for entities from level entity lists, rather than entities.
              //TODO: fix pause menu glitch
            }
          }

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }
        g.setColor(new Color(0xff00ff));
        g.fillRect(position.x + 25, position.y + 25, screen.width, screen.width);
      
        g.drawImage(image, position.x + 25, position.y + 25, screen.width, screen.width, null);
        g.dispose();
        bs.show();
    }
  
}