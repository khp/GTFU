package io.khp.github;


public class Tile {
	
	private int x;
	private int y;
	private TileType type;
	private boolean collision; 
	// The enum is a type. We are assigning "type" to the enum TileType
	

	final static int WIDTH = 20;
	final static int HEIGHT = 20;
	
    
	

	
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
			
	public boolean checkCollision() {
		return collision;
	}
	
	public void setType(TileType type) {
		this.type = type;
	}
    
	
	
	
}
