package com.mygdx.game.game_object;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;

public abstract class Box2DObject implements Pool.Poolable {


    protected Body body;
    protected float width, height, velY, velX;

    public Box2DObject(World world, float x, float y, float width, float height,
                       float velY, float velX, BodyDef.BodyType bodyType) {
        this.width = width;
        this.height = height;
        this.velY = velY;
        this.velX = velX;
        this.body = this.createBox(world, x, y, this.width, this.height, bodyType);
    }

    private Body createBox(World world, float x, float y, float width, float height,
                             BodyDef.BodyType bodyType) {
        Body body;

        BodyDef bodyDef = new BodyDef();
        if(bodyType == BodyDef.BodyType.StaticBody)
            bodyDef.type = BodyDef.BodyType.StaticBody;
        else if (bodyType == BodyDef.BodyType.DynamicBody)
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        else if (bodyType == BodyDef.BodyType.KinematicBody)
            bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.fixedRotation = true; //don t rotate
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);

        FixtureDef def = new FixtureDef();
        def.shape = polygonShape;
        def.density = 1.0f;
        body.createFixture(def).setUserData(this);
        polygonShape.dispose();

        body.setActive(false);

        return body;
    }

    public void init(float x, float y) {
        this.body.setTransform(x, y, 0);
        this.body.setActive(true);
    }

    public abstract void update(float deltaTime);

    @Override
    public void reset() {
        this.body.setTransform(0, 0, 0);
        this.body.setLinearVelocity(0, 0);
        this.body.setActive(false);
    }


    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }
}
