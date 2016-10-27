package com.asp.gravity;

import com.asp.gravity.data.GravityData;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author aspitsyn
 * @since 27.10.2016
 */
public class GravityContactListener implements ContactListener {

    @Override
    public void beginContact(final Contact contact) {

    }

    @Override
    public void endContact(final Contact contact) {

    }

    @Override
    public void preSolve(final Contact contact, final Manifold oldManifold) {

    }

    @Override
    public void postSolve(final Contact contact, final ContactImpulse impulse) {
        Body bodyA = null;
        Body bodyB = null;

        if (contact.getFixtureA() != null) {
            bodyA = contact.getFixtureA().getBody();
        }
        if (bodyA == null || bodyA.getUserData() == null) {
            return;
        }

        if (contact.getFixtureB() != null) {
            bodyB = contact.getFixtureB().getBody();
        }
        if (bodyB == null || bodyB.getUserData() == null) {
            return;
        }

        final GravityData userDataA = (GravityData) bodyA.getUserData();
        final GravityData userDataB = (GravityData) bodyB.getUserData();
        if (userDataA.getMass() >  userDataB.getMass()) {
            userDataB.markForDeletion();
            userDataA.setMass(userDataA.getMass() + userDataB.getMass());
        } else {
            userDataA.markForDeletion();
            userDataB.setMass(userDataB.getMass() + userDataA.getMass());
        }
    }
}
