package io.khp.github;

import java.util.ArrayList;

public class Map {
	
	private Tile[][] tileArray;
	private int tileHeight;
	private int tileWidth;

	Map (int pxWidth, int pxHeight, int pxTileWidth, int pxTileHeight, 
			Tile[][] tileArray) {
		this.tileHeight = pxHeight/pxTileHeight;
		this.tileWidth = pxWidth/pxTileWidth;
		
	}
	
	
	// initialize test map
	Map () {

		tileWidth = 26;
		tileHeight = 41;
		
		tileArray = new Tile[tileWidth + 1][tileHeight + 1];
		
		for (int i = 0; i <= tileWidth; i++) {
			for (int j = 0; j <= tileHeight; j++){
				if(i == 0 || i == tileWidth || j == 0 || j == tileHeight) {
					tileArray[i][j] = new Tile(TileType.WALL, 
							(i - 1)*20, (j - 1)*20);
				} else {
					tileArray[i][j] = new Tile(TileType.EMPTY, 
							(i - 1)*20, (j - 1)*20);
				}
			}
		}
		tileArray[24][24].setType(TileType.WALL);
		tileArray[23][23].setType(TileType.WALL);
	}
	
	
	
	
	
	
	
}
