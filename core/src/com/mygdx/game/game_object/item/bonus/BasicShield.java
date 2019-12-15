package com.mygdx.game.game_object.item.bonus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_object.item.Item;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;

import static com.mygdx.game.util.Constants.ITEM_HEIGHT;
import static com.mygdx.game.util.Constants.ITEM_WIDTH;

public class BasicShield extends Item {

    private float velY = 200;

    public BasicShield(World world) {
        super(world, 0, 0, ITEM_WIDTH, ITEM_HEIGHT, 5, 0);
    }

    @Override
    public void update(float deltaTime) {
        this.body.setLinearVelocity(0, -velY * deltaTime);
        this.texture = GameScreen.shieldImage;
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
