package com.example.myapplication.response;


import com.google.gson.annotations.SerializedName;

public class FaceRectangle{

	@SerializedName("top")
	private int top;

	@SerializedName("left")
	private int left;

	@SerializedName("width")
	private int width;

	@SerializedName("height")
	private int height;

	public void setTop(int top){
		this.top = top;
	}

	public int getTop(){
		return top;
	}

	public void setLeft(int left){
		this.left = left;
	}

	public int getLeft(){
		return left;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}

	@Override
 	public String toString(){
		return 
			"FaceRectangle{" + 
			"top = '" + top + '\'' + 
			",left = '" + left + '\'' + 
			",width = '" + width + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}