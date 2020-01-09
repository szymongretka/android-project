package com.mygdx.game.game_object.player;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.player.ship.Ship;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.MessageType;

import static com.mygdx.game.util.Constants.BASIC_SHIP_HP;
import static com.mygdx.game.util.Constants.BIT_ENEMY;
import static com.mygdx.game.util.Constants.BIT_ENEMY_BULLET;
import static com.mygdx.game.util.Constants.BIT_ITEM;
import static com.mygdx.game.util.Constants.BIT_OBSTACLE;
import static com.mygdx.game.util.Constants.BIT_PLAYER;
import static com.mygdx.game.util.Constants.HEIGHT;
import static com.mygdx.game.util.Constants.PLAYER_HEIGHT;
import static com.mygdx.game.util.Constants.PLAYER_WIDTH;
import static com.mygdx.game.util.Constants.PPM;
import static com.mygdx.game.util.Constants.WIDTH;


public class PlayerSpaceship extends Box2DObject implements Telegraph {

    private int tileWidth = 80;
    private int tileHeight = 127;
    private boolean isDead;

    public boolean gotShot = false;

    public enum State {STRAIGHT, TURNING_RIGHT, TURNING_LEFT, DEAD, GOT_HIT}

    public State currentState;
    public State previousState;

    private TextureRegion playerStraight; //player flying straight
    private TextureRegion playerDead;
    private Ship ship;
    private Animation playerMoveRightAnimation; //player turning right
    private Animation playerMoveLeftAnimation;
    private Animation playerGotHitAnimation;
    private Animation playerExplodeAnimation;
    private float stateTimer;
    private int totalHP;

    private float speed;
    private Vector2 direction = new Vector2();


    public PlayerSpaceship(GameScreen screen) {
        super(screen.world, 32, 32, PLAYER_WIDTH, PLAYER_HEIGHT, BASIC_SHIP_HP, 0,
                BodyDef.BodyType.DynamicBody, BIT_PLAYER, (short) (BIT_ENEMY | BIT_ITEM | BIT_ENEMY_BULLET | BIT_OBSTACLE), (short) 0, false);

        this.ship = screen.preferences.getActiveShip();
        this.setHp(ship.getHP());
        this.setSpeed(ship.getSpeed());
        this.totalHP = getHp();
        //this.width = getShip().getWidth();

        currentState = State.STRAIGHT;
        previousState = State.STRAIGHT;
        stateTimer = 0;

        initAnimations(screen);

    }

    private void initAnimations(GameScreen screen) {
        Array<TextureRegion> frames = new Array<>();

        if (ship.getName().equals(Constants.BASIC_SHIP)) {
            //get run animation frames and add them to animation
            for (int i = 5; i >= 1; i--)
                frames.add(new TextureRegion(screen.spaceshipAtlasRegion, i * tileWidth, 0, tileWidth, tileHeight));
            playerMoveLeftAnimation = new Animation(0.1f, frames);

            frames.clear();

            for (int i = 6; i <= 11; i++)
                frames.add(new TextureRegion(screen.spaceshipAtlasRegion, i * tileWidth, 0, tileWidth, tileHeight));
            playerMoveRightAnimation = new Animation(0.1f, frames);

            frames.clear();

            for (int i = 4; i >= 1; i--)
                frames.add(new TextureRegion(screen.spaceshipHitAtlasRegion, i * tileWidth, 0, tileWidth, tileHeight));
            for (int i = 1; i <= 4; i++)
                frames.add(new TextureRegion(screen.spaceshipHitAtlasRegion, i * tileWidth, 0, tileWidth, tileHeight));
            playerGotHitAnimation = new Animation(0.1f, frames);

            frames.clear();

            playerStraight = new TextureRegion(screen.spaceshipAtlasRegion, 5 * tileWidth, 0, tileWidth, tileHeight);

        } else if (ship.getName().equals(Constants.BIG_SHIP)) {
            //TODO ADD NEW SPACESHIP
        }
    }


    @Override
    public void update(float deltaTime) {
        if (getHp() <= 0)
            MessageManager.getInstance().dispatchMessage(MessageType.YOU_DIED_SCREEN);
        if(!isInBounds()){
            if(!isInLeftBound() && getBody().getLinearVelocity().x < 0)
                getBody().setLinearVelocity(0, getBody().getLinearVelocity().y);
            if(!isInBottomBound() && getBody().getLinearVelocity().y < 0)
                getBody().setLinearVelocity(getBody().getLinearVelocity().x, 0);

        }


    }

    public void move(Object touchPosInfo) {
        Vector3 vector3 = (Vector3) touchPosInfo;
        direction.set(vector3.x, vector3.y);
        direction.sub(this.getBody().getPosition());
        direction.nor();
        direction = direction.scl(speed);
        this.getBody().setLinearVelocity(direction.x, direction.y);

    }


    @Override
    public boolean handleMessage(Telegram msg) {

        switch (msg.message) {
            case MessageType.PLAYER_STOP:
                stop();
                return true;
            case MessageType.PLAYER_MOVE:
                move(msg.extraInfo);
                break;
        }

        return false;
    }

    public void stop() {
        this.getBody().setLinearVelocity(0, 0);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public TextureRegion getFrame(float delta) {
        //get current state. ie. flying straight, turning left, right
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch (currentState) {
            case DEAD:
                region = playerDead;
                break;
            case TURNING_LEFT:
                region = (TextureRegion) playerMoveLeftAnimation.getKeyFrame(stateTimer, true);
                break;
            case TURNING_RIGHT:
                region = (TextureRegion) playerMoveRightAnimation.getKeyFrame(stateTimer, true);
                break;
            case GOT_HIT:
                region = (TextureRegion) playerGotHitAnimation.getKeyFrame(stateTimer, true);
                break;
            case STRAIGHT:
            default:
                region = playerStraight;
                break;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }

    public State getState() {
        //Test to Box2D for velocity on the X and Y-Axis
        if (isDead)
            return State.DEAD;
        else if (this.getBody().getLinearVelocity().x > 0 && !gotShot)
            return State.TURNING_RIGHT;
        else if (this.getBody().getLinearVelocity().x < 0 && !gotShot)
            return State.TURNING_LEFT;
        else if (!this.getBody().isActive() || gotShot)
            return State.GOT_HIT;
            //if none of these return then he must be standing
        else
            return State.STRAIGHT;
    }


    private boolean isInBounds() {
        return this.getBody().getPosition().x > 0 && this.getBody().getPosition().x < WIDTH / PPM
                && this.getBody().getPosition().y > 0 && this.getBody().getPosition().y > HEIGHT / PPM;
    }

    private boolean isInLeftBound() {
        return this.getBody().getPosition().x > 0;
    }

    private boolean isInRightBound() {
        return this.getBody().getPosition().x < WIDTH / PPM;
    }

    private boolean isInUpperBound() {
        return this.getBody().getPosition().y < HEIGHT / PPM;
    }

    private boolean isInBottomBound() {
        return this.getBody().getPosition().x > 0;
    }

    public boolean isDead() {
        return isDead;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getTotalHP() {
        return totalHP;
    }
}
