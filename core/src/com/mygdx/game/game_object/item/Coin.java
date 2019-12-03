package com.mygdx.game.game_object.item;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Coin extends Item {


    public Coin(World world, float x, float y, float width, float height, float velY, float velX, int hp, int damage, BodyDef.BodyType bodyType, short categoryBits, short maskBits, short gIndex) {
        super(world, x, y, width, height, velY, velX, hp, damage, bodyType, categoryBits, maskBits, gIndex);
    }

    @Override
    public void update(float deltaTime) {

    }
}