package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPCurrency {
	
	@SerializedName("id")
	Integer id;
	
	@SerializedName("currency_name")
	String currency_name;
	
	@SerializedName("currency_symbol")
	String currency_symbol;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCurrency_name() {
		return currency_name;
	}

	public void setCurrency_name(String currency_name) {
		this.currency_name = currency_name;
	}

	public String getCurrency_symbol() {
		return currency_symbol;
	}

	public void setCurrency_symbol(String currency_symbol) {
		this.currency_symbol = currency_symbol;
	}


	
}
