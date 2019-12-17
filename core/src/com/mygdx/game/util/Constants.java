package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;

public class Constants {

    public static final float HEIGHT = Gdx.graphics.getHeight();
    public static final float WIDTH = Gdx.graphics.getWidth();
    public static final float PPM = 12f; //Pixels per meter

    public static final int BASIC_ENEMY_WIDTH = 6;
    public static final int BASIC_ENEMY_HEIGHT = 6;

    public static final float BASICBULLETWIDTH = 4f;
    public static final float BASICBULLETHEIGHT = 4f;
    public static final float BASIC_BULLET_RoF = 2000f;

    public static final int PLAYER_WIDTH = 10;
    public static final int PLAYER_HEIGHT = 14;

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

}
