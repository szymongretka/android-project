package com.mygdx.game.game_object.player.ship;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BasicShip extends Ship {

    public BasicShip(TextureRegion textureRegion){
        super(textureRegion);
        active = false; //is bought but not selected
        available = true;
        speed = 150f;
        name = "basicShip";
    }

}
