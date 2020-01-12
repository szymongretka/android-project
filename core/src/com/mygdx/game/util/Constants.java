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

    public static final int BOSS2_WIDTH = 25;
    public static final int BOSS2_HEIGHT = 25;


    public static final float BASICBULLETWIDTH = 4f;
    public static final float BASICBULLETHEIGHT = 5f;
    public static final float BASIC_BULLET_RoF = 2000f;

    public static final int PLAYER_WIDTH = 12;
    public static final int PLAYER_HEIGHT = 16;
    public static final int BASIC_SHIP_HP = 10;


    public static final int COIN_WIDTH = 4;
    public static final int COIN_HEIGHT = 6;

    public static final int ITEM_WIDTH = 8;
    public static final int ITEM_HEIGHT = 8;

    public static final int BASIC_SHIELD_WIDTH = 10;
    public static final int BASIC_SHIELD_HEIGHT = 10;

    public static final int METEOR1_WIDTH = 10;
    public static final int METEOR1_HEIGHT = 10;

    public static final int METEOR2_WIDTH = 30;
    public static final int METEOR2_HEIGHT = 30;

    public static final int MAX_NUMBER_OF_BULLETS = 4;

    //mask & category bits
    public static final short BIT_PLAYER = 1;
    public static final short BIT_ENEMY = 2;
    public static final short BIT_BULLET = 4;
    public static final short BIT_ITEM = 8;
    public static final short BIT_OBSTACLE = 16;
    public static final short BIT_ENEMY_BULLET = 2;


    //My preferences
    public static final String SHIP_ARRAY = "shipMap";
    public static final String NICKNAME = "nickname";
    public static final String EMPTY = "empty";
    public static final String POINTS = "points";
    public static final String BIG_SHIP = "bigShip";
    public static final String BASIC_SHIP = "basicShip";

    public static final String LOCALHOST_URL = "http://192.168.1.103:8080/";

}
