package com.mygdx.game.game_object.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.enums.ID;
import com.mygdx.game.game_object.GameObject;

public class BasicEnemy extends GameObject implements Pool.Poolable{

    public BasicEnemy() {
        super(ID.ENEMY, new Vector2(0, 0), false);
        velY = 20;
        velX = 20;
        width = 32;
        height = 32;
    }

    /**
     * Initialize the bullet. Call this method after getting a bullet from the pool.
     */
    public void init(float posX, float posY) {
        position.set(posX,  posY);
        alive = true;
    }

    /**
     * Method called each frame, which updates the bullet.
     */
    @Override
    public void update(float deltaTime) {
        //if(isOutOfScreen())
        position.add(velX * deltaTime, velY * deltaTime);
        //y += velY * deltaTime;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }


    /**
     * Callback method when the object is freed. It is automatically called by Pool.free()
     * Must reset every meaningful field of this enemy.
     */
    @Override
    public void reset() {
        position.set(0,0);
        alive = false;
        System.out.println("Enemy is reset");
    }
}
