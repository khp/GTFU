package io.khp.github;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.files.FileHandle;

public class MapDrawer {
	private Pixmap map;
	private Tile[][] translatedTileMap;
	private int WHITE = -1;
	private int BLACK = 255;
	private int GREEN = 16711935;
	private int BLUE = 65535;
	private int RED = -16776961;
	
	
	
	public MapDrawer(FileHandle file){
		map = new Pixmap (file);
		TileType tempTileType = null;
		int height = map.getHeight();
		int width = map.getWidth(); 
		translatedTileMap = new Tile[width][height];
		for(int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				int colourCode = map.getPixel(x, y);
				if (colourCode == BLACK){
					tempTileType = TileType.WALL;
				}
				else if (colourCode == WHITE){
					tempTileType = TileType.EMPTY;
				}
				else if (colourCode == GREEN){
					tempTileType = TileType.STARTA;
				}
				else if (colourCode == BLUE){
					tempTileType = TileType.STARTB;
				}
				else if (colourCode == RED){
					tempTileType = TileType.END;
				}
				translatedTileMap[x][y] = new Tile(tempTileType, 
						x, 
						height - y);
				
			}
		}	
	}

	public Tile[][] getTranslatedTileMap () {
		return translatedTileMap;
	}
}

