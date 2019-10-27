package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class TempatMakan implements Parcelable {
    private String nama_tempat;
    private String deskripsi;
    private String url_gambar;
    private double latitude;
    private double longitude;
    private double jarak;

    public String getNama_tempat() {
        return nama_tempat;
    }

    public void setNama_tempat(String nama_tempat) {
        this.nama_tempat = nama_tempat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getUrl_gambar() {
        return url_gambar;
    }

    public void setUrl_gambar(String url_gambar) {
        this.url_gambar = url_gambar;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getJarak() {
        return jarak;
    }

    public void setJarak(double jarak) {
        this.jarak = jarak;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nama_tempat);
        dest.writeString(this.deskripsi);
        dest.writeString(this.url_gambar);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.jarak);
    }

    public TempatMakan() {
    }

    protected TempatMakan(Parcel in) {
        this.nama_tempat = in.readString();
        this.deskripsi = in.readString();
        this.url_gambar = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.jarak = in.readDouble();
    }

    public static final Parcelable.Creator<TempatMakan> CREATOR = new Parcelable.Creator<TempatMakan>() {
        @Override
        public TempatMakan createFromParcel(Parcel source) {
            return new TempatMakan(source);
        }

        @Override
        public TempatMakan[] newArray(int size) {
            return new TempatMakan[size];
        }
    };
}
