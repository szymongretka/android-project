package com.mygdx.game.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.bullet.EnemyBullet;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.item.Item;
import com.mygdx.game.game_object.item.bonus.AddHealth;
import com.mygdx.game.game_object.item.bonus.NumberOfBulletsItem;
import com.mygdx.game.game_object.item.bonus.RevertMovement;
import com.mygdx.game.game_object.item.coin.Coin;
import com.mygdx.game.game_object.obstacle.Obstacle;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.game_object.pool.GenericPool;
import com.mygdx.game.handler.BulletHandler;
import com.mygdx.game.handler.CollisionManager;
import com.mygdx.game.handler.EnemyMovementHandler;
import com.mygdx.game.handler.WaveImageHandler;
import com.mygdx.game.handler.spawn.RandomItemSpawnSystem;
import com.mygdx.game.handler.spawn.SpawningSystem;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.screen.pause.PauseScreen;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.MessageType;
import com.mygdx.game.util.MyPreferences;

import java.util.Iterator;

import static com.mygdx.game.util.Constants.HEIGHT;
import static com.mygdx.game.util.Constants.PLAYER_HEIGHT;
import static com.mygdx.game.util.Constants.PLAYER_WIDTH;
import static com.mygdx.game.util.Constants.PPM;
import static com.mygdx.game.util.Constants.WIDTH;

public class GameScreen extends AbstractScreen {

    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;

    private Vector3 touchPos = new Vector3();
    public World world;
    private Stage stage;
    private Table table;

    //textures
    public TextureAtlas.AtlasRegion spaceshipAtlasRegion;
    public TextureAtlas.AtlasRegion spaceshipHitAtlasRegion;
    public static TextureAtlas.AtlasRegion bombAtlasRegion;
    public static TextureRegion fraction1OrangeShipTexture;
    public static TextureRegion fraction1OrangeShip2Texture;
    public static TextureRegion fraction1OrangeShip3Texture;
    public static TextureRegion fraction1OrangeShip4Texture;
    private TextureRegion bulletImage;
    public static TextureRegion enemyBulletImage;
    private TextureRegion pauseTexture;
    public static TextureRegion coinImage;
    public static TextureRegion revertImage;
    public static TextureRegion addHealthImage;
    public static TextureRegion increaseNumberOfBulletsImage;
    public TextureRegion wave1;
    public TextureRegion wave2;
    public TextureRegion wave3;
    public static TextureRegion youDiedImage;
    public static TextureRegion youWinImage;
    public static TextureRegion boss1Image;
    public static TextureRegion alien1Texture;
    public static TextureRegion alien2Texture;
    public static TextureRegion alien3Texture;
    public TextureRegion boss2Image;
    public TextureRegion meteor1;
    public TextureRegion meteor2;
    private TextureAtlas textureAtlas;
    private Texture lvl1background;

    private ImageButton pauseButton;

    //effects
    private ParticleEffect flameEffect;
    private ParticleEffect engineEffect;

    //Sound
    private Sound shootSound;
    private Sound scoreSound;
    private Music level1Music;

    //Pools
    private final GenericPool genericPool;
    private ParticleEffectPool effectPool;

    //Arrays
    private final Array<Enemy> activeEnemies = new Array<>();
    private final Array<Bullet> activeBullet2D = new Array<>();
    private final Array<EnemyBullet> activeEnemyBullets = new Array<>();
    private final Array<Obstacle> activeObstacles = new Array<>();
    private Array<PooledEffect> activeEffects = new Array<>();
    private Array<Item> items = new Array<>();

    //handlers
    private RandomItemSpawnSystem itemChanceList = new RandomItemSpawnSystem<>();
    public static WaveImageHandler waveImageHandler = new WaveImageHandler();
    private MessageManager messageManager;
    private BulletHandler bulletHandler;
    private EnemyMovementHandler enemyMovementHandler;

    public MyPreferences preferences = game.myPreferences;

    public static long lastBulletTime;
    private float backgroundY = 0f;

    private PlayerSpaceship playerSpaceship;

    private FPSLogger logger;
    public static float totalGameTime = 0;
    public static int POINTS;
    public static int NUMBER_OF_BULLETS = 1;
    private boolean wasTouched = false;

