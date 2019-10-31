package com.mygdx.game.game_object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.enums.ID;

public class PlayerSpaceship extends GameObject {

    public PlayerSpaceship(float x, float y, int hp, int velocityX, int velocityY, Texture texture) {
        super(x, y, ID.PLATYER, hp, texture);
        velX = velocityX;
        velY = velocityY;
    }

    @Override
    public void update(float deltaTime) {
        //y += velY * deltaTime;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }



}
