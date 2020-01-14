package com.mygdx.game.game_object.boss.bossStates;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_object.boss.Boss2;
import com.mygdx.game.util.MessageType;

public enum Boss2State implements State<Boss2> {


    GLOBAL_STATE() {
        @Override
        public void update(Boss2 boss2) {
            if (MathUtils.randomBoolean(0.5f) && boss2.getPlayer().hasLessThanHalfHp() && boss2.mana >= 150)
                boss2.getStateMachine().changeState(RAM_PLAYER);
            else if (MathUtils.randomBoolean(0.5f) && boss2.mana >= 150)
                boss2.getStateMachine().changeState(ATTACK);
            else if (MathUtils.randomBoolean(0.5f) && boss2.mana >= 150)
                boss2.getStateMachine().changeState(SHOOT_BOMB);

        }

    },

    SHOOT_BOMB() {
        @Override
        public void update(Boss2 boss2) {
            System.out.println("shoot bomb");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    MessageManager.getInstance().dispatchMessage(MessageType.BOSS_SHOOT_BOMB, boss2.getBody().getPosition());
                }
            }, 1);
            boss2.getStateMachine().changeState(GLOBAL_STATE);
        }

        @Override
        public void exit(Boss2 boss2) {
            boss2.mana = 0;
            MessageManager.getInstance().dispatchMessage(MessageType.UPPER_BOUND_MOVE);
        }

    },

    ATTACK() {
        @Override
        public void update(Boss2 boss2) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    MessageManager.getInstance().dispatchMessage(MessageType.BOSS_SHOOT_BULLET, boss2.getBody().getPosition());
                }
            }, 1, 0.3f, 2);
            boss2.getStateMachine().changeState(GLOBAL_STATE);
        }

        @Override
        public void exit(Boss2 boss2) {
            boss2.mana = 0;
            MessageManager.getInstance().dispatchMessage(MessageType.UPPER_BOUND_MOVE);
        }

    },
    RAM_PLAYER() {

        float playerPosX;
        float playerPosY;

        @Override
        public void enter(Boss2 boss2) {
            playerPosX = boss2.getPlayer().getBody().getPosition().x;
            playerPosY = boss2.getPlayer().getBody().getPosition().y;

            MessageManager.getInstance().dispatchMessage(MessageType.RAM_MOVE, new Vector2[]{
                    new Vector2(playerPosX, playerPosY), new Vector2(boss2.getBody().getPosition())});
        }

        @Override
        public void update(Boss2 boss2) {

            if (boss2.ramComplete) {
                MessageManager.getInstance().dispatchMessage(MessageType.UPPER_BOUND_MOVE);
                boss2.getStateMachine().changeState(GLOBAL_STATE);
            }
            //boss2.mana = 0;
        }

        @Override
        public void exit(Boss2 boss2) {
            boss2.mana = 0;
            boss2.ramComplete = false;
        }

    };

    @Override
    public void enter(Boss2 entity) {

    }

    @Override
    public void update(Boss2 entity) {

    }

    @Override
    public void exit(Boss2 entity) {

    }

    @Override
    public boolean onMessage(Boss2 entity, Telegram telegram) {
        return false;
    }
}
