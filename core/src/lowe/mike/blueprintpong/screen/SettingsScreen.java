package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lowe.mike.blueprintpong.Assets;

/**
 * Settings screen to show settings that the user can change.
 *
 * @author Mike Lowe
 */
final class SettingsScreen extends BaseScreen {

//    Settings Label
//    Difficulty
//    Sound
//    Back

    /**
     * Creates a new {@code SettingsScreen} given {@link Assets}, a {@link SpriteBatch}
     * and a {@link ScreenManager}.
     *
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     */
    SettingsScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
        super(assets, spriteBatch, screenManager);
    }

}