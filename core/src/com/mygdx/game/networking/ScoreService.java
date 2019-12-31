package com.mygdx.game.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.mygdx.game.util.IRequestCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.util.Constants.LOCALHOST_URL;


public class ScoreService {


    public final static String REQUEST_URL = LOCALHOST_URL + "highScore/mobile";

    private Map<String, Long> mapOfTopScores;

    public ScoreService() {
        mapOfTopScores = new HashMap<>();
    }

    public void createScoreRequest(final IRequestCallback requestCallback) {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest request = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(REQUEST_URL).build();
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(HttpResponse httpResponse) {
                parseResponse(httpResponse.getResultAsString());
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

    public Map<String, Long> getMapOfTopScores() {
        return mapOfTopScores;
    }

    private void parseResponse(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject innerObj = jsonArray.getJSONObject(i);
                mapOfTopScores.put(innerObj.getString("nickname"), innerObj.getLong("score"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
