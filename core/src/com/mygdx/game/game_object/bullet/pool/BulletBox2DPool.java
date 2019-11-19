package com.mygdx.game.game_object.bullet.pool;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.game_object.bullet.BulletBox2D;

public class BulletBox2DPool extends Pool<BulletBox2D> {

    private World world;
    private Vector2 position;

    // constructor with initial object count and max object count
    // max is the maximum of object held in the pool and not the
    // maximum amount of objects that can be created by the pool
    public BulletBox2DPool(int init, int max){
        super(init,max);
    }

    // make pool with default 16 initial objects and no max
    public BulletBox2DPool(World world, Vector2 position){
        super();
        this.world = world;
        this.position = position;
    }


    // method to create a single object
    @Override
    protected BulletBox2D newObject() {
        //System.out.println("Creating new bullet");

        return new BulletBox2D(this.world, this.position);
    }
}
