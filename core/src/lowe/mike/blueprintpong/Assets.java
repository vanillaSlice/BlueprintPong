package lowe.mike.blueprintpong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.utils.Disposable;

/**
 * {@code Assets} provides access to assets, such as {@link Texture}s,
 * used in the <i>Blueprint Pong</i> game.
 *
 * @author Mike Lowe
 */
public final class Assets implements Disposable {

    /*
     * Describe the assets to load in.
     */
    private static final AssetDescriptor<FreeTypeFontGenerator> FONT_GENERATOR_ASSET_DESCRIPTOR
            = new AssetDescriptor<FreeTypeFontGenerator>("BluprintDEMO.otf", FreeTypeFontGenerator.class);
    private static final AssetDescriptor<Texture> SPLASH_BACKGROUND_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("splash-background.png", Texture.class);
    private static final AssetDescriptor<Texture> BACKGROUND_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("background.png", Texture.class);
    private static final AssetDescriptor<Texture> BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("button-up.png", Texture.class);
    private static final AssetDescriptor<Texture> BUTTON_DOWN_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("button-down.png", Texture.class);
    private static final AssetDescriptor<Texture> LINE_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("line.png", Texture.class);
    private static final AssetDescriptor<Texture> PADDLE_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("paddle.png", Texture.class);
    private static final AssetDescriptor<Texture> BALL_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("ball.png", Texture.class);
    private static final AssetDescriptor<Sound> PADDLE_HIT_SOUND_ASSET_DESCRIPTOR
            = new AssetDescriptor<Sound>("paddle-hit.ogg", Sound.class);
    private static final AssetDescriptor<Sound> WALL_HIT_SOUND_ASSET_DESCRIPTOR
            = new AssetDescriptor<Sound>("wall-hit.ogg", Sound.class);
    private static final AssetDescriptor<Sound> POINT_SCORED_SOUND_ASSET_DESCRIPTOR
            = new AssetDescriptor<Sound>("point-scored.ogg", Sound.class);

    private static final Color FONT_COLOUR = Color.WHITE;

    /*
     * These are used to determine the portion of the screen that a font
     * should take up.
     */
    private static final int EXTRA_LARGE_FONT_DIVISOR = 6;
    private static final int LARGE_FONT_DIVISOR = 8;
    private static final int MEDIUM_FONT_DIVISOR = 13;

    private final AssetManager assetManager = new AssetManager();

    /*
     * References to assets.
     */
    private BitmapFont extraLargeFont;
    private BitmapFont largeFont;
    private BitmapFont mediumFont;
    private Texture splashBackgroundTexture;
    private Texture backgroundTexture;
    private Texture buttonUpTexture;
    private Texture buttonDownTexture;
    private Texture lineTexture;
    private Texture paddleTexture;
    private Texture ballTexture;
    private Sound paddleHitSound;
    private Sound wallHitSound;
    private Sound pointScoredSound;

    /**
     * Creates a new {@code Assets} instance.
     */
    Assets() {
        loadSplashBackgroundTexture();
        loadMainAssets();
    }

    /*
     * Wait until splash background texture is loaded before continuing.
     * This is so we cn display the splash screen while the main assets
     * are still being loaded.
     */
    private void loadSplashBackgroundTexture() {
        loadAsset(SPLASH_BACKGROUND_TEXTURE_ASSET_DESCRIPTOR);
        assetManager.finishLoading();
        splashBackgroundTexture = assetManager.get(SPLASH_BACKGROUND_TEXTURE_ASSET_DESCRIPTOR);
        addSmoothingFilter(splashBackgroundTexture);
    }

    private void loadAsset(AssetDescriptor... assetDescriptors) {
        for (AssetDescriptor assetDescriptor : assetDescriptors) {
            assetManager.load(assetDescriptor);
        }
    }

