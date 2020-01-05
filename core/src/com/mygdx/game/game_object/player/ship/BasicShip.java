package com.mygdx.game.game_object.player.ship;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BasicShip extends Ship {

    public BasicShip(TextureRegion textureRegion){
        super(textureRegion);
        speed = 150f;
        name = "basicShip";
        price = 20;
        HP = 25;
    }

    public BasicShip(){
        speed = 150f;
        name = "basicShip";
        price = 20;
        HP = 25;
    }

}
