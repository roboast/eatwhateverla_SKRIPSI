package com.example.myapplication;

import com.example.myapplication.response.Emotion;
import com.example.myapplication.response.FaceAttributes;
import com.example.myapplication.response.Kuliner;
import com.example.myapplication.response.ResponseAzure;
import com.example.myapplication.response.ResponseFoto;
import com.example.myapplication.response.ResponseRoute;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Headers({"Content-Type: application/json", "Ocp-Apim-Subscription-Key: 667cdde46a6440af974b82f3671eb214"})
    @POST("detect")
    Call<ArrayList<ResponseAzure>> getEmosi(
            @Query("returnFaceAttributes") String emotion,
            @Body JsonObject jsonObject
    );

    @Multipart
    @POST("uploadfoto.php")
    Call<ResponseFoto> uploadFoto(
            @Part ("id_user") RequestBody iduser,
            @Part MultipartBody.Part foto
    );
}
