package com.asp.gravity.actor;

import com.asp.gravity.AssetsProvider;
import com.asp.gravity.Constants;
import com.asp.gravity.data.PlanetData;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author aspitsyn
 * @since 26.10.2016
 */
public class PlanetActor extends GravityActor<PlanetData> {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final List<Vector2> orbit = new ArrayList<>();
    private int orbitSplitCounter;
    private Vector2 accelerationStartPoint;
    private Vector2 accelerationEndPoint;

    public PlanetActor(final World world, final AssetsProvider assetsProvider) {
        this(world, assetsProvider, new PlanetData(Constants.PLANET_MASS, Constants.PLANET_RADIUS));
    }

    public PlanetActor(final World world, final AssetsProvider assetsProvider, final PlanetData gravityData) {
        super(world, assetsProvider, gravityData);
    }

    @Override
    protected Texture createTexture() {
        return assetsProvider.getTexture(AssetsProvider.STAR_IMAGE_PATH);
    }

    public void setAccelerationStartPoint(final Vector2 accelerationStartPoint) {
        this.accelerationStartPoint = accelerationStartPoint;
    }

    public void setAccelerationEndPoint(final Vector2 accelerationEndPoint) {
        this.accelerationEndPoint = accelerationEndPoint;
    }

    public void start() {
        if (accelerationStartPoint == null || accelerationEndPoint == null) {
            return;
        }
        final Vector2 direction = new Vector2(accelerationStartPoint);
        direction.sub(accelerationEndPoint);

        body.applyForceToCenter(direction.scl(Constants.INITIAL_FORCE), true);
        orbit.clear();
    }

    public List<PlanetActor> split() {
        final PlanetData userData = getUserData();
        if (userData == null || !userData.isNeedToSplit()) {
            return Collections.emptyList();
        }
        int newPlanetsCount = (int) (userData.getMass() / Constants.SPLIT_MASS_RATION);
        if (newPlanetsCount <= 1) {
            newPlanetsCount = 2;
        }

        final List<PlanetActor> newPlanetActors = new ArrayList<>(newPlanetsCount);
        for (int i = 0; i < newPlanetsCount; i++) {
            final Vector2 newPosition = body.getPosition().cpy();

            final PlanetData newPlanetData = new PlanetData();
            newPlanetData.setMass(userData.getMass() / newPlanetsCount);
            newPlanetData.setVolume(userData.getVolume() / newPlanetsCount);
            newPlanetData.setPosition(newPosition);

            final PlanetActor newPlanetActor = new PlanetActor(world, assetsProvider, newPlanetData);
            //newPlanetActor.body.setLinearVelocity(body.getLinearVelocity());
            newPlanetActors.add(newPlanetActor);
        }
        return newPlanetActors;
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);

        addOrbitPosition();

        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (final Vector2 vector2 : orbit) {
            shapeRenderer.circle(vector2.x, vector2.y, 1);
        }
        shapeRenderer.end();
        batch.begin();
    }

    private void addOrbitPosition() {
        orbitSplitCounter++;
        if (orbit.size() > 100000) {
            orbit.remove(orbit.size() - 1);
        }
        //if (orbitSplitCounter > 10) {
            final Vector2 orbitPos = body.getPosition().cpy();
            if (orbit.size() == 0 || !orbit.get(orbit.size() - 1).equals(orbitPos)) {
                orbit.add(orbitPos);
            }
            orbitSplitCounter = 0;
        //}
    }
}
