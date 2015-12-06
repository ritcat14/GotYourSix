package com.ritcat14.GotYourSix.level;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.awt.Graphics2D;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.entity.portal.*;
import com.ritcat14.GotYourSix.entity.mob.enemy.Enemy;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.entity.particle.Particle;
import com.ritcat14.GotYourSix.entity.projectile.Projectile;
import com.ritcat14.GotYourSix.events.Event;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.layers.Layer;
import com.ritcat14.GotYourSix.items.Item;
import com.ritcat14.GotYourSix.level.tile.Tile;
import com.ritcat14.GotYourSix.level.worlds.*;
import com.ritcat14.GotYourSix.util.ImageUtil;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Level extends Layer {

    protected int               width       = 0, height = 0, tile_size = 0;
    protected int[]             tilesInt    = null, tiles = null;
    protected TileCoordinate    playerPos   = null;
    private int                 xScroll     = 0, yScroll = 0;

    private List<Entity>        entities    = new ArrayList<Entity>();
    private List<Projectile>    projectiles = new ArrayList<Projectile>();
    private List<Particle>      particles   = new ArrayList<Particle>();
    private List<Player> players     = new ArrayList<Player>();
    private List<Enemy>         enemies     = new ArrayList<Enemy>();
    private List<Item>          items       = new ArrayList<Item>();
    private List<Portal> portals = new ArrayList<Portal>();
    private BufferedImage       mapImage    = null;
    private static boolean generatedPortals = false;

    private Comparator<Node>    nodeSorter  = new Comparator<Node>() {
                                                public int compare(Node n0, Node n1) {
                                                    if (n1.fCost < n0.fCost)
                                                        return +1;
                                                    if (n1.fCost > n0.fCost)
                                                        return -1;
                                                    return 0;
                                                }
                                            };

    public static Level level1 = new BossLevel("/levels/level1.png", "Zombie", 60);
    public static Level level2 = new LavaLevel("/levels/level2.png", "Mummy", 120);
    public static Level level3 = new BossLevel("/levels/level1.png", "Goblin", 180);
    public static Level level4 = new BossLevel("/levels/level1.png", "Mummy", 240);
    public static Level level5 = new BossLevel("/levels/level1.png", "Goblin", 180);
    public static Level level6 = new BossLevel("/levels/level1.png", "Zombie", 60);
    public static Level         spawn       = new SpawnLevel("/levels/spawn.png");

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        tilesInt = new int[width * height];
        generateLevel();
    }

    public Level(String path, boolean boss) {
        loadLevel(path);
        mapImage = ImageUtil.getImage(path);
        if (!boss) generateLevel();
    }

    public BufferedImage getMapImage() {
        return mapImage;
    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        int type = 0;
        type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
  
    public static void initLevels(){
        Level.level1 = new BossLevel("/levels/level1.png", "Zombie", 60);
        Level.level2 = new LavaLevel("/levels/level1.png", "Mummy", 120);
        Level.level3 = new BossLevel("/levels/level1.png", "Goblin", 180);
        Level.level4 = new BossLevel("/levels/level1.png", "Mummy", 240);
        Level.level5 = new BossLevel("/levels/level1.png", "Goblin", 180);
    	  Level.level6 = new BossLevel("/levels/level1.png", "Zombie", 60);
        Level.spawn       = new SpawnLevel("/levels/spawn.png");
        generatedPortals = false;
    }
  
    public void generatePortals(){
        spawn.add(Portal.Level1 = new LevelPortal(4,4,this,level1));
        spawn.add(Portal.Level2 = new LevelPortal(6,4,this,level2));
        spawn.add(Portal.Level3 = new LevelPortal(8,4,this,level3));
        spawn.add(Portal.Level4 = new LevelPortal(10,4,this,level4));
        spawn.add(Portal.Level5 = new LevelPortal(12,4,this,level5));
        spawn.add(Portal.Level6 = new LevelPortal(14,4,this,level6));
        generatedPortals = true;
    }

    protected void generateLevel() {
    }

    protected void loadLevel(String path) {
    }

    public void update() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).updateInvent();
            players.get(i).updateMap();
        }
        if (!Game.paused) {
            if (Game.loaded) {
                for (int i = 0; i < entities.size(); i++) {
                    entities.get(i).update();
                }
            }
            for (int i = 0; i < projectiles.size(); i++) {
                projectiles.get(i).update();
            }
            for (int i = 0; i < particles.size(); i++) {
                particles.get(i).update();
            }
            for (int i = 0; i < players.size(); i++) {
                players.get(i).update();
            }
            for (int i = 0; i < portals.size(); i++) {
                portals.get(i).update();
            }
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update();
                enemies.get(i).checkLocation();
            }
        }
        if (!generatedPortals && spawn != null) generatePortals();
        remove();
    }

    public void onEvent(Event event) {
        getClientPlayer().onEvent(event);
    }
  
    public void removePlayer(int i){
        players.remove(i);
    }

    private void remove() {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).isRemoved())
                entities.remove(i);
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isRemoved())
                items.remove(i);
        }
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).isRemoved())
                projectiles.remove(i);
        }
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).isRemoved())
                particles.remove(i);
        }
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isRemoved())
                players.remove(i);
        }
        for (int i = 0; i < portals.size(); i++) {
            if (portals.get(i).isRemoved())
                portals.remove(i);
        }
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isRemoved())
                enemies.remove(i);
        }
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Item> getItems() {
        return items;
    }

    public Player getPlayerAt(int index) {
        return players.get(index);
    }

    public Player getClientPlayer() {
        return players.get(0);
    }

    public int[] getTiles() {
        return tiles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int xScroll() {
        return xScroll;
    }

    public int yScroll() {
        return yScroll;
    }

    public TileCoordinate getPlayerPos() {
        return playerPos;
    }

    public List<Node> findPath(Vector2i start, Vector2i goal) {
        List<Node> openList = new ArrayList<Node>();
        List<Node> closedList = new ArrayList<Node>();
        Node current = new Node(start, null, 0, getDistance(start, goal));
        openList.add(current);
        while (openList.size() > 0) {
            Collections.sort(openList, nodeSorter);
            current = openList.get(0);
            if (current.tile.samePos(goal)) {
                List<Node> path = new ArrayList<Node>();
                while (current.parent != null) {
                    path.add(current);
                    current = current.parent;
                }
                openList.clear();
                closedList.clear();
                return path;
            }
            openList.remove(current);
            closedList.add(current);
            for (int i = 0; i < 9; i++) {
                if (i == 4)
                    continue;
                int x = current.tile.getX();
                int y = current.tile.getY();
                int xi = (i % 3) - 1;
                int yi = (i / 3) - 1;
                Tile at = getTile(x + xi, y + yi);
                if (at == null)
                    continue;
                if (at.solid())
                    continue;
                Vector2i a = new Vector2i(x + xi, y + yi);
                double gCost = current.gCost + getDistance(current.tile, a) == 1 ? 1 : 0.95;
                ;
                double hCost = getDistance(a, goal);
                Node node = new Node(a, current, gCost, hCost);
                if (vecInList(closedList, a) && gCost >= node.gCost)
                    continue;
                if (!vecInList(openList, a) || gCost < node.gCost)
                    openList.add(node);
            }
        }
        closedList.clear();
        return null;
    }

    private boolean vecInList(List<Node> list, Vector2i vector) {
        for (Node n : list) {
            if (n.tile.equals(vector))
                return true;
        }
        return false;
    }

    private double getDistance(Vector2i tile, Vector2i goal) {
        double dx = tile.getX() - goal.getX();
        double dy = tile.getY() - goal.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public List<Entity> getEntities(Entity e, int radius) {
        List<Entity> result = new ArrayList<Entity>();
        int ex = (int)e.getX();
        int ey = (int)e.getY();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity.equals(e))
                continue;
            int x = (int)entity.getX();
            int y = (int)entity.getY();
            int dx = Math.abs(x - ex);
            int dy = Math.abs(y - ey);
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            if (distance <= radius)
                result.add(entity);
        }
        return result;
    }

    public List<Player> getPlayers(Entity e, int radius) {
        List<Player> result = new ArrayList<Player>();
        int ex = (int)e.getX();
        int ey = (int)e.getY();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int x = (int)player.getX();
            int y = (int)player.getY();
            int dx = Math.abs(x - ex);
            int dy = Math.abs(y - ey);
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            if (distance <= radius)
                result.add(player);
        }
        return result;
    }

    public void time() {
    }

    public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
        boolean solid = false;
        for (int c = 0; c < 4; c++) {
            int xt = (x - c % 2 * size + xOffset) >> 4;
            int yt = (y - c / 2 * size + yOffset) >> 4;
            if (getTile(xt, yt).solid())
                solid = true;
        }
        return solid;
    }

    public void setScroll(int xScroll, int yScroll) {
        this.xScroll = xScroll;
        this.yScroll = yScroll;
    }

    public void render(Screen screen) {
        screen.setOffset(xScroll, yScroll);
        int x0 = xScroll >> 4;
        int x1 = (xScroll + screen.width + 16) >> 4;
        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.height + 16) >> 4;

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                getTile(x, y).render(x, y, screen);
            }
        }
        renderEntities(screen);
        Game.loaded = true;
    }

    public void renderEntities(Screen screen) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(screen);
        }
        for (int i = 0; i < items.size(); i++) {
            items.get(i).render(screen);
        }
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(screen);
        }
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).render(screen);
        }
        for (int i = 0; i < players.size(); i++) {
            players.get(i).render(screen);
        }
        for (int i = 0; i < portals.size(); i++) {
            portals.get(i).render(screen);
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(screen);
        }
    }

    public void add(Object e) {
        if (e instanceof Entity)
            ((Entity)e).init(this);
        if (e instanceof Particle) {
            particles.add((Particle)e);
        } else if (e instanceof Projectile) {
            projectiles.add((Projectile)e);
        } else if (e instanceof Player) {
            players.add((Player)e);
        } else if (e instanceof Enemy) {
            enemies.add((Enemy)e);
        } else if (e instanceof Item) {
            items.add((Item)e);
        } else if (e instanceof Portal) {
            portals.add((Portal)e);
        } else {
            entities.add((Entity)e);
        }
    }

    public void setPlayerLocation() {
    }

    // 0xff00ff00 grass
    // 0xffffff00 flower
    // 0xff7f7f00 rock
    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height){
          if (this instanceof LavaLevel) return Tile.lava;
          else return Tile.water;
        }
        if (tiles[x + y * width] == Tile.col_sand)
          return Tile.sand;
        if (tiles[x + y * width] == Tile.col_stone)
          return Tile.stone;
        if (tiles[x + y * width] == Tile.col_wall)
          return Tile.wall;
        if (tiles[x + y * width] == Tile.col_wallShad)
          return Tile.wallShad;
        if (tiles[x + y * width] == Tile.col_crust)
          return Tile.crust;
        if (tiles[x + y * width] == Tile.col_wood)
          return Tile.wood;
        if (tiles[x + y * width] == Tile.col_moss)
          return Tile.moss;
        if (tiles[x + y * width] == Tile.col_mossShad)
          return Tile.mossShad;
        if (tiles[x + y * width] == Tile.col_grassTL)
          return Tile.grassTL;
        if (tiles[x + y * width] == Tile.col_grassML)
          return Tile.grassML;
        if (tiles[x + y * width] == Tile.col_grassBL)
          return Tile.grassBL;
        if (tiles[x + y * width] == Tile.col_grassTM)
          return Tile.grassTM;
        if (tiles[x + y * width] == Tile.col_grass)
          return Tile.grass;
        if (tiles[x + y * width] == Tile.col_grassBM)
          return Tile.grassBM;
        if (tiles[x + y * width] == Tile.col_grassTR)
          return Tile.grassTR;
        if (tiles[x + y * width] == Tile.col_grassMR)
          return Tile.grassMR;
        if (tiles[x + y * width] == Tile.col_grassBR)
          return Tile.grassBR;
        if (tiles[x + y * width] == Tile.col_waterTL)
          return Tile.waterTL;
        if (tiles[x + y * width] == Tile.col_waterML)
          return Tile.waterML;
        if (tiles[x + y * width] == Tile.col_waterBL)
          return Tile.waterBL;
        if (tiles[x + y * width] == Tile.col_waterTM)
          return Tile.waterTM;
        if (tiles[x + y * width] == Tile.col_water)
          return Tile.water;
        if (tiles[x + y * width] == Tile.col_waterBM)
          return Tile.waterBM;
        if (tiles[x + y * width] == Tile.col_waterTR)
          return Tile.waterTR;
        if (tiles[x + y * width] == Tile.col_waterMR)
          return Tile.waterMR;
        if (tiles[x + y * width] == Tile.col_waterBR)
          return Tile.waterBR;
        if (tiles[x + y * width] == Tile.col_lavaTL)
          return Tile.lavaTL;
        if (tiles[x + y * width] == Tile.col_lavaML)
          return Tile.lavaML;
        if (tiles[x + y * width] == Tile.col_lavaBL)
          return Tile.lavaBL;
        if (tiles[x + y * width] == Tile.col_lavaTM)
          return Tile.lavaTM;
        if (tiles[x + y * width] == Tile.col_lava)
          return Tile.lava;
        if (tiles[x + y * width] == Tile.col_lavaBM)
          return Tile.lavaBM;
        if (tiles[x + y * width] == Tile.col_lavaTR)
          return Tile.lavaTR;
        if (tiles[x + y * width] == Tile.col_lavaMR)
          return Tile.lavaMR;
        if (tiles[x + y * width] == Tile.col_lavaBR)
          return Tile.lavaBR;
        if (tiles[x + y * width] == Tile.col_enemy)
          return Tile.enemy;
        if (tiles[x + y * width] == Tile.col_boss)
          return Tile.boss;
        if (tiles[x + y * width] == Tile.col_stump)
          return Tile.stump;
        if (tiles[x + y * width] == Tile.col_reed)
          return Tile.reed;
        if (tiles[x + y * width] == Tile.col_bush01)
          return Tile.bush01;
        if (tiles[x + y * width] == Tile.col_bush02)
          return Tile.bush02;
        if (tiles[x + y * width] == Tile.col_rocks)
          return Tile.rocks;
        if (tiles[x + y * width] == Tile.col_grassGrave)
          return Tile.grassGrave;
        if (tiles[x + y * width] == Tile.col_lavaGrave)
          return Tile.lavaGrave;
        if (this instanceof LavaLevel) return Tile.lava;
        else return Tile.water;
    }

}
