package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.BlueprintPongGame;

/**
 * Main menu screen to show when the game is first opened.
 *
 * @author Mike Lowe
 */
final class MainMenuScreen extends BaseScreen {

    private static final String PLAY_BUTTON_TEXT = "Play";
    private static final String SETTINGS_BUTTON_TEXT = "Settings";

    private final Label titleLabel;
    private final TextButton playButton;
    private final TextButton settingsButton;

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
        this.titleLabel = createLabel(assets.getExtraLargeFont(), BlueprintPongGame.TITLE);
        this.playButton = initialisePlayButton();
        this.settingsButton = initialiseSettingsButton();
        this.stage.addActor(getMenuTable());
    }

    private TextButton initialisePlayButton() {
        TextButton button = createTextButton(PLAY_BUTTON_TEXT);
        addPlayButtonListener(button);
        return button;
    }

    private void addPlayButtonListener(final TextButton button) {
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    switchToDifficultyScreen();
                }
            }

        });
    }

    private void switchToDifficultyScreen() {
        // dispose this screen because it is unlikely we will be returning to it
        // after pressing the play button
        screenManager.removeAndDisposeCurrentScreen();
        screenManager.setScreen(new DifficultyScreen(assets, spriteBatch, screenManager));
    }

    private TextButton initialiseSettingsButton() {
        TextButton button = createTextButton(SETTINGS_BUTTON_TEXT);
        addSettingsButtonListener(button);
        return button;
    }

    private void addSettingsButtonListener(final TextButton button) {
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    switchToSettingsScreen();
                }
            }

        });
    }

    private void switchToSettingsScreen() {
        // don't dispose this screen here because it is likely we will be returning to
        // it after pressing the settings button
        screenManager.setScreen(new SettingsScreen(assets, spriteBatch, screenManager));
    }

    private Table getMenuTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.row();
        table.add(titleLabel).expandX();
        table.row();
        table.add(playButton).expandX();
        table.row().padTop(getComponentSpacing());
        table.add(settingsButton).expandX();
        return table;
    }

    @Override
    void onShow() {
        // ensure all buttons are unchecked when the screen is shown
        playButton.setChecked(false);
        settingsButton.setChecked(false);
    }

}