package io.khp.github;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.GL20;

public class MainMenu implements Screen {
	Skin skin;
	Stage stage;
	Texture bg;
	SpriteBatch batch;

	private OrthographicCamera camera;
	
	MainGame g;

	public MainMenu(MainGame g) {
		create();
		this.g = g;
	}

	public MainMenu() {
		create();
	}

	public void create() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		camera = new OrthographicCamera(); // init camera
		camera.setToOrtho(false, 800, 480);
		skin = new Skin();
		
		batch = new SpriteBatch();
		bg = new Texture(Gdx.files.internal("mainmenu.png"));
		
		Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();

		skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		BitmapFont bfont = new BitmapFont();
		bfont.scale(1);
		skin.add("default", bfont);

		// Configure a TextButtonStyle and name it "default". Skin resources are
		// stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

		textButtonStyle.font = skin.getFont("default");

		skin.add("default", textButtonStyle);

		// Create a button with the "default" TextButtonStyle. A 3rd parameter
		// can be used to specify a name other than "default".
		final TextButton textButton = new TextButton("Play", textButtonStyle);
		textButton.setPosition(400 - (textButton.getWidth() / 2), 240 - (textButton.getWidth() / 2));
		stage.addActor(textButton);
		stage.addActor(textButton);
		stage.addActor(textButton);
		textButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
			//System.out.println("Clicked! Is checked: " + button.isChecked());
			textButton.setText("Starting new game");
			g.setScreen( new GameScreen(g));
			 
			}
			});
	}

	public void render(float delta) {
		camera.update();
		batch.begin();
		batch.draw(bg, 0, 0);
		batch.end();
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

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
