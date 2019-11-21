package com.mygdx.game.methods;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.game_object.bullet.BulletBox2D;
import com.mygdx.game.game_object.bullet.pool.BulletBox2DPool;

public interface SpawnBullets {
    void spawnBullets(BulletBox2DPool bulletBox2DPool, float x, float y,
                      Array<BulletBox2D> activeBullet2D, float delta, Sound shootSound);
}
