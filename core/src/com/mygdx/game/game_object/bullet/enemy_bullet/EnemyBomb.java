package com.mygdx.game.game_object.bullet.enemy_bullet;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_object.bullet.EnemyBullet;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.Constants;

public class EnemyBomb extends EnemyBullet {

    public enum State {READY, EXPLODE}

    private State currentState;
    private State previousState;
    private boolean isTouched = false;
    private Animation bombExplodeAnimation;
    private TextureAtlas.AtlasRegion bombAtlasRegion;
    private TextureRegion readyBombTex;

    float stateTimer;

    private int tileWidth = 100;
    private int tileHeight = 100;

    public EnemyBomb(World world) {
        super(world, 0, 0, Constants.BOMB_WIDTH, Constants.BOMB_HEIGHT, 1, 2, false);
        bombAtlasRegion = GameScreen.bombAtlasRegion;
        this.texture = new TextureRegion(bombAtlasRegion, 0, 0, tileWidth, tileHeight);
        readyBombTex = texture;
        this.velX = 0f;
        this.velY = (-500f);
        currentState = State.READY;
        previousState = State.READY;
        initAnimations();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void hitPlayer(PlayerSpaceship playerSpaceship) {
        this.isTouched = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(Math.abs(getBody().getPosition().x - playerSpaceship.getBody().getPosition().x) < 8 ||
                        Math.abs(getBody().getPosition().y - playerSpaceship.getBody().getPosition().y) < 8) {
                    playerSpaceship.gotShot = true;
                    playerSpaceship.setHp(playerSpaceship.getHp() - getDamage());

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            playerSpaceship.gotShot = false;
                        }
                    }, 0.5f);
                }

                reset();
            }
        }, 0.8f);
    }

    @Override
    public TextureRegion getFrame(float delta) {
        TextureRegion region;

        currentState = getState();

        switch (currentState) {
            case READY:
                region = this.texture;
                break;
            case EXPLODE:
                region = (TextureRegion) bombExplodeAnimation.getKeyFrame(stateTimer, true);
                break;
            default:
                region = this.texture;

        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        //update previous state
        previousState = currentState;

        return region;
    }

    @Override
    public void reset() {
        super.reset();
        this.texture = readyBombTex;
        currentState = State.READY;
        previousState = State.READY;
    }

    private State getState() {
        if(isTouched) {
            currentState = State.EXPLODE;
        }
        return currentState;
    }

    private void initAnimations() {
        Array<TextureRegion> frames = new Array<>();


        for (int i = 1; i <= 10; i++)
            frames.add(new TextureRegion(bombAtlasRegion, i * tileWidth, 0, tileWidth, tileHeight));
        bombExplodeAnimation = new Animation(0.08f, frames);

        frames.clear();
    }

    @Override
    public void init(float x, float y, float vX, float vY) {
        super.init(x, y, vX, vY);
        isTouched = false;
    }

}