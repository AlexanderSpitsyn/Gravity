package com.asp.gravity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author aspitsyn
 * @since 24.10.2016
 */
public class GravityScreen implements Screen {

    private final AssetsProvider assetsProvider;
    private Stage stage;

    public GravityScreen(final AssetsProvider assetsProvider) {
        this.assetsProvider = assetsProvider;
    }

    @Override
    public void show() {
        stage = new GravityStage(assetsProvider);
    }

    @Override
    public void render(final float delta) {
        stage.act(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT
                | GL20.GL_DEPTH_BUFFER_BIT
                | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0)
        );

        stage.draw();
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
        stage.dispose();
    }
}
