package lowe.mike.blueprintpong.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Represents the paddles in the game.
 *
 * @author Mike Lowe
 */
public final class Paddle extends Image {

    public Paddle(Texture texture, World world) {
        super(texture);
    }

}