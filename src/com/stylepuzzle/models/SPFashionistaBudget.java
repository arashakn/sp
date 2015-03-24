package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPFashionistaBudget {
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getMin_price() {
		return min_price;
	}

	public void setMin_price(Integer min_price) {
		this.min_price = min_price;
	}

	public Integer getMax_price() {
		return max_price;
	}

	public void setMax_price(Integer max_price) {
		this.max_price = max_price;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	@SerializedName("category")
	String category;
	
	@SerializedName("price")
	Integer price;

	@SerializedName("min_price")
	Integer min_price;
	
	@SerializedName("max_price")
	Integer max_price;
	
	@SerializedName("step")
	Integer step;
}
