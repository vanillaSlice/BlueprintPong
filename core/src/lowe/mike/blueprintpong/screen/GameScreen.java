package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.sprite.Paddle;

/**
 * Screen to show when the game is being played.
 *
 * @author Mike Lowe
 */
final class GameScreen extends BaseScreen {

    private static final String PAUSE_BUTTON_TEXT = "Pause";


    //    Paddles
//    Ball
//    Line
//    Scores
//    Win messages
//    Pause button
    int computerScore;
    int playerScore;
    boolean gameOver;
    boolean playerWon;

    private final Label computerScoreLabel;
    private final TextButton pauseButton;
    private final Label playerScoreLabel;
    private final Image line;

    private final World world;
    private final Paddle paddle;

    /**
     * Creates a new {@code GameScreen} given {@link Assets}, a {@link SpriteBatch}
     * and a {@link ScreenManager}.
     *
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     */
    GameScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
        super(assets, spriteBatch, screenManager);
        this.computerScoreLabel = ScreenUtils.createComputerScoreLabel(this.assets, 0);
        this.pauseButton = initialisePauseButton();
        this.playerScoreLabel = ScreenUtils.createPlayerScoreLabel(this.assets, 0);
        this.line = new Image(assets.getLineTexture());
        this.stage.addActor(this.line);
        this.stage.addActor(this.computerScoreLabel);
        this.stage.addActor(this.pauseButton);
        this.stage.addActor(this.playerScoreLabel);
        this.line.setX((viewport.getWorldWidth() / 2));
        ScreenUtils.scaleActor(this.line);
        this.world = new World(new Vector2(0, 0), true);
        paddle = new Paddle(assets.getPaddleTexture(), world);
    }

    private TextButton initialisePauseButton() {
        TextButton button = ScreenUtils.createTextButton(assets, PAUSE_BUTTON_TEXT);
        addPauseButtonListener(button);
        float x = (viewport.getWorldWidth() / 2) - (button.getWidth() / 2);
        float y = viewport.getWorldHeight() - button.getHeight() - COMPONENT_SPACING;
        button.setPosition(x, y);
        return button;
    }

    private void addPauseButtonListener(final TextButton button) {
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    switchToPauseScreen();
                }
            }

        });
    }

    private void switchToPauseScreen() {
        // don't dispose this screen because we want to be able to return to it
        // from the next screen
        screenManager.setScreen(new PauseScreen(assets, spriteBatch, screenManager, this));
    }

    @Override
    void onShow() {
        pauseButton.setChecked(false);
    }

    void resumeGame() {
        Gdx.app.log("GameScreen", "Resume");
    }

    void restartGame() {
        Gdx.app.log("GameScreen", "Restart");
    }

    private void switchToGameOverScreen() {
        // don't dispose this screen because we want to be able to return to it
        // from the next screen
        screenManager.setScreen(new GameOverScreen(assets, spriteBatch, screenManager, this));
    }

    @Override
    void update(float delta) {
        world.step(delta, 6, 2);
        paddle.update();
    }

}