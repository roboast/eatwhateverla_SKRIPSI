package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitRetrofit {
    public static String URL_FACE = "https://skripsi.cognitiveservices.azure.com/face/v1.0/";
    public static String BASE_URL = "https://maps.googleapis.com/maps/api/directions/";
    public static String URL_WEBSERVICE = "https://makanla.000webhostapp.com/tempat/";


    public static Retrofit setInitAzure(){
        return new Retrofit.Builder().baseUrl(URL_FACE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static Retrofit setInitMaps(){
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static Retrofit setInitWebService(){
        return new Retrofit.Builder().baseUrl(URL_WEBSERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiServices getInstanceAzure(){
        return setInitAzure().create(ApiServices.class);
    }
    public static ApiServices getInstanceMaps(){
        return setInitMaps().create(ApiServices.class);
    }

    public static ApiServices getInstanceWebService(){
        return setInitWebService().create(ApiServices.class);
    }
}
