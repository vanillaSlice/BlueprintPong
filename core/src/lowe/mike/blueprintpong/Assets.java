package lowe.mike.blueprintpong;

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
import com.badlogic.gdx.utils.ObjectMap;

/**
 * {@code Assets} provides access to assets, such as {@link Texture}s,
 * used in the <i>Blueprint Pong</i> game.
 * <p>
 * {@code Assets} is a singleton.
 *
 * @author Mike Lowe
 */
public final class Assets implements Disposable {

    private static Assets instance;

    private static final AssetDescriptor<FreeTypeFontGenerator> FONT_GENERATOR_ASSET_DESCRIPTOR
            = new AssetDescriptor<FreeTypeFontGenerator>("BluprintDEMO.otf", FreeTypeFontGenerator.class);
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

    private static final FreeTypeFontGenerator.FreeTypeFontParameter FONT_PARAMETER
            = new FreeTypeFontGenerator.FreeTypeFontParameter();

    static {
        FONT_PARAMETER.color = Color.WHITE;
        // for smoothing fonts
        FONT_PARAMETER.minFilter = Texture.TextureFilter.Linear;
        FONT_PARAMETER.magFilter = Texture.TextureFilter.Linear;
    }

    private final AssetManager assetManager = new AssetManager();
    private final FreeTypeFontGeneratorLoader fontGeneratorLoader
            = new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver());
    private final ObjectMap<Integer, BitmapFont> fonts = new ObjectMap<Integer, BitmapFont>();
    private FreeTypeFontGenerator fontGenerator;
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
     * @return an instance of {@code Assets} (note that only one instance will be created)
     */
    public static Assets getInstance() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    private Assets() {
        this.assetManager.setLoader(FreeTypeFontGenerator.class, this.fontGeneratorLoader);
    }

    /**
     * Start loading assets.
     */
    public void load() {
        loadAssets(
                FONT_GENERATOR_ASSET_DESCRIPTOR,
                BACKGROUND_TEXTURE_ASSET_DESCRIPTOR,
                BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR,
                BUTTON_DOWN_TEXTURE_ASSET_DESCRIPTOR,
                LINE_TEXTURE_ASSET_DESCRIPTOR,
                PADDLE_TEXTURE_ASSET_DESCRIPTOR,
                BALL_TEXTURE_ASSET_DESCRIPTOR,
                PADDLE_HIT_SOUND_ASSET_DESCRIPTOR,
                WALL_HIT_SOUND_ASSET_DESCRIPTOR,
                POINT_SCORED_SOUND_ASSET_DESCRIPTOR);
    }

    private void loadAssets(AssetDescriptor... assetDescriptors) {
        for (AssetDescriptor assetDescriptor : assetDescriptors) {
            assetManager.load(assetDescriptor);
        }
    }

    /**
     * Continue loading assets.
     *
     * @return {@code true} if all loading is finished
     */
    public boolean update() {
        if (assetManager.update()) {
            assignLoadedAssetsToFields();
            addSmoothTextureFilter(
                    backgroundTexture,
                    lineTexture,
                    paddleTexture,
                    ballTexture);
            return true;
        } else {
            return false;
        }
    }

    private void assignLoadedAssetsToFields() {
        fontGenerator = assetManager.get(FONT_GENERATOR_ASSET_DESCRIPTOR);
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

    private static void addSmoothTextureFilter(Texture... textures) {
        for (Texture texture : textures) {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    /**
     * Loads a {@link Texture} from a file.
     *
     * @param fileName the file name
     * @return the {@link Texture}
     */
    public Texture loadTexture(String fileName) {
        assetManager.load(fileName, Texture.class);
        assetManager.finishLoading();
        Texture texture = assetManager.get(fileName);
        addSmoothTextureFilter(texture);
        return texture;
    }

    /**
     * @param size size of the font to generate
     * @return a {@link BitmapFont}
     */
    public BitmapFont generateFont(int size) {
        // use cached version if it exists
        if (fonts.containsKey(size)) {
            return fonts.get(size);
        }
        // otherwise generate a new font and cache it
        else {
            FONT_PARAMETER.size = size;
            BitmapFont font = fontGenerator.generateFont(FONT_PARAMETER);
            fonts.put(size, font);
            return font;
        }
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

    @Override
    public void dispose() {
        assetManager.dispose();

        // need to do this as the asset manager doesn't handle font disposal
        for (BitmapFont font : fonts.values()) {
            font.dispose();
        }
    }

}