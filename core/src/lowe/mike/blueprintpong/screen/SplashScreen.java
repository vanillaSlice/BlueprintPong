package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    private final Assets assets;
    private final SpriteBatch spriteBatch;
    private final ScreenManager screenManager;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport viewport;
    private final Stage stage;
    private final Image background;

    /**
     * Creates a new {@code SplashScreen} given {@link Assets}, a {@link SpriteBatch}
     * and a {@link ScreenManager}.
     *
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     */
    public SplashScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
        this.assets = assets;
        this.spriteBatch = spriteBatch;
        this.screenManager = screenManager;
        this.camera.setToOrtho(false);
        this.viewport = new FitViewport(
                BlueprintPongGame.VIRTUAL_WIDTH,
                BlueprintPongGame.VIRTUAL_HEIGHT,
                this.camera);
        this.stage = new Stage(this.viewport, this.spriteBatch);
        this.background = new Image(this.assets.getSplashBackgroundTexture());
        this.stage.addActor(this.background);
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
        if (assets.isFinishedLoading()) {
            switchToMainMenuScreen();
        }
    }

    private void switchToMainMenuScreen() {
        screenManager.removeAndDisposeCurrentScreen();
        screenManager.setScreen(new MainMenuScreen(assets, spriteBatch, screenManager));
    }

    @Override
    public void dispose() {
        assets.disposeSplashBackgroundTexture();
        stage.dispose();
    }

}