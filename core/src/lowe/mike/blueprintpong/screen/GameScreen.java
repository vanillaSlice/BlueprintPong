package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.BlueprintPongGame;
import lowe.mike.blueprintpong.Difficulty;
import lowe.mike.blueprintpong.GamePreferences;
import lowe.mike.blueprintpong.Scaling;
import lowe.mike.blueprintpong.actor.Ball;
import lowe.mike.blueprintpong.actor.Paddle;

/**
 * Screen to show when the game is being played.
 *
 * @author Mike Lowe
 */
final class GameScreen extends BaseScreen {

    private static final String PAUSE_BUTTON_TEXT = "Pause";
    private static final float PADDLE_OFFSET = 20f;
    private static final float PLAYER_PADDLE_SPEED = 200f; // in units per second
    private static final float NORMAL_BALL_SPEED = 220f; // in units per second
    private static final float FAST_BALL_SPEED = 225f; // in units per second
    private static final float FASTEST_BALL_SPEED = 250f; // in units per second
    private static final int WINNING_SCORE = 11;

    private final TextButton pauseButton;
    private final Label computerScoreLabel;
    private final Label playerScoreLabel;
    private final Ball ball;
    private final Paddle computerPaddle;
    private final Paddle playerPaddle;
    private Difficulty difficulty;
    private boolean playSounds;
    private int computerScore;
    private int playerScore;
    private boolean gameOver;
    private float ballSpeed; // in units per second
    private float ballAngle;
    private boolean hitWall;

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
        Image line = createLine();
        this.pauseButton = createPauseButton();
        this.computerScoreLabel = ScreenUtils.createComputerScoreLabel(this.assets, 0);
        this.playerScoreLabel = ScreenUtils.createPlayerScoreLabel(this.assets, 0);
        this.ball = new Ball(this.assets.getBallTexture());
        this.computerPaddle = new Paddle(this.assets.getPaddleTexture());
        this.playerPaddle = new Paddle(this.assets.getPaddleTexture());
        this.stage.addActor(line);
        this.stage.addActor(this.computerScoreLabel);
        this.stage.addActor(this.playerScoreLabel);
        this.stage.addActor(this.ball);
        this.stage.addActor(this.computerPaddle);
        this.stage.addActor(this.playerPaddle);
        this.stage.addActor(this.pauseButton);
        newGame();
    }

    private Image createLine() {
        Image line = new Image(assets.getLineTexture());
        line.setX(BlueprintPongGame.VIRTUAL_WIDTH / 2f);
        Scaling.scaleActor(line);
        return line;
    }

    private TextButton createPauseButton() {
        TextButton button = ScreenUtils.createTextButton(assets, PAUSE_BUTTON_TEXT);
        addPauseButtonListener(button);
        float x = (BlueprintPongGame.VIRTUAL_WIDTH / 2f) - (button.getWidth() / 2f);
        float y = BlueprintPongGame.VIRTUAL_HEIGHT - button.getHeight() - COMPONENT_SPACING;
        button.setPosition(x, y);
        return button;
    }

    private void addPauseButtonListener(final TextButton button) {
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    switchToPauseScreen();
                    button.setChecked(false);
                }
            }

        });
    }

    private void switchToPauseScreen() {
        // don't dispose this screen because we want to be able to return to it
        // from the next screen
        screenManager.setScreen(new PauseScreen(assets, spriteBatch, screenManager, this));
    }

    void newGame() {
        updatePreferences();
        computerScore = 0;
        ScreenUtils.updateComputerScoreLabel(computerScoreLabel, computerScore);
        playerScore = 0;
        ScreenUtils.updatePlayerScoreLabel(playerScoreLabel, playerScore);
        gameOver = false;
        resetBall();
        resetComputerPaddle();
        resetPlayerPaddle();
        newRound(true);
    }

    private void updatePreferences() {
        difficulty = GamePreferences.getDifficulty();
        playSounds = GamePreferences.shouldPlaySounds();
    }

    private void resetBall() {
        float x = (BlueprintPongGame.VIRTUAL_WIDTH / 2f) - (ball.getScaledWidth() / 2f);
        float y = (BlueprintPongGame.VIRTUAL_HEIGHT / 2f) - (ball.getScaledHeight() / 2f);
        ball.setPosition(x, y);
    }

    private void resetComputerPaddle() {
        float x = PADDLE_OFFSET;
        float y = (BlueprintPongGame.VIRTUAL_HEIGHT / 2f) - (computerPaddle.getScaledHeight() / 2f);
        computerPaddle.setPosition(x, y);
        computerPaddle.setTargetY(y);
        computerPaddle.setSpeed(difficulty.getComputerPaddleSpeed());
    }

    private void resetPlayerPaddle() {
        float x = BlueprintPongGame.VIRTUAL_WIDTH - PADDLE_OFFSET - playerPaddle.getScaledWidth();
        float y = (BlueprintPongGame.VIRTUAL_HEIGHT / 2f) - (playerPaddle.getScaledHeight() / 2f);
        playerPaddle.setPosition(x, y);
        playerPaddle.setTargetY(y);
        playerPaddle.setSpeed(PLAYER_PADDLE_SPEED);
    }

    private void newRound(boolean serveToPlayer) {
        ScreenUtils.updateComputerScoreLabel(computerScoreLabel, computerScore);
        ScreenUtils.updatePlayerScoreLabel(playerScoreLabel, playerScore);
        ballSpeed = NORMAL_BALL_SPEED;
        if (serveToPlayer)
            ballAngle = 180f;
        else
            ballAngle = 0f;
        resetBall();
    }

    void resumeGame() {
        updatePreferences();
    }

    @Override
    void update(float delta) {
        if (gameOver) {
            switchToGameOverScreen();
        } else {
            handleUserInput(delta);
            updateBallPosition(delta);
            handleCollisions(delta);
            updateComputerPaddlePosition(delta);
            updateScore();
            computerPaddle.updatePosition(delta);
            validatePaddlePosition(playerPaddle);
            validatePaddlePosition(computerPaddle);
        }
    }

    private void switchToGameOverScreen() {
        // don't dispose this screen because we want to be able to return to it
        // from the next screen
        screenManager.setScreen(new GameOverScreen(assets, spriteBatch, screenManager, this));
    }

    private void handleUserInput(float delta) {
        if (pauseButton.isPressed()) {
            return;
        }
        calculatePlayerPaddleYPosition(delta);
        //movePaddle(playerPaddle, y);
    }

    private void calculatePlayerPaddleYPosition(float delta) {
        float y = playerPaddle.getY();
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            // move player paddle up
            //y += (PLAYER_PADDLE_SPEED * delta);
            playerPaddle.moveUp(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            // move player paddle down
            playerPaddle.moveDown(delta);
        } else if (Gdx.input.isTouched()) {
            // move paddle to touched y coordinate
            Vector2 touchCoordinates = new Vector2(0, Gdx.input.getY());
            touchCoordinates = stage.getViewport().unproject(touchCoordinates);
            playerPaddle.setTargetY(touchCoordinates.y - (playerPaddle.getScaledHeight() / 2f));
        }
        playerPaddle.updatePosition(delta);
        // return y;

    }

    private void validatePaddlePosition(Paddle paddle) {
        if (paddle.getY() < 0) {
            paddle.setY(0);
        } else if (paddle.getY() + paddle.getScaledHeight()
                > BlueprintPongGame.VIRTUAL_HEIGHT) {
            paddle.setY(BlueprintPongGame.VIRTUAL_HEIGHT - playerPaddle.getScaledHeight());
        }
    }

    private void updateBallPosition(float delta) {
        float x = ball.getX() - (MathUtils.cosDeg(ballAngle) * ballSpeed * delta);
        float y = ball.getY() + (MathUtils.sinDeg(ballAngle) * ballSpeed * delta);
        ball.setPosition(x, y);
    }

    // look at adding actors again so we can easily scale
    // create bounding boxes slightly larger than textures
    private void handleCollisions(float delta) {
        if (ball.getX() + ball.getScaledWidth() >= playerPaddle.getX() && ball.getX() <= playerPaddle.getX() + playerPaddle.getScaledWidth() && hitPaddle(playerPaddle)) {
            //System.out.println(hitPaddle(playerPaddle));

            //ball.setX(playerPaddle.getX() - ball.getScaledWidth());

            if (ball.getY() < playerPaddle.getY()) {
                ballAngle = 320f;
            } else if (ball.getY() >= playerPaddle.getY() + playerPaddle.getScaledHeight() - (ball.getScaledHeight() / 2f)) {
                ballAngle = 40f;
            } else {
                ballAngle = 15f;
            }


            if (playSounds)
                assets.getPaddleHitSound().play(.2f);
        } else if (ball.getX() <= computerPaddle.getX() + computerPaddle.getScaledWidth()
                && ball.getX() >= computerPaddle.getX()
                && hitPaddle(computerPaddle)) {
            // ball.setX(computerPaddle.getX() + computerPaddle.getScaledWidth());


            if (ball.getY() < computerPaddle.getY()) {
                ballAngle = 220f;
            } else {
                ballAngle = 165f;
            }


            if (playSounds)
                assets.getPaddleHitSound().play(.2f);
        }

        if (!hitWall && ball.getY() <= 0) {
            ballAngle = 180 - (ballAngle % 180);
            if (playSounds)
                assets.getWallHitSound().play(.2f);
            ball.setY(0);
            hitWall = true;
        } else if (!hitWall && ball.getY() + ball.getScaledHeight() >= BlueprintPongGame.VIRTUAL_HEIGHT) {
            ballAngle = 360 - (ballAngle % 180);
            if (playSounds)
                assets.getWallHitSound().play(.2f);
            ball.setY(BlueprintPongGame.VIRTUAL_HEIGHT - ball.getScaledHeight());
            hitWall = true;
        } else if (hitWall) {
            hitWall = false;
        }
    }

    private boolean hitPaddle(Paddle paddle) {
        return (ball.getY() >= paddle.getY() && ball.getY() <= paddle.getY() + paddle.getScaledHeight())
                || (ball.getY() + ball.getScaledHeight() >= paddle.getY() && ball.getY() + ball.getScaledHeight() <= paddle.getY() + paddle.getScaledHeight());
    }

    private void updateComputerPaddlePosition(float delta) {
        computerPaddle.setTargetY(ball.getY() + (ball.getScaledHeight() / 2f) - (computerPaddle.getScaledHeight() / 2f));
    }

    private void updateScore() {
        // has computer scored
        if (ball.getX() > BlueprintPongGame.VIRTUAL_WIDTH) {
            computerScore++;
            if (playSounds)
                assets.getPointScoredSound().play(.2f);
            newRound(false);
        }
        // has player scored
        else if ((ball.getX() + (ball.getWidth() * ball.getScaleX())) < 0) {
            playerScore++;
            if (playSounds)
                assets.getPointScoredSound().play(.2f);
            newRound(true);
        }
        if (playerScore == WINNING_SCORE || computerScore == WINNING_SCORE) {
            gameOver = true;
        }
    }

    /**
     * @return the computer score
     */
    int getComputerScore() {
        return computerScore;
    }

    /**
     * @return the player score
     */
    int getPlayerScore() {
        return playerScore;
    }

    @Override
    public void pause() {
        switchToPauseScreen();
    }

}