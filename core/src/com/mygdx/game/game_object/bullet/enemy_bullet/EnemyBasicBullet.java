package com.mygdx.game.game_object.bullet.enemy_bullet;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.bullet.EnemyBullet;
import com.mygdx.game.game_object.player.PlayerSpaceship;

import static com.mygdx.game.util.Constants.BASICBULLETHEIGHT;
import static com.mygdx.game.util.Constants.BASICBULLETWIDTH;

public class EnemyBasicBullet extends EnemyBullet {


    public EnemyBasicBullet(World world) {
        super(world, 0, 0, BASICBULLETWIDTH, BASICBULLETHEIGHT, 1, 6);
        this.velX = 0f;
        this.velY = (-7000f);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void hitPlayer(PlayerSpaceship playerSpaceship) {
        super.hitPlayer(playerSpaceship);
        playerSpaceship.setHp(playerSpaceship.getHp() - this.damage);
    }

}