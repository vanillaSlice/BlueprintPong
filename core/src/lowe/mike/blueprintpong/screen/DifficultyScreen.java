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
 * Created by mikelowe on 02/03/2017.
 */

public class DifficultyScreen extends ScreenAdapter {

    //Difficulty buttons
    //Back
    // Play

    private final Assets assets;
    private final SpriteBatch spriteBatch;
    private final ScreenManager screenManager;

    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport viewport;

    private final Stage stage;
    private final Image background;
    private final Label difficultyLabel;
    private final Button easyButton;
    private final Button mediumButton;
    private final Button hardButton;
    private final Button backButton;
    private final Button playButton;

    public DifficultyScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
        this.assets = assets;
        this.spriteBatch = spriteBatch;
        this.screenManager = screenManager;

        // don't need to use ppm as we aren't interacting with box2d here`
        this.viewport = new FitViewport(BlueprintPongGame.VIRTUAL_WIDTH, BlueprintPongGame.VIRTUAL_HEIGHT, this.camera);
        this.stage = new Stage(this.viewport, spriteBatch);
        this.background = new Image(assets.getBackgroundTexture());

        Label.LabelStyle lstyle = new Label.LabelStyle();
        lstyle.font = assets.generateFont((int) (viewport.getScreenWidth() / 6));
        lstyle.font.getData().setScale(viewport.getWorldWidth() / viewport.getScreenWidth(), viewport.getWorldHeight() / viewport.getScreenHeight());
        this.difficultyLabel = new Label("Difficulty", lstyle);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = assets.generateFont(viewport.getScreenWidth() / 10);
        style.font.getData().setScale(viewport.getWorldWidth() / viewport.getScreenWidth(), viewport.getWorldHeight() / viewport.getScreenHeight());
        style.fontColor = Color.WHITE;
        style.overFontColor = Color.BLACK;
        style.downFontColor = Color.BLACK;
        style.up = new TextureRegionDrawable(new TextureRegion(assets.getButtonUpTexture()));
        style.over = new TextureRegionDrawable(new TextureRegion(assets.getButtonDownTexture()));
        style.down = style.over;

        this.easyButton = new TextButton("Easy", style);
        easyButton.pad(0, 10f, 0, 10f);
        this.mediumButton = new TextButton("Medium", style);
        mediumButton.pad(0, 10f, 0, 10f);
        this.hardButton = new TextButton("Hard", style);
        hardButton.pad(0, 10f, 0, 10f);
        this.backButton = new TextButton("Back", style);
        backButton.pad(0, 10f, 0, 10f);
        this.playButton = new TextButton("Play", style);
        playButton.pad(0, 10f, 0, 10f);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.row();
        table.add(difficultyLabel).expandX().colspan(3);
        table.row();
        table.add(easyButton).expandX();
        table.add(mediumButton).expandX();
        table.add(hardButton).expandX();
        table.row().padTop(10f);
        table.add(backButton).expandX();
        table.add();
        table.add(playButton).expandX();

        this.stage.addActor(this.background);
        this.stage.addActor(table);

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        Gdx.input.setInputProcessor(this.stage);

        this.backButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                switchToMainMenuScreen();
            }
        });
    }

    private void switchToMainMenuScreen() {
        screenManager.removeCurrentScreen();
        screenManager.setCurrentScreen(new MainMenuScreen(assets, spriteBatch, screenManager));
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