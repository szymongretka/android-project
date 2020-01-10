package com.mygdx.game.game_object.enemy.enemies.fraction2;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.MessageType;

import static com.mygdx.game.util.Constants.ORANGE_SPACESHIP1_HEIGHT;
import static com.mygdx.game.util.Constants.ORANGE_SPACESHIP1_WIDTH;

public class Alien3 extends Enemy {

    private float elapsedTime;

    public Alien3(World world) {
        super(world, 0, 0, ORANGE_SPACESHIP1_WIDTH, ORANGE_SPACESHIP1_HEIGHT, 10, 2);
        this.texture = GameScreen.alien3Texture;

        this.velX = 1700f;
        this.velY = (-1700f);
    }

    @Override
    public void update(float deltaTime) {
        elapsedTime += deltaTime;
        if (this.getHp() <= 0 && this.getBody().isActive()) {
            this.onDestroyCoordX = this.getX();
            this.onDestroyCoordY = this.getY();
            reset();
        }
        if(elapsedTime > 1.0f) {
            MessageManager.getInstance().dispatchMessage(MessageType.ENEMY_SHOOT, this.getBody().getPosition());
            elapsedTime = 0;
        }
    }
}