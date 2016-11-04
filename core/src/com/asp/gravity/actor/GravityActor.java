package com.asp.gravity.actor;

import com.asp.gravity.AssetsProvider;
import com.asp.gravity.Constants;
import com.asp.gravity.data.GravityData;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * @author aspitsyn
 * @since 26.10.2016
 */
public abstract class GravityActor<T extends GravityData> extends Actor {

    protected final World world;
    protected final AssetsProvider assetsProvider;
    protected final Body body;
    protected final Texture texture;

    public GravityActor(final World world, final AssetsProvider assetsProvider, final T gravityData) {
        this.world = world;
        this.assetsProvider = assetsProvider;
        this.body = createBody(gravityData);
        this.texture = createTexture();
    }

    protected Body createBody(final T gravityData) {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(gravityData.getPosition());
        final Body body = world.createBody(bodyDef);

        final CircleShape shape = new CircleShape();
        shape.setRadius(gravityData.getRadius());
        body.createFixture(shape, 0);
        shape.dispose();

        final MassData massData = body.getMassData();
        massData.mass = gravityData.getMass();
        body.setMassData(massData);

        body.setUserData(gravityData);
        return body;
    }

    protected abstract Texture createTexture();

    public T getUserData() {
        //noinspection unchecked
        return (T) body.getUserData();
    }

    public void move(final Vector2 targetPosition) {
        body.setTransform(targetPosition, 0);
    }

    public void move(final float x, final float y) {
        move(new Vector2(x, y));
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        final T userData = getUserData();
        if (userData == null) {
            return;
        }

        final float radius = userData.getRadius();

        final Array<Fixture> fixtureList = body.getFixtureList();
        if (fixtureList.size == 0) {
            createCircleShape();
        } else {
            final Fixture fixture = fixtureList.get(0);
            final CircleShape shape = (CircleShape) fixture.getShape();
            if (shape.getRadius() != userData.getRadius()) {
                body.destroyFixture(fixture);
                createCircleShape();
            }
        }

        final MassData massData = body.getMassData();
        massData.mass = userData.getMass();
        body.setMassData(massData);

        userData.setPosition(body.getPosition());

        batch.draw(texture, body.getPosition().x - radius, body.getPosition().y - radius, radius * 2, radius * 2);
        setBounds(body.getPosition().x - radius, body.getPosition().y - radius, radius * 2, radius * 2);
    }

    private void createCircleShape() {
        final CircleShape newShape = new CircleShape();
        newShape.setRadius(getUserData().getRadius());
        body.createFixture(newShape, 0);
        newShape.dispose();
    }

    public void applyGravityTo(final GravityActor target) {
        final Body targetBody = target.body;

        final float distance = targetBody.getPosition().cpy().dst(body.getPosition());
        final float forceValue = Constants.G * body.getMass() * targetBody.getMass() / (distance * distance);
        final Vector2 direction = body.getPosition().cpy().sub(targetBody.getPosition());

        targetBody.applyForceToCenter(direction.scl(forceValue), false);
    }
}
