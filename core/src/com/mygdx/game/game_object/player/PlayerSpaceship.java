package com.mygdx.game.game_object.player;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.Box2DObject;

import static com.mygdx.game.util.Constants.BIT_ENEMY;
import static com.mygdx.game.util.Constants.BIT_PLAYER;


public class PlayerSpaceship extends Box2DObject {


    private float width = 32;
    private float height = 32;
    private float velX = 0;
    private float velY = 18000;


    public PlayerSpaceship(World world) {
        super(world, 32, 32, 32, 32, 0, 0, 10, 0,
                BodyDef.BodyType.DynamicBody, BIT_PLAYER, BIT_ENEMY, (short) 0, false);
    }

    @Override
    public void update(float deltaTime) {
        //this.body.setLinearVelocity(0, velY * deltaTime * 4);
    }



}
