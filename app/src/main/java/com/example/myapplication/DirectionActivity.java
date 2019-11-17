package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.response.Distance;
import com.example.myapplication.response.Duration;
import com.example.myapplication.response.LegsItem;
import com.example.myapplication.response.ResponseRoute;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

public class DirectionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocation;
    private String API_KEY = "AIzaSyDmL-1tnQwKfLHiG_Ha_pKaObmbDhWztqY";
    private TextView tvdist, tvdur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);

        tvdist = findViewById(R.id.tvd_jarak);
        tvdur = findViewById(R.id.tvd_durasi);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLastLok();
    }

    void getLastLok(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.ACCESS_FINE_LOCATION  }, 1 );
        }
        else {
            mFusedLocation.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    getDirection(location.getLatitude(), location.getLongitude());
                }
            });
        }
    }

    void getDirection(final double latNow, final double lngNow){
        final double latdest = getIntent().getDoubleExtra("latDest",0.0);
        final double lngdest = getIntent().getDoubleExtra("lngDest",0.0);

        final LatLng now = new LatLng(latNow,lngNow);
        final LatLng dest = new LatLng(latdest,lngdest);

        ApiServices api = InitRetrofit.getInstance();
        Call<ResponseRoute> routeRequest = api.request_route(latNow + "," + lngNow, latdest + "," + lngdest,"now", API_KEY);
        routeRequest.enqueue(new Callback<ResponseRoute>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseRoute> call, Response<ResponseRoute> response) {
                if (response.isSuccessful()) {
                    ResponseRoute dataDirection = response.body();
                    LegsItem dataLegs = dataDirection.getRoutes().get(0).getLegs().get(0);
                    String polylinePoint = dataDirection.getRoutes().get(0).getOverviewPolyline().getPoints();
                    List<LatLng> decodePath = PolyUtil.decode(polylinePoint);
                    mMap.addPolyline(new PolylineOptions().addAll(decodePath)
                            .width(8f).color(Color.argb(255, 56, 167, 252)))
                            .setGeodesic(true);
                    mMap.addMarker(new MarkerOptions().position(now).title("Lokasi Awal"));
                    mMap.addMarker(new MarkerOptions().position(dest).title("Lokasi Akhir"));
                    Distance dataDistance = dataLegs.getDistance();
                    Duration dataDuration = dataLegs.getDuration();
                    tvdist.setText("Jarak: " + dataDistance.getText());
                    tvdur.setText("Durasi : " + dataDuration.getText());
                    LatLngBounds.Builder latLongBuilder = new LatLngBounds.Builder();
                    latLongBuilder.include(now);
                    latLongBuilder.include(dest);
                    LatLngBounds bounds = latLongBuilder.build();
                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    int paddingMap = (int) (width * 0.2);
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, paddingMap);
                    mMap.animateCamera(cu);
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseRoute> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
