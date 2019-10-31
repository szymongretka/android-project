package com.mygdx.game.game_object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.enums.ID;

public abstract class GameObject extends Rectangle {

    protected Vector2 position;
    protected boolean alive;
    protected ID id;
    protected float velX, velY;
    protected int hp;
    protected Texture texture;

    public GameObject(float x, float y, ID id, int hp, Texture texture) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.hp = hp;
        this.texture = texture;
    }

    public GameObject(ID id, Vector2 position, boolean alive) {
        this.id = id;
        this.position = position;
        this.alive = alive;
    }

    public abstract void update(float deltaTime);
    public abstract void draw(SpriteBatch batch);


    public ID getID() {
        return id;
    }
    public void setVelX(int velX) {
        this.velX = velX;
    }
    public void setVelY(int velY) {
        this.velY = velY;
    }
    public double getVelX() {
        return velX;
    }
    public double getVelY() {
        return velY;
    }
    public void setHP(int hp){ this.hp = hp; }
    public int getHP(){return hp;}
    public boolean isAlive() {
        return alive;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }
}
