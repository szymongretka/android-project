package com.mygdx.game.game_object.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.player.PlayerSpaceship;

import static com.mygdx.game.util.Constants.BIT_ITEM;
import static com.mygdx.game.util.Constants.BIT_PLAYER;

public abstract class Item extends Box2DObject {

    protected TextureRegion texture;
    protected boolean toDestroy;

    public Item(World world, float x, float y, float width, float height, int hp, int damage) {
        super(world, x, y, width, height, hp, damage, BodyDef.BodyType.DynamicBody,
                BIT_ITEM, BIT_PLAYER, (short) 0, false);
    }

    public void takenByPlayer(PlayerSpaceship playerSpaceship) {
        this.setToDestroy(true);
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public boolean isToDestroy() {
        return toDestroy;
    }

    public void setToDestroy(boolean toDestroy) {
        this.toDestroy = toDestroy;
    }

}
