package com.mygdx.game.game_object.enemy;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.bullet.BulletBox2D;

import static com.mygdx.game.util.Constants.BIT_BULLET;
import static com.mygdx.game.util.Constants.BIT_ENEMY;
import static com.mygdx.game.util.Constants.BIT_PLAYER;

public class EnemyBox2D extends Box2DObject {


    private float width = 32;
    private float height = 32;
    private float velX = 500;
    private float velY = 500;


    public EnemyBox2D(World world) {
        super(world, 0, 0, 32, 32, 50, 50, 3, 1,
                BodyDef.BodyType.DynamicBody, BIT_ENEMY, (short) (BIT_BULLET | BIT_PLAYER), (short) 0);

    }

    @Override
    public void update(float deltaTime) {
        this.body.setLinearVelocity(velX * deltaTime, velY * deltaTime);
        if(this.getHp() <= 0 && this.getBody().isActive())
            reset();
    }


}
