package com.mygdx.game.game_object.bullet;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.player.PlayerSpaceship;

import static com.mygdx.game.util.Constants.BIT_ENEMY_BULLET;
import static com.mygdx.game.util.Constants.BIT_PLAYER;

public abstract class EnemyBullet extends Box2DObject {

    protected boolean toDestroy;
    protected TextureRegion texture;

    public EnemyBullet(World world, float x, float y, float width, float height, int hp, int damage, boolean isBullet) {
        super(world, x, y, width, height, hp, damage, BodyDef.BodyType.DynamicBody,
                BIT_ENEMY_BULLET, BIT_PLAYER, (short) 0, isBullet);
    }

    @Override
    public void reset() {
        super.reset();
        this.toDestroy = false;
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

    public boolean isToDestroy() {
        return toDestroy;
    }

    public void setToDestroy(boolean toDestroy) {
        this.toDestroy = toDestroy;
    }

    public abstract TextureRegion getFrame(float delta);

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }
}