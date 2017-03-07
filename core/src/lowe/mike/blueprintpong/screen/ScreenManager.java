package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Disposable;

import java.util.Stack;

import lowe.mike.blueprintpong.BlueprintPongGame;

/**
 * {@code ScreenManager} is used to manage {@link Screen}s in
 * the <i>Blueprint Pong</i> game.
 * <p>
 * {@code ScreenManager} is a singleton.
 *
 * @author Mike Lowe
 */
public final class ScreenManager implements Disposable {

    private static ScreenManager instance;

    private final BlueprintPongGame game;
    private final Stack<Screen> screens = new Stack<Screen>();

    /**
     * Initialises the {@code ScreenManager} with a reference to the
     * {@link BlueprintPongGame} (note that only one instance will be created).
     *
     * @param game reference to the {@link BlueprintPongGame}
     * @return an instance of {@code ScreenManager}
     */
    public static ScreenManager initialise(BlueprintPongGame game) {
        if (instance == null) {
            instance = new ScreenManager(game);
        }
        return instance;
    }

    /**
     * @return an instance of {@code ScreenManager} (note that only one instance will be created)
     * @throws IllegalStateException if {@code ScreenManager} has not be initialised
     */
    public static ScreenManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ScreenManager has not been initialised");
        } else {
            return instance;
        }
    }

    private ScreenManager(BlueprintPongGame game) {
        this.game = game;
    }

    /**
     * Sets the {@link Screen} to display. Note that any existing
     * {@link Screen}s are NOT disposed.
     *
     * @param screen the {@link Screen} to display
     */
    public void setScreen(Screen screen) {
        screens.push(screen);
        game.setScreen(screen);
    }

    /**
     * Removes and disposes the current {@link Screen}, if one exists.
     */
    public void removeAndDisposeCurrentScreen() {
        if (!screens.isEmpty()) {
            screens.pop().dispose();
        }
    }

    /**
     * Switches to the previous {@link Screen}, if one exists. Note that
     * this removes and disposes the current {@link Screen}, if one exists.
     */
    public void switchToPreviousScreen() {
        removeAndDisposeCurrentScreen();
        if (!screens.isEmpty()) {
            game.setScreen(screens.peek());
        }
    }

    @Override
    public void dispose() {
        for (Screen screen : screens) {
            screen.dispose();
        }
    }

}