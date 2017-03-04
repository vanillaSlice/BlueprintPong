package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import lowe.mike.blueprintpong.Assets;

/**
 * Splash screen to show while assets are being loaded.
 *
 * @author Mike Lowe
 */
public final class SplashScreen extends BaseScreen {

    private final Image logo;

    /**
     * Creates a new {@code SplashScreen} given {@link Assets}, a {@link SpriteBatch}
     * and a {@link ScreenManager}.
     *
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     */
    public SplashScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
        super(assets, spriteBatch, screenManager);
        this.logo = new Image(assets.getSplashBackgroundTexture());
        this.stage.addActor(logo);
    }

    @Override
    public void resizeAssets() {
        logo.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
    }

    @Override
    void update(float delta) {
        if (assets.update()) {
            switchToMainMenuScreen();
        }
    }

    private void switchToMainMenuScreen() {
        screenManager.removeCurrentScreen();
        MainMenuScreen mainMenuScreen = new MainMenuScreen(assets, spriteBatch, screenManager);
        screenManager.setCurrentScreen(mainMenuScreen);
    }

    @Override
    public void disposeAssets() {
        assets.disposeSplashBackgroundTexture();
    }

}