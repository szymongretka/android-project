package com.mygdx.game.game_object.enemy.basic_enemy;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.util.Constants;


public class BasicEnemy extends Enemy {


    private float width = 32;
    private float height = 32;
    private float velX = 500;
    private float velY = 500;


    public BasicEnemy(World world) {
        super(world, 0, 0, 32, 32, 2500, 2500, 3, 1);
    }

    @Override
    public void update(float deltaTime) {
        this.body.setLinearVelocity(-velX * deltaTime, -velY * deltaTime);
        if(this.getHp() <= 0 && this.getBody().isActive()) {
            this.onDestroyCoordX = this.getX();
            this.onDestroyCoordY = this.getY();
            reset();
        }
        if(this.getBody().getPosition().x <= this.getWidth() ||
                this.getBody().getPosition().x >= Constants.WIDTH - this.getWidth())
            velX = -velX;

    }

}
