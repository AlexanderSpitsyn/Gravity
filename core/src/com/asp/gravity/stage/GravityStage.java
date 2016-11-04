package com.asp.gravity.stage;

import com.asp.gravity.AssetsProvider;
import com.asp.gravity.Constants;
import com.asp.gravity.GravityContactListener;
import com.asp.gravity.GravityGameManager;
import com.asp.gravity.actor.PlanetActor;
import com.asp.gravity.actor.StarActor;
import com.asp.gravity.data.GravityData;
import com.asp.gravity.data.PlanetData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author aspitsyn
 * @since 24.10.2016
 */
public class GravityStage extends Stage {

    private static final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private final Box2DDebugRenderer renderer = new Box2DDebugRenderer();

    private final GravityGameManager gravityGameManager;
    private final AssetsProvider assetsProvider;
    private final World world = new World(new Vector2(0, 0), true);
    private final StarActor starActor;
    private final List<PlanetActor> planetActors = Collections.synchronizedList(new ArrayList<>());

    public GravityStage(final GravityGameManager gravityGameManager, final AssetsProvider assetsProvider) {
        super(new ScalingViewport(
                Scaling.stretch, Gdx.graphics.getWidth() * Constants.WORLD_TO_SCREEN, Gdx.graphics.getHeight() * Constants.WORLD_TO_SCREEN,
                new OrthographicCamera(Gdx.graphics.getWidth() * Constants.WORLD_TO_SCREEN, Gdx.graphics.getHeight() * Constants.WORLD_TO_SCREEN)
        ));
        this.gravityGameManager = gravityGameManager;
        this.assetsProvider = assetsProvider;

        ((OrthographicCamera) getCamera()).zoom = gravityGameManager.getZoom();
        world.setContactListener(new GravityContactListener());

        starActor = new StarActor(world, assetsProvider);
        addActor(starActor);

        addListener(new DragListener() {
            private PlanetActor planetActor;

            @Override
            public void dragStart(final InputEvent event, final float x, final float y, final int pointer) {
                planetActor = new PlanetActor(world, assetsProvider);
                addActor(planetActor);
                planetActor.setAccelerationStartPoint(new Vector2(event.getStageX(), event.getStageY()));
            }

            @Override
            public void drag(final InputEvent event, final float x, final float y, final int pointer) {
                planetActor.move(event.getStageX(), event.getStageY());
            }

            @Override
            public void dragStop(final InputEvent event, final float x, final float y, final int pointer) {
                planetActor.setAccelerationEndPoint(new Vector2(event.getStageX(), event.getStageY()));
                planetActor.start();
                planetActors.add(planetActor);
            }
        });
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
        ((OrthographicCamera) getCamera()).zoom = gravityGameManager.getZoom();
        if (gravityGameManager.getState().equals(GravityGameManager.State.PAUSED)) {
            return;
        }

        processPlanets();

        accumulator += delta;
        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }

    private void processPlanets() {
        final List<PlanetActor> planetActorsNew = new ArrayList<>();
        for (final Iterator<PlanetActor> iterator = planetActors.iterator(); iterator.hasNext(); ) {
            final PlanetActor planetActor = iterator.next();
            final PlanetData userData = planetActor.getUserData();
            if (userData == null) {
                continue;
            }
            if (userData.isNeedToSplit()) {
                planetActorsNew.addAll(planetActor.split());
            }
            if (userData.isNeedToDelete()) {
                iterator.remove();
                planetActor.remove();
            }
        }

        final Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for (final Body body : bodies) {
            final Object userData = body.getUserData();
            if (userData != null && ((GravityData) userData).isNeedToDelete()) {
                world.destroyBody(body);
            }
        }

        for (final PlanetActor planetActor : planetActors) {
            planetActor.applyGravityTo(starActor);
            starActor.applyGravityTo(planetActor);
            for (final PlanetActor planetActor1 : planetActors) {
                if (!planetActor.equals(planetActor1)) {
                    planetActor.applyGravityTo(planetActor1);
                }
            }
        }

        for (final PlanetActor planetActorNew : planetActorsNew) {
            addActor(planetActorNew);
        }
        planetActors.addAll(planetActorsNew);
    }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, getCamera().combined);
    }
}
