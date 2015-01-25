package io.khp.github;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.glutils.*;

public class Player1 extends Player {

	Rectangle rect;

	public Player1() {
		
		rect = new Rectangle();
		rect.width = height;
		rect.height = width;
		rect.x = GameScreen.getBoardHeight() / 2 - rect.width / 2;
		rect.y = 200;
		this.setRect(rect);
	}
	
	public Player1(Tile start) {
		
		rect = new Rectangle();
		rect.width = height;
		rect.height = width;
		rect.x = start.getX();
		rect.y = start.getY();
		this.setRect(rect);
	}

	
	
}
