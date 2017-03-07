package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.BlueprintPongGame;

/**
 * Main menu screen to show when the game is first opened.
 *
 * @author Mike Lowe
 */

final class MainMenuScreen extends BaseScreen {

    private static final int TITLE_LABEL_FONT_DIVISOR = 6;
    private static final int BUTTON_FONT_DIVISOR = 10;
    private static final String PLAY_BUTTON_TEXT = "Play";
    private static final String SETTINGS_BUTTON_TEXT = "Settings";
    private static final float SETTINGS_BUTTON_TOP_PADDING = 10f;

    private final Label titleLabel;
    private final Button playButton;
    private final Button settingsButton;

    /**
     * Creates a new {@code MainMenuScreen} given {@link Assets}, a {@link SpriteBatch}
     * and a {@link ScreenManager}.
     *
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     */
    MainMenuScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
        super(assets, spriteBatch, screenManager);
        this.titleLabel = createLabel(
                viewport.getScreenWidth() / TITLE_LABEL_FONT_DIVISOR,
                BlueprintPongGame.TITLE);
        this.playButton = initialisePlayButton();
        this.settingsButton = initialiseSettingsButton();
        this.stage.addActor(getMenuTable());
    }

    private TextButton initialisePlayButton() {
        TextButton button = createTextButton(
                viewport.getScreenWidth() / BUTTON_FONT_DIVISOR,
                PLAY_BUTTON_TEXT);
        addPlayButtonListener(button);
        return button;
    }

    private void addPlayButtonListener(TextButton button) {
        button.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                switchToDifficultyScreen();
            }

        });
    }

    private void switchToDifficultyScreen() {
        screenManager.removeAndDisposeCurrentScreen();
        screenManager.setScreen(new DifficultyScreen(assets, spriteBatch, screenManager));
    }

    private TextButton initialiseSettingsButton() {
        TextButton button = createTextButton(
                viewport.getScreenWidth() / BUTTON_FONT_DIVISOR,
                SETTINGS_BUTTON_TEXT);
        addSettingsButtonListener(button);
        return button;
    }

    private void addSettingsButtonListener(TextButton button) {
        button.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                switchToSettingsScreen();
            }

        });
    }

    private void switchToSettingsScreen() {
        screenManager.setScreen(new SettingsScreen(assets, spriteBatch, screenManager));
    }

    private Table getMenuTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.row();
        table.add(titleLabel).expandX();
        table.row();
        table.add(playButton).expandX();
        table.row();
        table.add(settingsButton).expandX().padTop(SETTINGS_BUTTON_TOP_PADDING);
        return table;
    }

}