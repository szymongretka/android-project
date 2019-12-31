package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;

public class Constants {

    public static final float HEIGHT = Gdx.graphics.getHeight();
    public static final float WIDTH = Gdx.graphics.getWidth();
    public static final float PPM = 12f; //Pixels per meter

    public static final int ORANGE_SPACESHIP1_WIDTH = 8;
    public static final int ORANGE_SPACESHIP1_HEIGHT = 8;

    public static final int ORANGE_SPACESHIP2_WIDTH = 8;
    public static final int ORANGE_SPACESHIP2_HEIGHT = 12;

    public static final int ORANGE_SPACESHIP3_WIDTH = 8;
    public static final int ORANGE_SPACESHIP3_HEIGHT = 12;

    public static final int ORANGE_SPACESHIP4_WIDTH = 10;
    public static final int ORANGE_SPACESHIP4_HEIGHT = 10;

    public static final int BOSS1_WIDTH = 20;
    public static final int BOSS1_HEIGHT = 20;


    public static final float BASICBULLETWIDTH = 4f;
    public static final float BASICBULLETHEIGHT = 5f;
    public static final float BASIC_BULLET_RoF = 2000f;

    public static final int PLAYER_WIDTH = 12;
    public static final int PLAYER_HEIGHT = 16;
    public static final int BASIC_SHIP_HP = 10;

    public static final int COIN_WIDTH = 4;
    public static final int COIN_HEIGHT = 6;

    public static final int ITEM_WIDTH = 4;
    public static final int ITEM_HEIGHT = 5;

    public static final int BASIC_SHIELD_WIDTH = 10;
    public static final int BASIC_SHIELD_HEIGHT = 10;

    //mask & category bits
    public static final short BIT_PLAYER = 1;
    public static final short BIT_ENEMY = 2;
    public static final short BIT_BULLET = 4;
    public static final short BIT_ITEM = 8;
    public static final short BIT_OBSTACLE = 16;
    public static final short BIT_ENEMY_BULLET = 2;



    public static final String LOCALHOST_URL = "http://192.168.1.102:8080/";

}
