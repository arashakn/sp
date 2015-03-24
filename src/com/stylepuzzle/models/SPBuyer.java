package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPBuyer  extends SPUser{

	@SerializedName("username")
	String username;
	
	@SerializedName("phone")
	String phone;
	
	@SerializedName("first_name")
	String first_name;
	
	@SerializedName("last_name")
	String last_name;
	
	@SerializedName("fullname")
	String fullname;
	
	@SerializedName("email")
	String email;
	
	@SerializedName("status")
	String status;
	
	@SerializedName("default_currency")
	String default_currency;
	
	@SerializedName("profile_pic_url")
	String profile_pic_url;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDefault_currency() {
		return default_currency;
	}

	public void setDefault_currency(String default_currency) {
		this.default_currency = default_currency;
	}

	public String getProfile_pic_url() {
		return profile_pic_url;
	}

	public void setProfile_pic_url(String profile_pic_url) {
		this.profile_pic_url = profile_pic_url;
	}
}
