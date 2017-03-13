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
        this.computerScoreLabel = ScreenUtils.createComputerScoreLabel(this.assets, this.gameScreen.computerScore);
        this.playerScoreLabel = ScreenUtils.createPlayerScoreLabel(this.assets, this.gameScreen.playerScore);
        this.winnerLabel = initialiseWinnerLabel();
        this.playAgainButton = initialisePlayAgainButton();
        this.exitButton = ScreenUtils.createExitButton(this.assets, this.spriteBatch, this.screenManager);
        this.stage.addActor(getMenuTable());
        this.stage.addActor(this.computerScoreLabel);
        this.stage.addActor(this.playerScoreLabel);
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