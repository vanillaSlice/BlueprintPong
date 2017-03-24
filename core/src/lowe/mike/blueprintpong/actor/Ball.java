package lowe.mike.blueprintpong.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Represents the ball in the game.
 *
 * @author Mike Lowe
 */
public final class Ball extends ScaledImage {

    private final Circle bounds = new Circle();

    /**
     * Creates a new {@code Ball} given the {@link Texture}.
     *
     * @param texture the {@link Texture}
     */
    public Ball(Texture texture) {
        super(texture);
        this.bounds.setRadius(getScaledWidth() / 2f);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        float x = getX() + (getScaledWidth() / 2f);
        float y = getY() + (getScaledHeight() / 2f);
        bounds.setPosition(x, y);
    }

    /**
     * @return this {@code Ball}'s bounding {@link Circle}
     */
    public Circle getBounds() {
        return bounds;
    }

    //set speed

    //set angle

    //get angle

    //update position

}