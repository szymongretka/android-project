package com.mygdx.game.game_object.player.ship;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Ship {

    protected float speed = 150f;
    protected String name;

    protected boolean active; //is bought but not selected
    protected boolean available; //is not bought but available to buy

    protected TextureRegion textureRegion;

    public Ship(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public float getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

}
