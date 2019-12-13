package com.mygdx.game.game_object.bullet.basic_bullet;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.enemy.Enemy;

import static com.mygdx.game.util.Constants.BASICBULLETHEIGHT;
import static com.mygdx.game.util.Constants.BASICBULLETWIDTH;

public class BasicBullet extends Bullet {

    private float width = 32;
    private float height = 32;
    private float velX = 0;
    private float velY = 180000;


    public BasicBullet(World world) {
        super(world, 0, 0, BASICBULLETWIDTH, BASICBULLETHEIGHT, 1, 1);
    }

    @Override
    public void update(float deltaTime) {
        this.body.setLinearVelocity(0, velY * deltaTime);
    }

    @Override
    public void hitEnemy(Enemy enemy) {
        enemy.setHp(enemy.getHp() - this.getDamage());
        //reset();
    }

}
