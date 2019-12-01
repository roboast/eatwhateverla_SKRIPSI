package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.response.Kuliner;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPrefMood extends AppCompatActivity  {
    private TextView textView;
    private ImageView back, home;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pref_mood);
        back = findViewById(R.id.btn_back);
        home = findViewById(R.id.btn_home);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPrefMood.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        rv = findViewById(R.id.rv_makan);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        textView = findViewById(R.id.tv_tanya);

        String emo = getIntent().getStringExtra("emosi");

        textView.setText("Emosi anda sekarang adalah: "+emo);
        cekLokasi();
    }

    void cekLokasi(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.ACCESS_FINE_LOCATION  }, 1 );
        }
        else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    getMakan(location.getLatitude(),location.getLongitude());
                }
            });
        }
    }

    void getMakan(final double lat, final double longi){
        String latitude = String.valueOf(lat);
        String longitude = String.valueOf(longi);
        String emo = getIntent().getStringExtra("emosi");

        ApiServices apiServices = InitRetrofit.getInstanceWebService();
        Call<ArrayList<Kuliner>> getAllRekom = apiServices.getRekom(emo,latitude,longitude);

        getAllRekom.enqueue(new Callback<ArrayList<Kuliner>>() {
            @Override
            public void onResponse(Call<ArrayList<Kuliner>> call, Response<ArrayList<Kuliner>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Kuliner> tempatMakan = response.body();
                    adapter = new AdapterTempatMakan(tempatMakan, getApplicationContext());
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Kuliner>> call, Throwable t) {
               Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            cekLokasi();
        } else {
            Toast.makeText(getApplicationContext(), "Nyalakan",Toast.LENGTH_LONG).show();
        }
    }
}
