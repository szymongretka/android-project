package com.mygdx.game.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.game_object.bullet.Bullet;
import com.mygdx.game.game_object.bullet.basic_bullet.BasicBullet;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.item.Item;
import com.mygdx.game.game_object.item.bonus.BasicShield;
import com.mygdx.game.game_object.item.bonus.RevertMovement;
import com.mygdx.game.game_object.item.coin.Coin;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.game_object.pool.GenericPool;
import com.mygdx.game.handler.CollisionManager;
import com.mygdx.game.handler.spawn.RandomItemSpawnSystem;
import com.mygdx.game.handler.spawn.SpawningSystem;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.screen.pause.PauseScreen;

import java.util.Iterator;

import static com.mygdx.game.util.Constants.BASICBULLETHEIGHT;
import static com.mygdx.game.util.Constants.BASICBULLETWIDTH;
import static com.mygdx.game.util.Constants.BASIC_ENEMY_HEIGHT;
import static com.mygdx.game.util.Constants.BASIC_ENEMY_WIDTH;
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
    public static TextureRegion basicEnemyTexture;
    private Texture bulletImage;
    private Texture pauseTexture;
    public static Texture coinImage;
    public static Texture revertImage;
    public static Texture shieldImage;
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
    private final Pool enemyPool;
    private final Pool bulletBox2DPool;
    private ParticleEffectPool effectPool;

    //Arrays
    private final Array<Enemy> activeEnemies = new Array<>();
    private final Array<Bullet> activeBullet2D = new Array<>();
    private Array<PooledEffect> activeEffects = new Array<>();
    private Array<Item> items = new Array<>();

    private RandomItemSpawnSystem itemChanceList = new RandomItemSpawnSystem<>();

    private long lastBulletTime;
    private float backgroundY = 0f;

    private PlayerSpaceship playerSpaceship;

    private FPSLogger logger;
    public static float totalGameTime = 0;
    public static int POINTS = 0;


    public GameScreen(final MyGdxGame game) {
        super(game);

        this.world = new World(new Vector2(0, 0), false);
        this.world.setContactListener(new CollisionManager());

        //for testing only
        box2DDebugRenderer = new Box2DDebugRenderer();

        flameEffect = new ParticleEffect();
        engineEffect = new ParticleEffect();

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

        playerSpaceship = new PlayerSpaceship(this);
        playerSpaceship.init(0, 0);


        flameEffect.getEmitters().first();
        flameEffect.start();
        engineEffect.start();
        engineEffect.flipY();
        engineEffect.scaleEffect(1f/PPM);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH / PPM, HEIGHT / PPM);

        logger = new FPSLogger();

        itemChanceList.addEntry(Coin.class, 30f);
        itemChanceList.addEntry(RevertMovement.class, 10f);
        //itemChanceList.addEntry(RevertMovement.class, 30f);
        itemChanceList.addEntry(BasicShield.class, 60f);


    }

    @Override
    public void update(float delta) {
        totalGameTime += delta;
        if (Gdx.input.isTouched()) { //TODO TO REFACTOR
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            float revertHeight = HEIGHT - touchPos.y;
            if (Math.abs(touchPos.x - (playerSpaceship.getBody().getPosition().x * PPM)) > 8
                    || Math.abs(revertHeight - (playerSpaceship.getBody().getPosition().y * PPM)) > 12) {
                camera.unproject(touchPos);
                playerSpaceship.move(touchPos.x, touchPos.y);
            } else {
                playerSpaceship.stop();
            }
        } else {
            playerSpaceship.stop();
        }
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

        game.batch.draw(playerSpaceship.getFrame(delta), playerSpaceship.getBody().getPosition().x - PLAYER_WIDTH / 2,
                playerSpaceship.getBody().getPosition().y - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT);
        playerSpaceship.update(delta);

        updateAndDrawBullets(delta);
        updateAndDrawEnemies(delta);
        updateAndDrawEffects(delta);
        updateAndDrawItems(delta);

        engineEffect.setPosition(playerSpaceship.getBody().getPosition().x,
                playerSpaceship.getBody().getPosition().y - (PLAYER_HEIGHT/2));
        engineEffect.update(delta);
        engineEffect.draw(game.batch);

        game.batch.end();

        if (TimeUtils.nanoTime() - lastBulletTime > 400000000) {
            spawnBasicBullets();
            shootSound.play();
        }


        box2DDebugRenderer.render(this.world, camera.combined);
        world.step(1 / 60f, 6, 2);

        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        level1Music.play();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        game.gameScreenManager.setActiveScreen(GameState.PAUSE);
        game.gameScreenManager.setScreen(GameState.PAUSE, PauseScreen.class);
        level1Music.pause();
    }

    @Override
    public void resume() {

    }


    @Override
    public void dispose() {
        coinImage.dispose();
        revertImage.dispose();
        bulletImage.dispose();
        shootSound.dispose();
        level1Music.dispose();
        box2DDebugRenderer.dispose();
        world.dispose();
        flameEffect.dispose();
        engineEffect.dispose();
    }

    private void loadAssets() {

        textureAtlas = game.assets.manager.get("packedImages/playerAndEnemies.atlas", TextureAtlas.class);
        spaceshipAtlasRegion = textureAtlas.findRegion("basicPlayerSpaceship");
        basicEnemyTexture = new TextureRegion(textureAtlas.findRegion("fraction1/orangeship"));
        lvl1background = game.assets.manager.get("background/lvl1.jpg", Texture.class);
        bulletImage = game.assets.manager.get("droplet.png", Texture.class);
        shootSound = game.assets.manager.get("music/sfx-laser.wav", Sound.class);
        scoreSound = game.assets.manager.get("music/score.wav", Sound.class);
        level1Music = game.assets.manager.get("music/level1Music.wav", Music.class);
        flameEffect = game.assets.manager.get("effects/Particle.flame", ParticleEffect.class);
        engineEffect = game.assets.manager.get("effects/engine2.flame", ParticleEffect.class);
        pauseTexture = game.assets.manager.get("menu/pause.png", Texture.class);
        coinImage = game.assets.manager.get("coin.png", Texture.class);
        revertImage = game.assets.manager.get("revert.png", Texture.class);
        shieldImage = game.assets.manager.get("shield.png", Texture.class);
    }

    private void spawnBasicBullets() {
        /** Returns an object from this pool. The object may be new (from {@link #newObject()}) or reused (previously
         * {@link #free(Object) freed}). */
        // get a bullet from our pool
        BasicBullet basicBullet = (BasicBullet) bulletBox2DPool.obtain();
        basicBullet.init(playerSpaceship.getX(), playerSpaceship.getY() + PLAYER_HEIGHT);
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
                    bullet.getBody().getPosition().y - bullet.getHeight(), BASICBULLETWIDTH, BASICBULLETHEIGHT);

            if (bullet.getBody().getPosition().y > HEIGHT - BASICBULLETHEIGHT || !bullet.getBody().isActive() || bullet.isToDestroy()) {
                bulletBox2DPool.free(bullet); // reset and place back in pool
                activeBullet2D.removeValue(bullet, true); // remove bullet from our array so we don't render it anymore
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
                    enemy.getBody().getPosition().y - enemy.getHeight() / 2, BASIC_ENEMY_WIDTH, BASIC_ENEMY_HEIGHT);

            if (!enemy.getBody().isActive()) {
                Item item = (Item) itemChanceList.getRandomItem(world);
                if (item != null) {
                    item.init(enemy.getOnDestroyCoordX(), enemy.getOnDestroyCoordY());
                    items.add(item);
                }
                spawnEffects(enemy.getOnDestroyCoordX(), enemy.getOnDestroyCoordY());
                enemyPool.free(enemy); // place back in pool
                activeEnemies.removeValue(enemy, true); // remove bullet from our array so we don't render it anymore
            }
        }
    }

    private void updateAndDrawEffects(float delta) {
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

}
