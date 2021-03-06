package com.mygdx.game.game_object.enemy.enemies.fraction1;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.MessageType;

import static com.mygdx.game.util.Constants.ORANGE_SPACESHIP3_HEIGHT;
import static com.mygdx.game.util.Constants.ORANGE_SPACESHIP3_WIDTH;
import static com.mygdx.game.util.Constants.PPM;
import static com.mygdx.game.util.Constants.WIDTH;

public class OrangeSpaceship3 extends Enemy {

    private boolean isMovingLeft;
    private boolean isMovingRight;

    public OrangeSpaceship3(World world) {
        super(world, 0, 0, ORANGE_SPACESHIP3_WIDTH, ORANGE_SPACESHIP3_HEIGHT, 3, 1);
        this.texture = GameScreen.fraction1OrangeShip3Texture;
        isMovingLeft = false;
        isMovingRight = true;
        this.velX = 600f;
        this.velY = (-300f);
    }

    @Override
    public void update(float deltaTime) {
        if(this.getHp() <= 0 && this.getBody().isActive()) {
            this.onDestroyCoordX = this.getX();
            this.onDestroyCoordY = this.getY();
            reset();
        }

        if(MathUtils.randomBoolean(0.001f))
            MessageManager.getInstance().dispatchMessage(MessageType.ENEMY_SHOOT, this.getBody().getPosition());
       /* if(!isInLeftBound() && isMovingLeft)
            moveRight(deltaTime);
        else if(!isInRightBound() && isMovingRight)
            moveLeft(deltaTime);*/
    }

    private boolean isInLeftBound() {
        return this.body.getPosition().x > 0;
    }

    private boolean isInRightBound() {
        return this.body.getPosition().x < WIDTH/PPM;
    }

    private void moveLeft(float deltaTime) {
        this.body.setLinearVelocity(-velX * deltaTime, velY * deltaTime);
        isMovingLeft = true;
        isMovingRight = false;
    }
    private void moveRight(float deltaTime) {
        this.body.setLinearVelocity(velX * deltaTime, velY * deltaTime);
        isMovingLeft = false;
        isMovingRight = true;
    }

    @Override
    public void hitPlayer(PlayerSpaceship playerSpaceship) {
        super.hitPlayer(playerSpaceship);
    }
}