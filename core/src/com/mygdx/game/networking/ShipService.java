package com.mygdx.game.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.game.game_object.player.Player;
import com.mygdx.game.game_object.player.ship.BasicShip;
import com.mygdx.game.game_object.player.ship.BigShip;
import com.mygdx.game.game_object.player.ship.Ship;
import com.mygdx.game.util.IRequestCallback;
import com.mygdx.game.util.MyPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mygdx.game.util.Constants.LOCALHOST_URL;

public class ShipService {

    public final static String REQUEST_URL = LOCALHOST_URL + "player/mobile";

    public void createSaveShipRequest(final IRequestCallback requestCallback, Ship[] ships, MyPreferences preferences) {
        Net.HttpRequest saveRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        saveRequest.setUrl(REQUEST_URL);

        String content = initStringContent(ships, preferences);

        saveRequest.setContent(content);
        //Set a header so the server can tell this is JSON
        saveRequest.setHeader("Content-Type", "application/json");
        //Set a header telling the server that it's okay to send JSON back
        saveRequest.setHeader("Accept", "application/json");

        Gdx.net.sendHttpRequest(saveRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                requestCallback.onSucceed();
            }

            @Override
            public void failed(Throwable t) {
                System.out.println(t.getMessage());
                requestCallback.onError();
            }

            @Override
            public void cancelled() {
                requestCallback.onError();
            }
        });
    }

    private String initStringContent(Ship[] shipsArr, MyPreferences preferences) {

        Json json = new Json();
        List<Ship> ships = new ArrayList<>(Arrays.asList(shipsArr));
        Player player = new Player(preferences.getNickname(), ships);

        json.addClassTag("basicShip", BasicShip.class);
        json.addClassTag("bigShip", BigShip.class);
        json.setOutputType(JsonWriter.OutputType.json);
        //json.setUsePrototypes(false);
        json.setElementType(Player.class, "ships", Ship.class);

        return json.prettyPrint(player);
    }

}
