package com.mygdx.game.game_object.player.ship;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Ship {

    protected float speed = 150f;
    protected String name;

    protected boolean active; //is bought and selected
    protected boolean available; //is bought
    protected int price;

    protected TextureRegion textureRegion;

    public Ship(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public Ship() {}

    public float getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public int getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
