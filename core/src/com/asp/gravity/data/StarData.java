package com.asp.gravity.data;

import com.badlogic.gdx.math.Vector2;

/**
 * @author aspitsyn
 * @since 27.10.2016
 */
public class StarData extends GravityData {

    public StarData(final float mass, final float radius, final Vector2 position) {
        super(mass, radius);
        setPosition(position);
    }

    @Override
    public boolean isNeedToSplit() {
        return false;
    }
}
