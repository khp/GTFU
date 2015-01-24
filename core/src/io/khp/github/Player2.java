package io.khp.github;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Player2 {
	private Rectangle rect;
	private float yVelocity;
	private float xVelocity;
	private final int height = 24;
	private final int width = 24;
	private final int speed = 200;
	private boolean airborne;
	
	public Player2() {
		rect = new Rectangle();
		rect.width = height;
		rect.height = width;
		rect.x = GameScreen.getBoardHeight() / 2 - rect.width / 2;
		rect.y = 200;
		yVelocity = 0;
		xVelocity = 0;
	}
	
	public void jump() {
		this.yVelocity = 500;
		this.airborne = true;
	}
	
	public Rectangle getRect() {
		return this.rect;
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
	
	public float getYVelocity() {
		return this.yVelocity;
	}

	public void setYVelocity(float yvel) {
		this.yVelocity = yvel;
	}
	
	public int getSpeed() {
		return this.speed;
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
}
