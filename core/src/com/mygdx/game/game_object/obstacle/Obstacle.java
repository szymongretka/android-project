package com.mygdx.game.game_object.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.player.PlayerSpaceship;

import static com.mygdx.game.util.Constants.BIT_BULLET;
import static com.mygdx.game.util.Constants.BIT_OBSTACLE;
import static com.mygdx.game.util.Constants.BIT_PLAYER;

public abstract class Obstacle extends Box2DObject {


    protected float onDestroyCoordX;
    protected float onDestroyCoordY;
    protected TextureRegion texture;
    protected boolean toDestroy;

    public Obstacle(World world, float x, float y, float width, float height, int hp, int damage) {
        super(world, x, y, width, height, hp, damage,BodyDef.BodyType.DynamicBody,
                BIT_OBSTACLE, (short) (BIT_OBSTACLE | BIT_PLAYER | BIT_BULLET), (short) 0, false);
    }


    public void hitPlayer(PlayerSpaceship playerSpaceship) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                setOnDestroyCoordX(getX());
                setOnDestroyCoordY(getY());
                playerSpaceship.gotShot = true;
                playerSpaceship.setHp(playerSpaceship.getHp() - getDamage());
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        playerSpaceship.gotShot = false;
                    }
                }, 1);
            }
        });

    }

    @Override
    public void reset() {
        super.reset();
        this.toDestroy = false;
    }

    public float getOnDestroyCoordX() {
        return onDestroyCoordX;
    }

    public float getOnDestroyCoordY() {
        return onDestroyCoordY;
    }

    public void setOnDestroyCoordX(float onDestroyCoordX) {
        this.onDestroyCoordX = onDestroyCoordX;
    }

    public void setOnDestroyCoordY(float onDestroyCoordY) {
        this.onDestroyCoordY = onDestroyCoordY;
    }

    public boolean isToDestroy() {
        return toDestroy;
    }

    public void setToDestroy(boolean toDestroy) {
        this.toDestroy = toDestroy;
    }

    public TextureRegion getTexture() {
        return texture;
    }

}
