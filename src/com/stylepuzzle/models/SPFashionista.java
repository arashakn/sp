package com.stylepuzzle.models;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SPFashionista extends SPUser{
	
	@SerializedName("url")
	String url;
	
	@SerializedName("username")
	String  username;
	
	
	@SerializedName("height")
	String  height;
	
	@SerializedName("shopper_images")
	ArrayList<SPImage> shopper_images;

	@SerializedName("profile_pic_url")
	String profile_pic_url;
	
	@SerializedName("tagline")
	String  tagline;
	
	@SerializedName("location")
	String  location;
	


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public ArrayList<SPImage> getShopper_images() {
		return shopper_images;
	}

	public void setShopper_images(ArrayList<SPImage> shopper_images) {
		this.shopper_images = shopper_images;
	}



	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	public String getProfile_pic_url() {
		return profile_pic_url;
	}

	public void setProfile_pic_url(String profile_pic_url) {
		this.profile_pic_url = profile_pic_url;
	}

}
