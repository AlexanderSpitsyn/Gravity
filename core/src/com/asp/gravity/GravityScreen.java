package com.asp.gravity;

import com.asp.gravity.stage.GravityStage;
import com.asp.gravity.stage.HudStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * @author aspitsyn
 * @since 24.10.2016
 */
public class GravityScreen implements Screen {

    private final GravityGameManager gravityGameManager;
    private final AssetsProvider assetsProvider;
    private GravityStage gravityStage;
    private HudStage hudStage;

    public GravityScreen(final GravityGameManager gravityGameManager, final AssetsProvider assetsProvider) {
        this.gravityGameManager = gravityGameManager;
        this.assetsProvider = assetsProvider;
    }

    @Override
    public void show() {
        gravityStage = new GravityStage(gravityGameManager, assetsProvider);
        hudStage = new HudStage(gravityGameManager, assetsProvider);

        Gdx.input.setInputProcessor(new InputMultiplexer(hudStage, gravityStage));
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT
                | GL20.GL_DEPTH_BUFFER_BIT
                | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0)
        );

        gravityStage.draw();
        gravityStage.act(delta);

        hudStage.draw();
        hudStage.act(delta);
    }

    @Override
    public void resize(final int width, final int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gravityStage.dispose();
        hudStage.dispose();
    }
}
