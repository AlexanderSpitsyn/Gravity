package com.asp.gravity.data;

import com.badlogic.gdx.math.Vector2;

/**
 * @author aspitsyn
 * @since 27.10.2016
 */
public abstract class GravityData {
    private float mass;
    private float radius;
    private Vector2 position;
    private boolean needToDelete;
    private boolean needToSplit;

    public GravityData() {
    }

    public GravityData(final float mass, final float radius) {
        this.mass = mass;
        this.radius = radius;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(final float mass) {
        this.mass = mass;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(final float radius) {
        this.radius = radius;
    }

    public Vector2 getPosition() {
        return position == null ? Vector2.Zero : position;
    }

    public void setPosition(final Vector2 position) {
        this.position = position == null ? Vector2.Zero : position.cpy();
    }

    public boolean isNeedToDelete() {
        return needToDelete || isNeedToSplit();
    }

    public void markForDeletion() {
        needToDelete = true;
    }

    public boolean isNeedToSplit() {
        return needToSplit;
    }

    public void markForSplit() {
        this.needToSplit = true;
    }

    public float getVolume() {
        return (float) (4.0 / 3.0 * Math.PI * Math.pow(radius, 3.0));
    }

    public void setVolume(final float volume) {
        radius = (float) Math.pow(3.0 * volume / 4.0 / Math.PI, 1.0 / 3.0);
    }
}
