package com.mygdx.game.game_object.item.coin;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.item.Item;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;

import static com.mygdx.game.util.Constants.COIN_HEIGHT;
import static com.mygdx.game.util.Constants.COIN_WIDTH;

public class Coin extends Item {

    private float velY = 500;

    public Coin(World world) {
        super(world, 0, 0, COIN_WIDTH, COIN_HEIGHT);
        this.texture = GameScreen.coinImage;
    }

    @Override
    public void update(float deltaTime) {
        this.body.setLinearVelocity(0, -velY * deltaTime);
    }

    @Override
    public void takenByPlayer(PlayerSpaceship playerSpaceship) {
        super.takenByPlayer(playerSpaceship);
        GameScreen.POINTS++;
    }

}