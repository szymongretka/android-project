package com.mygdx.game.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.game_object.bullet.basic_bullet.BasicBullet;
import com.mygdx.game.game_object.enemy.basic_enemy.BasicEnemy;
import com.mygdx.game.game_object.pool.GenericPool;
import com.mygdx.game.handler.CollisionManager;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.handler.spawn.SpawningSystem;

public class GameScreen extends AbstractScreen {

    public static final int HEIGHT = 800; //Gdx.graphics.getHeight();
    public static final int WIDTH = 480; //Gdx.graphics.getWidth();
    public static float totalGameTime = 0;

    private OrthographicCamera camera;
    private SpawningSystem spawningSystem;
    private Box2DDebugRenderer box2DDebugRenderer;
    private World world;

    //textures
    private Texture spaceshipImage;
    private Texture bulletImage;

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
    private final Array<BasicEnemy> activeEnemies = new Array<>();
    private final Array<BasicBullet> activeBullet2D = new Array<>();
    private Array<PooledEffect> activeEffects = new Array<>();


    private Vector3 touchPos = new Vector3();
    private long lastBulletTime;
    private int bulletsShot = 0;

    private PlayerSpaceship playerSpaceship;
    private FPSLogger logger;


    public GameScreen(final MyGdxGame game) {
        super(game);

        CollisionManager collisionManager = new CollisionManager();
        this.world = new World(new Vector2(0, 0), false);
        this.world.setContactListener(collisionManager);

        //for testing only
        box2DDebugRenderer = new Box2DDebugRenderer();

        //spawningSystem = new SpawningSystem(game);

        flameEffect = new ParticleEffect();

        if (game.assets.manager.isFinished()) {
            loadAssets();
        }

        //pools
        genericPool = new GenericPool(world);
        enemyPool = genericPool.getEnemyPool();
        bulletBox2DPool = genericPool.getBulletPool();
        effectPool = new ParticleEffectPool(flameEffect, 0, 70);

        switch (game.gameScreenManager.getActiveScreen()) {
            case LEVEL1:
                new SpawningSystem(game, enemyPool, activeEnemies).spawn();
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
        //this.world.step(1/60f, 6, 2);
        totalGameTime += delta;
    }

    @Override
    public void render(float delta) {

        super.render(delta);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        logger.log();

        //flameEffect.update(delta);


        game.batch.begin();
        game.font.draw(game.batch, "Bullets shot: " + bulletsShot, 0, 800);
        game.font.draw(game.batch, "Game Time: " + totalGameTime, 0, 780);

        //flameEffect.draw(game.batch);

        game.batch.draw(spaceshipImage, playerSpaceship.getBody().getPosition().x - playerSpaceship.getWidth(),
                playerSpaceship.getBody().getPosition().y - playerSpaceship.getHeight());

        for (BasicBullet bullet : activeBullet2D) {
            bullet.update(delta);

            game.batch.draw(bulletImage, bullet.getBody().getPosition().x - bullet.getWidth(),
                    bullet.getBody().getPosition().y - bullet.getHeight());
        }

        for (BasicEnemy enemy : activeEnemies) {
            enemy.update(delta);
            game.batch.draw(spaceshipImage, enemy.getBody().getPosition().x - enemy.getWidth(),
                    enemy.getBody().getPosition().y - enemy.getHeight());
        }

        for(PooledEffect effect : activeEffects) {
            effect.update(delta);
            effect.draw(game.batch);
            if(effect.isComplete()) {
                activeEffects.removeValue(effect, true);
                effect.free();
            }
        }

        game.batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            playerSpaceship.getBody().setTransform(touchPos.x - 32 / 2, touchPos.y - 32 / 2, 0);

        }

        if (TimeUtils.nanoTime() - lastBulletTime > 800000000) {
            spawnBox2DBullets();
            bulletsShot++;
            shootSound.play();
        }


        for (BasicBullet bullet : activeBullet2D) {
            // check if bullet is off screen
            if (bullet.getBody().getPosition().y > HEIGHT - 60 || !bullet.getBody().isActive() || bullet.isToDestroy()) {
                bulletBox2DPool.free(bullet); // reset and place back in pool
                activeBullet2D.removeValue(bullet, true); // remove bullet from our array so we don't render it anymore
            }

        }

        for (BasicEnemy basicEnemy : activeEnemies) {
            // check if bullet is off screen
            if (!basicEnemy.getBody().isActive()) {
                spawnEffects(basicEnemy.getOnDestroyCoordX(), basicEnemy.getOnDestroyCoordY());
                enemyPool.free(basicEnemy); // place back in pool
                activeEnemies.removeValue(basicEnemy, true); // remove bullet from our array so we don't render it anymore
            }

        }



        box2DDebugRenderer.render(this.world, camera.combined);
        world.step(1/60f, 6, 2);
        /*Gdx.app.log("pool stats", "active: " + activeEffects.size + " | free: "
                + effectPool.getFree() + "/" + effectPool.max + " | record: " + effectPool.peak);*/
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        //rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
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
    }


    private void spawnBox2DBullets() {
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


}
