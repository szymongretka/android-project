package com.mygdx.game.game_object.item.bonus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.item.Item;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.Constants;

import static com.mygdx.game.util.Constants.ITEM_HEIGHT;
import static com.mygdx.game.util.Constants.ITEM_WIDTH;

public class NumberOfBullets extends Item {

    private float velY = 400;

    public NumberOfBullets(World world) {
        super(world, 0, 0, ITEM_WIDTH, ITEM_HEIGHT, 5, 0);
        this.body.setLinearVelocity(0, -velY * Gdx.graphics.getDeltaTime());
        this.texture = GameScreen.shieldImage;
    }

    @Override
    public void update(float deltaTime) {
        //this.getBody().get
    }

    @Override
    public void takenByPlayer(PlayerSpaceship playerSpaceship) {
        super.takenByPlayer(playerSpaceship);
        if(GameScreen.NUMBER_OF_BULLETS < Constants.MAX_NUMBER_OF_BULLETS)
            GameScreen.NUMBER_OF_BULLETS++;
    }
}
