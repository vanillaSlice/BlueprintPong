package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.BlueprintPongGame;

/**
 * Created by mikelowe on 27/02/2017.
 */

public class MainMenuScreen extends ScreenAdapter {

    // Blueprint Pong
    // Play
    // Settings

    private final Assets assets;
    private final SpriteBatch spriteBatch;
    private final ScreenManager screenManager;

    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport viewport;

    private final Stage stage;
    private final Image background;
    private final Label titleLabel;
    private final Button playButton;
    private final Button settingsButton;

    public MainMenuScreen(final Assets assets, final SpriteBatch spriteBatch, final ScreenManager screenManager) {
        this.assets = assets;
        this.spriteBatch = spriteBatch;
        this.screenManager = screenManager;
        this.camera.setToOrtho(false);
        // don't need to use ppm as we aren't interacting with box2d here`
        this.viewport = new FitViewport(BlueprintPongGame.VIRTUAL_WIDTH, BlueprintPongGame.VIRTUAL_HEIGHT, this.camera);
        this.stage = new Stage(this.viewport, spriteBatch);
        this.background = new Image(assets.backgroundTexture);

        Label.LabelStyle lstyle = new Label.LabelStyle();
        lstyle.font = assets.generateFont((int) (viewport.getScreenWidth() / 6));
        lstyle.font.getData().setScale(viewport.getWorldWidth() / viewport.getScreenWidth(), viewport.getWorldHeight() / viewport.getScreenHeight());
        this.titleLabel = new Label(BlueprintPongGame.TITLE, lstyle);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = assets.generateFont(viewport.getScreenWidth() / 10);
        style.font.getData().setScale(viewport.getWorldWidth() / viewport.getScreenWidth(), viewport.getWorldHeight() / viewport.getScreenHeight());
        style.fontColor = Color.WHITE;
        style.overFontColor = Color.BLACK;
        style.downFontColor = Color.BLACK;
        style.up = new TextureRegionDrawable(new TextureRegion(assets.buttonUpTexture));
        style.over = new TextureRegionDrawable(new TextureRegion(assets.buttonDownTexture));
        style.down = style.over;

        this.playButton = new TextButton("Play", style);
        playButton.pad(0, 10f, 0, 10f);
        this.settingsButton = new TextButton("Settings", style);
        settingsButton.pad(0, 10f, 0, 10f);

        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.row();
        table.add(titleLabel).expandX();
        table.row();
        table.add(playButton).expandX();
        table.row();
        table.add(settingsButton).expandX().padTop(10f);
        table.pack();

        this.stage.addActor(this.background);
        this.stage.addActor(table);

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        Gdx.input.setInputProcessor(this.stage);

        this.playButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                switchToDifficultyScreen();
            }
        });

        this.settingsButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Settings");
            }
        });
    }

    private void switchToDifficultyScreen() {
        screenManager.removeCurrentScreen();
        screenManager.setCurrentScreen(new DifficultyScreen(assets, spriteBatch, screenManager));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        background.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();

        Gdx.app.log("Disposed", this.getClass().getName());
    }
}
