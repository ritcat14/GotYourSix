package com.ritcat14.GotYourSix.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ritcat14.GotYourSix.Game;
import com.ritcat14.GotYourSix.entity.Entity;
import com.ritcat14.GotYourSix.entity.mob.Door;
import com.ritcat14.GotYourSix.entity.mob.Enemy;
import com.ritcat14.GotYourSix.entity.mob.Player;
import com.ritcat14.GotYourSix.entity.particle.Particle;
import com.ritcat14.GotYourSix.entity.projectile.Projectile;
import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.level.tile.Tile;
import com.ritcat14.GotYourSix.util.Vector2i;

public class Level {

    protected int            width, height;
    protected int[]          tilesInt;
    protected int[]          tiles;
    protected int            tile_size;
    private boolean          createdDoors = false;
    private int xScroll, yScroll;

    private List<Entity>     entities     = new ArrayList<Entity>();
    private List<Projectile> projectiles  = new ArrayList<Projectile>();
    private List<Particle>   particles    = new ArrayList<Particle>();
    private List<Player>     players      = new ArrayList<Player>();
    private List<Enemy>      enemies      = new ArrayList<Enemy>();
    private List<Door>       doors        = new ArrayList<Door>();

    private Comparator<Node> nodeSorter   = new Comparator<Node>() {
                                              public int compare(Node n0, Node n1) {
                                                  if (n1.fCost < n0.fCost)
                                                      return +1;
                                                  if (n1.fCost > n0.fCost)
                                                      return -1;
                                                  return 0;
                                              }
                                          };

    public static Level      spawn        = new SpawnLevel("/levels/spawn.png");
    public static Level      test         = new TestLevel("/levels/testLevel.png");
    public static Level      level1       = new Level1("/levels/level1.png");

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        tilesInt = new int[width * height];
        generateLevel();
    }

    public Level(String path) {
        loadLevel(path);
        generateLevel();
    }

    protected void generateLevel() {
    }

    protected void loadLevel(String path) {
    }

    public void update() {
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
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
            enemies.get(i).checkLocation();
        }
        if (!createdDoors) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (tiles[x + y * width] == Tile.col_door) {
                        Door d = new Door(x, y);
                        add(d);
                    }
                }
            }
          createdDoors = true;
        } 
        for (int i = 0; i < doors.size(); i++) {
            doors.get(i).update();
        }
        remove();
    }

    private void remove() {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).isRemoved())
                entities.remove(i);
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

    public Player getPlayerAt(int index) {
        return players.get(index);
    }

    public Player getClientPlayer() {
        return players.get(0);
    }
  
    public int[] getTiles(){
        return tiles;
    }
  
    public int getWidth(){
        return width;
    }
  
    public int getHeight(){
        return height;
    }
  
    public int xScroll(){
       return xScroll;
    }
  
    public int yScroll(){
        return yScroll;
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

    public void render(int xScroll, int yScroll, Screen screen) {
        this.xScroll = xScroll;
        this.yScroll = yScroll;
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
  
   public void renderEntities(Screen screen){
       for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(screen);
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
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(screen);
        }
        for (int i = 0; i < doors.size(); i++) {
            doors.get(i).render(screen);
        }
   }

    public void add(Entity e) {
        e.init(this);
        if (e instanceof Particle) {
            particles.add((Particle)e);
        } else if (e instanceof Projectile) {
            projectiles.add((Projectile)e);
        } else if (e instanceof Player) {
            players.add((Player)e);
        } else if (e instanceof Enemy) {
            enemies.add((Enemy)e);
        } else if (e instanceof Door) {
            doors.add((Door)e);
        } else {
            entities.add(e);
        }
    }

    public void setPlayerLocation() {
    }

    // 0xff00ff00 grass
    // 0xffffff00 flower
    // 0xff7f7f00 rock
    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return Tile.water;
        if (tiles[x + y * width] == Tile.col_grass)
            return Tile.grass;
        if (tiles[x + y * width] == Tile.col_flower)
            return Tile.flower;
        if (tiles[x + y * width] == Tile.col_brickWall)
            return Tile.brickWall;
        if (tiles[x + y * width] == Tile.col_gravel)
            return Tile.gravel;
        if (tiles[x + y * width] == Tile.col_mossWall)
            return Tile.mossWall;
        if (tiles[x + y * width] == Tile.col_water)
            return Tile.water;
        if (tiles[x + y * width] == Tile.col_woodFloor)
            return Tile.woodFloor;
        if (tiles[x + y * width] == Tile.col_portal)
            return Tile.portal;
        if (tiles[x + y * width] == Tile.col_door)
            return Tile.door;
        if (tiles[x + y * width] == Tile.col_mossWallShad)
            return Tile.mossWallShad;
        if (tiles[x + y * width] == Tile.col_brickWallShad)
            return Tile.brickWallShad;

        if (tiles[x + y * width] == Tile.col_wallGrassTL)
            return Tile.wallGrassTL;
        if (tiles[x + y * width] == Tile.col_wallGrassTM)
            return Tile.wallGrassTM;
        if (tiles[x + y * width] == Tile.col_wallGrassTR)
            return Tile.wallGrassTR;
        if (tiles[x + y * width] == Tile.col_wallGrassLM)
            return Tile.wallGrassLM;
        if (tiles[x + y * width] == Tile.col_wallGrassRM)
            return Tile.wallGrassRM;
        if (tiles[x + y * width] == Tile.col_wallGrassBL)
            return Tile.wallGrassBL;
        if (tiles[x + y * width] == Tile.col_wallGrassBM)
            return Tile.wallGrassBM;
        if (tiles[x + y * width] == Tile.col_wallGrassBR)
            return Tile.wallGrassBR;

        if (tiles[x + y * width] == Tile.col_wallWaterTL)
            return Tile.wallWaterTL;
        if (tiles[x + y * width] == Tile.col_wallWaterTM)
            return Tile.wallWaterTM;
        if (tiles[x + y * width] == Tile.col_wallWaterTR)
            return Tile.wallWaterTR;
        if (tiles[x + y * width] == Tile.col_wallWaterLM)
            return Tile.wallWaterLM;
        if (tiles[x + y * width] == Tile.col_wallWaterRM)
            return Tile.wallWaterRM;
        if (tiles[x + y * width] == Tile.col_wallWaterBL)
            return Tile.wallWaterBL;
        if (tiles[x + y * width] == Tile.col_wallWaterBM)
            return Tile.wallWaterBM;
        if (tiles[x + y * width] == Tile.col_wallWaterBR)
            return Tile.wallWaterBR;
        return Tile.water;
    }

}
