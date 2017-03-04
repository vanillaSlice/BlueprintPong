package lowe.mike.blueprintpong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lowe.mike.blueprintpong.screen.ScreenManager;
import lowe.mike.blueprintpong.screen.SplashScreen;

/**
 * Main class for <i>Blueprint Pong</i> game.
 *
 * @author Mike Lowe
 */
public final class BlueprintPongGame extends Game {

    public static final String TITLE = "Blueprint Pong";
    public static final float VIRTUAL_WIDTH = 320f;
    public static final float VIRTUAL_HEIGHT = 180f;
    public static final float PPM = 100f;

    private Assets assets;
    private SpriteBatch spriteBatch;
    private ScreenManager screenManager;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        initialiseAssets();
        initialiseScreenManager();
    }

    private void initialiseAssets() {
        assets = new Assets();
        assets.load();
    }

    private void initialiseScreenManager() {
        screenManager = new ScreenManager(this);
        SplashScreen splashScreen = new SplashScreen(assets, spriteBatch, screenManager);
        screenManager.setCurrentScreen(splashScreen);
    }

    @Override
    public void dispose() {
        assets.dispose();
        spriteBatch.dispose();
        screenManager.dispose();
    }

}