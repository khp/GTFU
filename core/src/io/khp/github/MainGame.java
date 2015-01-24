package io.khp.github;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.files.FileHandle;

public class MainGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Texture img;
	private FileHandle testMap;
	private Player1 player1;
	private MapDrawer mapDrawer;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		img = new Texture("badlogic.jpg");
		player1 = new Player1();
		testMap = Gdx.files.internal("testmap.png.jpg");
		mapDrawer = new MapDrawer(testMap);
		System.out.println();
	}
	

	@Override
	public void render () {
		draw();
		handleInput();
	}
	
	private void draw() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		batch.begin();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0, 1, 0, 1);
		shapeRenderer.rect(player1.getX(), player1.getY(), player1.getWidth(),
						   player1.getHeight());
		shapeRenderer.end();
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
