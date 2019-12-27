package com.mygdx.game.game_object.pool;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.bullet.EnemyBullet;
import com.mygdx.game.game_object.bullet.enemy_bullet.EnemyBasicBullet;
import com.mygdx.game.game_object.bullet.player_bullet.BasicBullet;
import com.mygdx.game.game_object.bullet.player_bullet.RedBullet;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.enemy.enemies.fraction1.OrangeSpaceship1;
import com.mygdx.game.game_object.enemy.enemies.fraction1.OrangeSpaceship2;
import com.mygdx.game.game_object.enemy.enemies.fraction1.OrangeSpaceship3;
import com.mygdx.game.game_object.enemy.enemies.fraction1.OrangeSpaceship4;

public class GenericPool {

    private World world;

    public GenericPool(World world) {
        this.world = world;
    }

    private final Pool<OrangeSpaceship1> orangeSpaceship1Pool = new Pool<OrangeSpaceship1>() {
        @Override
        protected OrangeSpaceship1 newObject() {
            return new OrangeSpaceship1(world);
        }
    };

    private final Pool<OrangeSpaceship2> orangeSpaceship2Pool = new Pool<OrangeSpaceship2>() {
        @Override
        protected OrangeSpaceship2 newObject() {
            return new OrangeSpaceship2(world);
        }
    };

    private final Pool<OrangeSpaceship3> orangeSpaceship3Pool = new Pool<OrangeSpaceship3>() {
        @Override
        protected OrangeSpaceship3 newObject() {
            return new OrangeSpaceship3(world);
        }
    };

    private final Pool<OrangeSpaceship4> orangeSpaceship4Pool = new Pool<OrangeSpaceship4>() {
        @Override
        protected OrangeSpaceship4 newObject() {
            return new OrangeSpaceship4(world);
        }
    };


    private final Pool<BasicBullet> basicBulletPool = new Pool<BasicBullet>() {
        @Override
        protected BasicBullet newObject() {
            return new BasicBullet(world);
        }
    };

    private final Pool<EnemyBasicBullet> enemyBulletPool = new Pool<EnemyBasicBullet>() {
        @Override
        protected EnemyBasicBullet newObject() {
            return new EnemyBasicBullet(world);
        }
    };

    private final Pool<RedBullet> redBulletPool = new Pool<RedBullet>() {
        @Override
        protected RedBullet newObject() {
            return new RedBullet(world);
        }
    };


    public Pool<BasicBullet> getBasicBulletPool() {
        return basicBulletPool;
    }

    public Pool<OrangeSpaceship1> getOrangeSpaceship1Pool() {
        return orangeSpaceship1Pool;
    }

    public Pool<OrangeSpaceship2> getOrangeSpaceship2Pool() {
        return orangeSpaceship2Pool;
    }

    public Pool<OrangeSpaceship3> getOrangeSpaceship3Pool() {
        return orangeSpaceship3Pool;
    }

    public Pool<OrangeSpaceship4> getOrangeSpaceship4Pool() {
        return orangeSpaceship4Pool;
    }

    public Pool<RedBullet> getRedBulletPool() {
        return redBulletPool;
    }

    public Pool<EnemyBasicBullet> getEnemyBulletPool() {
        return enemyBulletPool;
    }

    public void freeEnemyFromSpecifiedPool(Enemy enemy) {
        if (enemy instanceof OrangeSpaceship1)
            this.orangeSpaceship1Pool.free((OrangeSpaceship1) enemy);
        else if (enemy instanceof OrangeSpaceship2)
            this.orangeSpaceship2Pool.free((OrangeSpaceship2) enemy);
        else if (enemy instanceof OrangeSpaceship3)
            this.orangeSpaceship3Pool.free((OrangeSpaceship3) enemy);
        else if (enemy instanceof OrangeSpaceship4)
            this.orangeSpaceship4Pool.free((OrangeSpaceship4) enemy);

    }

    public void freeBulletFromSpecifiedPool(Bullet bullet) {
        if (bullet instanceof BasicBullet)
            this.basicBulletPool.free((BasicBullet) bullet);
        else if (bullet instanceof RedBullet)
            this.redBulletPool.free((RedBullet) bullet);
    }

    public void freeEnemyBulletFromSpecifiedPool(EnemyBullet enemyBullet) {
        if (enemyBullet instanceof EnemyBasicBullet)
            this.enemyBulletPool.free((EnemyBasicBullet) enemyBullet);

    }





}
