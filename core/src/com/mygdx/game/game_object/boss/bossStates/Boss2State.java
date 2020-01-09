package com.mygdx.game.game_object.boss.bossStates;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.game_object.boss.Boss1;
import com.mygdx.game.game_object.boss.Boss2;

import static com.mygdx.game.util.Constants.BASIC_SHIP_HP;

public enum Boss2State implements State<Boss2> {


    GLOBAL_STATE() {
        @Override
        public void update(Boss2 boss2) {


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
