package com.mygdx.game.game_object.player.ship;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BigShip extends Ship {

    public BigShip(TextureRegion textureRegion) {
        super(textureRegion);
        active = false; //is bought but not selected
        available = false; //is not bought but available to buy
        speed = 100f;
        name = "bigShip";
    }

}
