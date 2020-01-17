package com.mygdx.game.game_object.bullet;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.obstacle.Obstacle;

import static com.mygdx.game.util.Constants.BIT_BULLET;
import static com.mygdx.game.util.Constants.BIT_ENEMY;
import static com.mygdx.game.util.Constants.BIT_OBSTACLE;

public abstract class Bullet extends Box2DObject {

    protected boolean toDestroy;

    public Bullet(World world, float x, float y, float width, float height, int hp, int damage) {
        super(world, x, y, width, height, hp, damage, BodyDef.BodyType.DynamicBody,
                BIT_BULLET, (short) (BIT_ENEMY | BIT_OBSTACLE), (short) 0, 0f);
    }

    @Override
    public void reset() {
        super.reset();
        this.toDestroy = false;
    }

    public abstract void hitEnemy(Enemy enemy);

    public boolean isToDestroy() {
        return toDestroy;
    }

    public void setToDestroy(boolean toDestroy) {
        this.toDestroy = toDestroy;
    }

    public void hitObstacle(Obstacle obstacle) {
        obstacle.setHp(obstacle.getHp() - this.getDamage());
    }
}
