package com.mygdx.game.game_object.bullet;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.player.PlayerSpaceship;

import static com.mygdx.game.util.Constants.BIT_ENEMY_BULLET;
import static com.mygdx.game.util.Constants.BIT_PLAYER;

public abstract class EnemyBullet extends Box2DObject {

    protected boolean toDestroy;

    public EnemyBullet(World world, float x, float y, float width, float height, int hp, int damage) {
        super(world, x, y, width, height, hp, damage, BodyDef.BodyType.DynamicBody,
                BIT_ENEMY_BULLET, BIT_PLAYER, (short) 0, true);
    }

    @Override
    public void reset() {
        super.reset();
        this.toDestroy = false;
    }

    public abstract void hitPlayer(PlayerSpaceship playerSpaceship);

    public boolean isToDestroy() {
        return toDestroy;
    }

    public void setToDestroy(boolean toDestroy) {
        this.toDestroy = toDestroy;
    }
}