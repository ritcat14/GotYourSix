package com.ritcat14.GotYourSix.level.tile;

import com.ritcat14.GotYourSix.graphics.Screen;
import com.ritcat14.GotYourSix.graphics.Sprite;
import com.ritcat14.GotYourSix.level.tile.Tile;

public class SpawnWaterTile extends Tile{

	public SpawnWaterTile(Sprite sprite) {
		super(sprite);
	}
    
    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }

}
