package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;

public class Constants {

    public static final int HEIGHT = Gdx.graphics.getHeight();
    public static final int WIDTH = Gdx.graphics.getWidth();
    public static final int PPM = 12; //Pixels per meter

    public static final int BASIC_ENEMY_WIDTH = 6;
    public static final int BASIC_ENEMY_HEIGHT = 6;

    public static final int BASICBULLETWIDTH = 4;
    public static final int BASICBULLETHEIGHT = 4;
    public static final int BASIC_BULLET_RoF = 2000;

    public static final int PLAYER_WIDTH = 8;
    public static final int PLAYER_HEIGHT = 8;

    public static final int COIN_WIDTH = 4;
    public static final int COIN_HEIGHT = 6;

    public static final int ITEM_WIDTH = 4;
    public static final int ITEM_HEIGHT = 5;

    //mask & category bits
    public static final short BIT_PLAYER = 1;
    public static final short BIT_ENEMY = 2;
    public static final short BIT_BULLET = 4;
    public static final short BIT_OBSTACLE = 8;
    public static final short BIT_ITEM = 16;

}
