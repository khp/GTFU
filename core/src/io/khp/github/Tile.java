package io.khp.github;

import com.badlogic.gdx.math.Rectangle;


public class Tile implements Collidable {
	
	private int x;
	private int y;
	private TileType type;
	private boolean collision; 
	// The enum is a type. We are assigning "type" to the enum TileType
	private Rectangle rect;
	

	final static int WIDTH = 20;
	final static int HEIGHT = 20;
	
    
	

	
	public Tile(TileType type, int x, int y){
		this.type = type;
		if (this.type == TileType.WALL) {
			this.rect = new Rectangle(x*20, (y-1)*20, 20, 20);
		}
		this.x = x;
		this.y = y;
	}
	
	public Rectangle getRectangle() {
		return this.rect;
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
	
	public void checkCollisions(Player player){}
    
	
	
	
}
