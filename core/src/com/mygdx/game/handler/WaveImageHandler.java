package com.mygdx.game.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import static com.mygdx.game.util.Constants.HEIGHT;
import static com.mygdx.game.util.Constants.PPM;
import static com.mygdx.game.util.Constants.WIDTH;

public class WaveImageHandler {

    private Texture texture;
    private float velX;
    private float width = 80f;
    private float height = 20f;
    private float screenPointX = WIDTH / PPM / 2f - (width / 2); //center of screen X axis
    private float positionX;
    private float positionY = HEIGHT / PPM / 2f;
    private boolean isCompleted;


    public WaveImageHandler() {
        positionX = (WIDTH / PPM) + 3f * width;
        isCompleted = true;
    }

    public void update() {
        velX = (Math.abs(positionX - screenPointX)) * 2f * Gdx.graphics.getDeltaTime() + 0.1f;
        positionX -= velX;
        if (this.positionX < -width)
            setCompleted(true);
    }

    public void initWaveImage(Texture texture) {
        isCompleted = false;
        positionX = (WIDTH / PPM) + 3f * width;
        this.texture = texture;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public float getVelX() {
        return velX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getPositionX() {
        return positionX;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
