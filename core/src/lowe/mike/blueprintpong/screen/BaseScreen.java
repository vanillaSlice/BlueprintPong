package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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

    private static final float TEXT_BUTTON_PADDING = 10f;
    private static final TextButton.TextButtonStyle TEXT_BUTTON_STYLE
            = new TextButton.TextButtonStyle();
    private static boolean TEXT_BUTTON_STYLE_INITIALISED;

    static {
        TEXT_BUTTON_STYLE.fontColor = Color.WHITE;
        TEXT_BUTTON_STYLE.downFontColor = Color.BLACK;
        TEXT_BUTTON_STYLE.overFontColor = TEXT_BUTTON_STYLE.downFontColor;
        TEXT_BUTTON_STYLE.checkedFontColor = TEXT_BUTTON_STYLE.downFontColor;
    }

    final SpriteBatch spriteBatch;
    final OrthographicCamera camera = new OrthographicCamera();
    final Viewport viewport;
    final Stage stage;

    private boolean isPaused;

    /**
     * Creates a new {@code BaseScreen} given a {@link SpriteBatch}
     * and a {@link ScreenManager}.
     *
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     */
    BaseScreen(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        this.camera.setToOrtho(false);
        this.viewport = new FitViewport(
                BlueprintPongGame.VIRTUAL_WIDTH,
                BlueprintPongGame.VIRTUAL_HEIGHT,
                this.camera);
        this.stage = new Stage(this.viewport, this.spriteBatch);
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    /**
     * Creates a {@link Label} with the given font size and text.
     *
     * @param fontSize size of {@link Label} font
     * @param text     text to initialise the {@link Label} with
     * @return the {@link Label}
     */
    final Label createLabel(int fontSize, String text) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = generateFont(fontSize);
        Label label = new Label(text, style);
        label.setAlignment(Align.center);
        return label;
    }

    private BitmapFont generateFont(int fontSize) {
        BitmapFont font = Assets.getInstance().generateFont(fontSize);
        font.getData().setScale(getXScale(), getYScale());
        return font;
    }

    /**
     * @return x value to scale assets by
     */
    final float getXScale() {
        return viewport.getWorldWidth() / viewport.getScreenWidth();
    }

    /**
     * @return y value to scale assets by
     */
    final float getYScale() {
        return viewport.getWorldHeight() / viewport.getScreenHeight();
    }

    /**
     * Creates a {@link TextButton} with the given font size and text.
     *
     * @param fontSize size of {@link TextButton} font
     * @param text     text to initialise the {@link TextButton} with
     * @return the {@link TextButton}
     */
    final TextButton createTextButton(int fontSize, String text) {
        TEXT_BUTTON_STYLE.font = generateFont(fontSize);
        checkTextButtonStyleInitialised();
        TextButton textButton = new TextButton(text, TEXT_BUTTON_STYLE);
        textButton.padLeft(TEXT_BUTTON_PADDING);
        textButton.padRight(TEXT_BUTTON_PADDING);
        return textButton;
    }

    private void checkTextButtonStyleInitialised() {
        if (TEXT_BUTTON_STYLE_INITIALISED) {
            return;
        }
        TEXT_BUTTON_STYLE.up = getTextureRegionDrawable(Assets.getInstance().getButtonUpTexture());
        TEXT_BUTTON_STYLE.down = getTextureRegionDrawable(Assets.getInstance().getButtonDownTexture());
        TEXT_BUTTON_STYLE.over = TEXT_BUTTON_STYLE.down;
        TEXT_BUTTON_STYLE.checked = TEXT_BUTTON_STYLE.down;
        TEXT_BUTTON_STYLE_INITIALISED = true;
    }

    private TextureRegionDrawable getTextureRegionDrawable(Texture texture) {
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    @Override
    public final void resize(int width, int height) {
        viewport.update(width, height);
        resizeAssets();
    }

    /**
     * Method that subclasses can override to do any {@link Screen}
     * specific resizing.
     */
    void resizeAssets() {
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

    private void clearScreen() {
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
        disposeAssets();
        Gdx.app.log("Disposed", this.getClass().getName());
    }

    /**
     * Method that subclasses can override to do any {@link Screen}
     * specific disposal.
     */
    void disposeAssets() {
    }

}