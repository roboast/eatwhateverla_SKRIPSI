package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.response.Kuliner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class DetailTempatMakan extends AppCompatActivity implements OnMapReadyCallback {
    public static final String EXTRA_TEMPAT_MAKAN = "extra_tempat_makan";
    private ImageView back, home;
    private TextView tv_nama;
    private ImageView iv_gambar;
    private TextView tv_deskripsi;
    private TextView tv_alamat;
    private SupportMapFragment mapFragment;
    private GoogleMap gMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_tempat);

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
                Intent intent = new Intent(DetailTempatMakan.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tv_nama = findViewById(R.id.tv_nama_tempat);
        tv_alamat = findViewById(R.id.tv_alamat);
        tv_deskripsi = findViewById(R.id.tv_deskripsi);
        iv_gambar = findViewById(R.id.gambar_tempat);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getTempat();
    }

    void getTempat(){
        Kuliner tempatMakan = getIntent().getParcelableExtra(EXTRA_TEMPAT_MAKAN);
        tv_nama.setText(tempatMakan.getNamaKuliner());
        tv_alamat.setText(tempatMakan.getDeskripsi());
        tv_deskripsi.setText(tempatMakan.getDeskripsi());
        Picasso.get().load(tempatMakan.getGambar()).into(iv_gambar);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Kuliner tempatMakan = getIntent().getParcelableExtra(EXTRA_TEMPAT_MAKAN);
        gMap = googleMap;
        gMap.setMinZoomPreference(15);
        final LatLng ll = new LatLng(tempatMakan.getLatitude(),tempatMakan.getLongitude());
        gMap.addMarker(new MarkerOptions().position(ll).title(tempatMakan.getNamaKuliner()));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(ll));

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent = new Intent(DetailTempatMakan.this, DirectionActivity.class);
                intent.putExtra("latDest", ll.latitude);
                intent.putExtra("lngDest", ll.longitude);
                startActivity(intent);
            }
        });
    }
}
