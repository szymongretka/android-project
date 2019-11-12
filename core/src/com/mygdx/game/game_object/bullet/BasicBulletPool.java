package com.mygdx.game.game_object.bullet;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.game_object.GameObject;

public class BasicBulletPool extends Pool<BasicBullet> {

    // constructor with initial object count and max object count
    // max is the maximum of object held in the pool and not the
    // maximum amount of objects that can be created by the pool
    public BasicBulletPool(int init, int max){
        super(init,max);
    }

    // make pool with default 16 initial objects and no max
    public BasicBulletPool(){
        super();
    }


    // method to create a single object
    @Override
    protected BasicBullet newObject() {
        System.out.println("Creating new bullet");

        return new BasicBullet();
    }
}
