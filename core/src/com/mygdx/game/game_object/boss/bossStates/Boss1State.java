package com.mygdx.game.game_object.boss.bossStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.game_object.boss.Boss1;
import com.mygdx.game.util.MessageType;

import static com.mygdx.game.util.Constants.BASIC_SHIP_HP;

public enum Boss1State implements State<Boss1> {


    MOVE() {
        @Override
        public void enter(Boss1 boss1) {
            talk(boss1, "ENTERING MOVE STATE");
        }

        @Override
        public void update(Boss1 boss1) {
            if (!boss1.isInLeftBound() && boss1.isMovingLeft()) {
                boss1.moveRight(Gdx.graphics.getDeltaTime());
            } else if (!boss1.isInRightBound() && boss1.isMovingRight()) {
                boss1.moveLeft(Gdx.graphics.getDeltaTime());
            }
            boss1.getStateMachine().revertToPreviousState();
        }

        @Override
        public void exit(Boss1 boss1) {
            talk(boss1, "exit MOVE STATE");
        }

    },


    GLOBAL_STATE() {
        @Override
        public void update(Boss1 boss1) {
            if (!boss1.isInBounds())
                boss1.getStateMachine().changeState(MOVE);
            else if (MathUtils.randomBoolean(0.5f) && boss1.mana >= 250)
                boss1.getStateMachine().changeState(ATTACK);
            else if (MathUtils.randomBoolean(0.8f) && boss1.getPlayer().getHp() < BASIC_SHIP_HP / 2 && boss1.mana >= 250)
                boss1.getStateMachine().changeState(RAM_PLAYER);

        }

    },

    ATTACK() {
        @Override
        public void update(Boss1 boss1) {
            //shoot bullet
            talk(boss1, "SHOOYINH");
            MessageManager.getInstance().dispatchMessage(MessageType.BOSS_SHOOT_BULLET, boss1.getBody().getPosition());
            boss1.getStateMachine().revertToPreviousState();
        }

        @Override
        public void exit(Boss1 boss1) {
            boss1.mana = 0;
        }

    },

    RAM_PLAYER() {
        @Override
        public void enter(Boss1 boss1) {

        }

        @Override
        public void update(Boss1 boss1) {
            talk(boss1, "ramming player");
            boss1.getStateMachine().revertToPreviousState();
            //boss1Image.getStateMachine().changeState();
        }

        @Override
        public void exit(Boss1 boss1) {
            boss1.mana = 0;
        }

        @Override
        public boolean onMessage(Boss1 entity, Telegram telegram) {
            return false;
        }
    },

    SPIN() {
        @Override
        public void enter(Boss1 boss1) {

        }

        @Override
        public void update(Boss1 boss1) {


            //boss1Image.getStateMachine().changeState();
        }

        @Override
        public void exit(Boss1 boss1) {

        }

        @Override
        public boolean onMessage(Boss1 entity, Telegram telegram) {
            return false;
        }
    };

    @Override
    public void enter(Boss1 boss1) {
    }

    @Override
    public void update(Boss1 boss1) {
    }

    @Override
    public void exit(Boss1 boss1) {
    }

    protected void talk(Boss1 boss1, String msg) {
        GdxAI.getLogger().info(boss1.getClass().getSimpleName(), msg);
    }

    @Override
    public boolean onMessage(Boss1 entity, Telegram telegram) {
        return false;
    }


}
