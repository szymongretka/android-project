package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.screen.game.GameScreen;

public class MyPreferences {

    private Preferences prefs;
    private final String NICKNAME = "nickname";
    public final String EMPTY = "empty";
    private final String POINTS = "points";

    public MyPreferences() {
        prefs = Gdx.app.getPreferences("My Preferences");
    }

    public void updatePoints() {
        prefs.putInteger(POINTS, GameScreen.POINTS);
        prefs.flush();
    }

    public void setNickname(String nickname) {
        prefs.putString(NICKNAME, nickname);
        prefs.flush();
    }

    public int getPoints() {
        return prefs.getInteger(POINTS, 0);
    }

    public String getNickname() {
        return prefs.getString(NICKNAME, EMPTY);
    }

}
