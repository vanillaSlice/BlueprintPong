package lowe.mike.blueprintpong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lowe.mike.blueprintpong.screen.MainMenuScreen;
import lowe.mike.blueprintpong.screen.ScreenManager;

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
        assets = new Assets();
        initialiseScreenManager();
    }

    private void initialiseScreenManager() {
        screenManager = new ScreenManager(this);
        MainMenuScreen mainMenuScreen = new MainMenuScreen(assets, spriteBatch, screenManager);
        screenManager.setCurrentScreen(mainMenuScreen);
    }

    @Override
    public void dispose() {
        assets.dispose();
        spriteBatch.dispose();
        screenManager.dispose();

        Gdx.app.log("Disposed", this.getClass().getName());
    }

}