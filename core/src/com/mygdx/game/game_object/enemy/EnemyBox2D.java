package com.mygdx.game.game_object.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.enums.ID;
import com.mygdx.game.game_object.Box2DBuilder;

import static com.mygdx.game.util.Constants.PPM;

public class EnemyBox2D implements Pool.Poolable{

    private Box2DBuilder builder;
    private Body body;
    //private Vector2 position;
    private float width, height, velX, velY;
    private Texture texture;

    public EnemyBox2D(World world) {
        //this.position = new Vector2();
        velY = 5000;
        velX = 50;
        width = 32;
        height = 32;
        this.builder = new Box2DBuilder();
        this.body = builder.createBox(world, 0, 0, width, height, false);
    }

    @Override
    public void reset() {
        this.body.setTransform(0, 0, 0);
        this.body.setLinearVelocity(0, 0);
        this.body.setActive(false);
        System.out.println("Enemy reset!");
    }

    public void init(float x, float y) {
        this.body.setTransform(x, y, 0);
        this.body.setActive(true);
    }

    public void update(float deltaTime) {
        this.body.setLinearVelocity(velX * deltaTime, velY * deltaTime);
    }

    public Body getBody() {
        return body;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

}
