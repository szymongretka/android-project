package com.mygdx.game.game_object.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.enums.ID;
import com.mygdx.game.game_object.GameObject;

public class BasicBullet extends GameObject implements Pool.Poolable{

    public BasicBullet() {
        super(ID.BULLET, new Vector2(0, 0), false);
        velY = 200;
        width = 32;
        height = 32;
    }

    /**
     * Initialize the bullet. Call this method after getting a bullet from the pool.
     */
    public void init(float posX, float posY){//}, Texture texture) {
        position.set(posX,  posY);
        //this.texture = texture;
        alive = true;
    }

    /**
     * Method called each frame, which updates the bullet.
     */
    @Override
    public void update(float deltaTime) {
        //if(isOutOfScreen())
        position.add(0, velY * deltaTime);
        //y += velY * deltaTime;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }


    /**
     * Callback method when the object is freed. It is automatically called by Pool.free()
     * Must reset every meaningful field of this bullet.
     */
    @Override
    public void reset() {
        position.set(0,0);
        alive = false;
        //System.out.println("Bullet is reset");
    }

    public boolean isOverlapping(GameObject object) {
        return this.overlaps(object);
    }

}
