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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.BlueprintPongGame;
import lowe.mike.blueprintpong.Scaling;

/**
 * Provides a base class for the {@link Screen}s in the game.
 *
 * @author Mike Lowe
 */
class BaseScreen extends ScreenAdapter {

    private static final float COMPONENT_SPACING = 25f;

    private static final Color PRIMARY_TEXT_BUTTON_FONT_COLOUR = Color.WHITE;
    private static final Color SECONDARY_TEXT_BUTTON_FONT_COLOUR = Color.BLACK;

    final Assets assets;
    final SpriteBatch spriteBatch;
    final ScreenManager screenManager;
    final OrthographicCamera camera = new OrthographicCamera();
    final Viewport viewport;
    final Stage stage;

    private final Image background;
    private boolean isPaused;

    /*
     * Initialise these as and when required.
     */
    private Label.LabelStyle labelStyle;
    private TextButton.TextButtonStyle textButtonStyle;

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

    /**
     * Creates a {@link Label} with the given {@link BitmapFont} and text.
     *
     * @param font the {@link BitmapFont}
     * @param text text to initialise the {@link Label} with
     * @return the {@link Label}
     */
    final Label createLabel(BitmapFont font, String text) {
        if (labelStyle == null) {
            initialiseLabelStyle();
        }
        labelStyle.font = font;
        Label label = new Label(text, labelStyle);
        label.setAlignment(Align.center);
        return label;
    }

    private void initialiseLabelStyle() {
        labelStyle = new Label.LabelStyle();
    }

    /**
     * Creates a {@link TextButton} with the given text.
     *
     * @param text text to initialise the {@link TextButton} with
     * @return the {@link TextButton}
     */
    final TextButton createTextButton(String text) {
        if (textButtonStyle == null) {
            initialiseTextButtonStyle();
        }
        TextButton textButton = new TextButton(text, textButtonStyle);
        // size of font determines how much padding to apply
        float padding = (textButton.getHeight() - textButtonStyle.font.getCapHeight()) / 2;
        textButton.padLeft(padding).padRight(padding).align(Align.center);
        return textButton;
    }

    private void initialiseTextButtonStyle() {
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.fontColor = PRIMARY_TEXT_BUTTON_FONT_COLOUR;
        textButtonStyle.downFontColor = SECONDARY_TEXT_BUTTON_FONT_COLOUR;
        textButtonStyle.overFontColor = textButtonStyle.downFontColor;
        textButtonStyle.checkedFontColor = textButtonStyle.downFontColor;
        textButtonStyle.up = getTextureRegionDrawable(assets.getButtonUpTexture());
        textButtonStyle.down = getTextureRegionDrawable(assets.getButtonDownTexture());
        textButtonStyle.over = textButtonStyle.down;
        textButtonStyle.checked = textButtonStyle.down;
        textButtonStyle.font = assets.getMediumFont();
    }

    private static TextureRegionDrawable getTextureRegionDrawable(Texture texture) {
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    /**
     * @return component spacing value
     */
    final float getComponentSpacing() {
        return COMPONENT_SPACING * Scaling.getY();
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