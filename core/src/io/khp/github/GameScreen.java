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

	public static final float GRAVITY = 200;
	private static final int BOARDX = 800;
	private static final int BOARDY = 480;
	
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	
	private Player1 player1;
	private Player2 player2;
	
	private Player[] players;
	
	private FileHandle testMap;
	private MapDrawer mapDrawer;
	private JumpRenew jumprenew;
	
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
			
			
			player1 = new Player1();
			player2 = new Player2();
			players = new Player[2];
			players[0] = player1;
			players[1] = player2;
			
			jumprenew = new JumpRenew();
			
			intersectionPlayer1 = new Rectangle();
			intersectionPlayer2 = new Rectangle();
			intersectionPlayers = new Rectangle();
			
			map = new Map();
			
			testMap = Gdx.files.internal("testmap.png.jpg");
			mapDrawer = new MapDrawer(testMap);
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
		for (Rectangle r : map.getRectList()) {
			shapeRenderer.setColor(0,0,1,1);
			shapeRenderer.rect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
		}
		
		shapeRenderer.setColor(0, 1, 0, 1);
		shapeRenderer.rect(player1.getX(), player1.getY(), player1.getWidth(),
				player1.getHeight());
		
		shapeRenderer.circle(jumprenew.getX(), jumprenew.getY(), jumprenew.getRadius());

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
			if (Gdx.input.isKeyPressed(Keys.UP)) {
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
			if (Gdx.input.isKeyPressed(Keys.W)) {
				player2.jump();
			}
		}
	}

	private void updatePlayers() {
		
		// Update collisions
		updateCollisions();
		
		for(Player player : players){
			// Update Players
			for(Tile[] r : map.getTileList())
				for(Tile t : r)
					t.checkCollisions(player);

			player.setX(player.getX() + player.getXVelocity() * Gdx.graphics.getDeltaTime() );
			player.setY(player.getY() + player.getYVelocity() * Gdx.graphics.getDeltaTime());

			if (player.getX() > BOARDX - player.getWidth() / 2)
				player.setX(BOARDX - player.getHeight() / 2);
			else if (player.getX() < 0)
				player.setX(0);

			if (player.getY() > BOARDY - player.getHeight() / 2) {
				player.setYVelocity(0);
				player.setY(BOARDY - player.getHeight() / 2);
			} else if (player.getY() < 0) {
				player.setYVelocity(0);
				player.setY(0);
				player.setAirborne(false);
			} else if (player.getYVelocity() > -100) {
				player.setYVelocity(player.getYVelocity() - 10 * GRAVITY * Gdx.graphics.getDeltaTime());
			} else {
				player.setYVelocity(-100);

			}

			if (Intersector.overlaps(jumprenew.getCircle(), player.getRect())){
				player.setAirborne(false);
			}
		}
	}

	// Collision method
	private void updateCollisions() {
		
		if (Intersector.intersectRectangles(player1.getRect(), player2.getRect(), this.intersectionPlayers)) {

			if (player1.getY() > player2.getY() && player2.getYVelocity() != 0) {
				player1.setYVelocity(player2.getYVelocity());
			}
			else if (player1.getY() <= player2.getY() && player1.getYVelocity() != 0) {
				player2.setYVelocity(player1.getYVelocity());
			}
			
			float player1X = player1.getX();
			float player1Y = player1.getY();
			float player2X = player2.getX();
			float player2Y = player2.getY();
			float xDisplacement = intersectionPlayers.width / 2;
			float yDisplacement = intersectionPlayers.height / 2;
			
			if (player1Y > player2Y) {
				player1.setY(player1Y + yDisplacement);
				// player2.setY(player2Y - yDisplacement);
				
				player1.setAirborne(false);
			}
			else if (player1Y <= player2Y) {
				// player1.setY(player1Y - yDisplacement);
				player2.setY(player2Y + yDisplacement);
				
				player2.setAirborne(false);
			}
			else if (player1X >= player2X) {
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
