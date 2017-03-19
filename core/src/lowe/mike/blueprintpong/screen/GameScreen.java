package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lowe.mike.blueprintpong.Assets;

/**
 * Screen to show when the game is being played.
 *
 * @author Mike Lowe
 */
final class GameScreen extends BaseScreen {

    private static final String PAUSE_BUTTON_TEXT = "Pause";

    //  private final Label computerScoreLabel;
    //  private final Label playerScoreLabel;
    // private final Ball ball;
    // private final Paddle computerPaddle;
    // private final Paddle playerPaddle;

    // ceiling
    // ground
    // computer score bounds?
    // player score bounds?

    //private final Paddle computerPaddle;
    private int computerScore;
    private int playerScore;
    private boolean gameOver;
    private boolean playerWon;

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
//        Image line = createLine();
//        TextButton pauseButton = createPauseButton();
//        this.computerScoreLabel = ScreenUtils.createComputerScoreLabel(this.assets, 0);
//        this.playerScoreLabel = ScreenUtils.createPlayerScoreLabel(this.assets, 0);
////        this.world = new World(new Vector2(0, 0), true);
////        this.ball = new Ball(this.assets.getBallTexture(), this.world);
////        this.computerPaddle = new Paddle(this.assets.getPaddleTexture(), this.world);
////        this.playerPaddle = new Paddle(this.assets.getPaddleTexture(), this.world);
//        this.stage.addActor(line);
//        this.stage.addActor(this.computerScoreLabel);
//        this.stage.addActor(this.playerScoreLabel);
////        this.stage.addActor(this.ball);
////        this.stage.addActor(this.computerPaddle);
////        this.stage.addActor(this.playerPaddle);
//        this.stage.addActor(pauseButton);
//        createBoundaries();
//        newGame();
    }
