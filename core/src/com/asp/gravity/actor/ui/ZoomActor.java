package com.asp.gravity.actor.ui;

import com.asp.gravity.AssetsProvider;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * @author aspitsyn
 * @since 30.10.2016
 */
public abstract class ZoomActor extends Table {

    private final AssetsProvider assetsProvider;

    public ZoomActor(final AssetsProvider assetsProvider) {
        this.assetsProvider = assetsProvider;
        setWidth(50);
        setHeight(100);

        final Skin skin = new Skin();
        skin.addRegions(assetsProvider.getTextureAtlas(AssetsProvider.TEXTURE_PATH));

        final Button.ButtonStyle plusStyle = new Button.ButtonStyle();
        plusStyle.up = skin.getDrawable("plus");
        final Button plusButton = new Button(plusStyle);
        plusButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                onPlus();
            }
        });

        final Button.ButtonStyle minusStyle = new Button.ButtonStyle();
        minusStyle.up = skin.getDrawable("minus");
        final Button minusButton = new Button(minusStyle);
        minusButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                onMinus();
            }
        });

        add(plusButton).width(50).height(50);
        row();
        add(minusButton).width(50).height(50);
    }

    protected abstract void onMinus();

    protected abstract void onPlus();
}
