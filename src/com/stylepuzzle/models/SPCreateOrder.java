package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPCreateOrder {
	
	@SerializedName("id")
	Integer id;

	@SerializedName("shopper")
	Integer shopper;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShopper() {
		return shopper;
	}

	public void setShopper(Integer shopper) {
		this.shopper = shopper;
	}

}
