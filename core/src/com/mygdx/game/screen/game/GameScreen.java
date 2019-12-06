package com.mygdx.game.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.game_object.bullet.basic_bullet.BasicBullet;
import com.mygdx.game.game_object.enemy.basic_enemy.BasicEnemy;
import com.mygdx.game.game_object.pool.GenericPool;
import com.mygdx.game.handler.CollisionManager;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.handler.spawn.SpawningSystem;
import com.mygdx.game.screen.pause.PauseScreen;

import java.lang.reflect.InvocationTargetException;

public class GameScreen extends AbstractScreen {

    public static final int HEIGHT = 800; //Gdx.graphics.getHeight();
    public static final int WIDTH = 480; //Gdx.graphics.getWidth();
    public static float totalGameTime = 0;

    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private World world;
    private Stage stage;
    private Table table;

    //textures
    private Texture spaceshipImage;
    private Texture bulletImage;
    private Texture pauseTexture;

    private ImageButton pauseButton;

    //effects
    private ParticleEffect flameEffect;

    private Sound shootSound;
    //private Music rainMusic;

    //Pools
    private final GenericPool genericPool;
    private final Pool enemyPool;
    private final Pool bulletBox2DPool;
    private ParticleEffectPool effectPool;

    //Arrays
    private final Array<Enemy> activeEnemies = new Array<>();
    private final Array<Bullet> activeBullet2D = new Array<>();
    private Array<PooledEffect> activeEffects = new Array<>();


    private Vector3 touchPos = new Vector3();
    private long lastBulletTime;
    private int bulletsShot = 0;

    private PlayerSpaceship playerSpaceship;
    private FPSLogger logger;


    public GameScreen(final MyGdxGame game) {
        super(game);

        this.world = new World(new Vector2(0, 0), false);
        this.world.setContactListener(new CollisionManager());

        //for testing only
        box2DDebugRenderer = new Box2DDebugRenderer();

        flameEffect = new ParticleEffect();

        if (game.assets.manager.isFinished()) {
            loadAssets();
        }

        initTableStageAndPauseButton();

        //pools
        genericPool = new GenericPool(world);
        enemyPool = genericPool.getEnemyPool();
        bulletBox2DPool = genericPool.getBulletPool();
        effectPool = new ParticleEffectPool(flameEffect, 0, 70);

        switch (game.gameScreenManager.getActiveScreen()) {
            case LEVEL1:
                new SpawningSystem(game, enemyPool, activeEnemies);
                break;
            case LEVEL2:
                break;
        }

        playerSpaceship = new PlayerSpaceship(world);

        flameEffect.getEmitters().first();
        flameEffect.start();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        logger = new FPSLogger();

    }

    @Override
    public void update(float delta) {
        totalGameTime += delta;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        logger.log();

        game.batch.begin();
        game.font.draw(game.batch, "Bullets shot: " + bulletsShot, 0, 800);
        game.font.draw(game.batch, "Game Time: " + totalGameTime, 0, 780);

        game.batch.draw(spaceshipImage, playerSpaceship.getBody().getPosition().x - playerSpaceship.getWidth(),
                playerSpaceship.getBody().getPosition().y - playerSpaceship.getHeight());

        updateAndDrawBullets(delta);
        updateAndDrawEnemies(delta);
        updateAndDrawEffects(delta);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            playerSpaceship.getBody().setTransform(touchPos.x - 32 / 2, touchPos.y - 32 / 2, 0);

        }

        if (TimeUtils.nanoTime() - lastBulletTime > 800000000) {
            spawnBasicBullets();
            bulletsShot++;
            shootSound.play();
        }


        box2DDebugRenderer.render(this.world, camera.combined);
        world.step(1/60f, 6, 2);

        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        //rainMusic.play();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        game.gameScreenManager.setActiveScreen(GameState.PAUSE);
        game.gameScreenManager.setStageScreen(GameState.PAUSE, PauseScreen.class);

    }

    @Override
    public void resume() {

    }


    @Override
    public void dispose() {
        spaceshipImage.dispose();
        bulletImage.dispose();
        shootSound.dispose();
        //rainMusic.dispose();
        box2DDebugRenderer.dispose();
        world.dispose();
        flameEffect.dispose();
    }

    private void loadAssets() {
        spaceshipImage = game.assets.manager.get("spaceship.png", Texture.class);
        bulletImage = game.assets.manager.get("droplet.png", Texture.class);
        shootSound = game.assets.manager.get("sfx-laser.wav", Sound.class);
        flameEffect = game.assets.manager.get("effects/Particle.flame", ParticleEffect.class);
        pauseTexture = game.assets.manager.get("menu/pause.png", Texture.class);
    }


    private void spawnBasicBullets() {
        /** Returns an object from this pool. The object may be new (from {@link #newObject()}) or reused (previously
         * {@link #free(Object) freed}). */
        // get a bullet from our pool
        BasicBullet basicBullet = (BasicBullet) bulletBox2DPool.obtain();
        basicBullet.init(playerSpaceship.getX(), (playerSpaceship.getY() + 60));
        // add to our array of bullets so we can access them in our render method
        activeBullet2D.add(basicBullet);
        //System.out.println(bulletBox2DPool.getFree());
        lastBulletTime = TimeUtils.nanoTime();
    }

    private void spawnEffects(float x, float y) {
        PooledEffect effect = effectPool.obtain();
        effect.getEmitters().first().setPosition(x, y);
        activeEffects.add(effect);
        effect.start();
    }

    private void updateAndDrawBullets(float delta) {
        for (Bullet bullet : activeBullet2D) {
            bullet.update(delta);

            game.batch.draw(bulletImage, bullet.getBody().getPosition().x - bullet.getWidth(),
                    bullet.getBody().getPosition().y - bullet.getHeight());

            if (bullet.getBody().getPosition().y > HEIGHT - 60 || !bullet.getBody().isActive() || bullet.isToDestroy()) {
                bulletBox2DPool.free(bullet); // reset and place back in pool
                activeBullet2D.removeValue(bullet, true); // remove bullet from our array so we don't render it anymore
            }

        }
    }

    private void updateAndDrawEnemies(float delta) {
        for (Enemy enemy : activeEnemies) {
            enemy.update(delta);
            game.batch.draw(spaceshipImage, enemy.getBody().getPosition().x - enemy.getWidth(),
                    enemy.getBody().getPosition().y - enemy.getHeight());

            if (!enemy.getBody().isActive()) {
                spawnEffects(enemy.getOnDestroyCoordX(), enemy.getOnDestroyCoordY());
                enemyPool.free(enemy); // place back in pool
                activeEnemies.removeValue(enemy, true); // remove bullet from our array so we don't render it anymore
            }
        }
    }

    private void updateAndDrawEffects(float delta) {
        for(PooledEffect effect : activeEffects) {
            effect.update(delta);
            effect.draw(game.batch);
            if(effect.isComplete()) {
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

        pauseButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("elo");
                pause();
            }
        });

        table.add(pauseButton).size(50f, 50f);
        stage.addActor(table);
    }

}
