package com.mygdx.game.game_object;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.enums.ID;

import static com.mygdx.game.util.Constants.PPM;

public class Box2DBuilder {


    public Body createBox(World world, float x, float y, float width, float height, boolean isStatic) {
        Body body;

        BodyDef bodyDef = new BodyDef();
        if(isStatic)
            bodyDef.type = BodyDef.BodyType.StaticBody;
        else
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true; //don t rotate
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);

        FixtureDef def = new FixtureDef();
        def.shape = polygonShape;
        def.density = 1.0f;
        body.createFixture(def).setUserData(this);
        polygonShape.dispose();

        body.setActive(false);

        return body;
    }


}
