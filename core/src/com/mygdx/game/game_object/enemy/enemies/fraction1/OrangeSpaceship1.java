package com.mygdx.game.game_object.enemy.enemies.fraction1;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game_object.enemy.Enemy;
import com.mygdx.game.game_object.player.PlayerSpaceship;
import com.mygdx.game.screen.game.GameScreen;

import static com.mygdx.game.util.Constants.ORANGE_SPACESHIP1_WIDTH;
import static com.mygdx.game.util.Constants.ORANGE_SPACESHIP1_HEIGHT;
import static com.mygdx.game.util.Constants.PPM;
import static com.mygdx.game.util.Constants.WIDTH;


public class OrangeSpaceship1 extends Enemy {

    public OrangeSpaceship1(World world) {
        super(world, 0, 0, ORANGE_SPACESHIP1_WIDTH, ORANGE_SPACESHIP1_HEIGHT, 1, 1);
        this.texture = GameScreen.fraction1OrangeShipTexture;
        this.velX = 1200f;
        this.velY = (-500f);
    }

    @Override
    public void update(float deltaTime) {
        if(this.getHp() <= 0 && this.getBody().isActive()) {
            this.onDestroyCoordX = this.getX();
            this.onDestroyCoordY = this.getY();
            reset();
        }
    }


    @Override
    public void hitPlayer(PlayerSpaceship playerSpaceship) {
        super.hitPlayer(playerSpaceship);
    }


}
