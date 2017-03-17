package lowe.mike.blueprintpong.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import lowe.mike.blueprintpong.BlueprintPongGame;

/**
 * Represents the ball in the game.
 *
 * @author Mike Lowe
 */
public final class Ball extends Image {

    private Body body;

    public Ball(Texture texture, World world, float x, float y) {
        super(texture);
        setPosition(x, y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / BlueprintPongGame.PPM, y / BlueprintPongGame.PPM);
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius((texture.getWidth() / 2) / BlueprintPongGame.PPM );

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        body.createFixture(fixtureDef);

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
    }

    @Override
    public void act(float delta) {
        setPosition(body.getPosition().x * BlueprintPongGame.PPM, body.getPosition().y * BlueprintPongGame.PPM);
        System.out.println(body.getPosition().y);
    }
}