package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPPayment {
	@SerializedName("id")
	Integer id;
	
	@SerializedName("owner")
	Integer owner;
	
	@SerializedName("last_4")
	String last_4;
	
	@SerializedName("card_type")
	String card_type;
	
	@SerializedName("expiration_month")
	String expiration_month;
	
	@SerializedName("expiration_year")
	String expiration_year;
	
	@SerializedName("created_at")
	String created_at;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public String getLast_4() {
		return last_4;
	}

	public void setLast_4(String last_4) {
		this.last_4 = last_4;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getExpiration_month() {
		return expiration_month;
	}

	public void setExpiration_month(String expiration_month) {
		this.expiration_month = expiration_month;
	}

	public String getExpiration_year() {
		return expiration_year;
	}

	public void setExpiration_year(String expiration_year) {
		this.expiration_year = expiration_year;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}



}
