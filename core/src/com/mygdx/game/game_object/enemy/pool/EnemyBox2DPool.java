package com.mygdx.game.game_object.enemy.pool;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.game_object.enemy.EnemyBox2D;

public class EnemyBox2DPool extends Pool<EnemyBox2D> {

    private World world;

    // constructor with initial object count and max object count
    // max is the maximum of object held in the pool and not the
    // maximum amount of objects that can be created by the pool
    public EnemyBox2DPool(int init, int max){
        super(init,max);
    }

    // make pool with default 16 initial objects and no max
    public EnemyBox2DPool(World world){
        super();
        this.world = world;
    }


    // method to create a single object
    @Override
    protected EnemyBox2D newObject() {
        return new EnemyBox2D(this.world);
    }
}