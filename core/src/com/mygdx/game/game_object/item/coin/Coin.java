package com.mygdx.game.game_object.item.coin;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.item.Item;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;

import static com.mygdx.game.util.Constants.COIN_HEIGHT;
import static com.mygdx.game.util.Constants.COIN_WIDTH;

public class Coin extends Item {


    public Coin(World world) {
        super(world, 0, 0, COIN_WIDTH, COIN_HEIGHT, 0, 0);
        this.texture = GameScreen.coinImage;
        this.velY = (-2500f);
        this.body.setLinearVelocity(0, velY);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void takenByPlayer(PlayerSpaceship playerSpaceship) {
        super.takenByPlayer(playerSpaceship);
        GameScreen.POINTS++;
    }

}
