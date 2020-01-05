package com.mygdx.game.game_object.player.ship;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BigShip extends Ship {

    public BigShip(TextureRegion textureRegion) {
        super(textureRegion);
        speed = 100f;
        name = "bigShip";
        price = 25;
        HP = 50;
    }

    public BigShip() {
        speed = 100f;
        name = "bigShip";
        price = 25;
        HP = 50;
    }

}
