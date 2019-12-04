package com.mygdx.game.handler;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.item.Item;
import com.mygdx.game.game_object.player.PlayerSpaceship;


public class CollisionManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA == null || fixtureB == null) return;
        if (fixtureA.getUserData() == null || fixtureB.getUserData() == null) return;

        if (isBulletEnemyContact(fixtureA, fixtureB)) {
            if (fixtureA.getUserData() instanceof Enemy) {
                ((Bullet) fixtureB.getUserData()).hitEnemy((Enemy) fixtureA.getUserData());
                ((Bullet) fixtureB.getUserData()).setToDestroy(true); //because multiple contacts can happen
            } else {
                ((Bullet) fixtureA.getUserData()).hitEnemy((Enemy) fixtureB.getUserData());
                ((Bullet) fixtureA.getUserData()).setToDestroy(true);
            }
        }

        if (isPlayerEnemyContact(fixtureA, fixtureB)) {

        }

        if (isPlayerItemContact(fixtureA, fixtureB)) {

        }

    }


    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean isBulletEnemyContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Bullet || b.getUserData() instanceof Bullet) {
            if (a.getUserData() instanceof Enemy || b.getUserData() instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlayerEnemyContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof PlayerSpaceship || b.getUserData() instanceof Enemy) {
            if (a.getUserData() instanceof Enemy || b.getUserData() instanceof PlayerSpaceship) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlayerItemContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof PlayerSpaceship || b.getUserData() instanceof Item) {
            if (a.getUserData() instanceof Item || b.getUserData() instanceof PlayerSpaceship) {
                return true;
            }
        }
        return false;
    }


}
