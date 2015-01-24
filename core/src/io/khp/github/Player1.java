package io.khp.github;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.glutils.*;

public class Player1 {
	private Rectangle rect;
	
	public Player1() {
		rect = new Rectangle();
		rect.x = 800 / 2 - 64 / 2;
		rect.y = 20;
		rect.width = 64;
		rect.height = 64;
	}
	
	public void moveLeft() {
		rect.x -= 200 * Gdx.graphics.getDeltaTime();
	}
	
	public void moveRight() {
		rect.x += 200 * Gdx.graphics.getDeltaTime();
	}
	
	public float getX() {
		return rect.x;
	}
	
	public float getY() {
		return rect.y;
	}
	
	public float getWidth() {
		return rect.width;
	}
	
	public float getHeight() {
		return rect.height;
	}
}
