package io.khp.github;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	public Player1 player1;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		player1 = new Player1();

	}

	@Override
	public void render () {
		draw();
		handleInput();
	}
	
	private void draw() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		
	}
	
	private void handleInput() {
			if(Gdx.input.isKeyPressed(Keys.LEFT)) {
				player1.moveLeft();
			}
			if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
				player1.moveRight();
		}
	}
}
