package com.mygdx.game.handler.spawn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.game_object.boss.Boss1;
import com.mygdx.game.game_object.boss.Boss2;
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
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.game_object.pool.GenericPool;
import com.mygdx.game.handler.WaveImageHandler;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.MessageType;

import java.util.Random;

public class SpawningSystem {

    private final SpaceInvaderApp game;

    private GenericPool genericPool;
    private Array<Enemy> activeEnemiesArray;
    private Array<Obstacle> activeObstacles;
    private WaveImageHandler waveImageHandler;

    private World world;
    private PlayerSpaceship playerSpaceship;
    private Boss1 boss1;
    private Boss2 boss2;

    private int wave1;
    private int wave2;
    private int wave3;

    private TextureRegion wave1Tex;
    private TextureRegion wave2Tex;
    private TextureRegion wave3Tex;
    private TextureRegion wave4Tex;
    private TextureRegion boss2Tex;

    private Random random;

    private MessageManager messageManager;

    public SpawningSystem(final SpaceInvaderApp game, GenericPool genericPool,
                          Array<Enemy> activeEnemiesArray, Array<Obstacle> activeObstacles, GameScreen gameScreen) {
        this.game = game;
        this.genericPool = genericPool;
        this.activeEnemiesArray = activeEnemiesArray;
        this.activeObstacles = activeObstacles;
        this.messageManager = game.messageManager;
        messageManager.addListeners(game.gameScreenManager, MessageType.YOU_WIN_SCREEN, MessageType.YOU_DIED_SCREEN);
        waveImageHandler = GameScreen.waveImageHandler;
        this.world = gameScreen.world;
        this.playerSpaceship = gameScreen.getPlayerSpaceship();

        this.wave1Tex = gameScreen.wave1;
        this.wave2Tex = gameScreen.wave2;
        this.wave3Tex = gameScreen.wave3;
        this.boss2Tex = gameScreen.boss2Image;

    }

    public void spawn() {

        switch (game.gameScreenManager.getActiveScreen()) {

            case LEVEL1:
                firstLevel(activeEnemiesArray);
                break;
            case LEVEL2:
                boss1 = new Boss1(world, playerSpaceship);
                secondLevel(boss1, activeEnemiesArray);
                break;
            case LEVEL3:
                thirdLevel(activeObstacles);
                break;
            case LEVEL4:
                boss2 = new Boss2(world, boss2Tex, playerSpaceship);
                fourthLevel(boss2, activeEnemiesArray);
                break;
            case LEVEL5:
                fifthLevel(activeEnemiesArray, activeObstacles);
                break;

        }

    }

