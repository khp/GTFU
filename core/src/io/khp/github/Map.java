
package io.khp.github;
import java.util.ArrayList;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Map {
	
	final Tile startA = new Tile(TileType.STARTA, 5, 5);
	final Tile startB = new Tile(TileType.STARTB, 10, 5);
	
	private Tile[][] tileArray;
	private int tileHeight;
	private int tileWidth;

	public Map (Tile[][] tileArray) {
		this.tileArray = tileArray;
	}
	
	public Tile findStartA () {
		for (Tile[] tr : tileArray) {
			for (Tile t : tr) {
				if (t.getType() == TileType.STARTA) {
					return t;
				}
			}
		}
		return startA;
	}
	
	public Tile findStartB () {
		for (Tile[] tr : tileArray) {
			for (Tile t : tr) {
				if (t.getType() == TileType.STARTB) {
					return t;
				}
			}
		}
		return startB;
	}
	
	
	// initialize test map
	
	
	public Tile[][] getTileArray() {
		return tileArray;
	}
}
