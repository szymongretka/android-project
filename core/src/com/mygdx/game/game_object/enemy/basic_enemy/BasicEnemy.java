package com.mygdx.game.game_object.enemy.basic_enemy;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.enemy.Enemy;


public class BasicEnemy extends Enemy {


    private float width = 32;
    private float height = 32;
    private float velX = 500;
    private float velY = 500;


    public BasicEnemy(World world) {
        super(world, 0, 0, 32, 32, 50, 50, 3, 1);
    }

    @Override
    public void update(float deltaTime) {
        this.body.setLinearVelocity(-velX * deltaTime, -velY * deltaTime);
        if(this.getHp() <= 0 && this.getBody().isActive()) {
            this.onDestroyCoordX = this.getX();
            this.onDestroyCoordY = this.getY();
            reset();
        }
    }

}
