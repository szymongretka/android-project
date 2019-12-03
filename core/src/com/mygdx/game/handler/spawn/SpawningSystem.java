package com.mygdx.game.handler.spawn;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.game_object.enemy.basic_enemy.BasicEnemy;
import com.mygdx.game.util.Constants;

import java.util.Random;

public class SpawningSystem {//} implements Runnable{

    private final MyGdxGame game;
    private Pool pool;
    private Array array;
    //private Thread thread;

    private int wave1;
    private int wave2;
    private int wave3;

    private Random random;


    public SpawningSystem(final MyGdxGame game, Pool pool, Array array) {
        this.game = game;
        this.pool = pool;
        this.array = array;
        //thread = new Thread(this);
        //thread.start();
    }

    public void spawn() {

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

    /*@Override
    public void run() {
        spawn();
    }*/

    private void firstLevel(Pool enemyPool, Array<BasicEnemy> activeEnemies) {

        wave1 = 3;
        wave2 = 20;
        wave3 = 30;

        random = new Random();
        BasicEnemy basicEnemy;
        BasicEnemy basicEnemy2;

        basicEnemy = (BasicEnemy) enemyPool.obtain();
        basicEnemy.init(270, 300);
        activeEnemies.add(basicEnemy);

        basicEnemy2 = (BasicEnemy) enemyPool.obtain();
        basicEnemy2.init(330, 300);
        activeEnemies.add(basicEnemy2);
/*
        try {
            Thread.sleep(1000);
            for (int i = 0; i < wave1; i++) {
                basicEnemy = (BasicEnemy) enemyPool.obtain();
                basicEnemy.init(random.nextInt(Constants.WIDTH), Constants.HEIGHT + 32);
                activeEnemies.add(basicEnemy);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }



}
