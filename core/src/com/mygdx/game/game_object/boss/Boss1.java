package com.mygdx.game.game_object.boss;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.enemy.Enemy;

import static com.mygdx.game.util.Constants.BOSS1_HEIGHT;
import static com.mygdx.game.util.Constants.BOSS1_WIDTH;

public class Boss1 extends Enemy implements Telegraph {

    public Boss1(World world) {
        super(world, 0, 0, BOSS1_WIDTH, BOSS1_HEIGHT, 50, 5);

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public boolean handleMessage(Telegram msg) {

        return false;
    }
}
