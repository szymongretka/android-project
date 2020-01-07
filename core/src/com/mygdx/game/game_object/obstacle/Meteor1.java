package com.mygdx.game.game_object.obstacle;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.Constants;

public class Meteor1 extends Obstacle {


    public Meteor1(World world) {
        super(world, 0, 0, Constants.METEOR1_WIDTH, Constants.METEOR1_HEIGHT, 5, 1);
        this.texture = GameScreen.meteor1;
        this.velX = 100f;
        this.velY = (-1500f);
    }

    @Override
    public void update(float deltaTime) {

    }


    public void hitPlayer(PlayerSpaceship playerSpaceship) {
        this.reset();
        playerSpaceship.gotShot = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                playerSpaceship.gotShot = false;
            }
        }, 1);
    }


}
