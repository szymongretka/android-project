package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.game_object.PlayerSpaceship;
import com.mygdx.game.game_object.bullet.BulletBox2D;
import com.mygdx.game.game_object.bullet.pool.BulletBox2DPool;
import com.mygdx.game.game_object.enemy.EnemyBox2D;
import com.mygdx.game.game_object.enemy.pool.EnemyBox2DPool;
import com.mygdx.game.handler.CollisionManager;
import com.mygdx.game.spawn.SpawningSystem;

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

    private Sound shootSound;
    //private Music rainMusic;

    //Arrays
    private final Array<EnemyBox2D> activeEnemies = new Array<>();
    private final Array<BulletBox2D> activeBullet2D = new Array<>();

    //Pools
    private final EnemyBox2DPool enemyPool;
    private final BulletBox2DPool bulletBox2DPool;

    private Vector3 touchPos = new Vector3();
    private long lastBulletTime;
    private int bulletsShot = 0;

    private PlayerSpaceship playerSpaceship;
    private FPSLogger logger;

    private BulletBox2D bulletBox2D;


    public GameScreen(final MyGdxGame game) {
        super(game);

        this.world = new World(new Vector2(0, 0), false);
        this.world.setContactListener(new CollisionManager());

        enemyPool = new EnemyBox2DPool(world);

        box2DDebugRenderer = new Box2DDebugRenderer();

        spawningSystem = new SpawningSystem(game);

        //load all assets
        game.assets.load();
        game.assets.manager.finishLoading();

        if (game.assets.manager.isFinished()) {
            loadAssets();
        }

        spawningSystem.spawn(enemyPool, activeEnemies);

        playerSpaceship = new PlayerSpaceship(HEIGHT / 2 - 64 / 2, 20, 100,
                50, 50, spaceshipImage);


        bulletBox2DPool = new BulletBox2DPool(world);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        //spawnBullets();

        logger = new FPSLogger();


    }

    @Override
    public void update(float delta) {
        this.world.step(1/60f, 6, 2);
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
        game.font.draw(game.batch, "Game Time: " + totalGameTime, 0, 700);
        playerSpaceship.draw(game.batch);

        for (BulletBox2D bullet : activeBullet2D) {
            bullet.update(delta);

            game.batch.draw(bulletImage, bullet.getBody().getPosition().x - bullet.getWidth(),
                    bullet.getBody().getPosition().y - bullet.getHeight());
        }

        for (EnemyBox2D enemy : activeEnemies) {
            enemy.update(delta);
            game.batch.draw(spaceshipImage, enemy.getBody().getPosition().x - enemy.getWidth(),
                    enemy.getBody().getPosition().y - enemy.getHeight());
        }

        game.batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            playerSpaceship.setX(touchPos.x - 64 / 2);

        }

        if (TimeUtils.nanoTime() - lastBulletTime > 800000000) {
            spawnBox2DBullets();
            bulletsShot++;
            shootSound.play();
        }


        for (BulletBox2D bullet : activeBullet2D) {
            // check if bullet is off screen
            if (bullet.getBody().getPosition().y > HEIGHT - 60 || !bullet.getBody().isActive()) {
                bulletBox2DPool.free(bullet); // place back in pool
                activeBullet2D.removeValue(bullet, true); // remove bullet from our array so we don't render it anymore
            }

        }

        for (EnemyBox2D enemyBox2D : activeEnemies) {
            // check if bullet is off screen
            if (!enemyBox2D.getBody().isActive()) {
                enemyPool.free(enemyBox2D); // place back in pool
                activeEnemies.removeValue(enemyBox2D, true); // remove bullet from our array so we don't render it anymore
            }

        }


        box2DDebugRenderer.render(this.world, camera.combined);

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
    }

    private void loadAssets() {
        spaceshipImage = game.assets.manager.get("spaceship.png", Texture.class);
        bulletImage = game.assets.manager.get("droplet.png", Texture.class);
        shootSound = game.assets.manager.get("sfx-laser.wav", Sound.class);
    }


    private void spawnBox2DBullets() {
        /** Returns an object from this pool. The object may be new (from {@link #newObject()}) or reused (previously
         * {@link #free(Object) freed}). */
        // get a bullet from our pool
        BulletBox2D bulletBox2D = bulletBox2DPool.obtain();
        bulletBox2D.init(playerSpaceship.getX(), (playerSpaceship.getY() + 60));
        // add to our array of bullets so we can access them in our render method
        activeBullet2D.add(bulletBox2D);
        //System.out.println(bulletBox2DPool.getFree());
        lastBulletTime = TimeUtils.nanoTime();
    }


}
