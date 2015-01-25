package io.khp.github;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public abstract class Player implements Collidable {
	
	private Rectangle rect;
	private float yVelocity;
	private float xVelocity;
	public final int height = 24;
	public final int width = 24;
	private final int speed = 200;
	private boolean airborne;
	
	public void jump() {
		this.yVelocity = 700;
		this.airborne = true;
	}
	
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
	
	public void checkCollisionsP(Player player, Tile[][] tileArray) {
		checkXP(player, tileArray);
		checkYP(player, tileArray);	
	}
	
	public void checkCollisions(Player player) {
		checkX(player);
		checkY(player);	
	}
	
	private void checkX(Player player){
		float displacement = Gdx.graphics.getDeltaTime();
		Rectangle playerRect = player.getRect();
		Rectangle intersection = new Rectangle();
		float xVel1 = this.getXVelocity() * displacement;
		float xVel2 = player.getXVelocity() * displacement;
		float x1 = this.getX();
		float x2 = player.getX();
		this.setX(x1 + xVel1);
		player.setX(x2 + xVel2);
		
		if (Intersector.intersectRectangles(playerRect, this.rect, intersection)) {
			player.setXVelocity(player.getXVelocity() + this.getXVelocity());
			this.setXVelocity(player.getXVelocity());
		}
		this.setX(this.getX() - xVel1);
		player.setX(player.getX() - xVel2);
	}
	
	
	private void checkXP(Player player, Tile[][] tileArray){
		float displacement = Gdx.graphics.getDeltaTime();
		Rectangle playerRect = player.getRect();
		Rectangle intersection = new Rectangle();
		float xVel1 = this.getXVelocity() * displacement;
		float xVel2 = player.getXVelocity() * displacement;
		float x1 = this.getX();
		float x2 = player.getX();
		this.setX(x1 + xVel1);
		player.setX(x2 + xVel2);
		
		if (Intersector.intersectRectangles(playerRect, this.rect, intersection)) {
			player.setXVelocity(player.getXVelocity() + this.getXVelocity());
			this.setXVelocity(player.getXVelocity());
		
			boolean wallCheck = false;
				for (Tile [] r : tileArray) {
					for (Tile t : r) {
						if (t.getType() == TileType.WALL){
							if ((Intersector.intersectRectangles(playerRect, 
									t.getRectangle(), intersection) ||
									Intersector.intersectRectangles(this.rect, 
									t.getRectangle(), intersection))
									&& intersection.getHeight() > 0) {
								player.setXVelocity(0);
								this.setXVelocity(0);
							}
						}
					}
				}
		
			
		}
		this.setX(this.getX() - xVel1);
		player.setX(player.getX() - xVel2);
	}
	
	private void checkY(Player player){
		float displacement = Gdx.graphics.getDeltaTime();
		Rectangle playerRect = player.getRect();
		Rectangle intersection = new Rectangle();
		float yVel1 = this.getYVelocity() * displacement;
		float yVel2 = player.getYVelocity() * displacement;
		float y1 = this.getY();
		float y2 = player.getY();
		this.setY(y1 + yVel1);
		player.setY(y2 + yVel2);
		
		if (Intersector.intersectRectangles(playerRect, this.rect, intersection)) {
			player.setYVelocity(player.getYVelocity() + this.getYVelocity());
			this.setYVelocity(player.getYVelocity());
			this.setAirborne(false);
			player.setAirborne(false);
		}
		this.setY(this.getY() - yVel1);
		player.setY(player.getY() - yVel2);
	}
	private void checkYP(Player player, Tile[][] tileArray){
		float displacement = Gdx.graphics.getDeltaTime();
		Rectangle playerRect = player.getRect();
		Rectangle intersection = new Rectangle();
		float yVel1 = this.getYVelocity() * displacement;
		float yVel2 = player.getYVelocity() * displacement;
		float y1 = this.getY();
		float y2 = player.getY();
		this.setY(y1 + yVel1);
		player.setY(y2 + yVel2);
		
		if (Intersector.intersectRectangles(playerRect, this.rect, intersection)) {
			player.setYVelocity(player.getYVelocity() + this.getYVelocity());
			this.setYVelocity(player.getYVelocity());
			this.setAirborne(false);
			player.setAirborne(false);
			for (Tile [] r : tileArray) {
				for (Tile t : r) {
					if (t.getType() == TileType.WALL){
						if ((Intersector.intersectRectangles(playerRect, 
								t.getRectangle(), intersection) ||
								Intersector.intersectRectangles(this.rect, 
								t.getRectangle(), intersection))
								&& intersection.getWidth() > 0) {
							player.setYVelocity(0);
							this.setYVelocity(0);
						}
					}
				}
			}
			
		} 
		this.setY(this.getY() - yVel1);
		player.setY(player.getY() - yVel2);
	}

}
