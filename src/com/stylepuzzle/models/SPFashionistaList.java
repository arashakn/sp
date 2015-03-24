package com.stylepuzzle.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SPFashionistaList {
	
	@SerializedName("count")
	Integer count;
	
	@SerializedName("results")
	ArrayList<SPOrderFashionista> results;
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public ArrayList<SPOrderFashionista> getResult() {
		return results;
	}

	public void setResult(ArrayList<SPOrderFashionista> result) {
		this.results = result;
	}
	

}
