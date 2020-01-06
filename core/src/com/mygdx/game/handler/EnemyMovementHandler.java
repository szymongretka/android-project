package com.mygdx.game.handler;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.util.MessageType;


public class EnemyMovementHandler implements Telegraph {

    private enum State {DEFAULT, CLASSIC, LEFT_RIGHT, CIRCULAR, RANDOM};
    private State currentLevelAndWave;
    private Array<Enemy> enemyArray;
    private SpaceInvaderApp game;


    public EnemyMovementHandler(SpaceInvaderApp game, Array<Enemy> enemyArray) {
        this.game = game;
        this.enemyArray = enemyArray;
        currentLevelAndWave = State.DEFAULT;
        game.messageManager.addListeners(this, MessageType.CLASSIC_SPACE_INVADER_MOVE, MessageType.LEFT_RIGHT_MOVE);
    }

    public void update(float delta) {
       switch (currentLevelAndWave) {
           case CLASSIC:
                firstLevelFirstWave(enemyArray, delta);
                break;
           case LEFT_RIGHT:
                firstLevelSecondWave(enemyArray, delta);
                break;

           case DEFAULT:
                break;
        }

    }

    private void firstLevelFirstWave(Array<Enemy> enemyArray, float delta) {
        if(!enemyArray.isEmpty()) {
            Enemy enemy = enemyArray.get(0); //doesn't matter which enemy exceeds limit
            if (Math.abs(enemy.getBody().getPosition().x - enemy.getBoundaryCoordX()) > 10f)
                for (Enemy e : enemyArray) {
                    e.setBoundaryCoordX(e.getBody().getPosition().x);
                    if(e.getBody().getLinearVelocity().x < 0)
                        e.getBody().setLinearVelocity(Math.abs(enemy.getVelX()) * delta, enemy.getVelY() * delta);
                    else
                        e.getBody().setLinearVelocity((-1)*enemy.getVelX() * delta, enemy.getVelY() * delta);

                }
        }
    }

    private void firstLevelSecondWave(Array<Enemy> enemyArray, float delta) {
        if(!enemyArray.isEmpty()) {
            Enemy enemy = enemyArray.get(0); //doesn't matter which enemy exceeds limit
            if (Math.abs(enemy.getBody().getPosition().x - enemy.getBoundaryCoordX()) > 100f)
                for (Enemy e : enemyArray) {
                    e.setBoundaryCoordX(e.getBody().getPosition().x);
                    if(e.getBody().getLinearVelocity().x < 0)
                        e.getBody().setLinearVelocity(Math.abs(enemy.getVelX()) * delta, 0);
                    else
                        e.getBody().setLinearVelocity((-1)*enemy.getVelX() * delta, 0);
                }
        }
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

        }

        return false;
    }
}
