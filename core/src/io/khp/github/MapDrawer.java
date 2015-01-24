package io.khp.github;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.files.FileHandle;

public class MapDrawer {
	private Pixmap map;
	private Tile[][] translatedMap;
	private int WHITE = -1;
	private int BLACK = 255;
			
	
	public MapDrawer(FileHandle file){
		map = new Pixmap (file);
		TileType tempTile = null;
		int height = map.getHeight();
		int width = map.getWidth(); 
		translatedMap = new Tile[height][width];
		for(int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				int colourCode = map.getPixel(j, i);
				if (colourCode == WHITE){
					tempTile = TileType.WALL;
				}
				else if (colourCode == BLACK){
					tempTile = TileType.EMPTY;
				}
				translatedMap[i][j] = new Tile(tempTile, j*20, i*20);
				
			}
		}
		
	
	}


	
	
}
