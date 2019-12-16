package com.mygdx.game.game_object.enemy.basic_enemy;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.Constants;

import static com.mygdx.game.util.Constants.BASIC_ENEMY_WIDTH;
import static com.mygdx.game.util.Constants.BASIC_ENEMY_HEIGHT;


public class BasicEnemy extends Enemy {


    private float width = 32;
    private float height = 32;
    private float velX = 500;
    private float velY = 500;


    public BasicEnemy(World world) {
        super(world, 0, 0, BASIC_ENEMY_WIDTH, BASIC_ENEMY_HEIGHT, 3, 1);
        this.texture = GameScreen.basicEnemyTexture;
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
