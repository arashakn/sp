package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPImage {
	
	@SerializedName("id")
	Integer id;
	
	@SerializedName("size")
	String  size;
	
	@SerializedName("image_url")
	String  image_url;
	
	@SerializedName("image")
	String  image;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


}
