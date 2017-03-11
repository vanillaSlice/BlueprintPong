package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
 * Provides a base class for the {@link Screen}s in the game.
 *
 * @author Mike Lowe
 */
class BaseScreen extends ScreenAdapter {

    static final float COMPONENT_SPACING = BlueprintPongGame.VIRTUAL_HEIGHT / 30;

    final Assets assets;
    final SpriteBatch spriteBatch;
    final ScreenManager screenManager;
    final OrthographicCamera camera = new OrthographicCamera();
    final Viewport viewport;
    final Stage stage;

    private final Image background;
    private boolean isPaused;

    /**
     * Creates a new {@code BaseScreen} given {@link Assets}, a {@link SpriteBatch}
     * and a {@link ScreenManager}. Note that the default background {@link Texture}
     * will be used.
     *
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     */
    BaseScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
        this(assets, spriteBatch, screenManager, assets.getBackgroundTexture());
    }

    /**
     * Creates a new {@code BaseScreen} given {@link Assets}, a {@link SpriteBatch}
     * , a {@link ScreenManager} and background {@link Texture}.
     *
     * @param assets            {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch       {@link SpriteBatch} to add sprites to
     * @param screenManager     the {@link ScreenManager} used to manage game {@link Screen}s
     * @param backgroundTexture the background {@link Texture}
     */
    BaseScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager, Texture backgroundTexture) {
        this.assets = assets;
        this.spriteBatch = spriteBatch;
        this.screenManager = screenManager;
        this.camera.setToOrtho(false);
        this.viewport = new FitViewport(
                BlueprintPongGame.VIRTUAL_WIDTH,
                BlueprintPongGame.VIRTUAL_HEIGHT,
                this.camera
        );
        this.stage = new Stage(this.viewport, this.spriteBatch);
        this.background = new Image(backgroundTexture);
        this.stage.addActor(this.background);
    }

    @Override
    public final void show() {
        Gdx.input.setInputProcessor(this.stage);
        onShow();
    }

    /**
     * Method that subclasses can override to determine what
     * happens when this becomes the current {@link Screen}.
     */
    void onShow() {
    }

    @Override
    public final void resize(int width, int height) {
        viewport.update(width, height);
        background.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
    }

    @Override
    public final void pause() {
        isPaused = true;
    }

    @Override
    public final void resume() {
        isPaused = false;
    }

    @Override
    public final void render(float delta) {
        if (isPaused) {
            return;
        }
        clearScreen();
        update(delta);
        spriteBatch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();
    }

    private static void clearScreen() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 0);
    }

    /**
     * Method that subclasses can override to determine how to
     * update the {@link Screen} in each frame.
     *
     * @param delta time in seconds since the last frame
     */
    void update(float delta) {
    }

    @Override
    public final void dispose() {
        stage.dispose();
        Gdx.app.log("Disposed", this.getClass().getName());
        onDispose();
    }

    /**
     * Method that subclasses can override to determine what to
     * dispose.
     */
    void onDispose() {
    }

}