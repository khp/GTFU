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
				&& intersection.getHeight() > 2) {
			player.setXVelocity((player.getXVelocity() + this.getXVelocity())/2);
			this.setXVelocity(player.getXVelocity());

			for (Tile [] r : tileArray) {
				for (Tile t : r) {
					if (t.getType() == TileType.WALL){
						if (Intersector.intersectRectangles(playerRect, 
								t.getRectangle(), intersection) 
								&& intersection.getHeight() > 2) {
							player.setXVelocity(0);
							this.setXVelocity(0);
							if (intersection.getX() == player.getX()){
								player.setX(intersection.getX() + intersection.getWidth());
								this.setX(player.getX() + player.getWidth());
								return;
							} else {
								player.setX(intersection.getX() - player.getWidth());
								this.setX(player.getX() - player.getWidth());
								return;
							}
						}
						if (Intersector.intersectRectangles(this.rect, 
								t.getRectangle(), intersection)
								&& intersection.getHeight() > 1) {
							player.setXVelocity(0);
							this.setXVelocity(0);
							if (intersection.getX() == this.getX()){
								this.setX(intersection.getX() + intersection.getWidth());
								player.setX(player.getX() + this.getWidth());
								return;
							} else {
								this.setX(intersection.getX() - this.getWidth());
								player.setX(player.getX() - this.getWidth());
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
		
		if (Intersector.intersectRectangles(playerRect, this.rect, intersection)
				&& intersection.getWidth() > 2) {
			player.setYVelocity((player.getYVelocity() + this.getYVelocity())/2);
			this.setYVelocity(player.getYVelocity());
			this.setAirborne(false);
			player.setAirborne(false);
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
								return;
							} else {
								player.setY(intersection.getY() - player.getHeight());
								this.setY(player.getY() - player.getHeight());
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
								player.setY(player.getY() + this.getHeight());
								return;
							} else {
								this.setY(intersection.getY() - this.getHeight());
								player.setY(player.getY() - this.getHeight());
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