    private static void addSmoothingFilter(Texture... textures) {
        for (Texture texture : textures) {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    private void loadMainAssets() {
        // need this so we can load in fonts
        assetManager.setLoader(
                FreeTypeFontGenerator.class,
                new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver())
        );
        loadAsset(
                FONT_GENERATOR_ASSET_DESCRIPTOR,
                BACKGROUND_TEXTURE_ASSET_DESCRIPTOR,
                BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR,
                BUTTON_DOWN_TEXTURE_ASSET_DESCRIPTOR,
                LINE_TEXTURE_ASSET_DESCRIPTOR,
                PADDLE_TEXTURE_ASSET_DESCRIPTOR,
                BALL_TEXTURE_ASSET_DESCRIPTOR,
                PADDLE_HIT_SOUND_ASSET_DESCRIPTOR,
                WALL_HIT_SOUND_ASSET_DESCRIPTOR,
                POINT_SCORED_SOUND_ASSET_DESCRIPTOR
        );
    }

    /**
     * @return {@code true} if all loading is finished
     */
    public boolean isFinishedLoading() {
        if (assetManager.update()) {
            loadFonts();
            assignLoadedAssetsToFields();
            addSmoothingFilterToAssets();
            return true;
        } else {
            return false;
        }
    }

    private void loadFonts() {
        FreeTypeFontGenerator fontGenerator = assetManager.get(FONT_GENERATOR_ASSET_DESCRIPTOR);

        FreeTypeFontGenerator.FreeTypeFontParameter parameter
                = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = FONT_COLOUR;
        // apply smoothing filters
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;

        extraLargeFont = loadFont(fontGenerator, parameter, EXTRA_LARGE_FONT_DIVISOR);
        largeFont = loadFont(fontGenerator, parameter, LARGE_FONT_DIVISOR);
        mediumFont = loadFont(fontGenerator, parameter, MEDIUM_FONT_DIVISOR);

        // finished with font generator so dispose it
        assetManager.unload(FONT_GENERATOR_ASSET_DESCRIPTOR.fileName);
    }

    private BitmapFont loadFont(FreeTypeFontGenerator fontGenerator,
                                FreeTypeFontGenerator.FreeTypeFontParameter parameter,
                                int divisor) {
        parameter.size = Gdx.graphics.getWidth() / divisor;
        BitmapFont font = fontGenerator.generateFont(parameter);
        font.getData().setScale(
                BlueprintPongGame.VIRTUAL_WIDTH / Gdx.graphics.getWidth(),
                BlueprintPongGame.VIRTUAL_HEIGHT / Gdx.graphics.getHeight()
        );
        return font;
    }

    private void assignLoadedAssetsToFields() {
        backgroundTexture = assetManager.get(BACKGROUND_TEXTURE_ASSET_DESCRIPTOR);
        buttonUpTexture = assetManager.get(BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR);
        buttonDownTexture = assetManager.get(BUTTON_DOWN_TEXTURE_ASSET_DESCRIPTOR);
        lineTexture = assetManager.get(LINE_TEXTURE_ASSET_DESCRIPTOR);
        paddleTexture = assetManager.get(PADDLE_TEXTURE_ASSET_DESCRIPTOR);
        ballTexture = assetManager.get(BALL_TEXTURE_ASSET_DESCRIPTOR);
        paddleHitSound = assetManager.get(PADDLE_HIT_SOUND_ASSET_DESCRIPTOR);
        wallHitSound = assetManager.get(WALL_HIT_SOUND_ASSET_DESCRIPTOR);
        pointScoredSound = assetManager.get(POINT_SCORED_SOUND_ASSET_DESCRIPTOR);
    }

    private void addSmoothingFilterToAssets() {
        addSmoothingFilter(
                backgroundTexture,
                lineTexture,
                paddleTexture,
                ballTexture
        );
    }

    /**
     * @return the extra large {@link BitmapFont}
     */
    public BitmapFont getExtraLargeFont() {
        return extraLargeFont;
    }

    /**
     * @return the large {@link BitmapFont}
     */
    public BitmapFont getLargeFont() {
        return largeFont;
    }

    /**
     * @return the medium {@link BitmapFont}
     */
    public BitmapFont getMediumFont() {
        return mediumFont;
    }

    /**
     * @return the splash background {@link Texture}
     */
    public Texture getSplashBackgroundTexture() {
        return splashBackgroundTexture;
    }

    /**
     * @return the background {@link Texture}
     */
    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    /**
     * @return the button up {@link Texture}
     */
    public Texture getButtonUpTexture() {
        return buttonUpTexture;
    }

    /**
     * @return the button down {@link Texture}
     */
    public Texture getButtonDownTexture() {
        return buttonDownTexture;
    }

    /**
     * @return the line {@link Texture}
     */
    public Texture getLineTexture() {
        return lineTexture;
    }

    /**
     * @return the paddle {@link Texture}
     */
    public Texture getPaddleTexture() {
        return paddleTexture;
    }

    /**
     * @return the ball {@link Texture}
     */
    public Texture getBallTexture() {
        return ballTexture;
    }

    /**
     * @return the paddle hit {@link Sound}
     */
    public Sound getPaddleHitSound() {
        return paddleHitSound;
    }

    /**
     * @return the wall hit {@link Sound}
     */
    public Sound getWallHitSound() {
        return wallHitSound;
    }

    /**
     * @return the point scored {@link Sound}
     */
    public Sound getPointScoredSound() {
        return pointScoredSound;
    }

    /**
     * Disposes the splash background {@link Texture}.
     */
    public void disposeSplashBackgroundTexture() {
        if (assetManager.isLoaded(SPLASH_BACKGROUND_TEXTURE_ASSET_DESCRIPTOR.fileName)) {
            assetManager.unload(SPLASH_BACKGROUND_TEXTURE_ASSET_DESCRIPTOR.fileName);
        }
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        extraLargeFont.dispose();
        largeFont.dispose();
        mediumFont.dispose();
    }

}