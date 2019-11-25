package com.mygdx.game.game_object.bullet;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.enemy.EnemyBox2D;

import static com.mygdx.game.util.Constants.BIT_BULLET;
import static com.mygdx.game.util.Constants.BIT_ENEMY;

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

}
