package com.mygdx.game.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.mygdx.game.util.IRequestCallback;

public class ScoreService {

    public final static String REQUEST_URL = "http://192.168.1.100:8080/highScore/mobile";

    public void createScoreRequest(final IRequestCallback requestCallback) {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest request = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(REQUEST_URL).build();
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(HttpResponse httpResponse) {
                System.out.println("Result:");
                System.out.println(httpResponse.getResultAsString());
                System.out.println("---------------");

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

}
