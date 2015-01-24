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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.files.FileHandle;

public class MainGame extends ApplicationAdapter {

	public static final float GRAVITY = 200;
	private static final int BOARDX = 800;
	private static final int BOARDY = 480;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	private Player1 player1;
	private Player2 player2;
	
	private FileHandle testMap;
	private MapDrawer mapDrawer;

	// sets up the game
	@Override
	public void create() {
		camera = new OrthographicCamera(); // init camera
		camera.setToOrtho(false, BOARDX, BOARDY);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		player1 = new Player1();
		player2 = new Player2();

		testMap = Gdx.files.internal("testmap.png.jpg");
		mapDrawer = new MapDrawer(testMap);
		System.out.println();
	}

	// Analogous to main - Handles inputs, updates player coordinates / physics
	// and
	// Draw to board.
	@Override
	public void render() {
		handleInput();
		updatePlayers();
		draw();
	}

	//
	private void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		batch.begin();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0, 1, 0, 1);
		shapeRenderer.rect(player1.getX(), player1.getY(), player1.getWidth(),
				player1.getHeight());

		shapeRenderer.setColor(1, 0, 0, 0);
		shapeRenderer.rect(player2.getX(), player2.getY(), player2.getWidth(),
				player2.getHeight());
		shapeRenderer.end();
		
		batch.end();

	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			player1.moveLeft();
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			player1.moveRight();
		}
		if (!player1.getAirborne()) {
			if (Gdx.input.isKeyPressed(Keys.UP)) {
				player1.jump();
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			player2.moveLeft();
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			player2.moveRight();
		}
		if (!player2.getAirborne()) {
			if (Gdx.input.isKeyPressed(Keys.W)) {
				player2.jump();
			}
		}
	}

	private void updatePlayers() {
		
		// Update Player 1
		if (player1.getX() > BOARDX - player1.getWidth() / 2)
			player1.setX(BOARDX - player1.getHeight() / 2);
		else if (player1.getX() < 0)
			player1.setX(0);

		float currentYVel = player1.getYVelocity();
		float currentY = player1.getY();

		player1.setY(currentY + currentYVel * Gdx.graphics.getDeltaTime());

		if (player1.getY() > BOARDY - player1.getHeight() / 2) {
			player1.setYVelocity(0);
			player1.setY(BOARDY - player1.getHeight() / 2);
		} else if (player1.getY() < 0) {
			player1.setYVelocity(0);
			player1.setY(0);
			player1.setAirborne(false);
		} else {
			player1.setYVelocity(currentYVel - 10 * GRAVITY
					* Gdx.graphics.getDeltaTime());
		}
		
		// Update Player 2
		if (player2.getX() > BOARDX - player2.getWidth() / 2)
			player2.setX(BOARDX - player2.getHeight() / 2);
		else if (player2.getX() < 0)
			player2.setX(0);

		currentYVel = player2.getYVelocity();
		currentY = player2.getY();

		player2.setY(currentY + currentYVel * Gdx.graphics.getDeltaTime());

		if (player2.getY() > BOARDY - player2.getHeight() / 2) {
			player2.setYVelocity(0);
			player2.setY(BOARDY - player2.getHeight() / 2);
		} else if (player1.getY() <= 0) {
			player2.setYVelocity(0);
			player2.setY(0);
			player2.setAirborne(false);
		} else {
			player2.setYVelocity(currentYVel - GRAVITY
					* Gdx.graphics.getDeltaTime());
		}
	}

	public static int getBoardHeight() {
		return BOARDY;
	}

	public static int getBoardWidth() {
		return BOARDX;
	}
}
