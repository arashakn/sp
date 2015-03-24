package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPOrderItem {
	
	@SerializedName("id")
	Integer id;
	
	@SerializedName("category")
	String category;
	
	@SerializedName("budget")
	String budget;
	
	@SerializedName("created_at")
	String created_at;
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}



}
