package com.mygdx.game.game_object.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.enums.ID;
import com.mygdx.game.game_object.Box2DBuilder;

public class EnemyBox2D implements Pool.Poolable{

    private Box2DBuilder builder;
    private Body body;
    private Vector2 position;
    private float width, height, velX, velY;
    private Texture texture;
    private boolean alive;

    public EnemyBox2D(World world) {
        this.position = new Vector2();
        alive = false;
        velY = 50;
        velX = 50;
        width = 32;
        height = 32;
        this.builder = new Box2DBuilder();
        this.body = builder.createBox(world, ID.ENEMY, 0, 0, width, height, false);
    }

    @Override
    public void reset() {
        position.set(0,0);
        alive = false;
    }


    public void init(float x, float y) {
        position.set(x, y);
        //this.texture = texture;
        alive = true;
    }

    public void update(float deltaTime) {
        //if(isOutOfScreen())
        position.add(velX * deltaTime, velY * deltaTime);
        //y += velY * deltaTime;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Body getBody() {
        return body;
    }
}
