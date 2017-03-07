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
     * Creates a new {@code SettingsScreen} given a {@link SpriteBatch}
     * and a {@link ScreenManager}.
     *
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     */
    SettingsScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
        super(assets, spriteBatch, screenManager);
    }

}