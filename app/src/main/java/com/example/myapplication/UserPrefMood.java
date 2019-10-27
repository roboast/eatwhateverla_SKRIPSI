package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class UserPrefMood extends AppCompatActivity implements LocationListener {
    private TextView textView;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private ArrayList<TempatMakan> tempatMakanArrayList;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pref_mood);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        rv = findViewById(R.id.rv_makan);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        tempatMakanArrayList = new ArrayList<>();

        textView = findViewById(R.id.tv_tanya);

        //String emo = getIntent().getStringExtra("emosi");

        textView.setText("Emosi anda sekarang adalah: ");
        cekLokasi();
        //getMakan();

    }

    void cekLokasi(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.ACCESS_FINE_LOCATION  }, 1 );
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,1, this);
        }
    }


    void getMakan(double lat, double longi){
        String URL = "http://192.168.137.103/cf_makan.php?lat="+lat+"&long="+longi;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        TempatMakan tempatMakan = new TempatMakan();
                        JSONObject makan = response.getJSONObject(i);
                        tempatMakan.setNama_tempat(makan.getString("nama_kuliner"));
                        tempatMakan.setDeskripsi(makan.getString("deskripsi"));
                        tempatMakan.setUrl_gambar(makan.getString("gambar"));
                        tempatMakan.setJarak(makan.getDouble("jarak"));
                        tempatMakan.setLatitude(makan.getDouble("latitude"));
                        tempatMakan.setLongitude(makan.getDouble("longitude"));
                        tempatMakanArrayList.add(tempatMakan);

                    }

                    adapter = new AdapterTempatMakan(tempatMakanArrayList, getApplicationContext());
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext())).add(jsonArrayRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // Permission was granted.
            cekLokasi();
        } else {
            Toast.makeText(getApplicationContext(), "Nyalakan",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Latitude: "+location.getLatitude()+" Longitude: "+location.getLongitude();
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        getMakan(location.getLatitude(),location.getLongitude());

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
