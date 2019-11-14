package com.example.myapplication.response;


import com.google.gson.annotations.SerializedName;


public class FaceAttributes{

	@SerializedName("emotion")
	private Emotion emotion;

	public void setEmotion(Emotion emotion){
		this.emotion = emotion;
	}

	public Emotion getEmotion(){
		return emotion;
	}

	@Override
 	public String toString(){
		return 
			"FaceAttributes{" + 
			"emotion = '" + emotion + '\'' + 
			"}";
		}
}