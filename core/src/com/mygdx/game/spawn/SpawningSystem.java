package com.mygdx.game.spawn;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.enums.Constants;
import com.mygdx.game.game_object.enemy.BasicEnemy;
import com.mygdx.game.game_object.enemy.pool.BasicEnemyPool;

public class SpawningSystem<T> {

    private final MyGdxGame game;

    public SpawningSystem(final MyGdxGame game) {
        this.game = game;
    }


    public void spawn(BasicEnemyPool pool, Array array) {

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

    private void firstLevel(BasicEnemyPool enemyPool, Array<BasicEnemy> activeEnemies) {

        BasicEnemy basicEnemy = (BasicEnemy) enemyPool.obtain();

        basicEnemy.init(100, 100);
        // add to our array of bullets so we can access them in our render method
        activeEnemies.add(basicEnemy);
        System.out.println(enemyPool.getFree());

    }


}
