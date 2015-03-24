package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPAddress {

	@SerializedName("id")
	Integer id;
	
	@SerializedName("recipient_name")
	String recipient_name;
	
	@SerializedName("company")
	String String;
	
	@SerializedName("street")
	String street;
	
	@SerializedName("city")
	String city;
	
	@SerializedName("state")
	String state;
	
	@SerializedName("zipcode")
	String zipcode;
	
	@SerializedName("country")
	String country;
	
	@SerializedName("phone")
	String phone;
	
	@SerializedName("owner")
	String owner;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRecipient_name() {
		return recipient_name;
	}

	public void setRecipient_name(String recipient_name) {
		this.recipient_name = recipient_name;
	}

	public String getString() {
		return String;
	}

	public void setString(String string) {
		String = string;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}
