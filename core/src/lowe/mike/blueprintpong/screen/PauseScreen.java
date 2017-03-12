package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import lowe.mike.blueprintpong.Assets;

/**
 * Screen to show when the game is paused.
 *
 * @author Mike Lowe
 */
final class PauseScreen extends BaseScreen {

    private static final String RESUME_BUTTON_TEXT = "Resume";
    private static final String RESTART_BUTTON_TEXT = "Restart";
    private static final String SETTINGS_BUTTON_TEXT = "Settings";
    private static final String EXIT_BUTTON_TEXT = "Exit";

    private final GameScreen gameScreen;
    private final TextButton resumeButton;
    private final TextButton restartButton;
    private final TextButton settingsButtons;
    private final TextButton exitButton;

    /**
     * Creates a new {@code PauseScreen} given {@link Assets}, a {@link SpriteBatch}, a
     * {@link ScreenManager} and a reference to the {@link GameScreen}.
     *
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     * @param gameScreen    reference to the {@link GameScreen}
     */
    PauseScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager, GameScreen gameScreen) {
        super(assets, spriteBatch, screenManager);
        this.gameScreen = gameScreen;
        this.resumeButton = initialiseResumeButton();
        this.restartButton = initialiseRestartButton();
        this.settingsButtons = initialiseSettingsButton();
        this.exitButton = initialiseExitButton();
        this.stage.addActor(getMenuTable());
    }

    private TextButton initialiseResumeButton() {
        TextButton button = ScreenUtils.createTextButton(assets, RESUME_BUTTON_TEXT);
        addResumeButtonListener(button);
        return button;
    }

    private void addResumeButtonListener(final TextButton button) {
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    switchToGameScreenAndResume();
                }
            }

        });
    }

    private void switchToGameScreenAndResume() {
        screenManager.switchToPreviousScreen();
        gameScreen.resumeGame();
    }

    private TextButton initialiseRestartButton() {
        TextButton button = ScreenUtils.createTextButton(assets, RESTART_BUTTON_TEXT);
        addRestartButtonListener(button);
        return button;
    }

    private void addRestartButtonListener(final TextButton button) {
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    switchToGameScreenAndRestart();
                }
            }

        });
    }

    private void switchToGameScreenAndRestart() {
        screenManager.switchToPreviousScreen();
        gameScreen.restartGame();
    }

    private TextButton initialiseSettingsButton() {
        TextButton button = ScreenUtils.createTextButton(assets, SETTINGS_BUTTON_TEXT);
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
        // don't dispose this screen because we want to be able to return to it
        // from the next screen
        screenManager.setScreen(new SettingsScreen(assets, spriteBatch, screenManager));
    }

    private TextButton initialiseExitButton() {
        TextButton button = ScreenUtils.createTextButton(assets, EXIT_BUTTON_TEXT);
        addExitButtonListener(button);
        return button;
    }

    private void addExitButtonListener(final TextButton button) {
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    switchToMainMenuScreen();
                }
            }

        });
    }

    private void switchToMainMenuScreen() {
        // dispose this screen because we won't be able to return to it
        // from the next screen
        screenManager.disposeAndClearAllScreens();
        screenManager.setScreen(new MainMenuScreen(assets, spriteBatch, screenManager));
    }

    private Table getMenuTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.row().padBottom(COMPONENT_SPACING);
        table.add(resumeButton);
        table.row().padBottom(COMPONENT_SPACING);
        table.add(restartButton);
        table.row().padBottom(COMPONENT_SPACING);
        table.add(settingsButtons);
        table.row();
        table.add(exitButton);
        return table;
    }

    @Override
    void onShow() {
        // ensure all buttons are unchecked when the screen is shown
        resumeButton.setChecked(false);
        restartButton.setChecked(false);
        settingsButtons.setChecked(false);
        exitButton.setChecked(false);
    }

}