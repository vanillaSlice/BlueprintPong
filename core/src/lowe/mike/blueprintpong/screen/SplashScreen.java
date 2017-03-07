package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.BlueprintPongGame;

/**
 * Splash screen to show while assets are being loaded.
 *
 * @author Mike Lowe
 */
public final class SplashScreen extends ScreenAdapter {

    private static final String BACKGROUND_TEXTURE_PATH = "splash-background.png";

    private final Assets assets;
    private final SpriteBatch spriteBatch;
    private final ScreenManager screenManager;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport viewport;
    private final Stage stage;
    private final Texture backgroundTexture;
    private final Image background;

    /**
     * Creates a new {@code SplashScreen} given a {@link SpriteBatch}.
     *
     * @param spriteBatch {@link SpriteBatch} to add sprites to
     */
    public SplashScreen(SpriteBatch spriteBatch) {
        // set up the screen
        this.assets = Assets.getInstance();
        this.spriteBatch = spriteBatch;
        this.screenManager = ScreenManager.getInstance();
        this.camera.setToOrtho(false);
        this.viewport = new FitViewport(
                BlueprintPongGame.VIRTUAL_WIDTH,
                BlueprintPongGame.VIRTUAL_HEIGHT,
                this.camera);
        this.stage = new Stage(this.viewport, this.spriteBatch);
        this.backgroundTexture = this.assets.loadTexture(BACKGROUND_TEXTURE_PATH);
        this.background = new Image(this.backgroundTexture);
        this.stage.addActor(this.background);

        // start loading the assets for the game
        this.assets.load();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        background.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        spriteBatch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();
        if (assets.update()) {
            switchToMainMenuScreen();
        }
    }

    private void switchToMainMenuScreen() {
        screenManager.removeAndDisposeCurrentScreen();
        screenManager.setScreen(new MainMenuScreen(spriteBatch));
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        stage.dispose();
    }

}