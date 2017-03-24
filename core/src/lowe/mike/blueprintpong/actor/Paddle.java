package lowe.mike.blueprintpong.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents the paddles in the game.
 *
 * @author Mike Lowe
 */
public final class Paddle extends ScaledImage {

    /*
     * Needed because paddle texture has margin that needs
     * to be removed from the bounding box.
     */
    private static final float MARGIN = 8f;

    private final Rectangle bounds = new Rectangle();
    private float speed; // in units per second
    private final Vector2 start = new Vector2();
    private final Vector2 end = new Vector2();
    private final Vector2 direction = new Vector2();
    private float distance;
    private boolean isMoving;

    /**
     * Creates a new {@code Paddle} given the {@link Texture}.
     *
     * @param texture the {@link Texture}
     */
    public Paddle(Texture texture) {
        super(texture);
        this.bounds.setSize(getScaledWidth() - (MARGIN * 2 * getScaleX()), getScaledHeight());
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        bounds.setPosition(getX() + (MARGIN * getScaleX()), getY());
    }

    /**
     * @return this {@code Paddle}'s bounding {@link Rectangle}
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * @param speed the speed of this {@code Paddle} (in units per second)
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * @param targetY the y position this {@code Paddle} should start moving towards
     */
    public void setTargetY(float targetY) {
        start.y = getY();
        end.y = targetY;
        direction.y = new Vector2(0, end.y - start.y).nor().y;
        distance = start.dst(0, end.y);
        isMoving = true;
    }

    /**
     * Updates this {@code Paddle}'s position, if required.
     *
     * @param delta time in seconds since the last frame
     */
    public void updatePosition(float delta) {
        if (!isMoving) {
            return;
        }
        moveBy(0, direction.y * speed * delta);
        if (start.dst(0, getY()) >= distance) {
            setY(end.y);
            isMoving = false;
        }
    }

    /**
     * Moves this {@code Paddle} up.
     *
     * @param delta time in seconds since the last frame
     */
    public void moveUp(float delta) {
        setTargetY(getY() + (speed * delta));
    }

    /**
     * Moves this {@code Paddle} down.
     *
     * @param delta time in seconds since the last frame
     */
    public void moveDown(float delta) {
        setTargetY(getY() - (speed * delta));
    }

}