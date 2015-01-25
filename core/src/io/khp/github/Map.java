
package io.khp.github;
import java.util.ArrayList;

import com.badlogic.gdx.math.Intersector;
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
	
	
	public Tile[][] getTileArray() {
		return tileArray;
	}
	
	public ArrayList<Rectangle> getRectList() {
		return rectList;
	}	
}
