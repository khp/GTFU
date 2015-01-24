package io.khp.github;

import java.util.ArrayList;

public class Map {
	
	private ArrayList<Tile> tileArray;
	private int tileHeight;
	private int tileWidth;

	Map (int pxWidth, int pxHeight, int pxTileWidth, int pxTileHeight) {
		this.tileHeight = pxHeight/pxTileHeight;
		this.tileWidth = pxWidth/pxTileWidth;
		this.tileArray = new ArrayList();
		
	}
	
	Map () {
		tileArray = new ArrayList();
		tileWidth = 24;
		tileHeight = 40;
		for (int i = 0; i < 24 * 40; i++) {
			tileArray.add(new Tile(TileType.EMPTY, 0, 0));
		}
		tileArray.get(24*40-1).setType(TileType.WALL);
		tileArray.get(24*40-2).setType(TileType.WALL);
	}
	
	
	
	
	
	
}