    private ShapeRenderer shapeRenderer;
    private float hpBarWidth = Constants.WIDTH/4f, hpBarHeight = Constants.HEIGHT/40f;
    private float hpBarX = hpBarWidth/8f, hpBarY = Constants.HEIGHT - (1.5f*hpBarHeight);
    private float hpEnemyBarWidth = 140f, hpEnemyBarHeight = 14f;
    private float hpUnit, hpEnemyUnit;


    public GameScreen(final SpaceInvaderApp game) {
        super(game);

        this.world = new World(new Vector2(0, 0), false);
        this.world.setContactListener(new CollisionManager());
        this.shapeRenderer = new ShapeRenderer();

        //for testing only
        box2DDebugRenderer = new Box2DDebugRenderer();

        POINTS = game.myPreferences.getPoints();

        flameEffect = new ParticleEffect();
        engineEffect = new ParticleEffect();

        if (game.assets.manager.isFinished()) {
            loadAssets();
        }

        initTableStageAndPauseButton();

        //pools
        genericPool = new GenericPool(world, this);
        effectPool = new ParticleEffectPool(flameEffect, 0, 40);

        playerSpaceship = new PlayerSpaceship(this);
        playerSpaceship.init(50, 50, 0, 0);
        hpUnit = hpBarWidth/playerSpaceship.getTotalHP();

        bulletHandler = new BulletHandler(genericPool, activeBullet2D, activeEnemyBullets);
        enemyMovementHandler = new EnemyMovementHandler(game, activeEnemies);
        SpawningSystem spawningSystem = new SpawningSystem(game, genericPool, activeEnemies, activeObstacles, this);
        spawningSystem.spawn();


        flameEffect.start();
        flameEffect.scaleEffect(2f);
        engineEffect.start();
        engineEffect.flipY();
        engineEffect.scaleEffect(1f / PPM);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH / PPM, HEIGHT / PPM);

        logger = new FPSLogger();

