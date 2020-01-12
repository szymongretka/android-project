package com.mygdx.game.game_object.item.bonus;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_object.item.Item;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;

import static com.mygdx.game.util.Constants.ITEM_HEIGHT;
import static com.mygdx.game.util.Constants.ITEM_WIDTH;

public class AddHealth extends Item {

    public AddHealth(World world) {
        super(world, 0, 0, ITEM_WIDTH, ITEM_HEIGHT, 0, 0);
        this.texture = GameScreen.addHealthImage;
        this.velY = (-2500f);
        this.body.setLinearVelocity(0, velY);
    }

    @Override
    public void update(float deltaTime) {
        //this.getBody().get
    }

    @Override
    public void takenByPlayer(PlayerSpaceship playerSpaceship) {
        super.takenByPlayer(playerSpaceship);
        if(playerSpaceship.getHp() < playerSpaceship.getTotalHP())
            playerSpaceship.setHp(playerSpaceship.getHp() + 1);
    }


}
