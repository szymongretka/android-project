package com.mygdx.game.game_object.obstacle;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.Constants;

import java.util.Random;

public class Meteor1 extends Obstacle {

    public Meteor1(World world) {
        super(world, 0, 0, Constants.METEOR1_WIDTH, Constants.METEOR1_HEIGHT, 8, 1);

        if(MathUtils.randomBoolean(0.7f))
            this.texture = GameScreen.meteor1;
        else {
            this.texture = GameScreen.meteor2;
            setWidth(Constants.METEOR1_WIDTH * 1.5f);
            setHeight(Constants.METEOR1_HEIGHT * 1.5f);
        }

        this.velX = new Random().nextInt(2400) - 1200;
        this.velY = (-1) * (new Random().nextInt( 2400) + 1900);
    }

    @Override
    public void update(float deltaTime) {
        if(this.getHp() <= 0 && this.getBody().isActive()) {
            this.onDestroyCoordX = this.getX();
            this.onDestroyCoordY = this.getY();
            setToDestroy(true);
        }
    }


}
