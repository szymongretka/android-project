package com.mygdx.game.spawn;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.game_object.enemy.EnemyBox2D;
import com.mygdx.game.game_object.enemy.pool.EnemyBox2DPool;

public class SpawningSystem<T> {

    private final MyGdxGame game;

    public SpawningSystem(final MyGdxGame game) {
        this.game = game;
    }


    public void spawn(EnemyBox2DPool pool, Array array) {

        switch (game.gameScreenManager.getActiveScreen()) {

            case LEVEL1:
                firstLevel(pool, array);
                break;
            case LEVEL2:

                break;
            case LEVEL3:

                break;
            case PAUSE:

                break;

        }

    }

    private void firstLevel(EnemyBox2DPool enemyPool, Array<EnemyBox2D> activeEnemies) {

        EnemyBox2D basicEnemy = enemyPool.obtain();

        basicEnemy.init(0, 500);
        // add to our array of bullets so we can access them in our render method
        activeEnemies.add(basicEnemy);

    }


}
