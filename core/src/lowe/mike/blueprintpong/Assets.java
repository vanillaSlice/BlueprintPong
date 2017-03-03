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

import java.util.HashMap;
import java.util.Map;

/**
 * {@code Assets} provides access to assets, such as {@link Texture}s,
 * used in the <i>Blueprint Pong</i> game.
 *
 * @author Mike Lowe
 */
public final class Assets implements Disposable {

    private static final AssetDescriptor<Texture> BACKGROUND_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("background.png", Texture.class);
    private static final AssetDescriptor<FreeTypeFontGenerator> FONT_GENERATOR_ASSET_DESCRIPTOR
            = new AssetDescriptor<FreeTypeFontGenerator>("BluprintDEMO.otf", FreeTypeFontGenerator.class);
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

    public final Texture backgroundTexture;
    public final Texture buttonUpTexture;
    public final Texture buttonDownTexture;
    public final Texture lineTexture;
    public final Texture paddleTexture;
    public final Texture ballTexture;
    public final Sound paddleHitSound;
    public final Sound wallHitSound;
    public final Sound pointScoredSound;

    private final AssetManager assetManager = new AssetManager();
    private final FreeTypeFontGeneratorLoader loader
            = new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver());
    private final FreeTypeFontGenerator fontGenerator;
    private final Map<Integer, BitmapFont> fonts = new HashMap<Integer, BitmapFont>();

    /**
     * Creates a new {@code Assets} instance and loads the assets
     * used throughout the game.
     */
    public Assets() {
        // needed when using free type font generator
        this.assetManager.setLoader(FreeTypeFontGenerator.class, this.loader);

        loadAssets(
                BACKGROUND_TEXTURE_ASSET_DESCRIPTOR,
                FONT_GENERATOR_ASSET_DESCRIPTOR,
                BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR,
                BUTTON_DOWN_TEXTURE_ASSET_DESCRIPTOR,
                LINE_TEXTURE_ASSET_DESCRIPTOR,
                PADDLE_TEXTURE_ASSET_DESCRIPTOR,
                BALL_TEXTURE_ASSET_DESCRIPTOR,
                PADDLE_HIT_SOUND_ASSET_DESCRIPTOR,
                WALL_HIT_SOUND_ASSET_DESCRIPTOR,
                POINT_SCORED_SOUND_ASSET_DESCRIPTOR);

        // need to wait before continuing
        this.assetManager.finishLoading();

        // assign loaded assets to variables
        this.backgroundTexture = this.assetManager.get(BACKGROUND_TEXTURE_ASSET_DESCRIPTOR);
        this.fontGenerator = this.assetManager.get(FONT_GENERATOR_ASSET_DESCRIPTOR);
        this.buttonUpTexture = this.assetManager.get(BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR);
        this.buttonDownTexture = this.assetManager.get(BUTTON_DOWN_TEXTURE_ASSET_DESCRIPTOR);
        this.lineTexture = this.assetManager.get(LINE_TEXTURE_ASSET_DESCRIPTOR);
        this.paddleTexture = this.assetManager.get(PADDLE_TEXTURE_ASSET_DESCRIPTOR);
        this.ballTexture = this.assetManager.get(BALL_TEXTURE_ASSET_DESCRIPTOR);
        this.paddleHitSound = this.assetManager.get(PADDLE_HIT_SOUND_ASSET_DESCRIPTOR);
        this.wallHitSound = this.assetManager.get(WALL_HIT_SOUND_ASSET_DESCRIPTOR);
        this.pointScoredSound = this.assetManager.get(POINT_SCORED_SOUND_ASSET_DESCRIPTOR);

        // for smoothing textures
        addTextureFilter(
                this.backgroundTexture,
                this.lineTexture,
                this.paddleTexture,
                this.ballTexture);
    }

    private void loadAssets(AssetDescriptor... assetDescriptors) {
        for (AssetDescriptor assetDescriptor : assetDescriptors) {
            assetManager.load(assetDescriptor);
        }
    }

    private static void addTextureFilter(Texture... textures) {
        for (Texture texture : textures) {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
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

        // generate a new font and cache it
        FONT_PARAMETER.size = size;
        BitmapFont font = fontGenerator.generateFont(FONT_PARAMETER);
        fonts.put(size, font);
        return font;
    }

    @Override
    public void dispose() {
        assetManager.dispose();

        // need to do this as the asset manager doesn't handle font disposal
        for (BitmapFont font : fonts.values()) {
            font.dispose();
        }

        Gdx.app.log("Disposed", this.getClass().getName());
    }

}