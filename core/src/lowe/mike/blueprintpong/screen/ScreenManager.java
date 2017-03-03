package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Disposable;

import java.util.Stack;

import lowe.mike.blueprintpong.BlueprintPongGame;

/**
 * {@code ScreenManager} is used to manage the current {@link Screen}
 * in the <i>Blueprint Pong</i> game.
 *
 * @author Mike Lowe
 */
public final class ScreenManager implements Disposable {

    private final BlueprintPongGame game;
    private final Stack<Screen> screens = new Stack<Screen>();

    /**
     * Creates a new {@code ScreenManager} instance given the
     * {@link BlueprintPongGame} to manage the {@link Screen} for.
     *
     * @param game reference to the {@link BlueprintPongGame}
     */
    public ScreenManager(BlueprintPongGame game) {
        this.game = game;
    }

    /**
     * Sets the current {@link Screen}. Note that any existing
     * {@link Screen}s are NOT disposed.
     *
     * @param screen the {@link Screen} to display
     */
    public void setCurrentScreen(Screen screen) {
        screens.push(screen);
        game.setScreen(screen);
    }

    /**
     * Removes and disposes the current {@link Screen}, if one exists.
     */
    public void removeCurrentScreen() {
        if (screens.peek() != null) {
            screens.pop().dispose();
        }
    }

    @Override
    public void dispose() {
        for (Screen screen : screens) {
            screen.dispose();
        }
        Gdx.app.log("Disposed", this.getClass().getName());
    }

}