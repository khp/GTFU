package io.khp.github;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

public class Map {
	
	private Tile[][] tileArray;
	private ArrayList<Rectangle> rectList;
	private int tileHeight;
	private int tileWidth;

	public Map (Tile[][] tileArray) {
		this.tileArray = tileArray;
	}
	
	
	// initialize test map
	public Map () {

		tileWidth = 41;
		tileHeight = 26;
		
		tileArray = new Tile[tileWidth + 1][tileHeight + 1];
		rectList = new ArrayList<Rectangle>();
		
		for (int i = 0; i <= tileWidth; i++) {
			for (int j = 0; j <= tileHeight; j++){
				if(i == 0 || i == tileWidth || j == 0 || j == tileHeight
						|| i == 2) {
					tileArray[i][j] = new Tile(TileType.WALL, 
							i, j-1);
				} else {
					tileArray[i][j] = new Tile(TileType.EMPTY, 
							i, j-1);
				}
			}
		}
		for (int i = 0; i <= tileWidth; i++) {
			for (int j = 0; j <= tileHeight; j++) {
				if (tileArray[i][j].getType() == TileType.WALL) {
					rectList.add(tileArray[i][j].getRectangle());
				}
			}
		}
	}
	
	public ArrayList<Rectangle> getRectList() {
		return rectList;
	}	
}
