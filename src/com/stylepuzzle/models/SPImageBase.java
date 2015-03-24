package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPImageBase {



	@SerializedName("id")
	Integer id;
	
	
	@SerializedName("image_url")
	String  image_url;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getImage_url() {
		return image_url;
	}


	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	


}
