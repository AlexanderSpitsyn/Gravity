package com.asp.gravity.actor;

import com.asp.gravity.Constants;
import com.asp.gravity.data.PlanetData;
import com.asp.gravity.data.StarData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author aspitsyn
 * @since 24.10.2016
 */
public class StarActor extends GravityActor<StarData> {

    private static final float RADIUS = 40;
    private static final float DENSITY = 0;
    private static final float MASS = 32890;

    public StarActor(final World world) {
        super(world);
    }

    @Override
    protected Body createBody() {
        final Vector2 position = new Vector2(Constants.WIDTH / 2, Constants.HEIGHT / 2);

        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        final Body body = world.createBody(bodyDef);

        final CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS);
        body.createFixture(shape, DENSITY);
        shape.dispose();

        final MassData massData = body.getMassData();
        massData.mass = MASS;
        body.setMassData(massData);
        final StarData starData = new StarData(MASS, RADIUS);
        starData.setPosition(position);
        body.setUserData(starData);

        return body;
    }

    @Override
    protected Texture createTexture() {
        return new Texture(Gdx.files.internal("img/star.png"));
    }
}
