package com.mygdx.game.game_object.item.bonus;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_object.item.Item;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;

import static com.mygdx.game.util.Constants.ITEM_HEIGHT;
import static com.mygdx.game.util.Constants.ITEM_WIDTH;


public class RevertMovement extends Item {

    private float velY = 100;

    public RevertMovement(World world) {
        super(world, 0, 0, ITEM_WIDTH, ITEM_HEIGHT);
        this.texture = GameScreen.revertImage;
    }

    @Override
    public void update(float deltaTime) {
        this.body.setLinearVelocity(0, -velY * deltaTime);
    }


    @Override
    public void takenByPlayer(PlayerSpaceship playerSpaceship) {
        super.takenByPlayer(playerSpaceship);
        playerSpaceship.setSpeed(-playerSpaceship.getSpeed());

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                playerSpaceship.setSpeed(Math.abs(playerSpaceship.getSpeed()));
            }
        }, 2);

    }


}