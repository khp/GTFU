package io.khp.github;

import com.badlogic.gdx.math.Rectangle;

public abstract class Player {
	
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

}
