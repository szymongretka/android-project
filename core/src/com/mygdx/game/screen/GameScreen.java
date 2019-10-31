package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.game_object.PlayerSpaceship;
import com.mygdx.game.game_object.bullet.BasicBullet;
import com.mygdx.game.game_object.bullet.BasicBulletPool;


import java.util.Iterator;

public class GameScreen implements Screen {

    public static final int HEIGHT = Gdx.graphics.getHeight();
    public static final int WIDTH = Gdx.graphics.getWidth();

    private final MyGdxGame game;
    private OrthographicCamera camera;

    private Texture spaceshipImage;
    private Texture bulletImage;

    private Sound shootSound;
    //private Music rainMusic;

    private final Array<BasicBullet> activeBullets = new Array<BasicBullet>();
    private final BasicBulletPool bulletPool = new BasicBulletPool();
    private Vector3 touchPos = new Vector3();
    private long lastBulletTime;
    private int bulletsShot = 0;

    private PlayerSpaceship playerSpaceship;


    public GameScreen(final MyGdxGame game) {

        this.game = game;

        //load all assets
        game.assets.load();
        game.assets.manager.finishLoading();

        if(game.assets.manager.isFinished()){
            loadAssets();
        }

        playerSpaceship = new PlayerSpaceship(HEIGHT / 2 - 64 / 2, 20, 100,
                50, 50, spaceshipImage);


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);

        spawnBullets();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        //game.batch.draw(spaceshipImage, spaceShip.x, spaceShip.y);
        game.font.draw(game.batch, "Bullets shot: " + bulletsShot, 0, 800);

        playerSpaceship.draw(game.batch);

        /*for (Rectangle bullet : bullets) {
            game.batch.draw(bulletImage, bullet.x, bullet.y);
        }*/
        for (BasicBullet bullet : activeBullets) {
            bullet.update(delta);
            game.batch.draw(bulletImage, bullet.getPosition().x, bullet.getPosition().y);
        }


        game.batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            playerSpaceship.setX(touchPos.x - 64 / 2);
        }

        if (TimeUtils.nanoTime() - lastBulletTime > 1000000000)
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                spawnBullets();
                bulletsShot++;
                shootSound.play();
            }



        for(BasicBullet bullet : activeBullets){
            // check if bullet is off screen
            if(bullet.getPosition().y > Gdx.graphics.getHeight()){
                // bullet is off screen so free it and then remove it
                bulletPool.free(bullet); // place back in pool
                activeBullets.removeValue(bullet, true); // remove bullet from our array so we don't render it anymore
            }
        }

        /*for (Iterator<BasicBullet> iter = bullets.iterator(); iter.hasNext(); ) {
            BasicBullet bullet = iter.next();
            bullet.update(delta); //delta time to roznica pomiedzy poprzednia a nastepna klatka
                                                        // wyrenderowana w metodzie render - gra bedzie tak samo wygladac na lepszym i wolniejszym urzadzeniu
            if (bullet.y - 64 > 800) iter.remove();
			/*if(bullet.overlaps(spaceShip)) {
				shootSound.play();
				iter.remove();
			}
        }*/

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
    }

    private void loadAssets() {
        spaceshipImage = game.assets.manager.get("spaceship.png", Texture.class);
        bulletImage = game.assets.manager.get("droplet.png", Texture.class);
        shootSound = game.assets.manager.get("sfx-laser.wav", Sound.class);
    }

    private void spawnBullets() {
        // get a bullet from our pool
        BasicBullet basicBullet = bulletPool.obtain();
        basicBullet.init(playerSpaceship.getX(), playerSpaceship.getY(), bulletImage);
        basicBullet.width = 32;
        basicBullet.height = 32;
        // add to our array of bullets so we can access them in our render method
        activeBullets.add(basicBullet);
        System.out.println(bulletPool.getFree());

        //bullets.add(basicBullet);
        lastBulletTime = TimeUtils.nanoTime();
    }

}
