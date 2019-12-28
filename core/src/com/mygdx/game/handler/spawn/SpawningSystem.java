package com.mygdx.game.handler.spawn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.game_object.boss.Boss1;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.enemy.enemies.fraction1.OrangeSpaceship1;
import com.mygdx.game.game_object.enemy.enemies.fraction1.OrangeSpaceship2;
import com.mygdx.game.game_object.enemy.enemies.fraction1.OrangeSpaceship3;
import com.mygdx.game.game_object.enemy.enemies.fraction1.OrangeSpaceship4;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.game_object.pool.GenericPool;
import com.mygdx.game.handler.WaveImageHandler;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.MessageType;

import java.util.Random;

public class SpawningSystem {

    private final MyGdxGame game;
    private GenericPool genericPool;
    private Array array;
    private WaveImageHandler waveImageHandler;
    private World world;
    private PlayerSpaceship playerSpaceship;
    public Boss1 boss1;

    private int wave1;
    private int wave2;
    private int wave3;

    private Random random;

    private MessageManager messageManager;

    public SpawningSystem(final MyGdxGame game, GenericPool genericPool, Array array, GameScreen gameScreen) {
        this.game = game;
        this.genericPool = genericPool;
        this.array = array;
        this.messageManager = game.messageManager;
        messageManager.addListeners(game.gameScreenManager, MessageType.YOU_WIN_SCREEN, MessageType.YOU_DIED_SCREEN);
        waveImageHandler = GameScreen.waveImageHandler;
        this.world = gameScreen.world;
        this.playerSpaceship = gameScreen.getPlayerSpaceship();
    }

    public void spawn() {

        switch (game.gameScreenManager.getActiveScreen()) {

            case LEVEL1:
                firstLevel(array);
                break;
            case LEVEL2:
                boss1 = new Boss1(world, playerSpaceship);
                secondLevel(boss1, array);
                break;
            case LEVEL3:

                break;
            case PAUSE:

                break;

        }

    }


    private void firstLevel(Array<Enemy> activeEnemies) {

        wave1 = 6;
        wave2 = 10;
        wave3 = 20;

        Pool<OrangeSpaceship1> orangeSpaceship1Pool = genericPool.getOrangeSpaceship1Pool();
        Pool<OrangeSpaceship2> orangeSpaceship2Pool = genericPool.getOrangeSpaceship2Pool();
        Pool<OrangeSpaceship3> orangeSpaceship3Pool = genericPool.getOrangeSpaceship3Pool();
        Pool<OrangeSpaceship4> orangeSpaceship4Pool = genericPool.getOrangeSpaceship4Pool();

        random = new Random();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // do something important here, asynchronously to the rendering thread
                // post a Runnable to the rendering thread that processes the result
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        // process the result, e.g. add it to an Array<Result> field of the ApplicationListener.
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                waveImageHandler.initWaveImage(GameScreen.wave1);
                            }
                        }, 1);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                OrangeSpaceship1 orangeSpaceship1;

                                for (int i = 0; i < wave1; i++) {
                                    orangeSpaceship1 = orangeSpaceship1Pool.obtain();
                                    orangeSpaceship1.init(random.nextInt((int) ((int) Constants.WIDTH / Constants.PPM)),
                                            Constants.HEIGHT / Constants.PPM, orangeSpaceship1.getVelX(), orangeSpaceship1.getVelY());
                                    activeEnemies.add(orangeSpaceship1);
                                }

                            }
                        }, 6);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {

                                waveImageHandler.initWaveImage(GameScreen.wave2);

                            }
                        }, 15);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                OrangeSpaceship2 orangeSpaceship2;

                                for (int i = 0; i < wave2; i++) {
                                    orangeSpaceship2 = orangeSpaceship2Pool.obtain();
                                    orangeSpaceship2.init(random.nextInt((int) ((int) Constants.WIDTH / Constants.PPM)),
                                            Constants.HEIGHT / Constants.PPM, orangeSpaceship2.getVelX(), orangeSpaceship2.getVelY());
                                    activeEnemies.add(orangeSpaceship2);
                                }

                            }
                        }, 19);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                waveImageHandler.initWaveImage(GameScreen.wave3);

                            }
                        }, 25);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                OrangeSpaceship3 orangeSpaceship3;
                                for (int i = 0; i < wave3; i++) {
                                    orangeSpaceship3 = orangeSpaceship3Pool.obtain();
                                    orangeSpaceship3.init(random.nextInt((int) ((int) Constants.WIDTH / Constants.PPM)),
                                            Constants.HEIGHT / Constants.PPM, orangeSpaceship3.getVelX(), orangeSpaceship3.getVelY());
                                    activeEnemies.add(orangeSpaceship3);
                                }
                            }
                        }, 29);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                messageManager.dispatchMessage(MessageType.YOU_WIN_SCREEN);
                            }
                        }, 39);
                    }
                });
            }
        }).start();

    }


    private void secondLevel(Boss1 boss1, Array<Enemy> activeEnemies) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // do something important here, asynchronously to the rendering thread
                // post a Runnable to the rendering thread that processes the result
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        // process the result, e.g. add it to an Array<Result> field of the ApplicationListener.
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                waveImageHandler.initWaveImage(GameScreen.wave1);
                            }
                        }, 1);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {

                                boss1.init(Constants.WIDTH / Constants.PPM / 2,
                                        (Constants.HEIGHT / Constants.PPM - boss1.getHeight()), boss1.getVelX(), boss1.getVelY());
                                activeEnemies.add(boss1);
                            }
                        }, 6);
                    }
                });
            }
        }).start();

    }


}
