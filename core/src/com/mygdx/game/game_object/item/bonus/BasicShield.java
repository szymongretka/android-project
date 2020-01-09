package com.mygdx.game.game_object.item.bonus;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_object.item.Item;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;

import static com.mygdx.game.util.Constants.ITEM_HEIGHT;
import static com.mygdx.game.util.Constants.ITEM_WIDTH;

public class BasicShield extends Item {

    public BasicShield(World world) {
        super(world, 0, 0, ITEM_WIDTH, ITEM_HEIGHT, 0, 0);
        this.texture = GameScreen.shieldImage;
        this.velY = (-1500f);
        this.body.setLinearVelocity(0, velY);
    }

    @Override
    public void update(float deltaTime) {
        //this.getBody().get
    }

    @Override
    public void takenByPlayer(PlayerSpaceship playerSpaceship) {
        super.takenByPlayer(playerSpaceship);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                System.out.println("player pos x: " + playerSpaceship.getBody().getPosition().x);
            }
        }, 0, 2);
    }


}
