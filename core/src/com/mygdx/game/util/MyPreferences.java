package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.game_object.player.ship.Ship;
import com.mygdx.game.screen.game.GameScreen;

import static com.mygdx.game.util.Constants.EMPTY;
import static com.mygdx.game.util.Constants.NICKNAME;
import static com.mygdx.game.util.Constants.POINTS;
import static com.mygdx.game.util.Constants.SHIP_ARRAY;


public class MyPreferences {

    private Preferences prefs;

    public MyPreferences() {
        prefs = Gdx.app.getPreferences("My Preferences");
    }


    public void updatePoints() {
        prefs.putInteger(POINTS, GameScreen.POINTS);
        prefs.flush();
    }

    public void setNewAmountOfPoints(Integer points) {
        prefs.putInteger(POINTS, points);
        prefs.flush();
    }

    public void setNickname(String nickname) {
        prefs.putString(NICKNAME, nickname);
        prefs.flush();
    }

    public int getPoints() {
        return prefs.getInteger(POINTS, 50);
    }

    public String getNickname() {
        return prefs.getString(NICKNAME, EMPTY);
    }


    public void initShipMap(Ship[] ships) {
        Json json = new Json();
        prefs.putString(SHIP_ARRAY, json.toJson(ships));
        prefs.flush();
    }

    public Ship[] getShipArray() {
        Json json = new Json();
        String serializedShips = prefs.getString(SHIP_ARRAY);
        return json.fromJson(Ship[].class, serializedShips);
    }

    public void setActiveShip(Ship ship) {
        Json json = new Json();
        Ship[] ships = json.fromJson(Ship[].class, prefs.getString(SHIP_ARRAY));
        for(Ship s : ships) {
            if(s.getClass() == ship.getClass())
                s.setActive(true);
            else {
                s.setActive(false);
            }
        }
        prefs.putString(SHIP_ARRAY, json.toJson(ships));
        prefs.flush();
    }

    public void buyShip(Class<?> clazz) {
        Json json = new Json();
        Ship[] ships = json.fromJson(Ship[].class, prefs.getString(SHIP_ARRAY));
        for(Ship s : ships)
            if(s.getClass() == clazz)
               s.setAvailable(true);
        prefs.putString(SHIP_ARRAY, json.toJson(ships));
        prefs.flush();
    }

}
