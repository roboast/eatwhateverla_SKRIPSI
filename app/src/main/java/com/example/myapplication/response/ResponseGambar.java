package com.example.myapplication.response;


import com.google.gson.annotations.SerializedName;


public class ResponseGambar{

	@SerializedName("result")
	private String result;

	@SerializedName("pesan")
	private String pesan;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGambar{" + 
			"result = '" + result + '\'' + 
			",pesan = '" + pesan + '\'' + 
			"}";
		}
}