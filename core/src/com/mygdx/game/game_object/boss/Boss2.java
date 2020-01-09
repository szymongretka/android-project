package com.mygdx.game.game_object.boss;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.boss.bossStates.Boss2State;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;

import static com.mygdx.game.util.Constants.BOSS2_HEIGHT;
import static com.mygdx.game.util.Constants.BOSS2_WIDTH;

public class Boss2 extends Enemy implements Telegraph {

    private StateMachine<Boss2, Boss2State> stateMachine;
    private PlayerSpaceship player;

    public Boss2(World world, PlayerSpaceship playerSpaceship) {
        super(world, 0, 0, BOSS2_WIDTH, BOSS2_HEIGHT, 50, 5);
        this.texture = new TextureRegion(GameScreen.boss1Image);

        stateMachine = new DefaultStateMachine<>(this, Boss2State.GLOBAL_STATE, Boss2State.GLOBAL_STATE);
        this.velX = 750f;
        this.velY = 0f;
        //setPlayer(playerSpaceship);
    }


    @Override
    public void update(float deltaTime) {


        stateMachine.update();


        if (this.getHp() <= 0 && this.getBody().isActive()) {
            this.onDestroyCoordX = this.getX();
            this.onDestroyCoordY = this.getY();
            reset();
        }
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        return stateMachine.handleMessage(msg);
    }


}
