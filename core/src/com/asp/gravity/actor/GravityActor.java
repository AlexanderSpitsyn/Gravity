package com.asp.gravity.actor;

import com.asp.gravity.AssetsProvider;
import com.asp.gravity.Constants;
import com.asp.gravity.data.GravityData;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author aspitsyn
 * @since 26.10.2016
 */
public abstract class GravityActor<T extends GravityData> extends Actor {

    protected final World world;
    protected final AssetsProvider assetsProvider;
    protected final Body body;
    protected final Texture texture;

    public GravityActor(final World world, final AssetsProvider assetsProvider) {
        this.world = world;
        this.assetsProvider = assetsProvider;
        this.body = createBody();
        this.texture = createTexture();
    }

    protected abstract Body createBody();

    protected abstract Texture createTexture();

    public T getUserData() {
        //noinspection unchecked
        return (T) body.getUserData();
    }

    public void move(final Vector2 targetPosition) {
        body.setTransform(targetPosition, 0);
        getUserData().setPosition(targetPosition);
    }

    public void move(final float x, final float y) {
        move(new Vector2(x, y));
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        final T userData = getUserData();
        final float radius = userData.getRadius();

        final MassData massData = body.getMassData();
        massData.mass = userData.getMass();
        body.setMassData(massData);
        userData.setPosition(body.getPosition());

        batch.draw(texture, body.getPosition().x - radius, body.getPosition().y - radius, radius * 2, radius * 2);
        setBounds(body.getPosition().x - radius, body.getPosition().y - radius, radius * 2, radius * 2);
    }

    @Override
    public boolean remove() {
        return super.remove();
    }

    public void applyGravityTo(final GravityActor target) {
        final Body targetBody = target.body;

        final float distance = targetBody.getPosition().dst(body.getPosition());
        final float forceValue = Constants.G * body.getMass() * targetBody.getMass() / (distance * distance);
        final Vector2 direction = body.getPosition().sub(targetBody.getPosition());

        targetBody.applyForceToCenter(direction.scl(forceValue), false);
    }
}
