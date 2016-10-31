package com.asp.gravity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author aspitsyn
 * @since 30.10.2016
 */
public class AssetsProvider implements Disposable {

    public static final String STAR_IMAGE_PATH = "star.png";
    public static final String TEXTURE_PATH = "texture.atlas";

    private final AssetManager assetManager = new AssetManager();

    public AssetsProvider() {
        assetManager.load(STAR_IMAGE_PATH, Texture.class);
        assetManager.load(TEXTURE_PATH, TextureAtlas.class);
    }

    public Texture getTexture(final String fileName) {
        assetManager.finishLoadingAsset(fileName);
        return assetManager.get(fileName, Texture.class);
    }

    public TextureAtlas getTextureAtlas(final String fileName) {
        assetManager.finishLoadingAsset(fileName);
        return assetManager.get(fileName, TextureAtlas.class);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
