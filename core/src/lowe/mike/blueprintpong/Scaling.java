package lowe.mike.blueprintpong;

import com.badlogic.gdx.Gdx;

/**
 * {@code Scaling} provides helper methods for scaling objects
 * in the <i><Blueprint Pong/i> game.
 * <p>
 * Instances of {@code Scaling} cannot be created.
 *
 * @author Mike Lowe
 */
public final class Scaling {

    // don't want instances
    private Scaling() {
    }

    /**
     * @return the x scale value
     */
    public static float getX() {
        return BlueprintPongGame.VIRTUAL_WIDTH / Gdx.graphics.getWidth();
    }

    /**
     * @return the y scale value
     */
    public static float getY() {
        return BlueprintPongGame.VIRTUAL_HEIGHT / Gdx.graphics.getHeight();
    }

}