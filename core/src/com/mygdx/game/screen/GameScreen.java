package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.mygdx.game.game_object.TestObject;
import com.mygdx.game.game_object.bullet.BulletBox2D;
import com.mygdx.game.game_object.bullet.pool.BulletBox2DPool;
import com.mygdx.game.game_object.enemy.BasicEnemy;
import com.mygdx.game.game_object.enemy.EnemyBox2D;
import com.mygdx.game.game_object.enemy.pool.BasicEnemyPool;
import com.mygdx.game.game_object.enemy.pool.EnemyBox2DPool;
import com.mygdx.game.handler.CollisionManager;
import com.mygdx.game.spawn.SpawningSystem;
import static com.mygdx.game.util.Constants.PPM;

public class GameScreen extends AbstractScreen {

    public static final int HEIGHT = 800; //Gdx.graphics.getHeight();
    public static final int WIDTH = 480; //Gdx.graphics.getWidth();

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
    //private final Array<BasicBullet> activeBullets = new Array<BasicBullet>();
    //private final Array<BasicEnemy> activeEnemies = new Array<BasicEnemy>();
    private final Array<EnemyBox2D> activeEnemies = new Array<>();
    private final Array<BulletBox2D> activeBullet2D = new Array<>();

    //Pools
    //private final BasicBulletPool bulletPool = new BasicBulletPool();
    //private final BasicEnemyPool enemyPool = new BasicEnemyPool();
    private final EnemyBox2DPool enemyPool;
    private final BulletBox2DPool bulletBox2DPool;

    private Vector3 touchPos = new Vector3();
    private long lastBulletTime;
    private int bulletsShot = 0;

    private PlayerSpaceship playerSpaceship;
    private FPSLogger logger;


    private TestObject player, obj1, obj2;

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


        bulletBox2DPool = new BulletBox2DPool(world, new Vector2(playerSpaceship.getX(), playerSpaceship.getY()));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        //spawnBullets();

        logger = new FPSLogger();


        //player = new TestObject(world, "PLAYER", 64, 64);
        //obj1 = new TestObject(world, "obj1", 150, 150);
        //obj2 = new TestObject(world, "obj2", 50, 50);

    }

    @Override
    public void update(float delta) {
        this.world.step(1/60f, 6, 2);

        /*float x = 0, y = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            y += 1;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            y -= 1;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            x -= 1;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            x += 1;

        player.body.setLinearVelocity(x * 5, y * 5);*/

    }

    @Override
    public void render(float delta) {

        super.render(delta);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        logger.log();


        game.batch.begin();
        game.font.draw(game.batch, "Bullets shot: " + bulletsShot, 0, 800);
        playerSpaceship.draw(game.batch);

        for (BulletBox2D bullet : activeBullet2D) {
            bullet.update(delta);
            game.batch.draw(bulletImage, bullet.getBody().getPosition().x * PPM, bullet.getBody().getPosition().y * PPM);
        }

        for (EnemyBox2D enemy : activeEnemies) {
            enemy.update(delta);
            game.batch.draw(spaceshipImage, enemy.getBody().getPosition().x * PPM, enemy.getBody().getPosition().y * PPM);
        }
        //game.batch.draw(spaceshipImage, player.body.getPosition().x * PPM,player.body.getPosition().y * PPM);
        //game.batch.draw(spaceshipImage, obj1.body.getPosition().x * PPM,obj1.body.getPosition().y * PPM);
        //game.batch.draw(spaceshipImage, obj2.body.getPosition().x * PPM,obj2.body.getPosition().y * PPM);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            playerSpaceship.setX(touchPos.x - 64 / 2);

        }

        if (TimeUtils.nanoTime() - lastBulletTime > 1000000000) {
            spawnBox2DBullets();
            bulletsShot++;
            shootSound.play();
        }


        for (BulletBox2D bullet : activeBullet2D) {

            // check if bullet is off screen
            if (bullet.getPosition().y > Gdx.graphics.getHeight()) {
                // bullet is off screen so free it and then remove it
                bulletBox2DPool.free(bullet); // place back in pool
                activeBullet2D.removeValue(bullet, true); // remove bullet from our array so we don't render it anymore
            }

        }


        box2DDebugRenderer.render(this.world, camera.combined);

        //if(bullet.overlaps(spaceShip))

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

    /*private void spawnBullets() {
        /** Returns an object from this pool. The object may be new (from {@link #newObject()}) or reused (previously
         * {@link #free(Object) freed}). */
        // get a bullet from our pool
        /*BasicBullet basicBullet = bulletPool.obtain();
        basicBullet.init(playerSpaceship.getX(), playerSpaceship.getY());//, bulletImage);
        // add to our array of bullets so we can access them in our render method
        activeBullets.add(basicBullet);
        //System.out.println(bulletPool.getFree());

        //bullets.add(basicBullet);
        lastBulletTime = TimeUtils.nanoTime();
    }*/

    private void spawnBox2DBullets() {
        /** Returns an object from this pool. The object may be new (from {@link #newObject()}) or reused (previously
         * {@link #free(Object) freed}). */
        // get a bullet from our pool
        /*BulletBox2D bulletBox2D = bulletBox2DPool.obtain();
        bulletBox2D.init(playerSpaceship.getX(), playerSpaceship.getY() + 60);
        // add to our array of bullets so we can access them in our render method
        activeBullet2D.add(bulletBox2D);
        //System.out.println(bulletBox2DPool.getFree());
        lastBulletTime = TimeUtils.nanoTime();*/
        this.activeBullet2D.add(new BulletBox2D(world, new Vector2(playerSpaceship.getX(), playerSpaceship.getY())));
        lastBulletTime = TimeUtils.nanoTime();
    }


}
