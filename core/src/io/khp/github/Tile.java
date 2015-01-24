package io.khp.github;


enum TileType{
	END,
	STARTA,
	STARTB,
	EMPTY,
	WALL
}

public class Tile {
	
	private int x;
	private int y;
	private TileType type;
	// The enum is a type. We are assigning "type" to the enum TileType
	
	int WIDTH = 20;
	int HEIGHT = 10;
	
    
	

	
	public Tile(TileType type, int x, int y){
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public TileType getType(){
		return this.type;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
			
    
	
	
	
}
