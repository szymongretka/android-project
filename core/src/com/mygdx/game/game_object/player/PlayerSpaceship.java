package com.mygdx.game.game_object.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.Box2DObject;
import com.mygdx.game.game_object.item.Item;
import com.mygdx.game.screen.game.GameScreen;

import static com.mygdx.game.util.Constants.BIT_ENEMY;
import static com.mygdx.game.util.Constants.BIT_ITEM;
import static com.mygdx.game.util.Constants.BIT_PLAYER;
import static com.mygdx.game.util.Constants.PLAYER_HEIGHT;
import static com.mygdx.game.util.Constants.PLAYER_WIDTH;


public class PlayerSpaceship extends Box2DObject {


    private float width = 32;
    private float height = 32;
    private float speed = 500;
    private Vector2 direction = new Vector2();

    public PlayerSpaceship(World world) {
        super(world, 32, 32, PLAYER_WIDTH, PLAYER_HEIGHT, 0, 0,
                BodyDef.BodyType.DynamicBody, BIT_PLAYER, (short) (BIT_ENEMY | BIT_ITEM), (short) 0, false);
    }

    @Override
    public void update(float deltaTime) {
        //this.body.setLinearVelocity(100, 100);
    }

    public void move(float x, float y) {
        direction.set(x, y);
        direction.sub(this.getBody().getPosition());
        direction.nor();
        this.getBody().setLinearVelocity(direction.scl(speed));
    }
    public void stop() {
        this.getBody().setLinearVelocity(0, 0);
    }

    public void pickItem(Item item) {
        GameScreen.POINTS++;
        item.setToDestroy(true);
    }

}
