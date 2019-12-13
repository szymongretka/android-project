package com.mygdx.game.game_object.item;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.Box2DObject;
import static com.mygdx.game.util.Constants.BIT_ITEM;
import static com.mygdx.game.util.Constants.BIT_PLAYER;

public abstract class Item extends Box2DObject {

    public Item(World world, float x, float y, float width, float height) {
        super(world, x, y, width, height, 0, 0, BodyDef.BodyType.DynamicBody,
                BIT_ITEM, BIT_PLAYER, (short) 0, false);
    }


}
