package com.example.myapplication.response;


import com.google.gson.annotations.SerializedName;


public class ResponseFoto{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("error")
	private String result;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"ResponseFoto{" + 
			"pesan = '" + pesan + '\'' + 
			",result = '" + result + '\'' +
			"}";
		}
}