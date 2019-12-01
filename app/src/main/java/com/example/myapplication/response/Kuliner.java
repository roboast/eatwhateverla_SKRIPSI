package com.example.myapplication.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Kuliner implements Parcelable {

	@SerializedName("cf")
	private double cf;

	@SerializedName("jarak")
	private String jarak;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("0")
	private String jsonMember0;

	@SerializedName("1")
	private String jsonMember1;

	@SerializedName("2")
	private String jsonMember2;

	@SerializedName("3")
	private String jsonMember3;

	@SerializedName("4")
	private String jsonMember4;

	@SerializedName("5")
	private String jsonMember5;

	@SerializedName("6")
	private String jsonMember6;

	@SerializedName("nama_kuliner")
	private String namaKuliner;

	@SerializedName("deskripsi")
	private String deskripsi;

	@SerializedName("longitude")
	private double longitude;

	public void setCf(double cf){
		this.cf = cf;
	}

	public double getCf(){
		return cf;
	}

	public void setJarak(String jarak){
		this.jarak = jarak;
	}

	public String getJarak(){
		return jarak;
	}

	public void setLatitude(double latitude){
		this.latitude = latitude;
	}

	public double getLatitude(){
		return latitude;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setJsonMember0(String jsonMember0){
		this.jsonMember0 = jsonMember0;
	}

	public String getJsonMember0(){
		return jsonMember0;
	}

	public void setJsonMember1(String jsonMember1){
		this.jsonMember1 = jsonMember1;
	}

	public String getJsonMember1(){
		return jsonMember1;
	}

	public void setJsonMember2(String jsonMember2){
		this.jsonMember2 = jsonMember2;
	}

	public String getJsonMember2(){
		return jsonMember2;
	}

	public void setJsonMember3(String jsonMember3){
		this.jsonMember3 = jsonMember3;
	}

	public String getJsonMember3(){
		return jsonMember3;
	}

	public void setJsonMember4(String jsonMember4){
		this.jsonMember4 = jsonMember4;
	}

	public String getJsonMember4(){
		return jsonMember4;
	}

	public void setJsonMember5(String jsonMember5){
		this.jsonMember5 = jsonMember5;
	}

	public String getJsonMember5(){
		return jsonMember5;
	}
	public void setJsonMember6(String jsonMember5){
		this.jsonMember5 = jsonMember5;
	}

	public String getJsonMember6(){
		return jsonMember5;
	}

	public void setNamaKuliner(String namaKuliner){
		this.namaKuliner = namaKuliner;
	}

	public String getNamaKuliner(){
		return namaKuliner;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public void setLongitude(double longitude){
		this.longitude = longitude;
	}

	public double getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return "Kuliner{" +
				"cf = '" + cf + '\'' +
				",jarak = '" + jarak + '\'' +
				",latitude = '" + latitude + '\'' +
				",gambar = '" + gambar + '\'' +
				",alamat = '" + alamat + '\'' +
				",0 = '" + jsonMember0 + '\'' +
				",1 = '" + jsonMember1 + '\'' +
				",2 = '" + jsonMember2 + '\'' +
				",3 = '" + jsonMember3 + '\'' +
				",4 = '" + jsonMember4 + '\'' +
				",5 = '" + jsonMember5 + '\'' +
				",6 = '" + jsonMember6 + '\'' +
				",nama_kuliner = '" + namaKuliner + '\'' +
				",deskripsi = '" + deskripsi + '\'' +
				",longitude = '" + longitude + '\'' +
				"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(this.cf);
		dest.writeString(this.jarak);
		dest.writeDouble(this.latitude);
		dest.writeString(this.gambar);
		dest.writeString(this.alamat);
		dest.writeString(this.jsonMember0);
		dest.writeString(this.jsonMember1);
		dest.writeString(this.jsonMember2);
		dest.writeString(this.jsonMember3);
		dest.writeString(this.jsonMember4);
		dest.writeString(this.jsonMember5);
		dest.writeString(this.jsonMember6);
		dest.writeString(this.namaKuliner);
		dest.writeString(this.deskripsi);
		dest.writeDouble(this.longitude);
	}

	public Kuliner() {
	}

	protected Kuliner(Parcel in) {
		this.cf = in.readDouble();
		this.jarak = in.readString();
		this.latitude = in.readDouble();
		this.gambar = in.readString();
		this.alamat = in.readString();
		this.jsonMember0 = in.readString();
		this.jsonMember1 = in.readString();
		this.jsonMember2 = in.readString();
		this.jsonMember3 = in.readString();
		this.jsonMember4 = in.readString();
		this.jsonMember5 = in.readString();
		this.jsonMember6 = in.readString();
		this.namaKuliner = in.readString();
		this.deskripsi = in.readString();
		this.longitude = in.readDouble();
	}

	public static final Creator<Kuliner> CREATOR = new Creator<Kuliner>() {
		@Override
		public Kuliner createFromParcel(Parcel source) {
			return new Kuliner(source);
		}

		@Override
		public Kuliner[] newArray(int size) {
			return new Kuliner[size];
		}
	};
}