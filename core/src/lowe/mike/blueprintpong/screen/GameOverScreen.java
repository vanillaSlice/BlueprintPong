package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import lowe.mike.blueprintpong.Assets;

/**
 * Screen to show when the game is over.
 *
 * @author Mike Lowe
 */
final class GameOverScreen extends BaseScreen {

    private static final String COMPUTER_WINS_LABEL_TEXT = "Computer Wins";
    private static final String PLAYER_WINS_LABEL_TEXT = "You Win";
    private static final String PLAY_AGAIN_BUTTON_TEXT = "Play Again";
    private static final String EXIT_BUTTON_TEXT = "Exit";

    private final GameScreen gameScreen;
    private final Label computerScoreLabel;
    private final Label playerScoreLabel;
    private final Label winnerLabel;
    private final TextButton playAgainButton;
    private final TextButton exitButton;

    /**
     * Creates a new {@code GameOverScreen} given {@link Assets}, a {@link SpriteBatch}, a
     * {@link ScreenManager} and a reference to the {@link GameScreen}.
     *
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     * @param gameScreen    reference to the {@link GameScreen}
     */
    GameOverScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager, GameScreen gameScreen) {
        super(assets, spriteBatch, screenManager);
        this.gameScreen = gameScreen;
        this.computerScoreLabel = initialiseComputerScoreLabel();
        this.playerScoreLabel = initialisePlayerScoreLabel();
        this.winnerLabel = initialiseWinnerLabel();
        this.playAgainButton = initialisePlayAgainButton();
        this.exitButton = initialiseExitButton();
        this.stage.addActor(getMenuTable());
        this.stage.addActor(this.computerScoreLabel);
        this.stage.addActor(this.playerScoreLabel);
    }

    private Label initialiseComputerScoreLabel() {
        Label label = ScreenUtils.createLabel(assets.getMediumFont(), Integer.toString(gameScreen.computerScore));
        float x = (viewport.getWorldWidth() / 4) - (label.getWidth() / 2);
        float y = viewport.getWorldHeight() - label.getHeight();
        label.setPosition(x, y);
        return label;
    }

    private Label initialisePlayerScoreLabel() {
        Label label = ScreenUtils.createLabel(assets.getMediumFont(), Integer.toString(gameScreen.playerScore));
        float x = ((viewport.getWorldWidth() / 4) * 3) - (label.getWidth() / 2);
        float y = viewport.getWorldHeight() - label.getHeight();
        label.setPosition(x, y);
        return label;
    }

    private Label initialiseWinnerLabel() {
        String message = (gameScreen.playerWon) ? PLAYER_WINS_LABEL_TEXT : COMPUTER_WINS_LABEL_TEXT;
        return ScreenUtils.createLabel(assets.getLargeFont(), message);
    }

    private TextButton initialisePlayAgainButton() {
        TextButton button = ScreenUtils.createTextButton(assets, PLAY_AGAIN_BUTTON_TEXT);
        addPlayAgainButtonListener(button);
        return button;
    }

    private void addPlayAgainButtonListener(final TextButton button) {
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
        table.row();
        table.add(winnerLabel);
        table.row().padBottom(COMPONENT_SPACING);
        table.add(playAgainButton);
        table.row();
        table.add(exitButton);
        return table;
    }

}