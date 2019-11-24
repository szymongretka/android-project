package com.mygdx.game.handler;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.game_object.bullet.BulletBox2D;
import com.mygdx.game.game_object.enemy.EnemyBox2D;

public class CollisionManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA == null || fixtureB == null) return;
        if(fixtureA.getUserData() == null || fixtureB.getUserData() == null) return;


        if(isBulletEnemyContact(fixtureA, fixtureB)) {
            EnemyBox2D enemyBox2D;
            BulletBox2D bulletBox2D;
            if (fixtureA.getUserData() instanceof EnemyBox2D) {
                enemyBox2D =  (EnemyBox2D) fixtureA.getUserData();
                bulletBox2D =  (BulletBox2D) fixtureB.getUserData();
            } else {
                enemyBox2D =  (EnemyBox2D) fixtureB.getUserData();
                bulletBox2D =  (BulletBox2D) fixtureA.getUserData();
            }

            bulletBox2D.hitEnemy(enemyBox2D);

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
        if(a.getUserData() instanceof BulletBox2D || b.getUserData() instanceof BulletBox2D) {
            if(a.getUserData() instanceof EnemyBox2D || b.getUserData() instanceof EnemyBox2D) {
                return true;
            }
        }
        return false;
    }

}
