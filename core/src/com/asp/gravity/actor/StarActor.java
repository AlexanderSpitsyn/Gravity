package com.asp.gravity.actor;

import com.asp.gravity.AssetsProvider;
import com.asp.gravity.Constants;
import com.asp.gravity.data.StarData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author aspitsyn
 * @since 24.10.2016
 */
public class StarActor extends GravityActor<StarData> {

    public StarActor(final World world, final AssetsProvider assetsProvider) {
        super(world, assetsProvider, new StarData(Constants.STAR_MASS, Constants.STAR_RADIUS, new Vector2(
                Gdx.graphics.getWidth() * Constants.WORLD_TO_SCREEN / 2,
                Gdx.graphics.getHeight() * Constants.WORLD_TO_SCREEN / 2
        )));
    }

    @Override
    protected Texture createTexture() {
        return assetsProvider.getTexture(AssetsProvider.STAR_IMAGE_PATH);
    }
}
