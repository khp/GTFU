package io.khp.github;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Player2 extends Player {
	Rectangle rect;
	
	public Player2() {
		
		rect = new Rectangle();
		rect.width = height;
		rect.height = width;
		rect.x = GameScreen.getBoardHeight() / 2 - rect.width / 2 + 100;
		rect.y = 200;
		this.setRect(rect);
	}
	
	public Player2(Tile start) {
		
		rect = new Rectangle();
		rect.width = height;
		rect.height = width;
		rect.x = start.getX();
		rect.y = start.getY();
		this.setRect(rect);
	}
}
