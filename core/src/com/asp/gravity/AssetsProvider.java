package com.asp.gravity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author aspitsyn
 * @since 30.10.2016
 */
public class AssetsProvider implements Disposable {

    public static final String STAR_IMAGE_PATH = "img/star.png";

    private final AssetManager assetManager = new AssetManager();

    public AssetsProvider() {
        assetManager.load(STAR_IMAGE_PATH, Texture.class);
    }

    public Texture getTexture(final String fileName) {
        assetManager.finishLoadingAsset(fileName);
        return assetManager.get(fileName, Texture.class);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
