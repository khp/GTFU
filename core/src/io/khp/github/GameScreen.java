package io.khp.github;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;



public class GameScreen implements Screen{
	
	Skin skin;
	Stage stage;

	MainGame g;

	private static final float GRAVITY = 400;
	private static final int BOARDX = 800;
	private static final int BOARDY = 480;
	private static final float terminalVel = -300;
	
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	
	private Player1 player1;
	private Player2 player2;
	
	private FileHandle testMap;
	private MapDrawer mapDrawer;
	private JumpRenew jumprenew;
	private JumpRenew[] renewButtons;
	
	private Rectangle intersectionPlayer1;
	private Rectangle intersectionPlayer2;
	private Rectangle intersectionPlayers;
	
	private Map map;
	
	public GameScreen(MainGame g) {
		create();
		this.g = g;
	}

	public GameScreen() {
		create();
	}

	public void create() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		//setScreen(new MainMenu(this));
			camera = new OrthographicCamera(); // init camera
			camera.setToOrtho(false, BOARDX, BOARDY);
			shapeRenderer = new ShapeRenderer();
			
			// set up the players and their intersections
			player1 = new Player1();
			player2 = new Player2();
			intersectionPlayer1 = new Rectangle();
			intersectionPlayer2 = new Rectangle();
			intersectionPlayers = new Rectangle();
			
			// set up the jumprenews
			renewButtons = new JumpRenew[6];
			renewButtons[0] = new JumpRenew (100, 100);
			renewButtons[1] = new JumpRenew (100, 200);
			renewButtons[2] = new JumpRenew (100, 300);
			renewButtons[3] = new JumpRenew (100, 400);
			renewButtons[4] = new JumpRenew (100, 500);
			renewButtons[5] = new JumpRenew (100, 600);
			
			testMap = Gdx.files.internal("level0.bmp");
			mapDrawer = new MapDrawer(testMap);
			map = new Map(mapDrawer.getTranslatedTileMap());
			
