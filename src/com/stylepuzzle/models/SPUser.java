package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPUser {
	@SerializedName("id")
	Integer id;

	@SerializedName("name")
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
