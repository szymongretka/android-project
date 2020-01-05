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
import com.mygdx.game.game_object.bullet.player_bullet.BasicBullet;
import com.mygdx.game.game_object.bullet.player_bullet.RedBullet;
import com.mygdx.game.game_object.pool.GenericPool;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.MessageType;

import static com.mygdx.game.util.Constants.PLAYER_HEIGHT;

public class BulletHandler implements Telegraph {

    private Pool basicBulletPool;
    private Pool redBulletPool;
    private Pool enemyBulletPool;
    private Array<Bullet> activeBullets;
    private Array<EnemyBullet> activeEnemyBullets;


    public BulletHandler(GenericPool genericPool, Array<Bullet> activeBullets, Array<EnemyBullet> activeEnemyBullets) {
        this.basicBulletPool = genericPool.getBasicBulletPool();
        this.redBulletPool = genericPool.getRedBulletPool();
        this.enemyBulletPool = genericPool.getEnemyBulletPool();
        this.activeBullets = activeBullets;
        this.activeEnemyBullets = activeEnemyBullets;
    }

    public void spawnBasicBullets(float x, float y) {
        /** Returns an object from this pool. The object may be new (from {@link #newObject()}) or reused (previously
         * {@link #free(Object) freed}). */
        // get a bullet from pool

        new Thread(new Runnable() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 1; i <= GameScreen.NUMBER_OF_BULLETS; i++) {
                            BasicBullet basicBullet = (BasicBullet) basicBulletPool.obtain();
                            basicBullet.init(x + i, y + PLAYER_HEIGHT / 2f, basicBullet.getVelX(), basicBullet.getVelY());
                            // add to our array of bullets so we can access them in our render method
                            activeBullets.add(basicBullet);
                        }
                        GameScreen.lastBulletTime = TimeUtils.nanoTime();
                    }
                });
            }
        }).start();

    }

    public void spawnRedBullets(float x, float y) {
        RedBullet redBullet = (RedBullet) redBulletPool.obtain();
        redBullet.init(x, y + PLAYER_HEIGHT/2f, redBullet.getVelX(), redBullet.getVelY());
        activeBullets.add(redBullet);
        GameScreen.lastBulletTime = TimeUtils.nanoTime();
    }

    public void spawnBossBullets(Object bossCoordsInfo) {
        Vector2 vector2 = (Vector2) bossCoordsInfo;
        EnemyBasicBullet enemyBullet = (EnemyBasicBullet) enemyBulletPool.obtain();
        enemyBullet.init(vector2.x, vector2.y + PLAYER_HEIGHT/2f, enemyBullet.getVelX(), enemyBullet.getVelY());
        activeEnemyBullets.add(enemyBullet);
    }


    @Override
    public boolean handleMessage(Telegram msg) {
        if(msg.message == MessageType.BOSS_SHOOT_BULLET) {
            spawnBossBullets(msg.extraInfo);
            return true;
        }

        return false;
    }
}
