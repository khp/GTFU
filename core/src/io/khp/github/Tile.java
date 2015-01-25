package io.khp.github;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Tile {

	private int x;
	private int y;
	private TileType type;
	private boolean collision;
	// The enum is a type. We are assigning "type" to the enum TileType
	private Rectangle rect;

	final static int WIDTH = 20;
	final static int HEIGHT = 20;

	public Tile(TileType type, int x, int y) {
		this.type = type;
		if (this.type == TileType.WALL) {
			this.rect = new Rectangle(x*20, (y-1)*20, 20, 20);
		}
		this.x = x*20;
		this.y = y*20;
	}

	public Rectangle getRectangle() {
		return this.rect;
	}

	public TileType getType() {
		return this.type;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean checkCollision() {
		return collision;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public void checkCollisions(Player player) {
		if (this.type != TileType.WALL)
			return;
		checkX(player);
		checkY(player);	
	}
	
	private void checkX(Player player){
		float dt = Gdx.graphics.getDeltaTime();
		Rectangle playerRect = player.getRect();
		Rectangle intersection = new Rectangle();
		float displacement = player.getXVelocity() * dt;
		float currentX = player.getX();
		player.setX(currentX + displacement);
		
		if (Intersector.intersectRectangles(playerRect, this.rect, intersection)
				&& intersection.getHeight() > 2) {
			player.setXVelocity(0);
			if (intersection.getX() == player.getX()){
				player.setX(intersection.getX() + intersection.getWidth());
			} else {
				player.setX(intersection.getX() - player.getWidth());
			}
		}
		else { 
			player.setX(player.getX() - displacement);
		}
	}
	
	private void checkY(Player player){
		float displacement = Gdx.graphics.getDeltaTime();
		Rectangle playerRect = player.getRect();
		Rectangle intersection = new Rectangle();
		float currentYVel = player.getYVelocity() * displacement;
		float currentY = player.getY();
		player.setY(currentY + currentYVel);
		
		if (Intersector.intersectRectangles(playerRect, this.rect, intersection)
				&& intersection.getWidth() > 2) {
			player.setYVelocity(0);
			if (intersection.getY() == player.getY()) {
				player.setY(intersection.getY() + intersection.getHeight());
				player.setAirborne(false);
			} else {
				player.setY(intersection.getY() - player.getHeight());
			}
		}
		else {
			player.setY(player.getY() - currentYVel);
		}
	}

}
