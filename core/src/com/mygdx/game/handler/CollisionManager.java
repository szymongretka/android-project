package com.mygdx.game.handler;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.bullet.EnemyBullet;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.item.Item;
import com.mygdx.game.game_object.obstacle.Obstacle;
import com.mygdx.game.game_object.player.PlayerSpaceship;


public class CollisionManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA == null || fixtureB == null) return;
        else if (fixtureA.getUserData() == null || fixtureB.getUserData() == null) return;

        else if (isBulletEnemyContact(fixtureA, fixtureB)) {
            if (fixtureA.getUserData() instanceof Enemy) {
                ((Bullet) fixtureB.getUserData()).hitEnemy((Enemy) fixtureA.getUserData());
                ((Bullet) fixtureB.getUserData()).setToDestroy(true); //because multiple contacts can happen
            } else {
                ((Bullet) fixtureA.getUserData()).hitEnemy((Enemy) fixtureB.getUserData());
                ((Bullet) fixtureA.getUserData()).setToDestroy(true);
            }
        }

        else if (isPlayerItemContact(fixtureA, fixtureB)) {
            if (fixtureA.getUserData() instanceof PlayerSpaceship) {
                ((Item) fixtureB.getUserData()).takenByPlayer((PlayerSpaceship) fixtureA.getUserData());
            } else {
                ((Item) fixtureA.getUserData()).takenByPlayer((PlayerSpaceship) fixtureB.getUserData());
            }
        }

        else if (isPlayerEnemyBulletContact(fixtureA, fixtureB)) {
            if (fixtureA.getUserData() instanceof PlayerSpaceship) {
                ((EnemyBullet) fixtureB.getUserData()).hitPlayer((PlayerSpaceship) fixtureA.getUserData());
            } else {
                ((EnemyBullet) fixtureA.getUserData()).hitPlayer((PlayerSpaceship) fixtureB.getUserData());
            }
        }

        else if (isPlayerEnemyContact(fixtureA, fixtureB)) {
            if (fixtureA.getUserData() instanceof PlayerSpaceship) {
                ((Enemy) fixtureB.getUserData()).hitPlayer((PlayerSpaceship) fixtureA.getUserData());
            } else {
                ((Enemy) fixtureA.getUserData()).hitPlayer((PlayerSpaceship) fixtureB.getUserData());
            }
        }

        else if (isPlayerObstacleContact(fixtureA, fixtureB)) {
            if (fixtureA.getUserData() instanceof PlayerSpaceship) {
                ((Obstacle) fixtureB.getUserData()).hitPlayer((PlayerSpaceship) fixtureA.getUserData());
                ((Obstacle) fixtureB.getUserData()).setToDestroy(true); //because multiple contacts can happen
            } else {
                ((Obstacle) fixtureA.getUserData()).hitPlayer((PlayerSpaceship) fixtureB.getUserData());
                ((Obstacle) fixtureA.getUserData()).setToDestroy(true);
            }
        }

        else if (isObstacleObstacleContact(fixtureA, fixtureB)) {
            fixtureA.getBody().applyLinearImpulse(fixtureA.getBody().getLinearVelocity(), fixtureA.getBody().getLocalCenter(), true);
            fixtureB.getBody().applyLinearImpulse(fixtureB.getBody().getLinearVelocity(), fixtureB.getBody().getLocalCenter(), true);
        }

        else if (isObstacleBulletContact(fixtureA, fixtureB)) {
            if (fixtureA.getUserData() instanceof Obstacle) {
                ((Bullet) fixtureB.getUserData()).hitObstacle((Obstacle) fixtureA.getUserData());
                ((Bullet) fixtureB.getUserData()).setToDestroy(true);
            } else {
                ((Bullet) fixtureA.getUserData()).hitObstacle((Obstacle) fixtureB.getUserData());
                ((Bullet) fixtureA.getUserData()).setToDestroy(true);
            }
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

    private boolean isObstacleBulletContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Bullet || b.getUserData() instanceof Bullet) {
            if (a.getUserData() instanceof Obstacle || b.getUserData() instanceof Obstacle) {
                return true;
            }
        }
        return false;
    }


    private boolean isObstacleObstacleContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Obstacle && b.getUserData() instanceof Obstacle) {
            return true;
        }
        return false;
    }

    private boolean isPlayerObstacleContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof PlayerSpaceship || b.getUserData() instanceof PlayerSpaceship) {
            if (a.getUserData() instanceof Obstacle || b.getUserData() instanceof Obstacle) {
                return true;
            }
        }
        return false;
    }

    private boolean isBulletEnemyContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Bullet || b.getUserData() instanceof Bullet) {
            if (a.getUserData() instanceof Enemy || b.getUserData() instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlayerEnemyBulletContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof PlayerSpaceship || b.getUserData() instanceof PlayerSpaceship) {
            if (a.getUserData() instanceof EnemyBullet || b.getUserData() instanceof EnemyBullet) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlayerEnemyContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof PlayerSpaceship || b.getUserData() instanceof PlayerSpaceship) {
            if (a.getUserData() instanceof Enemy || b.getUserData() instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlayerItemContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof PlayerSpaceship || b.getUserData() instanceof PlayerSpaceship) {
            if (a.getUserData() instanceof Item || b.getUserData() instanceof Item) {
                return true;
            }
        }
        return false;
    }


}
