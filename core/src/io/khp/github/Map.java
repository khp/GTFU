package io.khp.github;

import java.util.ArrayList;

public class Map {
	
	private ArrayList<Tile> tileArray;
	private int tileHeight = 10;
	private int tileWidth = 10;

	Map (int pxWidth, int pxHeight, int pxTileWidth, int pxTileHeight) {
		tileHeight = pxHeight/pxTileHeight;
		tileWidth = pxWidth/pxTileWidth;
	}
	
	
}
