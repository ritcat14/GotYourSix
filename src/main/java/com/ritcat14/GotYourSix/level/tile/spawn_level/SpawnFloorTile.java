package com.ritcat14.GotYourSix.level.tile.spawn_level;

import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.level.tile.Tile;

public class SpawnFloorTile extends Tile{

	public SpawnFloorTile(Sprite sprite) {
		super(sprite);
	}
    
    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }

}