        itemChanceList.addEntry(Coin.class, 85f);
        itemChanceList.addEntry(RevertMovement.class, 1f);
        itemChanceList.addEntry(AddHealth.class, 4f);
        itemChanceList.addEntry(NumberOfBulletsItem.class, 5f);

    }

    private float elapsedTime = 0;

    @Override
    public void update(float delta) {
        elapsedTime = +delta;
        updatePlayerMovement();
        waveImageHandler.update();

        GdxAI.getTimepiece().update(delta);

        if (elapsedTime > 0.8f) {
            // Dispatch any delayed messages
            MessageManager.getInstance().update();
            elapsedTime = 0;
        }

        if (TimeUtils.nanoTime() - lastBulletTime > 250000000) {
            bulletHandler.spawnBasicBullets(playerSpaceship.getBody().getPosition().x,
                    playerSpaceship.getBody().getPosition().y);
            shootSound.play();
        }

        enemyMovementHandler.update(delta);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        logger.log();
        backgroundY -= 5 * delta;

        game.batch.begin();

        game.batch.draw(lvl1background, 0, backgroundY, WIDTH / PPM, HEIGHT / PPM * 6f);

        game.batch.draw(playerSpaceship.getFrame(delta), playerSpaceship.getBody().getPosition().x - PLAYER_WIDTH / 2f,
                playerSpaceship.getBody().getPosition().y - PLAYER_HEIGHT / 2f, PLAYER_WIDTH, PLAYER_HEIGHT);
        playerSpaceship.update(delta);

        updateAndDrawBullets(delta);
        updateAndDrawEnemies(delta);
        updateAndDrawEffects(delta);
        updateAndDrawEnemyBullets(delta);
        updateAndDrawItems(delta);
        if(game.gameScreenManager.getActiveScreen().equals(GameState.LEVEL3) ||
                game.gameScreenManager.getActiveScreen().equals(GameState.LEVEL5))
            updateAndDrawObstacles(delta);

        game.batch.end();

        if(game.gameScreenManager.getActiveScreen().equals(GameState.LEVEL2) ||
                game.gameScreenManager.getActiveScreen().equals(GameState.LEVEL4))
            updateAndDrawEnemyHpBar();
        updateAndDrawHPbar();


        //box2DDebugRenderer.render(this.world, camera.combined);
        world.step(1 / 45f, 6, 2);

        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Timer.instance().start();
        level1Music.play();
        Gdx.input.setInputProcessor(stage);
        initMessages();
    }

    @Override
    public void hide() {
        level1Music.pause();
        engineEffect.reset();
        flameEffect.reset();
    }

    @Override
    public void pause() {
        game.gameScreenManager.setPreviousActiveScreen(game.gameScreenManager.getActiveScreen());
        game.gameScreenManager.setActiveScreen(GameState.PAUSE);
        game.gameScreenManager.setScreen(GameState.PAUSE, PauseScreen.class);
        level1Music.pause();
        Timer.instance().stop();
    }

    @Override
    public void resume() {

    }


    @Override
    public void dispose() {
        shootSound.dispose();
        level1Music.dispose();
        box2DDebugRenderer.dispose();
        world.dispose();
        flameEffect.dispose();
        engineEffect.dispose();
        shapeRenderer.dispose();
    }

    private void loadAssets() {

        textureAtlas = game.assets.manager.get("packedImages/playerAndEnemies.atlas", TextureAtlas.class);
        spaceshipAtlasRegion = textureAtlas.findRegion("basicPlayerSpaceship");
        spaceshipHitAtlasRegion = textureAtlas.findRegion("basicPlayerShipHIT");
        bombAtlasRegion = textureAtlas.findRegion("bullet/enemyBomb");
        fraction1OrangeShipTexture = new TextureRegion(textureAtlas.findRegion("fraction1/orangeship"));
        fraction1OrangeShip2Texture = new TextureRegion(textureAtlas.findRegion("fraction1/orangeship2"));
        fraction1OrangeShip3Texture = new TextureRegion(textureAtlas.findRegion("fraction1/orangeship3"));
        fraction1OrangeShip4Texture = new TextureRegion(textureAtlas.findRegion("fraction1/orangeship4"));
        alien1Texture = new TextureRegion(textureAtlas.findRegion("fraction3/alien1"));
        alien2Texture = new TextureRegion(textureAtlas.findRegion("fraction3/alien2"));
        alien3Texture = new TextureRegion(textureAtlas.findRegion("fraction3/alien3"));
        pauseTexture = new TextureRegion(textureAtlas.findRegion("menu/pause_buttons/pause"));
        lvl1background = game.assets.manager.get("background/lvl1.jpg");
        bulletImage = new TextureRegion(textureAtlas.findRegion("bullet/bullet3"));
        enemyBulletImage = new TextureRegion(textureAtlas.findRegion("bullet/bullet2"));
        wave1 = new TextureRegion(textureAtlas.findRegion("waves/wave1"));
        wave2 = new TextureRegion(textureAtlas.findRegion("waves/wave2"));
        wave3 = new TextureRegion(textureAtlas.findRegion("waves/wave3"));
        boss1Image = new TextureRegion(textureAtlas.findRegion("boss/boss1/spacestation"));
        boss2Image = new TextureRegion(textureAtlas.findRegion("boss/boss3/tribase-u1-d0"));
        youDiedImage = new TextureRegion(textureAtlas.findRegion("menu/event_screen/lose"));
        youWinImage = new TextureRegion(textureAtlas.findRegion("menu/event_screen/win"));
        meteor1 = new TextureRegion(textureAtlas.findRegion("obstacle/met1"));
        meteor2 = new TextureRegion(textureAtlas.findRegion("obstacle/met2"));
        shootSound = game.assets.manager.get("music/sfx-laser.wav", Sound.class);
        scoreSound = game.assets.manager.get("music/score.wav", Sound.class);
        level1Music = game.assets.manager.get("music/level1Music.wav", Music.class);
        flameEffect = game.assets.manager.get("effects/explosion.flame", ParticleEffect.class);
        engineEffect = game.assets.manager.get("effects/engine2.flame", ParticleEffect.class);
        coinImage = new TextureRegion(textureAtlas.findRegion("items/coin"));
        revertImage =  new TextureRegion(textureAtlas.findRegion("items/revertMovement"));
        addHealthImage = new TextureRegion(textureAtlas.findRegion("items/health"));
        increaseNumberOfBulletsImage =  new TextureRegion(textureAtlas.findRegion("items/addBullet"));
    }

    private void initMessages() {
        messageManager = game.messageManager;
        messageManager.addListeners(playerSpaceship, MessageType.PLAYER_MOVE, MessageType.PLAYER_STOP);
        messageManager.addListeners(bulletHandler, MessageType.BOSS_SHOOT_BULLET, MessageType.ENEMY_SHOOT, MessageType.BOSS_SHOOT_BOMB);
    }


    private void spawnEffects(float x, float y) {
        PooledEffect effect = effectPool.obtain();
        effect.setPosition(x, y);
        activeEffects.add(effect);
        effect.start();
    }

    private void updateAndDrawEnemyHpBar() {
        if(!activeEnemies.isEmpty()) {
            Enemy enemy = activeEnemies.get(0); //only boos on the screen
            float x = enemy.getBody().getPosition().x * PPM;
            float y = (enemy.getBody().getPosition().y + enemy.getHeight()/2f)* PPM;
            hpEnemyUnit = hpEnemyBarWidth/enemy.getTotalHp();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(x - (hpEnemyBarWidth/2f) , y, hpEnemyBarWidth, hpEnemyBarHeight);

            Gdx.gl20.glDisable(GL20.GL_BLEND);

            int missingHP = enemy.getTotalHp() - enemy.getHp();

            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(x - (hpEnemyBarWidth/2f), y, hpEnemyBarWidth - (hpEnemyUnit * missingHP), hpEnemyBarHeight);
            shapeRenderer.end();
        }
    }

    private void updateAndDrawHPbar() {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(hpBarX, hpBarY, hpBarWidth, hpBarHeight);

        Gdx.gl20.glDisable(GL20.GL_BLEND);

        int missingHP = playerSpaceship.getTotalHP() - playerSpaceship.getHp();

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(hpBarX, hpBarY, hpBarWidth - (hpUnit*missingHP), hpBarHeight);
        shapeRenderer.end();
    }

    private void updateAndDrawObstacles(float delta) {
        for(Obstacle obstacle : activeObstacles) {
            obstacle.update(delta);
            game.batch.draw(obstacle.getTexture(), obstacle.getBody().getPosition().x - (obstacle.getWidth()),
                    obstacle.getBody().getPosition().y - (obstacle.getHeight()), obstacle.getWidth()*2, obstacle.getHeight()*2);

            if (obstacle.getBody().getPosition().y < 0) {
                genericPool.freeObstacleFromSpecifiedPool(obstacle);
                activeObstacles.removeValue(obstacle, true);
            } else if (!obstacle.getBody().isActive() || obstacle.isToDestroy()) {
                spawnEffects(obstacle.getOnDestroyCoordX(), obstacle.getOnDestroyCoordY());
                genericPool.freeObstacleFromSpecifiedPool(obstacle);
                activeObstacles.removeValue(obstacle, true);
            }
        }
    }

    private void updateAndDrawBullets(float delta) {
        for (Bullet bullet : activeBullet2D) {

            game.batch.draw(bulletImage, bullet.getBody().getPosition().x - (bullet.getWidth() / 2),
                    bullet.getBody().getPosition().y - (bullet.getHeight() / 2), bullet.getWidth(), bullet.getHeight());

            if (bullet.getBody().getPosition().y > HEIGHT / PPM - bullet.getHeight()|| !bullet.getBody().isActive() || bullet.isToDestroy()) {
                genericPool.freeBulletFromSpecifiedPool(bullet); // reset and place back in pool
                activeBullet2D.removeValue(bullet, true); // remove bullet from our array so we don't render it anymore
            }

        }
    }

    private void updateAndDrawEnemyBullets(float delta) {
        for (EnemyBullet enemyBullet : activeEnemyBullets) {
            enemyBullet.update(delta);

            game.batch.draw(enemyBullet.getFrame(delta), enemyBullet.getBody().getPosition().x - (enemyBullet.getWidth() / 2),
                    enemyBullet.getBody().getPosition().y - (enemyBullet.getHeight() / 2), enemyBullet.getWidth(), enemyBullet.getHeight());

            if (enemyBullet.getBody().getPosition().y < 0 || !enemyBullet.getBody().isActive() || enemyBullet.isToDestroy()) {
                genericPool.freeEnemyBulletFromSpecifiedPool(enemyBullet);
                activeEnemyBullets.removeValue(enemyBullet, true);
            }

        }
    }

    private void updateAndDrawItems(float delta) {
        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            if (item.isToDestroy()) {
                scoreSound.play();
                iter.remove();
                item.reset();
            }
            item.update(delta);
            game.batch.draw(item.getTexture(), item.getBody().getPosition().x - item.getWidth() / 2,
                    item.getBody().getPosition().y - item.getHeight() / 2, item.getWidth(), item.getHeight());
        }
    }

    private void updateAndDrawEnemies(float delta) {
        for (Enemy enemy : activeEnemies) {
            enemy.update(delta);
            game.batch.draw(enemy.getTexture(), enemy.getBody().getPosition().x - enemy.getWidth() / 2,
                    enemy.getBody().getPosition().y - enemy.getHeight() / 2, enemy.getWidth(), enemy.getHeight());

            if (!enemy.getBody().isActive()) {
                Item item = (Item) itemChanceList.getRandomItem(world);
                if (item != null) {
                    item.init(enemy.getOnDestroyCoordX(), enemy.getOnDestroyCoordY(), item.getVelX(), item.getVelY());
                    items.add(item);
                }
                spawnEffects(enemy.getOnDestroyCoordX(), enemy.getOnDestroyCoordY());
                genericPool.freeEnemyFromSpecifiedPool(enemy); // place back in pool
                activeEnemies.removeValue(enemy, true); // remove bullet from our array so we don't render it anymore
            } else if (enemy.getBody().getPosition().y < 0) {
                genericPool.freeEnemyFromSpecifiedPool(enemy);
                activeEnemies.removeValue(enemy, true);
            }

        }
    }

    private void updatePlayerMovement() {
        if (Gdx.input.isTouched()) {
            wasTouched = true;
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            float revertHeight = HEIGHT - touchPos.y;
            if (Math.abs(touchPos.x - (playerSpaceship.getBody().getPosition().x * PPM)) > 8
                    || Math.abs(revertHeight - (playerSpaceship.getBody().getPosition().y * PPM)) > 12) {
                camera.unproject(touchPos);
                messageManager.dispatchMessage(MessageType.PLAYER_MOVE, touchPos);
            } else {
                playerSpaceship.stop();
            }
        } else if (wasTouched) {
            messageManager.dispatchMessage(MessageType.PLAYER_STOP);
            wasTouched = false;
        }
    }

    private void updateAndDrawEffects(float delta) {

        if (!waveImageHandler.isCompleted())
            game.batch.draw(waveImageHandler.getTexture(), waveImageHandler.getPositionX(), waveImageHandler.getPositionY(),
                    waveImageHandler.getWidth(), waveImageHandler.getHeight());

        engineEffect.setPosition(playerSpaceship.getBody().getPosition().x,
                playerSpaceship.getBody().getPosition().y - (PLAYER_HEIGHT / 2f));
        engineEffect.update(delta);
        engineEffect.draw(game.batch);

        for (PooledEffect effect : activeEffects) {
            effect.scaleEffect(0.2f);
            effect.update(delta);
            effect.draw(game.batch);
            if (effect.isComplete()) {
                activeEffects.removeValue(effect, true);
                effect.free();
            }
        }
    }

    private void initTableStageAndPauseButton() {
        //stage & table
        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.topRight);
        table.setPosition(0, Gdx.graphics.getHeight());

        TextureRegion region = new TextureRegion(pauseTexture);
        Drawable drawable = new TextureRegionDrawable(region);
        pauseButton = new ImageButton(drawable);

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pause();
            }
        });

        table.add(pauseButton).size(50f, 50f);
        stage.addActor(table);
    }

    public PlayerSpaceship getPlayerSpaceship() {
        return playerSpaceship;
    }


}
