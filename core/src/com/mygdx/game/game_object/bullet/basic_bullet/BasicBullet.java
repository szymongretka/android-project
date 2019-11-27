package com.mygdx.game.game_object.bullet.basic_bullet;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.enemy.basic_enemy.BasicEnemy;

public class BasicBullet extends Bullet {

    private float width = 32;
    private float height = 32;
    private float velX = 0;
    private float velY = 18000;


    public BasicBullet(World world) {
        super(world, 0, 0, 32, 32, 200000, 0, 1, 1);
    }

    @Override
    public void update(float deltaTime) {
        this.body.setLinearVelocity(0, velY * deltaTime * 4);
    }


    public void hitEnemy(BasicEnemy basicEnemy) {
        basicEnemy.setHp(basicEnemy.getHp() - this.getDamage());
        reset();
    }

    @Override
    public void hitEnemy(Enemy enemy) {

    }
}
