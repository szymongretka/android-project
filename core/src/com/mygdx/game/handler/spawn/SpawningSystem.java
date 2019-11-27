package com.mygdx.game.handler.spawn;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.game_object.enemy.basic_enemy.BasicEnemy;

public class SpawningSystem<T> {

    private final MyGdxGame game;

    public SpawningSystem(final MyGdxGame game) {
        this.game = game;
    }


    public void spawn(Pool pool, Array array) {

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

    private void firstLevel(Pool enemyPool, Array<BasicEnemy> activeEnemies) {
        BasicEnemy basicEnemy = (BasicEnemy) enemyPool.obtain();
        basicEnemy.init(0, 500);
        activeEnemies.add(basicEnemy);
    }


}
