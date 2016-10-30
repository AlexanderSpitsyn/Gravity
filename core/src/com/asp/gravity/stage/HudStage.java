package com.asp.gravity.stage;

import com.asp.gravity.AssetsProvider;
import com.asp.gravity.GravityGameManager;
import com.asp.gravity.actor.ui.ZoomActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

/**
 * @author aspitsyn
 * @since 30.10.2016
 */
public class HudStage extends Stage {

    private final GravityGameManager gravityGameManager;
    private final AssetsProvider assetsProvider;

    public HudStage(final GravityGameManager gravityGameManager, final AssetsProvider assetsProvider) {
        super(new ScalingViewport(
                Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
        ));
        this.gravityGameManager = gravityGameManager;
        this.assetsProvider = assetsProvider;

        addActor(new ZoomActor(assetsProvider) {
            @Override
            protected void onMinus() {
                gravityGameManager.incZoom();
            }

            @Override
            protected void onPlus() {
                gravityGameManager.decZoom();
            }
        });
    }
}
