package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import lowe.mike.blueprintpong.Assets;

/**
 * Created by mikelowe on 02/03/2017.
 */

final class GameScreen extends BaseScreen {

    //    Paddles
//    Ball
//    Line
//    Scores
//    Win messages
//    Pause button
    private static final String PAUSE_BUTTON_TEXT = "Pause";

    private final TextButton pauseButton;

    GameScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
        super(assets, spriteBatch, screenManager);
        this.pauseButton = initialisePauseButton();
        this.stage.addActor(this.pauseButton);
    }

    private TextButton initialisePauseButton() {
        TextButton button = ScreenUtils.createTextButton(assets, PAUSE_BUTTON_TEXT);
        addPauseButtonListener(button);
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

}