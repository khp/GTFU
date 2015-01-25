package io.khp.github;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public abstract class Player{
	
	private Rectangle rect;
	private float yVelocity;
	private float xVelocity;
	public final int height = 24;
	public final int width = 24;
	private final int speed = 200;
	private boolean airborne;
	
	public void jump() {
		this.yVelocity = 800;
		this.airborne = true;
	}
	
	// Setters and Getters Below
	public Rectangle getRect() {
		return this.rect;
	}
	
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
	public float getX() {
		return rect.x;
	}
	
	public void setX(float x) {
		rect.x = x;
	}
	
	public float getY() {
		return rect.y;
	}
	
	public void setY(float y) {
		rect.y = y;
	}
	
	public float getWidth() {
		return rect.width;
	}
	
	public float getHeight() {
		return rect.height;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public float getYVelocity() {
		return this.yVelocity;
	}

	public void setYVelocity(float yvel) {
		this.yVelocity = yvel;
	}
	
	public float getXVelocity() {
		return this.xVelocity;
	}
	
	public void setXVelocity(float xvel) {
		this.xVelocity = xvel;
	}
	
	public boolean getAirborne() {
		return this.airborne;
	}
	public void setAirborne(boolean airborne) {
		this.airborne = airborne;
	}
	// Setters and Getters above
	
	
	
	// Collision Code
	public void checkCollisions(Player player, Tile[][] tileArray) {
		checkY(player, tileArray);	
		checkX(player, tileArray);
		
	}
	
	// Check left and right boarders of each player and tile with predictive collision
	// "this" refers to the current player
	// "player" refers to the other player
	// "intersection" refers to the overlap between two players or a player and a wall.
	private void checkX(Player player, Tile[][] tileArray){
		float dt = Gdx.graphics.getDeltaTime();     
		Rectangle playerRect = player.getRect();     
		Rectangle intersection = new Rectangle();
		float dx1 = this.getXVelocity() * dt;
		float dx2 = player.getXVelocity() * dt;
		float x1 = this.getX();
		float x2 = player.getX();
		this.setX(x1 + dx1);    
		player.setX(x2 + dx2);  
		
		if (Intersector.intersectRectangles(playerRect, this.rect, intersection)
				&& intersection.getHeight() > 1) {
			player.setXVelocity((player.getXVelocity() + this.getXVelocity())/2);
			this.setXVelocity(player.getXVelocity());

			for (Tile [] r : tileArray) {
				for (Tile t : r) {
					if (t.getType() == TileType.WALL){
						if (Intersector.intersectRectangles(playerRect, 
								t.getRectangle(), intersection) 
								&& intersection.getHeight() > 100) {
							player.setXVelocity(0);
							this.setXVelocity(0);
							if (intersection.getX() == player.getX()){
								player.setX(intersection.getX() + intersection.getWidth());
								this.setX(player.getX() + player.getWidth());
								return;
							} else {
								player.setX(intersection.getX() - player.getWidth());
								this.setX(player.getX() - this.getWidth());
								return;
							}
						}
						if (Intersector.intersectRectangles(this.rect, 
								t.getRectangle(), intersection)
								&& intersection.getHeight() > 100) {
							player.setXVelocity(0);
							this.setXVelocity(0);
							if (intersection.getX() == this.getX()){
								this.setX(intersection.getX() + intersection.getWidth());
								player.setX(player.getX() + this.getWidth());
								return;
							} else {
								this.setX(intersection.getX() - this.getWidth());
								player.setX(player.getX() - player.getWidth());
								return;
							}
						}
						
					}
				}
			}
		
			
		} 
		this.setX(this.getX() - dx1);
		player.setX(player.getX() - dx2);
		
	}
	
	// Check top and bottom boarders of each player and tile with predictive collision
	// "this" refers to the current player
	// "player" refers to the other player
	// "intersection" refers to the overlap between two players or a player and a wall.
	private void checkY(Player player, Tile[][] tileArray){
		float displacement = Gdx.graphics.getDeltaTime();
		Rectangle playerRect = player.getRect();
		Rectangle intersection = new Rectangle();
		float yVel1 = this.getYVelocity() * displacement;
		float yVel2 = player.getYVelocity() * displacement;
		float y1 = this.getY();
		float y2 = player.getY();
		this.setY(y1 + yVel1);
		player.setY(y2 + yVel2);
		
		if (Intersector.intersectRectangles(playerRect, this.rect, intersection)
				&& intersection.getWidth() > 1) {
			player.setYVelocity((player.getYVelocity() + this.getYVelocity())/2);
			this.setYVelocity(player.getYVelocity());
			
			
			if (this.rect.getY() > playerRect.getY()) {
				this.setAirborne(false);
			} else {
				player.setAirborne(false);
			}
			
			for (Tile [] r : tileArray) {
				for (Tile t : r) {
					if (t.getType() == TileType.WALL){
						if (Intersector.intersectRectangles(playerRect, 
								t.getRectangle(), intersection) 
								&& intersection.getWidth() > 2) {
							player.setYVelocity(0);
							this.setYVelocity(0);
							if (intersection.getY() == player.getY()){
								player.setY(intersection.getY() + intersection.getHeight());
								this.setY(player.getY() + player.getHeight());
								this.setAirborne(false);
								player.setAirborne(false);
								return;
							} else {
								player.setY(intersection.getY() - player.getHeight());
								this.setY(player.getY() - this.getHeight());
								return;
							}
						}
						if (Intersector.intersectRectangles(this.rect, 
								t.getRectangle(), intersection)
								&& intersection.getWidth() > 2) {
							player.setYVelocity(0);
							this.setYVelocity(0);
							if (intersection.getY() == this.getY()){
								this.setY(intersection.getY() + intersection.getHeight());
								player.setY(this.getY() + this.getHeight());
								return;
							} else {
								this.setY(intersection.getY() - this.getHeight());
								player.setY(this.getY() - player.getHeight());
								return;
							}
						}
					}
				}
			}
			
		} 
		this.setY(this.getY() - yVel1);
		player.setY(player.getY() - yVel2);
	}
}
