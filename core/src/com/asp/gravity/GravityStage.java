package com.asp.gravity;

import com.asp.gravity.actor.StarActor;
import com.asp.gravity.actor.PlanetActor;
import com.asp.gravity.data.GravityData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.ArrayList;
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

    private final World world = new World(new Vector2(0, 0), true);
    private final StarActor starActor = new StarActor(world);
    private final List<PlanetActor> planetActors = new ArrayList<>();

    public GravityStage() {
        super(new ScalingViewport(
                Scaling.stretch, Constants.WIDTH, Constants.HEIGHT,
                new OrthographicCamera(Constants.WIDTH, Constants.HEIGHT)
        ));
        Gdx.input.setInputProcessor(this);

        world.setContactListener(new GravityContactListener());
        addActor(starActor);

        addListener(new DragListener() {
            private PlanetActor planetActor;

            @Override
            public void dragStart(final InputEvent event, final float x, final float y, final int pointer) {
                planetActor = new PlanetActor(world);
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

        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void draw() {
        super.draw();

        for (final Iterator<PlanetActor> iterator = planetActors.iterator(); iterator.hasNext(); ) {
            final PlanetActor planetActor = iterator.next();
            if (planetActor.getUserData().isNeedToDelete()) {
                iterator.remove();
                planetActor.remove();
            }
        }

        final Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        bodies.forEach(body -> {
            final Object userData = body.getUserData();
            if (userData != null && ((GravityData) userData).isNeedToDelete()) {
                world.destroyBody(body);
            }
        });

        planetActors.forEach(planetActor -> {
            planetActor.applyGravityTo(starActor);
            starActor.applyGravityTo(planetActor);
            planetActors.stream()
                    .filter(planetActor1 -> !planetActor.equals(planetActor1))
                    .forEach(planetActor::applyGravityTo);
        });

        renderer.render(world, getCamera().combined);
    }
}
