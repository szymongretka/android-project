package com.mygdx.game.game_object.enemy;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.Box2DObject;

import static com.mygdx.game.util.Constants.BIT_BULLET;
import static com.mygdx.game.util.Constants.BIT_ENEMY;
import static com.mygdx.game.util.Constants.BIT_PLAYER;

public abstract class Enemy extends Box2DObject {

    protected float onDestroyCoordX;
    protected float onDestroyCoordY;

    public Enemy(World world, float x, float y, float width, float height, int hp, int damage) {
        super(world, x, y, width, height, hp, damage, BodyDef.BodyType.DynamicBody,
                BIT_ENEMY, (short) (BIT_BULLET | BIT_PLAYER), (short) 0, false);
    }

    public float getOnDestroyCoordX() {
        return onDestroyCoordX;
    }

    public float getOnDestroyCoordY() {
        return onDestroyCoordY;
    }
}
