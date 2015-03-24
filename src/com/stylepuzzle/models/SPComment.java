package com.stylepuzzle.models;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SPComment {

	@SerializedName("id")
	Integer id;
	
	@SerializedName("order")
	Integer order;
	
	@SerializedName("content")
	String content;
	
	@SerializedName("created_at")
	String created_at;
	
	@SerializedName("owner")
	SPBuyer owner;

	@SerializedName("images")
	ArrayList<SPImageBase> images;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public SPBuyer getOwner() {
		return owner;
	}

	public void setOwner(SPBuyer owner) {
		this.owner = owner;
	}
	
	
	public ArrayList<SPImageBase> getImages() {
		return images;
	}

	public void setImages(ArrayList<SPImageBase> images) {
		this.images = images;
	}



}
