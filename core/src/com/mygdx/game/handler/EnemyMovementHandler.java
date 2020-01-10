package com.mygdx.game.handler;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.util.MessageType;

import static com.mygdx.game.util.Constants.PPM;
import static com.mygdx.game.util.Constants.WIDTH;
import static com.mygdx.game.util.Constants.HEIGHT;


public class EnemyMovementHandler implements Telegraph {

    private enum State {DEFAULT, CLASSIC, LEFT_RIGHT, CIRCULAR, GO_BOTTOM, UPPER_BOUND, RAM, RANDOM}

    private State currentLevelAndWave;
    private Array<Enemy> enemyArray;
    private SpaceInvaderApp game;
    private float screenX = WIDTH / PPM;
    private float screenY = HEIGHT / PPM;


    private CatmullRomSpline<Vector2> enemyCirclePath;
    private CatmullRomSpline<Vector2> enemyUpperBoundPath;
    private CatmullRomSpline<Vector2> playerPositionPath;
    private CatmullRomSpline<Vector2> goBottomPath;
    private Vector2 targetPosition = new Vector2();

    public EnemyMovementHandler(SpaceInvaderApp game, Array<Enemy> enemyArray) {
        this.game = game;
        this.enemyArray = enemyArray;
        currentLevelAndWave = State.DEFAULT;
        game.messageManager.addListeners(this, MessageType.CLASSIC_SPACE_INVADER_MOVE,
                MessageType.LEFT_RIGHT_MOVE, MessageType.CIRCULAR_MOVE, MessageType.GO_BOTTOM_MOVE,
                MessageType.UPPER_BOUND_MOVE, MessageType.RAM_MOVE);

        initEnemyPaths();


    }

    public void update(float delta) {
        switch (currentLevelAndWave) {
            case CLASSIC:
                classicPattern(enemyArray, delta);
                break;
            case LEFT_RIGHT:
                leftRightPattern(enemyArray, delta);
                break;
            case CIRCULAR:
                circularPattern(enemyArray, delta);
                break;
            case GO_BOTTOM:
                goBottomPattern(enemyArray, delta);
                break;
            case UPPER_BOUND:
                upperBoundPattern(enemyArray, delta);
                break;
            case RAM:
                ramPlayerPattern(enemyArray, delta);
                break;
            case DEFAULT:
                break;
        }

    }

    private void circularPattern(Array<Enemy> enemyArray, float delta) {
        if (!enemyArray.isEmpty()) {
            for (Enemy e : enemyArray) {

                e.enemyTime += delta;
                float maxHuntTime = 6.0f; // Hunt will last for 4 seconds, get a fraction value of this between 0 and 1.
                float f = e.enemyTime / maxHuntTime;
                if (f <= 1.0f) {
                    Vector2 bodyPosition = e.getBody().getWorldCenter();
                    enemyCirclePath.valueAt(targetPosition, f);
                    Vector2 positionDelta = (new Vector2(targetPosition)).sub(bodyPosition);

                    e.getBody().setLinearVelocity(positionDelta.scl(10));
                } else {
                    e.enemyTime = 0;
                }
            }
        }
    }

    private void upperBoundPattern(Array<Enemy> enemyArray, float delta) {
        if (!enemyArray.isEmpty()) {
            for (Enemy e : enemyArray) {

                e.enemyTime += delta;
                float maxHuntTime = 12.0f; // Hunt will last for 12 seconds, get a fraction value of this between 0 and 1.
                float f = e.enemyTime / maxHuntTime;
                if (f <= 1.0f) {
                    Vector2 bodyPosition = e.getBody().getWorldCenter();
                    enemyUpperBoundPath.valueAt(targetPosition, f);
                    Vector2 positionDelta = (new Vector2(targetPosition)).sub(bodyPosition);

                    e.getBody().setLinearVelocity(positionDelta.scl(10));
                } else {
                    e.enemyTime = 0;
                }
            }
        }
    }


    private void goBottomPattern(Array<Enemy> enemyArray, float delta) {
        if (!enemyArray.isEmpty()) {
            for (Enemy e : enemyArray) {
                e.enemyTime += delta;
                float maxHuntTime = 3.0f; // Hunt will last for 12 seconds, get a fraction value of this between 0 and 1.
                float f = e.enemyTime / maxHuntTime;
                if (f <= 1.0f) {
                    Vector2 bodyPosition = e.getBody().getWorldCenter();
                    goBottomPath.valueAt(targetPosition, f);
                    Vector2 positionDelta = (new Vector2(targetPosition)).sub(bodyPosition);

                    e.getBody().setLinearVelocity(positionDelta.scl(10));
                } else {
                    e.enemyTime = 0;
                }
            }
        }
    }

