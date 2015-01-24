package io.khp.github;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.files.FileHandle;

public class MapDrawer {
	private Pixmap map;
	private Tile[][] translatedTileMap;
	private int WHITE = -1;
	private int BLACK = 255;
			
	
	
	
	public MapDrawer(FileHandle file){
		map = new Pixmap (file);
		TileType tempTileType = null;
		int height = map.getHeight();
		int width = map.getWidth(); 
		translatedTileMap = new Tile[height][width];
		for(int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				int colourCode = map.getPixel(y, x);
				if (colourCode == WHITE){
					tempTileType = TileType.WALL;
				}
				else if (colourCode == BLACK){
					tempTileType = TileType.EMPTY;
				}
				translatedTileMap[y][x] = new Tile(tempTileType, y*Tile.WIDTH, x*Tile.HEIGHT);
				
			}
		}
		
	
	}


	
	
}
