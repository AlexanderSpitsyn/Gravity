package com.asp.gravity.data;

import com.badlogic.gdx.math.Vector2;

/**
 * @author aspitsyn
 * @since 27.10.2016
 */
public abstract class GravityData {
    private float mass;
    private float radius;
    private Vector2 position = new Vector2();
    private boolean needToDelete;

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
        return position;
    }

    public void setPosition(final Vector2 position) {
        this.position = position;
    }

    public boolean isNeedToDelete() {
        return needToDelete;
    }

    public void markForDeletion() {
        needToDelete = true;
    }
}
