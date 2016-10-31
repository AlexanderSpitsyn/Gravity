package com.asp.gravity.actor;

import com.asp.gravity.AssetsProvider;
import com.asp.gravity.Constants;
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

    private static final float RADIUS = 200;
    private static final float DENSITY = 0;
    private static final float MASS = 32890;

    public StarActor(final World world, final AssetsProvider assetsProvider) {
        super(world, assetsProvider);
    }

    @Override
    protected Body createBody() {
        final Vector2 position = new Vector2(
                Gdx.graphics.getWidth() * Constants.WORLD_TO_SCREEN / 2,
                Gdx.graphics.getHeight() * Constants.WORLD_TO_SCREEN / 2
        );

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
        body.setUserData(new StarData(MASS, RADIUS));

        return body;
    }

    @Override
    protected Texture createTexture() {
        return assetsProvider.getTexture(AssetsProvider.STAR_IMAGE_PATH);
    }
}
