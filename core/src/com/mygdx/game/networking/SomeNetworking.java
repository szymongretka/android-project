package com.mygdx.game.networking;

public class SomeNetworking {

    /*
    try
      {
         //I use Jackson for JSON serialisation; you'd need to change this for however you generate your JSON
         String json = mapper.writeValueAsString(mapData);

         //Create a POST request
         HttpRequest saveRequest = new HttpRequest(HttpMethods.POST);
         //Set your URL
         saveRequest.setUrl("http://localhost:8080/some/path");
         //Set the body of your HTTP request to be the JSON
         saveRequest.setContent(json);
         //Set a header so the server can tell this is JSON
         saveRequest.setHeader("Content-Type", "application/json");
         //Set a header telling the server that it's okay to send JSON back
         saveRequest.setHeader("Accept", "application/json");

         //Assuming you're using HTTP Basic
         String userCredentials = "username:password";
         String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
         saveRequest.setHeader("Authorization", basicAuth);

         //Send the request, and define a callback to execute when we get a response
         Gdx.net.sendHttpRequest(saveRequest, new HttpResponseListener()
         {
            //This is executed on successful response
            @Override
            public void handleHttpResponse(HttpResponse httpResponse)
            {
               Gdx.app.log("Net", "response code was "+httpResponse.getStatus().getStatusCode());
            }

            //This happens if it goes wrong
            @Override
            public void failed(Throwable t)
            {
               Gdx.app.log("Net", "Failed");
            }
         });
      }
      catch(IOException e)
      {
         throw new GdxRuntimeException("JSON b0rk");
      }
    */


}
