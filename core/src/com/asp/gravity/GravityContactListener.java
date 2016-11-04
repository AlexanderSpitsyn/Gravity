package com.asp.gravity;

import com.asp.gravity.data.GravityData;
import com.asp.gravity.data.StarData;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

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
        if (userDataA instanceof StarData) {
            absorb(userDataA, userDataB);
        } else if (userDataB instanceof StarData) {
            absorb(userDataB, userDataA);
        } else if (userDataA.getMass() >  userDataB.getMass()) {
            processCollision(userDataA, userDataB);
        } else {
            processCollision(userDataB, userDataA);
        }
    }

    private void processCollision(final GravityData userDataHeavy, final GravityData userDataLight) {
        if (userDataHeavy.getMass() / userDataLight.getMass() > Constants.ABSORPTION_MASS_RATIO) {
            absorb(userDataHeavy, userDataLight);
        } else {
            if (userDataHeavy.getMass() > Constants.SPLIT_MIN_MASS) {
                userDataHeavy.markForSplit();
            }
            if (userDataLight.getMass() > Constants.SPLIT_MIN_MASS) {
                userDataLight.markForSplit();
            }
        }
    }

    private void absorb(final GravityData userData, final GravityData userDataTarget) {
        userDataTarget.markForDeletion();
        userData.setMass(userData.getMass() + userDataTarget.getMass());
        userData.setVolume(userData.getVolume() + userDataTarget.getVolume());
    }
}
