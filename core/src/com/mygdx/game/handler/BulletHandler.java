package com.mygdx.game.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.bullet.EnemyBullet;
import com.mygdx.game.game_object.bullet.enemy_bullet.EnemyBasicBullet;
import com.mygdx.game.game_object.bullet.enemy_bullet.EnemyBomb;
import com.mygdx.game.game_object.bullet.player_bullet.BasicBullet;
import com.mygdx.game.game_object.bullet.player_bullet.RedBullet;
import com.mygdx.game.game_object.pool.GenericPool;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.MessageType;

import static com.mygdx.game.util.Constants.ORANGE_SPACESHIP1_WIDTH;
import static com.mygdx.game.util.Constants.PLAYER_HEIGHT;
import static com.mygdx.game.util.Constants.PLAYER_WIDTH;

public class BulletHandler implements Telegraph {

    private Pool basicBulletPool;
    private Pool redBulletPool;
    private Pool enemyBulletPool;
    private Pool enemyBombPool;
    private Array<Bullet> activeBullets;
    private Array<EnemyBullet> activeEnemyBullets;

    private Array<Bullet> bullets = new Array<>();


    public BulletHandler(GenericPool genericPool, Array<Bullet> activeBullets, Array<EnemyBullet> activeEnemyBullets) {
        this.basicBulletPool = genericPool.getBasicBulletPool();
        this.redBulletPool = genericPool.getRedBulletPool();
        this.enemyBulletPool = genericPool.getEnemyBulletPool();
        this.activeBullets = activeBullets;
        this.enemyBombPool = genericPool.getEnemyBombPool();
        this.activeEnemyBullets = activeEnemyBullets;
    }

    private void prepareBulletsToInit(float x, float y) {

        BasicBullet basicBullet;
        BasicBullet basicBullet2;
        BasicBullet basicBullet3;
        BasicBullet basicBullet4;
        float width = PLAYER_WIDTH / 2f;

        switch (GameScreen.NUMBER_OF_BULLETS) {

            case 1:
                basicBullet = (BasicBullet) basicBulletPool.obtain();
                basicBullet.init(x, y + PLAYER_HEIGHT / 2f, basicBullet.getVelX(), basicBullet.getVelY());
                bullets.add(basicBullet);
                break;
            case 2:
                basicBullet = (BasicBullet) basicBulletPool.obtain();
                basicBullet2 = (BasicBullet) basicBulletPool.obtain();
                basicBullet.init(x - width, y, basicBullet.getVelX(), basicBullet.getVelY());
                basicBullet2.init(x + width, y, basicBullet.getVelX(), basicBullet.getVelY());
                bullets.add(basicBullet, basicBullet2);
                break;
            case 3:
                basicBullet = (BasicBullet) basicBulletPool.obtain();
                basicBullet2 = (BasicBullet) basicBulletPool.obtain();
                basicBullet3 = (BasicBullet) basicBulletPool.obtain();
                basicBullet.init(x - width, y, basicBullet.getVelX(), basicBullet.getVelY());
                basicBullet2.init(x, y + PLAYER_HEIGHT / 2f, basicBullet.getVelX(), basicBullet.getVelY());
                basicBullet3.init(x + width, y, basicBullet.getVelX(), basicBullet.getVelY());
                bullets.add(basicBullet, basicBullet2, basicBullet3);
                break;
            case 4:
                basicBullet = (BasicBullet) basicBulletPool.obtain();
                basicBullet2 = (BasicBullet) basicBulletPool.obtain();
                basicBullet3 = (BasicBullet) basicBulletPool.obtain();
                basicBullet4 = (BasicBullet) basicBulletPool.obtain();
                basicBullet.init(x - width, y, basicBullet.getVelX(), basicBullet.getVelY());
                basicBullet2.init(x - width / 2f, y + PLAYER_HEIGHT / 2f, basicBullet.getVelX(), basicBullet.getVelY());
                basicBullet3.init(x + width / 2f, y + PLAYER_HEIGHT / 2f, basicBullet.getVelX(), basicBullet.getVelY());
                basicBullet4.init(x + width, y, basicBullet.getVelX(), basicBullet.getVelY());
                bullets.add(basicBullet, basicBullet2, basicBullet3, basicBullet4);
                break;
        }
    }

    public void spawnBasicBullets(float x, float y) {


        prepareBulletsToInit(x, y);

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                activeBullets.addAll(bullets);
                GameScreen.lastBulletTime = TimeUtils.nanoTime();
                bullets.clear();
            }
        });


    }

    public void spawnRedBullets(float x, float y) {
        RedBullet redBullet = (RedBullet) redBulletPool.obtain();
        redBullet.init(x, y + PLAYER_HEIGHT / 2f, redBullet.getVelX(), redBullet.getVelY());
        activeBullets.add(redBullet);
        GameScreen.lastBulletTime = TimeUtils.nanoTime();
    }

    public void spawnBossBullets(Object bossCoordsInfo) {
        Vector2 vector2 = (Vector2) bossCoordsInfo;
        EnemyBasicBullet enemyBullet = (EnemyBasicBullet) enemyBulletPool.obtain();
        enemyBullet.init(vector2.x, vector2.y + PLAYER_HEIGHT / 2f, enemyBullet.getVelX(), enemyBullet.getVelY());
        activeEnemyBullets.add(enemyBullet);
    }

    public void spawnEnemyBullets(Object enemyCoordsInfo) {
        Vector2 vector2 = (Vector2) enemyCoordsInfo;
        EnemyBasicBullet enemyBullet = (EnemyBasicBullet) enemyBulletPool.obtain();
        enemyBullet.init(vector2.x, vector2.y + ORANGE_SPACESHIP1_WIDTH / 2f, enemyBullet.getVelX(), enemyBullet.getVelY());
        activeEnemyBullets.add(enemyBullet);
    }

    private void spawnBomb(Object bossCoordsInfo) {
        Vector2 vector2 = (Vector2) bossCoordsInfo;
        EnemyBomb enemyBomb = (EnemyBomb) enemyBombPool.obtain();
        enemyBomb.init(vector2.x, vector2.y + ORANGE_SPACESHIP1_WIDTH / 2f, enemyBomb.getVelX(), enemyBomb.getVelY());
        activeEnemyBullets.add(enemyBomb);
    }


    @Override
    public boolean handleMessage(Telegram msg) {
        if (msg.message == MessageType.BOSS_SHOOT_BULLET) {
            spawnBossBullets(msg.extraInfo);
            return true;
        }
        if (msg.message == MessageType.ENEMY_SHOOT) {
            spawnEnemyBullets(msg.extraInfo);
            return true;
        }
        if (msg.message == MessageType.BOSS_SHOOT_BOMB) {
            spawnBomb(msg.extraInfo);
            return true;
        }

        return false;
    }
}
