package io.khp.github;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.*;

public class Player1 {
	private Rectangle rect;
	
	public void Player1() {
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
	
	public 
}
