package com.asp.gravity;

/**
 * @author aspitsyn
 * @since 30.10.2016
 */
public class GravityGameManager {

    private static final float ZOOM_DELTA = 0.1f;
    private static final float MAX_ZOOM = 1.2f;

    public enum State {
        RUNNING, PAUSED
    }

    private State state = State.PAUSED;
    private float zoom = 0.25f;

    public State getState() {
        return state;
    }

    public void setState(final State state) {
        this.state = state;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(final float zoom) {
        this.zoom = zoom;
    }

    public void incZoom() {
        if (zoom < MAX_ZOOM) {
            zoom += ZOOM_DELTA;
        }
    }

    public void decZoom() {
        if (zoom > ZOOM_DELTA) {
            zoom -= ZOOM_DELTA;
        }
    }
}
