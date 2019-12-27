package com.mygdx.game.game_object.boss;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.boss.bossStates.Boss1State;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;

import static com.mygdx.game.util.Constants.BOSS1_HEIGHT;
import static com.mygdx.game.util.Constants.BOSS1_WIDTH;
import static com.mygdx.game.util.Constants.PPM;
import static com.mygdx.game.util.Constants.WIDTH;

public class Boss1 extends Enemy implements Telegraph {

    private boolean moveLeft;
    private boolean moveRight;
    public int mana = 0;

    private StateMachine<Boss1, Boss1State> stateMachine;
    private PlayerSpaceship player;

    public Boss1(World world, PlayerSpaceship playerSpaceship) {
        super(world, 0, 0, BOSS1_WIDTH, BOSS1_HEIGHT, 500, 5);
        this.texture = new TextureRegion(GameScreen.boss1Image);
        moveLeft = false;
        moveRight = true;
        stateMachine = new DefaultStateMachine<>(this, Boss1State.GLOBAL_STATE, Boss1State.GLOBAL_STATE);
        this.velX = 750f;
        this.velY = 0f;
        setPlayer(playerSpaceship);
    }

    @Override
    public void update(float deltaTime) {

        stateMachine.update();
        mana += 1;

        if(this.getHp() <= 0 && this.getBody().isActive()) {
            this.onDestroyCoordX = this.getX();
            this.onDestroyCoordY = this.getY();
            reset();
        }
    }

    public boolean isInLeftBound() {
        return this.body.getPosition().x > 0;
    }

    public boolean isInRightBound() {
        return this.body.getPosition().x < WIDTH/PPM;
    }

    public boolean isInBounds() {
        return body.getPosition().x > 0 && body.getPosition().x < WIDTH/PPM;
    }

    public void moveLeft(float deltaTime) {
        this.body.setLinearVelocity(-velX * deltaTime, velY * deltaTime);
        moveLeft = true;
        moveRight = false;
    }
    public void moveRight(float deltaTime) {
        this.body.setLinearVelocity(velX * deltaTime, velY * deltaTime);
        moveLeft = false;
        moveRight = true;
    }

    public StateMachine<Boss1, Boss1State> getStateMachine () {
        return stateMachine;
    }

    public PlayerSpaceship getPlayer () {
        return player;
    }

    public void setPlayer (PlayerSpaceship player) {
        this.player = player;
    }

    public boolean isMovingLeft() {
        return moveLeft;
    }

    public boolean isMovingRight() {
        return moveRight;
    }

    public void spawnBullets() {

    }

    @Override
    public void hitPlayer(PlayerSpaceship playerSpaceship) {
        playerSpaceship.setHp(playerSpaceship.getHp() - 6);
        System.out.println("hp: " + playerSpaceship.getHp());
    }


    @Override
    public boolean handleMessage (Telegram msg) {
        return stateMachine.handleMessage(msg);
    }



}
