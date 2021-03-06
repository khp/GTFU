package io.khp.github;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;



public class GameScreen implements Screen{
	
	Skin skin;
	Stage stage;

	MainGame g;

	private static final float GRAVITY = 450;
	private static final int BOARDX = 800;
	private static final int BOARDY = 480;
	private static final float TERMINALVEL = -300;
	private static final int NUMOFLEVELS = 7;
	
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	
	private Player1 player1;
	private Player2 player2;
	private Player[] players;
	
	private FileHandle testMap;
	private MapDrawer mapDrawer;
	private JumpRenew[] renewButtons;
	
	private Rectangle intersectionPlayers;
	private int levelCounter = 0;
	
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

		setUpLevel();
			
		intersectionPlayers = new Rectangle();
			
	}
	
	public void setUpLevel() {
		
		String level;
		if (levelCounter > NUMOFLEVELS) {
			 level = "level1.bmp";
			 levelCounter = 1;
		} else { 
			//level = "Chase1.bmp";
			// comment out below level, rename above to test level
			level = "level"+levelCounter+".bmp";
		}
		testMap = Gdx.files.internal(level);
		mapDrawer = new MapDrawer(testMap);
		map = new Map(mapDrawer.getTranslatedTileMap());
		
		// Set up the players on the map
		player1 = new Player1(map.findStartA());
		player2 = new Player2(map.findStartB());
		players = new Player[2];
		players[0] = player1;
		players[1] = player2;
		
		// set up the JumpRenews
		
		/*renewButtons = new JumpRenew[6];
		renewButtons[0] = new JumpRenew (100, 100);
		renewButtons[1] = new JumpRenew (100, 200);
		renewButtons[2] = new JumpRenew (100, 300);
		renewButtons[3] = new JumpRenew (100, 400);
		renewButtons[4] = new JumpRenew (100, 500);
		renewButtons[5] = new JumpRenew (100, 600);*/
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
				if (t.getType() == TileType.WALL) {
					shapeRenderer.setColor(0,0,1,1);
					shapeRenderer.rect(t.getRectangle().getX(), t.getRectangle().getY(), 
						20, 20);
				
				}
				if (t.getType() == TileType.END){
					shapeRenderer.setColor(0,1,1,1);
					shapeRenderer.rect(t.getRectangle().getX(), t.getRectangle().getY(), 
							20, 20);
				}
				if (t.getType() == TileType.JUMPR) {
					shapeRenderer.setColor(1, 1, 1, 1);
					shapeRenderer.rect(t.getRectangle().getX(), t.getRectangle().getY(),
							t.getRectangle().getWidth(), t.getRectangle().getHeight());
				}
			}
		}
		
		// render the players and jump renews
		shapeRenderer.setColor(0, 1, 0, 1);
		shapeRenderer.rect(player1.getX(), player1.getY(), player1.getWidth(),
				player1.getHeight());
		
		shapeRenderer.setColor(1, 1, 1, 1);
		;
		/*for (JumpRenew jumper: renewButtons) {
		shapeRenderer.circle(jumper.getX(), jumper.getY(), jumper.getRadius());
		}*/

		shapeRenderer.setColor(1, 0, 0, 0);
		shapeRenderer.rect(player2.getX(), player2.getY(), player2.getWidth(),
				player2.getHeight());
		
		shapeRenderer.end();
		
		g.batch.end();

	}
	
	private void handleInput() {
		if (Gdx.input.isKeyPressed(Keys.R)) {
			setUpLevel();
		}
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

		boolean first = true;
				
		for(Player player : players){
			// Update Players
			if (first){
				player1.checkCollisions(player2, map.getTileArray());
				player2.checkCollisions(player1, map.getTileArray());
				first = false;
			}
			for(Tile[] r : map.getTileArray())
				for(Tile t : r)
					if (t.getType() == TileType.WALL){
						t.checkCollisions(player);
					}
					else if (t.getType() == TileType.END){
						if(Intersector.overlaps(player.getRect(), t.getRectangle())){
							levelCounter++;
						setUpLevel();
						return;
						}
					}
					else if (t.getType() == TileType.JUMPR){
						if(Intersector.overlaps(player.getRect(), t.getRectangle())){
							player.setAirborne(false);
							//t.setType(TileType.EMPTY);
									
						}
					
					}
					
			


			player.setX(player.getX() + player.getXVelocity() * Gdx.graphics.getDeltaTime());
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
			} else if (player.getYVelocity() > TERMINALVEL) {
				player.setYVelocity(player.getYVelocity() - 10 * GRAVITY * Gdx.graphics.getDeltaTime());
			} 
			else if (player.getYVelocity() > -TERMINALVEL) {
				player.setYVelocity(-TERMINALVEL);
			}	
			else {
				player.setYVelocity(TERMINALVEL);
			}	
		
			// Renew the player's jump if they hit a JumpRenew
			/*
			 * for (JumpRenew jumper : renewButtons) {
				jumper.checkCollisions(player);
				*/
			}
		}
	
	
	public void createNextLevel(){
		
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
