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
public class HudActor extends Table {

    public interface ClickListener {
        void onMinus();
        void onPlus();
        void onStartPause(boolean start);
    }

    private final ClickListener clickListener;
    private boolean start;

    public HudActor(final AssetsProvider assetsProvider, final ClickListener clickListener) {
        this.clickListener = clickListener;
        setWidth(100);
        setHeight(300);

        final Skin skin = new Skin();
        skin.addRegions(assetsProvider.getTextureAtlas(AssetsProvider.TEXTURE_PATH));

        final Button.ButtonStyle pauseStyle = new Button.ButtonStyle();
        pauseStyle.up = skin.getDrawable("pause");
        final Button.ButtonStyle startStyle = new Button.ButtonStyle();
        startStyle.up = skin.getDrawable("start");
        final Button startPauseButton = new Button(pauseStyle);
        startPauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                start = !start;
                clickListener.onStartPause(start);
                startPauseButton.setStyle(start ? startStyle : pauseStyle);
            }
        });

        final Button.ButtonStyle plusStyle = new Button.ButtonStyle();
        plusStyle.up = skin.getDrawable("plus");
        final Button plusButton = new Button(plusStyle);
        plusButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                clickListener.onPlus();
            }
        });

        final Button.ButtonStyle minusStyle = new Button.ButtonStyle();
        minusStyle.up = skin.getDrawable("minus");
        final Button minusButton = new Button(minusStyle);
        minusButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                clickListener.onMinus();
            }
        });

        add(startPauseButton).width(100).height(100);
        row();
        add(plusButton).width(100).height(100);
        row();
        add(minusButton).width(100).height(100);
    }
}
