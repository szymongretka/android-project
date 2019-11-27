package com.mygdx.game.handler;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.bullet.basic_bullet.BasicBullet;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.enemy.basic_enemy.BasicEnemy;

public class CollisionManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA == null || fixtureB == null) return;
        if(fixtureA.getUserData() == null || fixtureB.getUserData() == null) return;


        if(isBulletEnemyContact(fixtureA, fixtureB)) {
            Enemy enemy;
            Bullet bullet;

            BasicBullet basicBullet;
            BasicEnemy basicEnemy;

            if (fixtureA.getUserData() instanceof Enemy) {
                if (isBasicEnemy(fixtureA)) {
                    basicEnemy = (BasicEnemy) fixtureA.getUserData();
                    basicBullet = (BasicBullet) fixtureB.getUserData();
                    basicBullet.hitEnemy(basicEnemy);
                }
                //other enemies

            } else {
                if(isBasicBullet(fixtureA)) {
                    basicEnemy = (BasicEnemy) fixtureB.getUserData();
                    basicBullet = (BasicBullet) fixtureA.getUserData();
                    basicBullet.hitEnemy(basicEnemy);
                }
                //other bullets
            }

            //bullet.hitEnemy(enemy);

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
        if(a.getUserData() instanceof Bullet || b.getUserData() instanceof Bullet) {
            if(a.getUserData() instanceof Enemy || b.getUserData() instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

    private boolean isBasicBullet(Fixture fix) {
        if(fix.getUserData() instanceof BasicBullet)
            return true;

        return false;
    }

    private boolean isBasicEnemy(Fixture fix) {
        if(fix.getUserData() instanceof BasicEnemy)
            return true;

        return false;
    }


}
