package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPCheckout {
	
	@SerializedName("id")
	Integer id;
	
	@SerializedName("address")
	String address;
	
	@SerializedName("currency")
	SPCurrency currency;
	
	@SerializedName("summary")
	SPSummary summary;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public SPCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(SPCurrency currency) {
		this.currency = currency;
	}

	public SPSummary getSummary() {
		return summary;
	}

	public void setSummary(SPSummary summary) {
		this.summary = summary;
	}



	
}
