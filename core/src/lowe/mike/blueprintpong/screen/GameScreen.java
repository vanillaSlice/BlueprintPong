package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.BlueprintPongGame;
import lowe.mike.blueprintpong.actor.Ball;
import lowe.mike.blueprintpong.actor.Paddle;

/**
 * Screen to show when the game is being played.
 *
 * @author Mike Lowe
 */
final class GameScreen extends BaseScreen {

    private static final String PAUSE_BUTTON_TEXT = "Pause";

    private final Image line;
    private final Label computerScoreLabel;
    private final TextButton pauseButton;
    private final Label playerScoreLabel;

    private final World world;
    private final Ball ball;
    private final Paddle computerPaddle;
    private final Paddle playerPaddle;
    private int computerScore = 10;
    private int playerScore = 5;
    private boolean gameOver;
    private boolean playerWon;

    Box2DDebugRenderer debugRenderer;
    Body ground;

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
        this.line = initialiseLine();
        this.computerScoreLabel = ScreenUtils.createLeftScoreLabel(this.assets, 0);
        this.pauseButton = initialisePauseButton();
        this.playerScoreLabel = ScreenUtils.createRightScoreLabel(this.assets, 0);
        this.world = initialiseWorld();
        this.ball = initialiseBall();
        this.computerPaddle = initialisePaddle();
        this.playerPaddle = initialisePaddle();
        this.stage.addActor(this.line);
        this.stage.addActor(this.computerScoreLabel);
        this.stage.addActor(this.pauseButton);
        this.stage.addActor(this.playerScoreLabel);
        this.stage.addActor(this.ball);
        this.stage.addActor(this.computerPaddle);
        this.stage.addActor(this.playerPaddle);

        debugRenderer = new Box2DDebugRenderer();
        newGame();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -(1 / BlueprintPongGame.PPM));
        ground = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(BlueprintPongGame.VIRTUAL_WIDTH / BlueprintPongGame.PPM, 1);
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        ground.createFixture(fixtureDef);
        shape.dispose();
    }

    private Image initialiseLine() {
        Image line = new Image(assets.getLineTexture());
        line.setX(BlueprintPongGame.VIRTUAL_WIDTH / 2);
        ScreenUtils.scaleActor(line);
        return line;
    }

    private TextButton initialisePauseButton() {
        TextButton button = ScreenUtils.createTextButton(assets, PAUSE_BUTTON_TEXT);
        addPauseButtonListener(button);
        float x = (BlueprintPongGame.VIRTUAL_WIDTH / 2) - (button.getWidth() / 2);
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

    private World initialiseWorld() {
        World world = new World(new Vector2(0, -10f), true);
        return world;
    }

    private Ball initialiseBall() {
        Ball ball = new Ball(assets.getBallTexture(), world, 50, 50);
        ScreenUtils.scaleActor(ball);
        return ball;
    }

    private Paddle initialisePaddle() {
        Paddle paddle = new Paddle(assets.getPaddleTexture(), world);
        ScreenUtils.scaleActor(paddle);
        return paddle;
    }

//    @Override
//    void onShow() {
//        pauseButton.setChecked(false);
//    }

    // done up to here

    void newGame() {
        Gdx.app.log("GameScreen", "New Game");
    }

    void resumeGame() {
        Gdx.app.log("GameScreen", "Resume");
    }

    private void switchToGameOverScreen() {
        // don't dispose this screen because we want to be able to return to it
        // from the next screen
        screenManager.setScreen(new GameOverScreen(assets, spriteBatch, screenManager, this));
    }

    @Override
    void update(float delta) {
        world.step(delta, 6, 2);
        debugRenderer.render(world, camera.combined.cpy().scale(BlueprintPongGame.PPM, BlueprintPongGame.PPM, 1f));

    }

    private void updateComputerScoreLabel() {
        ScreenUtils.updateLeftScoreLabel(computerScoreLabel, computerScore);
    }

    private void updatePlayerScoreLabel() {
        ScreenUtils.updateRightScoreLabel(playerScoreLabel, playerScore);
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

    /**
     * @return {@code true} if the player has won; {@code false} if not
     */
    boolean hasPlayerWon() {
        return playerWon;
    }

    @Override
    void onDispose() {
        world.dispose();
    }


    @Override
    public void pause() {
        switchToPauseScreen();
    }
}