    private void firstLevel(Array<Enemy> activeEnemies) {

        wave1 = 30;
        wave2 = 20;
        wave3 = 40;

        Pool<OrangeSpaceship1> orangeSpaceship1Pool = genericPool.getOrangeSpaceship1Pool();
        Pool<OrangeSpaceship2> orangeSpaceship2Pool = genericPool.getOrangeSpaceship2Pool();
        Pool<OrangeSpaceship3> orangeSpaceship3Pool = genericPool.getOrangeSpaceship3Pool();
        Pool<OrangeSpaceship4> orangeSpaceship4Pool = genericPool.getOrangeSpaceship4Pool();


        new Thread(new Runnable() {
            @Override
            public void run() {

                float screenX = Constants.WIDTH / Constants.PPM;
                float screenY = Constants.HEIGHT / Constants.PPM;

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        // process the result, e.g. add it to an Array<Result> field of the ApplicationListener.
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                waveImageHandler.initWaveImage(wave1Tex);
                            }
                        }, 1);
                        Timer.schedule(new Timer.Task() {
                            float spaceBetweenX = screenX / 10f;
                            float spaceBetweenY = 10f;

                            @Override
                            public void run() {
                                OrangeSpaceship1 orangeSpaceship1;
                                for (int i = 0; i < wave1 / 10; i++) {
                                    for (int j = 0; j < wave1 / 3; j++) {
                                        orangeSpaceship1 = orangeSpaceship1Pool.obtain();
                                        orangeSpaceship1.init(j * spaceBetweenX, screenY + i * spaceBetweenY, orangeSpaceship1.getVelX(), orangeSpaceship1.getVelY());
                                        orangeSpaceship1.setBoundaryCoordX(j * spaceBetweenX);
                                        activeEnemies.add(orangeSpaceship1);
                                    }
                                }
                                game.messageManager.dispatchMessage(MessageType.CLASSIC_SPACE_INVADER_MOVE);
                            }
                        }, 6);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {

                                waveImageHandler.initWaveImage(wave2Tex);

                            }
                        }, 15);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        OrangeSpaceship2 orangeSpaceship2 = orangeSpaceship2Pool.obtain();
                                        OrangeSpaceship2 orangeSpaceship22 = orangeSpaceship2Pool.obtain();
                                        orangeSpaceship2.init(screenX / 2f, screenY * 0.75f, orangeSpaceship2.getVelX(), 0);
                                        orangeSpaceship22.init(screenX / 2f, screenY * 0.75f + 15f, orangeSpaceship2.getVelX(), 0);
                                        orangeSpaceship2.setBoundaryCoordX(screenX / 2f);
                                        orangeSpaceship22.setBoundaryCoordX(screenX / 2f);
                                        activeEnemies.add(orangeSpaceship2);
                                        activeEnemies.add(orangeSpaceship22);

                                    }
                                }, 1, 0.3f, wave2 / 2);

                                game.messageManager.dispatchMessage(MessageType.LEFT_RIGHT_MOVE);
                            }
                        }, 19);

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                waveImageHandler.initWaveImage(wave3Tex);

                            }
                        }, 25);
                        Timer.schedule(new Timer.Task() {
                            float spaceBetweenX = screenX / 10f;
                            float spaceBetweenY = 10f;

                            @Override
                            public void run() {
                                OrangeSpaceship3 orangeSpaceship3;
                                for (int i = 0; i < wave3 / 10; i++) {
                                    for (int j = 0; j < wave3 / 4; j++) {
                                        orangeSpaceship3 = orangeSpaceship3Pool.obtain();
                                        orangeSpaceship3.init(j * spaceBetweenX, screenY + i * spaceBetweenY, orangeSpaceship3.getVelX(), orangeSpaceship3.getVelY());
                                        orangeSpaceship3.setBoundaryCoordX(j * spaceBetweenX);
                                        activeEnemies.add(orangeSpaceship3);
                                    }
                                }
                                game.messageManager.dispatchMessage(MessageType.CLASSIC_SPACE_INVADER_MOVE);
                            }
                        }, 29);


                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                if (activeEnemies.isEmpty())
                                    messageManager.dispatchMessage(MessageType.YOU_WIN_SCREEN);
                            }
                        }, 39, 2f, 200);
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
                                waveImageHandler.initWaveImage(wave1Tex);
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

    private void thirdLevel(Array<Obstacle> activeObstacles) {
        Pool<Meteor1> meteorPool = genericPool.getMeteorPool();

        random = new Random();

        new Thread(new Runnable() {
            @Override
            public void run() {

                float screenX = Constants.WIDTH / Constants.PPM;
                float screenY = Constants.HEIGHT / Constants.PPM;

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                waveImageHandler.initWaveImage(wave1Tex);
                            }
                        }, 1);

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                Meteor1 meteor1 = meteorPool.obtain();
                                meteor1.init(random.nextInt((int) screenX), screenY, meteor1.getVelX(), meteor1.getVelY());
                                activeObstacles.add(meteor1);

                            }
                        }, 6, 0.7f, 40);

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                waveImageHandler.initWaveImage(wave2Tex);
                            }
                        }, 35);

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                Meteor1 meteor1 = meteorPool.obtain();
                                meteor1.init(random.nextInt((int) screenX), screenY, meteor1.getVelX(), meteor1.getVelY());
                                activeObstacles.add(meteor1);
                            }
                        }, 40, 0.6f, 60);

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                if (activeObstacles.isEmpty())
                                    messageManager.dispatchMessage(MessageType.YOU_WIN_SCREEN);
                            }
                        }, 75, 2f, 200);
                    }
                });
            }
        }).start();
    }

    private void fourthLevel(Boss2 boss2, Array<Enemy> activeEnemiesArray) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        waveImageHandler.initWaveImage(wave1Tex);
                    }
                }, 1);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        boss2.init(Constants.WIDTH / Constants.PPM / 2,
                                (Constants.HEIGHT / Constants.PPM - boss2.getHeight()), boss2.getVelX(), boss2.getVelY());
                        activeEnemiesArray.add(boss2);
                    }
                }, 6);
                game.messageManager.dispatchMessage(MessageType.UPPER_BOUND_MOVE);
            }
        });
    }

    private void fifthLevel(Array<Enemy> activeEnemiesArray, Array<Obstacle> activeObstacles) {

        Pool<Meteor1> meteorPool = genericPool.getMeteorPool();
        random = new Random();
        float screenX = Constants.WIDTH / Constants.PPM;
        float screenY = Constants.HEIGHT / Constants.PPM;

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
                                waveImageHandler.initWaveImage(wave1Tex);
                            }
                        }, 1);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        Alien1 alien1 = genericPool.getAlien1Pool().obtain();
                                        alien1.init(50, 150, alien1.getVelX(), alien1.getVelY());
                                        activeEnemiesArray.add(alien1);
                                    }
                                }, 1, 1f, 12);
                                game.messageManager.dispatchMessage(MessageType.CIRCULAR_MOVE);
                            }
                        }, 8);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                Meteor1 meteor1 = meteorPool.obtain();
                                meteor1.init(random.nextInt((int) screenX), screenY, meteor1.getVelX(), meteor1.getVelY());
                                activeObstacles.add(meteor1);

                            }
                        }, 6, 0.7f, 20);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                if(!activeEnemiesArray.isEmpty())
                                    game.messageManager.dispatchMessage(MessageType.GO_BOTTOM_MOVE);
                            }
                        }, 30);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                waveImageHandler.initWaveImage(wave2Tex);
                            }
                        }, 33);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        Alien2 alien2 = genericPool.getAlien2Pool().obtain();
                                        alien2.init(50, 150, alien2.getVelX(), alien2.getVelY());
                                        activeEnemiesArray.add(alien2);
                                    }
                                }, 1, 1f, 15);
                                game.messageManager.dispatchMessage(MessageType.UPPER_BOUND_MOVE);
                            }
                        }, 37);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                if(!activeEnemiesArray.isEmpty())
                                    game.messageManager.dispatchMessage(MessageType.GO_BOTTOM_MOVE);
                            }
                        }, 60);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                waveImageHandler.initWaveImage(wave3Tex);
                            }
                        }, 63);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        Alien3 alien3 = genericPool.getAlien3Pool().obtain();
                                        alien3.init(50, 150, alien3.getVelX(), alien3.getVelY());
                                        activeEnemiesArray.add(alien3);
                                    }
                                }, 1, 1f, 18);
                                game.messageManager.dispatchMessage(MessageType.UPPER_BOUND_MOVE);
                            }
                        }, 68);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                if (activeObstacles.isEmpty())
                                    messageManager.dispatchMessage(MessageType.YOU_WIN_SCREEN);
                            }
                        }, 88, 2f, 20);
                    }
                });
            }
        }).start();
    }

}
