package com.mygdx.game.game_object.pool;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.game_object.bullet.basic_bullet.BasicBullet;
import com.mygdx.game.game_object.enemy.basic_enemy.BasicEnemy;

public class GenericPool {

    private World world;

    public GenericPool(World world) {
        this.world = world;
    }

    private final Pool<BasicEnemy> enemyPool = new Pool<BasicEnemy>() {
        @Override
        protected BasicEnemy newObject() {
            return new BasicEnemy(world);
        }
    };

    private final Pool<BasicBullet> bulletPool = new Pool<BasicBullet>() {
        @Override
        protected BasicBullet newObject() {
            return new BasicBullet(world);
        }
    };

    public Pool<BasicBullet> getBulletPool() {
        return bulletPool;
    }

    public Pool<BasicEnemy> getEnemyPool() {
        return enemyPool;
    }
}