    private void ramPlayerPattern(Array<Enemy> enemyArray, float delta) {
        if (!enemyArray.isEmpty()) {
            for (Enemy e : enemyArray) {
                e.enemyTime += delta;
                float maxHuntTime = 3.0f; // Hunt will last for 12 seconds, get a fraction value of this between 0 and 1.
                float f = e.enemyTime / maxHuntTime;
                if (f <= 1.0f) {
                    Vector2 bodyPosition = e.getBody().getWorldCenter();
                    playerPositionPath.valueAt(targetPosition, f);
                    Vector2 positionDelta = (new Vector2(targetPosition)).sub(bodyPosition);

                    e.getBody().setLinearVelocity(positionDelta.scl(10));
                } else {
                    e.ramComplete = true;
                }
            }
        }
    }

    private void classicPattern(Array<Enemy> enemyArray, float delta) {
        if (!enemyArray.isEmpty()) {
            Enemy enemy = enemyArray.get(0); //doesn't matter which enemy exceeds limit
            if (Math.abs(enemy.getBody().getPosition().x - enemy.getBoundaryCoordX()) > 10f)
                for (Enemy e : enemyArray) {
                    e.setBoundaryCoordX(e.getBody().getPosition().x);
                    if (e.getBody().getLinearVelocity().x < 0)
                        e.getBody().setLinearVelocity(Math.abs(enemy.getVelX()) * delta, enemy.getVelY() * delta);
                    else
                        e.getBody().setLinearVelocity((-1) * enemy.getVelX() * delta, enemy.getVelY() * delta);

                }
        }
    }

    private void leftRightPattern(Array<Enemy> enemyArray, float delta) {
        if (!enemyArray.isEmpty()) {
            Enemy enemy = enemyArray.get(0); //doesn't matter which enemy exceeds limit
            if (Math.abs(enemy.getBody().getPosition().x - enemy.getBoundaryCoordX()) > 100f)
                for (Enemy e : enemyArray) {
                    e.setBoundaryCoordX(e.getBody().getPosition().x);
                    if (e.getBody().getLinearVelocity().x < 0)
                        e.getBody().setLinearVelocity(Math.abs(enemy.getVelX()) * delta, 0);
                    else
                        e.getBody().setLinearVelocity((-1) * enemy.getVelX() * delta, 0);
                }
        }
    }

    private void initEnemyPaths() {
        enemyCirclePath = new CatmullRomSpline<>(new Vector2[]{
                new Vector2(0.75f * screenX, 0.5f * screenY),
                new Vector2(0.5f * screenX, 0.3f * screenY),
                new Vector2(0.25f * screenX, 0.5f * screenY),
                new Vector2(0.5f * screenX, 0.7f * screenY),
        }, true);

        enemyUpperBoundPath = new CatmullRomSpline<>(new Vector2[]{
                new Vector2(0.5f * screenX, 0.8f * screenY),
                new Vector2(0.8f * screenX, 0.65f * screenY),
                new Vector2(0.5f * screenX, 0.8f * screenY),
                new Vector2(0.2f * screenX, 0.65f * screenY),

        }, true);

        goBottomPath = new CatmullRomSpline<>(new Vector2[]{
                new Vector2(0f, -10f),

        }, true);


    }


    public Array<Enemy> getEnemyArray() {
        return enemyArray;
    }

    public void setEnemyArray(Array<Enemy> enemyArray) {
        this.enemyArray = enemyArray;
    }

    @Override
    public boolean handleMessage(Telegram msg) {

        switch (msg.message) {
            case MessageType.CLASSIC_SPACE_INVADER_MOVE:
                currentLevelAndWave = State.CLASSIC;
                return true;
            case MessageType.LEFT_RIGHT_MOVE:
                currentLevelAndWave = State.LEFT_RIGHT;
                return true;
            case MessageType.GO_BOTTOM_MOVE:
                currentLevelAndWave = State.GO_BOTTOM;
                return true;
            case MessageType.CIRCULAR_MOVE:
                currentLevelAndWave = State.CIRCULAR;
                return true;
            case MessageType.UPPER_BOUND_MOVE:
                currentLevelAndWave = State.UPPER_BOUND;
                return true;
            case MessageType.RAM_MOVE:
                playerPositionPath = new CatmullRomSpline<>((Vector2[]) msg.extraInfo, true);
                currentLevelAndWave = State.RAM;
                return true;
        }

        return false;
    }
}
