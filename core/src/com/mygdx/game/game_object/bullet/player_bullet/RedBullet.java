package com.mygdx.game.game_object.bullet.player_bullet;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.enemy.Enemy;

import static com.mygdx.game.util.Constants.BASICBULLETHEIGHT;
import static com.mygdx.game.util.Constants.BASICBULLETWIDTH;

public class RedBullet extends Bullet {

    public RedBullet(World world) {
        super(world, 0, 0, BASICBULLETWIDTH, BASICBULLETHEIGHT, 1, 3);
        this.velX = 0f;
        this.velY = 12000f;
        //this.body.setLinearVelocity(0, velY);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void hitEnemy(Enemy enemy) {
        enemy.setHp(enemy.getHp() - this.getDamage());
        //reset();
    }

}