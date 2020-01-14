package com.mygdx.game.game_object.pool;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.bullet.EnemyBullet;
import com.mygdx.game.game_object.bullet.enemy_bullet.EnemyBasicBullet;
import com.mygdx.game.game_object.bullet.enemy_bullet.EnemyBomb;
import com.mygdx.game.game_object.bullet.player_bullet.BasicBullet;
import com.mygdx.game.game_object.bullet.player_bullet.RedBullet;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.enemy.enemies.fraction1.OrangeSpaceship1;
import com.mygdx.game.game_object.enemy.enemies.fraction1.OrangeSpaceship2;
import com.mygdx.game.game_object.enemy.enemies.fraction1.OrangeSpaceship3;
import com.mygdx.game.game_object.enemy.enemies.fraction1.OrangeSpaceship4;
import com.mygdx.game.game_object.enemy.enemies.fraction2.Alien1;
import com.mygdx.game.game_object.enemy.enemies.fraction2.Alien2;
import com.mygdx.game.game_object.enemy.enemies.fraction2.Alien3;
import com.mygdx.game.game_object.obstacle.Meteor1;
import com.mygdx.game.game_object.obstacle.Obstacle;
import com.mygdx.game.screen.game.GameScreen;

public class GenericPool {

    private World world;
    private GameScreen gameScreen;

    public GenericPool(World world, GameScreen gameScreen) {
        this.world = world;
        this.gameScreen = gameScreen;
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

    private final Pool<Alien1> alien1Pool = new Pool<Alien1>() {
        @Override
        protected Alien1 newObject() {
            return new Alien1(world);
        }
    };

    private final Pool<Alien2> alien2Pool = new Pool<Alien2>() {
        @Override
        protected Alien2 newObject() {
            return new Alien2(world);
        }
    };

    private final Pool<Alien3> alien3Pool = new Pool<Alien3>() {
        @Override
        protected Alien3 newObject() {
            return new Alien3(world);
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

    private final Pool<EnemyBomb> enemyBombPool = new Pool<EnemyBomb>() {
        @Override
        protected EnemyBomb newObject() {
            return new EnemyBomb(world);
        }
    };


    private final Pool<Meteor1> meteorPool = new Pool<Meteor1>() {
        @Override
        protected Meteor1 newObject() {
            if(MathUtils.randomBoolean(0.7f))
                return new Meteor1(world, gameScreen.meteor1);
            else
                return new Meteor1(world, gameScreen.meteor2);
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

    public Pool<Meteor1> getMeteorPool() {
        return meteorPool;
    }

    public Pool<Alien1> getAlien1Pool() {
        return alien1Pool;
    }

    public Pool<Alien2> getAlien2Pool() {
        return alien2Pool;
    }

    public Pool<Alien3> getAlien3Pool() {
        return alien3Pool;
    }

    public Pool<EnemyBomb> getEnemyBombPool() {
        return enemyBombPool;
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
        else if(enemy instanceof Alien1)
            this.alien1Pool.free((Alien1) enemy);
        else if(enemy instanceof Alien2)
            this.alien2Pool.free((Alien2) enemy);
        else if(enemy instanceof Alien3)
            this.alien3Pool.free((Alien3) enemy);

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
        if (enemyBullet instanceof EnemyBomb)
            this.enemyBombPool.free((EnemyBomb) enemyBullet);
    }

    public void freeObstacleFromSpecifiedPool(Obstacle obstacle) {
        if(obstacle instanceof Meteor1)
            this.meteorPool.free((Meteor1) obstacle);
    }





}
