package com.example.myapplication.response;


import com.google.gson.annotations.SerializedName;


public class Emotion{

	@SerializedName("contempt")
	private double contempt;

	@SerializedName("surprise")
	private double surprise;

	@SerializedName("happiness")
	private double happiness;

	@SerializedName("neutral")
	private double neutral;

	@SerializedName("sadness")
	private double sadness;

	@SerializedName("disgust")
	private double disgust;

	@SerializedName("anger")
	private double anger;

	@SerializedName("fear")
	private double fear;

	public void setContempt(double contempt){
		this.contempt = contempt;
	}

	public double getContempt(){
		return contempt;
	}

	public void setSurprise(double surprise){
		this.surprise = surprise;
	}

	public double getSurprise(){
		return surprise;
	}

	public void setHappiness(double happiness){
		this.happiness = happiness;
	}

	public double getHappiness(){
		return happiness;
	}

	public void setNeutral(double neutral){
		this.neutral = neutral;
	}

	public double getNeutral(){
		return neutral;
	}

	public void setSadness(double sadness){
		this.sadness = sadness;
	}

	public double getSadness(){
		return sadness;
	}

	public void setDisgust(double disgust){
		this.disgust = disgust;
	}

	public double getDisgust(){
		return disgust;
	}

	public void setAnger(double anger){
		this.anger = anger;
	}

	public double getAnger(){
		return anger;
	}

	public void setFear(double fear){
		this.fear = fear;
	}

	public double getFear(){
		return fear;
	}

	@Override
 	public String toString(){
		return 
			"Emotion{" + 
			"contempt = '" + contempt + '\'' + 
			",surprise = '" + surprise + '\'' + 
			",happiness = '" + happiness + '\'' + 
			",neutral = '" + neutral + '\'' + 
			",sadness = '" + sadness + '\'' + 
			",disgust = '" + disgust + '\'' + 
			",anger = '" + anger + '\'' + 
			",fear = '" + fear + '\'' + 
			"}";
		}
}