package com.mygdx.game.game_object.item.bonus;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.item.Item;

import static com.mygdx.game.util.Constants.ITEM_HEIGHT;
import static com.mygdx.game.util.Constants.ITEM_WIDTH;


public class RevertMovement extends Item {

    public RevertMovement(World world) {
        super(world, 0, 0, ITEM_WIDTH, ITEM_HEIGHT);
    }

    @Override
    public void update(float deltaTime) {

    }
}
