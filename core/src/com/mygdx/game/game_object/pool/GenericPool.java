package com.mygdx.game.game_object.pool;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.bullet.BulletBox2D;
import com.mygdx.game.game_object.enemy.EnemyBox2D;

public class GenericPool {

    private World world;

    public GenericPool(World world) {
        this.world = world;
    }

    private final Pool<EnemyBox2D> enemyPool = new Pool<EnemyBox2D>() {
        @Override
        protected EnemyBox2D newObject() {
            return new EnemyBox2D(world);
        }
    };

    private final Pool<BulletBox2D> bulletPool = new Pool<BulletBox2D>() {
        @Override
        protected BulletBox2D newObject() {
            return new BulletBox2D(world);
        }
    };

    public Pool<BulletBox2D> getBulletPool() {
        return bulletPool;
    }

    public Pool<EnemyBox2D> getEnemyPool() {
        return enemyPool;
    }
}
