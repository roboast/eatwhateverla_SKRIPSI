package com.example.myapplication;

import com.example.myapplication.response.Kuliner;
import com.example.myapplication.response.ResponseRoute;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("json")
    Call<ResponseRoute> request_route(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("departure_time") String departure_time,
            @Query("key") String key
    );

    @GET("cfmakan.php")
    Call<ArrayList<Kuliner>> getRekom(
            @Query("lat") String lat,
            @Query("long") String longi
    );


    @POST("detect")
    Call<ResponseRoute> getEmosi(
            @Header("Content-Type") String content,
            @Header("Ocp-Apim-Subscription-Key") String key,
            @Query("returnFaceAttributes") String emotion,
            @Body JsonObject jsonObject
    );
}