			System.out.println();
		}

	public void render(float delta) {
		handleInput();
		updatePlayers();
		draw();
	}
	
	private void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		g.batch.begin();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		shapeRenderer.begin(ShapeType.Filled);

		//draw map from ArrayList of Rectangle
		for (Tile[] ta : map.getTileArray()) {
			for (Tile t : ta) {
				shapeRenderer.setColor(0,0,1,1);
				if (t.getType() == TileType.WALL) {
					shapeRenderer.rect(t.getRectangle().getX(), t.getRectangle().getY(), 
						20, 20);
				}
			}
		}
		
		// render the players and jumprenews
		shapeRenderer.setColor(0, 1, 0, 1);
		shapeRenderer.rect(player1.getX(), player1.getY(), player1.getWidth(),
				player1.getHeight());
		
		shapeRenderer.setColor(1, 1, 1, 1);
		;
		for (JumpRenew jumper: renewButtons) {
		shapeRenderer.circle(jumper.getX(), jumper.getY(), jumper.getRadius());
		}

		shapeRenderer.setColor(1, 0, 0, 0);
		shapeRenderer.rect(player2.getX(), player2.getY(), player2.getWidth(),
				player2.getHeight());
		
		shapeRenderer.end();
		
		g.batch.end();

	}
	
	private void handleInput() {
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			player1.setXVelocity(-player1.getSpeed());
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			player1.setXVelocity(player1.getSpeed());
		}
		else {
			player1.setXVelocity(0);
		}
		
		if (!player1.getAirborne()) {
			if (Gdx.input.isKeyJustPressed(Keys.UP)) {
				player1.jump();
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			player2.setXVelocity(-player2.getSpeed());
		}
		else if (Gdx.input.isKeyPressed(Keys.D)) {
			player2.setXVelocity(player2.getSpeed());
		}
		else {
			player2.setXVelocity(0);
		}
		if (!player2.getAirborne()) {
			if (Gdx.input.isKeyJustPressed(Keys.W)) {
				player2.jump();
			}
		}
	}

	private void updatePlayers() {
		
		// Update collisions
		updateCollsions();
		
		// Update Player 1
		// Get player 1's x position and velocity
		float currentXVel = player1.getXVelocity();
		float currentX = player1.getX();
		
		// Check for map collisions, then move player 1 horizontally
		Rectangle dummyR = player1.getRect();
		dummyR.setX(currentX + currentXVel * Gdx.graphics.getDeltaTime());
		player1.getRect().setX(map.moveX(dummyR));
		
		// Keep the player in the horizontal bounds of the map
		if (player1.getX() > BOARDX - player1.getWidth() / 2)
			player1.setX(BOARDX - player1.getHeight() / 2);
		else if (player1.getX() < 0)
			player1.setX(0);

		// Get player 1's y position and velocity
		float currentYVel = player1.getYVelocity();
		float currentY = player1.getY();
		
		// Check for map collisions, then move player 1 vertically
		dummyR = player1.getRect();
		dummyR.setY(currentY + currentYVel * Gdx.graphics.getDeltaTime());
		player1.getRect().setY(map.moveY(dummyR, player1));

		// Keep the player in the vertical bounds off the map
		// Set the player's Airborne flag to false if they hit the bottom of the screen
		// Cap the player's fall speed to terminal velocity if it exceeds it, otherwise accelerate by gravity
		if (player1.getY() > BOARDY - player1.getHeight() / 2) {
			player1.setYVelocity(0);
			player1.setY(BOARDY - player1.getHeight() / 2);
		} else if (player1.getY() < 0) {
			player1.setYVelocity(0);
			player1.setY(0);
			player1.setAirborne(false);
		} else if (player1.getYVelocity() > terminalVel) {
			player1.setYVelocity(currentYVel - 10 * GRAVITY
					* Gdx.graphics.getDeltaTime());
		} else {
			player1.setYVelocity(terminalVel);
		}
		
		// Update Player 2
		// Get player 2's current x position and velocity
		currentXVel = player2.getXVelocity();
		currentX = player2.getX();
		
		// Check for map collisions then move player 2 horizontally
		dummyR = player2.getRect();
		dummyR.setX(currentX + currentXVel * Gdx.graphics.getDeltaTime());
		player2.getRect().setX(map.moveX(dummyR));
		
		// Keep player 2 in the horizontal bounds off the screen
		if (player2.getX() > BOARDX - player2.getWidth() / 2)
			player2.setX(BOARDX - player2.getHeight() / 2);
		else if (player2.getX() < 0)
			player2.setX(0);

		// Get player 2's current y position and velocity
		currentYVel = player2.getYVelocity();
		currentY = player2.getY();
		
		// Check for map collisions then move player 2 vertically
		dummyR = player2.getRect();
		dummyR.setY(currentY + currentYVel * Gdx.graphics.getDeltaTime());
		player2.getRect().setY(map.moveY(dummyR, player2));

		// Keep player 2 in the horizontal bounds off the screen
		// Set player 2's airborne flag to false if they hit the bottom of the screen
		// Cap the player's fall speed to terminal velocity, otherwise accelerate due to gravity
		if (player2.getY() > BOARDY - player2.getHeight() / 2) {
			player2.setYVelocity(0);
			player2.setY(BOARDY - player2.getHeight() / 2);
		} else if (player2.getY() < 0) {
			player2.setYVelocity(0);
			player2.setY(0);
			player2.setAirborne(false);
		} else if (player2.getYVelocity() > terminalVel) {
			player2.setYVelocity(currentYVel - 10 * GRAVITY
					* Gdx.graphics.getDeltaTime());
		} else {
			player2.setYVelocity(terminalVel);
		}
		
		// Renew the player's jump if they hit a JumpRenew
		for (JumpRenew jumper : renewButtons) {
			jumper.checkCollisions(player1);
			jumper.checkCollisions(player2);
		}
	}

	// Collision method
	private void updateCollsions() {
		
		// check collisions between players
		if (Intersector.intersectRectangles(player1.getRect(), player2.getRect(), this.intersectionPlayers)) {
			
			// If player 1 is above player 2 when they collide:
			// Set player 1's y-velocity to player 2's
			if (player1.getY() > player2.getY() && player2.getYVelocity() != terminalVel) {
				player1.setYVelocity(player2.getYVelocity());
			}
			// Vice versa if player 2 is above player 1
			else if (player1.getY() <= player2.getY() && player2.getYVelocity() != terminalVel) {
				player2.setYVelocity(player1.getYVelocity());
			}
			
			// Get the positions of the 2 players, as well as the magnitude of their overlap
			float player1X = player1.getX();
			float player1Y = player1.getY();
			float player2X = player2.getX();
			float player2Y = player2.getY();
			float xDisplacement = intersectionPlayers.width / 2;
			float yDisplacement = intersectionPlayers.height / 2;
			
			// If player 1 is above player 2, displace player 1 up and refresh jump
			if (player1Y > player2Y) {
				player1.setY(player1Y + yDisplacement * 2);
				player1.setAirborne(false);
			}
			// If player 2 is above player 2, displace player 2 up and refresh jump
			else if (player1Y < player2Y) {
				player2.setY(player2Y + yDisplacement * 2);
				player2.setAirborne(false);
			}
			// If player 1 is on player 2's right, displace them both in the appropriate direction
			else if (player1X > player2X) {
				player1.setX(player1X + xDisplacement);
				player2.setX(player2X - xDisplacement);
			}
			else {
				player1.setX(player1X - xDisplacement);
				player2.setX(player2X + xDisplacement);
			}
		}
		
	}
	
	public static int getBoardHeight() {
		return BOARDY;
	}

	public static int getBoardWidth() {
		return BOARDX;
	}


	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
}
