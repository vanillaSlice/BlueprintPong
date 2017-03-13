package lowe.mike.blueprintpong.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by mikelowe on 02/03/2017.
 */
public final class Paddle extends Sprite {

    private Body body;

    public Paddle(Texture texture, World world) {
        super(texture);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX(), getY());

        // Create a body in the world using our definition
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        // We are a box, so this makes sense, no?
        // Basically set the physics polygon to a box with the same dimensionsas our sprite
        shape.setAsBox(getWidth()/2, getHeight()/2);

        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of thebody
        // you also define it's properties like density, restitution and others we will see shortly
        // If you are wondering, density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();

    }

    public void update() {
        setPosition(body.getPosition().x, body.getPosition().y);
    }

}