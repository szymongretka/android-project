package com.mygdx.game.game_object.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.player.PlayerSpaceship;

import static com.mygdx.game.util.Constants.BIT_BULLET;
import static com.mygdx.game.util.Constants.BIT_ENEMY;
import static com.mygdx.game.util.Constants.BIT_PLAYER;

public abstract class Enemy extends Box2DObject {

    protected float onDestroyCoordX;
    protected float onDestroyCoordY;
    protected TextureRegion texture;
    protected float boundaryCoordX;

    public Enemy(World world, float x, float y, float width, float height, int hp, int damage) {
        super(world, x, y, width, height, hp, damage, BodyDef.BodyType.DynamicBody,
                BIT_ENEMY, (short) (BIT_BULLET | BIT_PLAYER), (short) 0, false);
    }

    public void hitPlayer(PlayerSpaceship playerSpaceship) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                playerSpaceship.getBody().setActive(false);
                playerSpaceship.gotShot = true;
                playerSpaceship.setHp(playerSpaceship.getHp() - getDamage());
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        playerSpaceship.gotShot = false;
                        playerSpaceship.getBody().setActive(true);
                    }
                }, 1.0f);
            }
        });
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

    public float getBoundaryCoordX() {
        return boundaryCoordX;
    }

    public void setBoundaryCoordX(float boundaryCoordX) {
        this.boundaryCoordX = boundaryCoordX;
    }

}
