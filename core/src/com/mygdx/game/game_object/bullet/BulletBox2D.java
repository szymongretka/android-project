package com.mygdx.game.game_object.bullet;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.enemy.EnemyBox2D;

import static com.mygdx.game.util.Constants.BIT_BULLET;
import static com.mygdx.game.util.Constants.BIT_ENEMY;
import static com.mygdx.game.util.Constants.BIT_PLAYER;

public class BulletBox2D extends Box2DObject {

    private float width = 32;
    private float height = 32;
    private float velX = 0;
    private float velY = 18000;


    public BulletBox2D(World world) {
        super(world, 0, 0, 32, 32, 200000, 0, 1, 1,
                BodyDef.BodyType.DynamicBody, BIT_BULLET, BIT_ENEMY, (short) 0);
    }

    @Override
    public void update(float deltaTime) {
        this.body.setLinearVelocity(0, velY * deltaTime * 4);
    }

    public void hitEnemy(EnemyBox2D enemyBox2D) {
        enemyBox2D.setHp(enemyBox2D.getHp() - this.getDamage());
        reset();
    }

    @Override
    public void reset() {
        super.reset();
        System.out.println("bullet rest");
    }




    /*private Box2DBuilder builder;
    private Body body;
    private float width, height, velY;


    public BulletBox2D(World world) {
        velY = 15000;
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
    }


    public void init(float x, float y) {
        this.body.setTransform(x, y, 0);
        this.body.setActive(true);
    }

    public void update(float deltaTime) {
        this.body.setLinearVelocity(0, velY * deltaTime);
    }

    public Body getBody() {
        return this.body;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }*/
}