//
//    private Image createLine() {
//        Image line = new Image(assets.getLineTexture());
//        line.setX(BlueprintPongGame.VIRTUAL_WIDTH / 2);
//        line.setScale(BlueprintPongGame.X_SCALE, BlueprintPongGame.Y_SCALE);
//        return line;
//    }
//
//    private TextButton createPauseButton() {
//        TextButton button = ScreenUtils.createTextButton(assets, PAUSE_BUTTON_TEXT);
//        addPauseButtonListener(button);
//        float x = (BlueprintPongGame.VIRTUAL_WIDTH / 2) - (button.getWidth() / 2);
//        float y = BlueprintPongGame.VIRTUAL_HEIGHT - button.getHeight() - COMPONENT_SPACING;
//        button.setPosition(x, y);
//        return button;
//    }
//
//    private void addPauseButtonListener(final TextButton button) {
//        button.addListener(new ChangeListener() {
//
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                if (button.isChecked()) {
//                    switchToPauseScreen();
//                    button.setChecked(false);
//                }
//            }
//
//        });
//    }
//
//    private void switchToPauseScreen() {
//        // don't dispose this screen because we want to be able to return to it
//        // from the next screen
//        screenManager.setScreen(new PauseScreen(assets, spriteBatch, screenManager, this));
//    }
//
//    private void createBoundaries() {
//        // top
//        createBoundary((BlueprintPongGame.VIRTUAL_HEIGHT / BlueprintPongGame.PPM) + 1f);
//        // bottom
//        createBoundary(-1f);
//    }
//
////    private void createBoundary(float y) {
////        Body body = createBoundaryBody(y);
////        createBoundaryFixture(body);
////    }
////
////    private Body createBoundaryBody(float y) {
////        BodyDef bodyDef = new BodyDef();
////        bodyDef.type = BodyDef.BodyType.StaticBody;
////        bodyDef.position.set(0, y);
////        return world.createBody(bodyDef);
////    }
////
////    private void createBoundaryFixture(Body body) {
////        FixtureDef fixtureDef = new FixtureDef();
////        PolygonShape shape = new PolygonShape();
////        shape.setAsBox(BlueprintPongGame.VIRTUAL_WIDTH / BlueprintPongGame.PPM, 1);
////        fixtureDef.shape = shape;
////        fixtureDef.density = 1f;
////        body.createFixture(fixtureDef);
////        shape.dispose();
////    }
//
//    // done up to here

    void newGame() {
//        computerScore = 0;
//        ScreenUtils.updateComputerScoreLabel(computerScoreLabel, computerScore);
//        playerScore = 0;
//        ScreenUtils.updatePlayerScoreLabel(playerScoreLabel, playerScore);
//        gameOver = false;
//        playerWon = false;
//        // reset paddle positions
//        // reset ball position -- delay then fire
//        resetBall();
//        resetComputerPaddle();
//        resetPlayerPaddle();
//
//        ball.getBody().applyLinearImpulse(new Vector2(2.5f, 0f), ball.getBody().getWorldCenter(), true);

    }
//
//    private void resetBall() {
//        float x = (BlueprintPongGame.VIRTUAL_WIDTH / 2) / BlueprintPongGame.PPM;
//        float y = (BlueprintPongGame.VIRTUAL_HEIGHT / 2) / BlueprintPongGame.PPM;
//        ball.getBody().setTransform(x, y, 0f);
//        ball.getBody().setAngularVelocity(0f);
//        ball.getBody().setLinearVelocity(0f, 0f);
//    }
//
//    private void resetComputerPaddle() {
//        float x = (((computerPaddle.getWidth() / 2) * BlueprintPongGame.X_SCALE) / BlueprintPongGame.PPM)
//                + (COMPONENT_SPACING / BlueprintPongGame.PPM);
//        float y = (BlueprintPongGame.VIRTUAL_HEIGHT / 2) / BlueprintPongGame.PPM;
//        computerPaddle.getBody().setTransform(x, y, 0f);
//        computerPaddle.getBody().setAngularVelocity(0f);
//        computerPaddle.getBody().setLinearVelocity(0f, 0f);
//    }
//
//    private void resetPlayerPaddle() {
//        float x = (BlueprintPongGame.VIRTUAL_WIDTH / BlueprintPongGame.PPM -
//                ((computerPaddle.getWidth() / 2) * BlueprintPongGame.X_SCALE) / BlueprintPongGame.PPM)
//                - (COMPONENT_SPACING / BlueprintPongGame.PPM);
//        float y = (BlueprintPongGame.VIRTUAL_HEIGHT / 2) / BlueprintPongGame.PPM;
//        playerPaddle.getBody().setTransform(x, y, 0f);
//        playerPaddle.getBody().setAngularVelocity(0f);
//        playerPaddle.getBody().setLinearVelocity(0f, 0f);
//    }
//
//    @Override
//    void update(float delta) {
//        world.step(1f/60f, 6, 2);
//        if (gameOver) {
//            switchToGameOverScreen();
//        } else {
//            handleUserInput();
//        }
//    }
//
//    private void switchToGameOverScreen() {
//        // don't dispose this screen because we want to be able to return to it
//        // from the next screen
//        screenManager.setScreen(new GameOverScreen(assets, spriteBatch, screenManager, this));
//    }
//
//    private void handleUserInput() {
//        if (Gdx.input.isKeyPressed(Input.Keys.UP) && playerPaddle.getBody().getLinearVelocity().y <= 2.5) {
//            playerPaddle.getBody().setLinearVelocity(0, 1f);
//            //playerPaddle.getBody().applyLinearImpulse(new Vector2(0, 5f), playerPaddle.getBody().getWorldCenter(), true);
//        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && playerPaddle.getBody().getLinearVelocity().y >= -2.5) {
//            playerPaddle.getBody().setLinearVelocity(0, -1f);
//            //playerPaddle.getBody().applyLinearImpulse(new Vector2(0, -0.1f), playerPaddle.getBody().getWorldCenter(), true);
//        }
//    }
//
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
//
//    @Override
//    public void resume() {
//        switchToPauseScreen();
//    }

}