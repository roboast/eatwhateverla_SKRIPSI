package com.example.myapplication.response;


import com.google.gson.annotations.SerializedName;


public class ResponseAzure{

	@SerializedName("faceRectangle")
	private FaceRectangle faceRectangle;

	@SerializedName("faceAttributes")
	private FaceAttributes faceAttributes;

	@SerializedName("faceId")
	private String faceId;

	public void setFaceRectangle(FaceRectangle faceRectangle){
		this.faceRectangle = faceRectangle;
	}

	public FaceRectangle getFaceRectangle(){
		return faceRectangle;
	}

	public void setFaceAttributes(FaceAttributes faceAttributes){
		this.faceAttributes = faceAttributes;
	}

	public FaceAttributes getFaceAttributes(){
		return faceAttributes;
	}

	public void setFaceId(String faceId){
		this.faceId = faceId;
	}

	public String getFaceId(){
		return faceId;
	}

	@Override
 	public String toString(){
		return 
			"ResponseAzure{" + 
			"faceRectangle = '" + faceRectangle + '\'' + 
			",faceAttributes = '" + faceAttributes + '\'' + 
			",faceId = '" + faceId + '\'' + 
			"}";
		}
}