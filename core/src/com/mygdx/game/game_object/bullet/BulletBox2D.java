package com.mygdx.game.game_object.bullet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.enums.ID;
import com.mygdx.game.game_object.Box2DBuilder;

public class BulletBox2D implements Pool.Poolable{

    private Box2DBuilder builder;
    private Body body;
    private Vector2 position;
    private float width, height, velX, velY;
    private Texture texture;
    private boolean alive;

    public BulletBox2D(World world, Vector2 position) {
        //this.position = new Vector2();
        this.position = position;
        alive = false;
        velY = 150;
        width = 32;
        height = 32;
        this.builder = new Box2DBuilder();
        this.body = builder.createBox(world,position.x, position.y + 120, width, height, false);
        this.body.setActive(true);
    }

    @Override
    public void reset() {
        position.set(0,0);
        this.body.setActive(false);
        alive = false;
        //System.out.println("Bullet is reset");
    }


    public void init(float x, float y) {
        this.position.set(x, y);
        //this.texture = texture;
        this.body.setActive(true);
        alive = true;
    }

    public void update(float deltaTime) {
        //if(isOutOfScreen())
        this.body.setLinearVelocity(0, velY * deltaTime);
        //position.y += velY * deltaTime;
        //position.add(0, velY * deltaTime);
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

    public boolean isAlive() {
        return alive;
    }


}
