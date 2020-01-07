package com.mygdx.game.game_object.obstacle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.player.PlayerSpaceship;

import static com.mygdx.game.util.Constants.BIT_OBSTACLE;
import static com.mygdx.game.util.Constants.BIT_PLAYER;

public abstract class Obstacle extends Box2DObject {


    protected float onDestroyCoordX;
    protected float onDestroyCoordY;
    protected TextureRegion texture;

    public Obstacle(World world, float x, float y, float width, float height, int hp, int damage) {
        super(world, x, y, width, height, hp, damage,BodyDef.BodyType.DynamicBody,
                BIT_OBSTACLE, (short) (BIT_OBSTACLE | BIT_PLAYER), (short) 0, false);
    }



    public void hitPlayer(PlayerSpaceship playerSpaceship) {
        this.reset();
        playerSpaceship.gotShot = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                playerSpaceship.gotShot = false;
            }
        }, 1);
    }

    public float getOnDestroyCoordX() {
        return onDestroyCoordX;
    }

    public float getOnDestroyCoordY() {
        return onDestroyCoordY;
    }

    public TextureRegion getTexture() {
        return texture;
    }

}
