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


    GLOBAL_STATE() {
        @Override
        public void update(Boss1 boss1) {
            if (MathUtils.randomBoolean(0.5f) && boss1.mana >= 250)
                boss1.getStateMachine().changeState(ATTACK);
            if (MathUtils.randomBoolean(0.8f) && boss1.getPlayer().getHp() < BASIC_SHIP_HP / 2 && boss1.mana >= 250)
                boss1.getStateMachine().changeState(RAM_PLAYER);

        }

    },

    ATTACK() {
        @Override
        public void update(Boss1 boss1) {
            MessageManager.getInstance().dispatchMessage(MessageType.BOSS_SHOOT_BULLET, boss1.getBody().getPosition());
            boss1.getStateMachine().changeState(GLOBAL_STATE);
        }

        @Override
        public void exit(Boss1 boss1) {
            boss1.mana = 0;
        }

    },

    RAM_PLAYER() {

        float playerPosX;
        float playerPosY;
        float tempBossPosX;
        float tempBossPosY;

        @Override
        public void enter(Boss1 boss1) {

            playerPosX = boss1.getPlayer().getBody().getPosition().x;
            playerPosY = boss1.getPlayer().getBody().getPosition().y;

            tempBossPosX = boss1.getBody().getPosition().x;
            tempBossPosY = boss1.getBody().getPosition().y;

            boss1.ramPlayer(playerPosX, playerPosY);

        }

        @Override
        public void update(Boss1 boss1) {


            if (boss1.rammed && Math.abs(boss1.getBody().getPosition().y - playerPosY) < 4) {
                boss1.moveToDirection(tempBossPosX, tempBossPosY);
                boss1.rammed = false;
            }
            if (Math.abs(boss1.getBody().getPosition().y - tempBossPosY) < 4 && !boss1.rammed) {
                boss1.stop();
                boss1.getBody().setLinearVelocity(boss1.getVelX() * Gdx.graphics.getDeltaTime(), 0);
                boss1.getStateMachine().changeState(GLOBAL_STATE);
            }
            boss1.mana = 0;
        }

        @Override
        public void exit(Boss1 boss1) {
            boss1.mana = 0;
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
