package com.stylepuzzle.models;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SPLoginUser extends SPUser{

	@SerializedName("url")
	String url;
	
	@SerializedName("username")
	String  username;
	
	@SerializedName("phone")
	String  phone;
	
	@SerializedName("first_name")
	String  first_name;
	
	@SerializedName("last_name")
	String  last_name;
	
	@SerializedName("email")
	String  email;
	
	@SerializedName("status")
	String  status;
	
	@SerializedName("default_currency")
	Integer  default_currency;
	
	@SerializedName("auth_token")
	String  auth_token;
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

	public Integer getDefault_currency() {
		return default_currency;
	}

	public void setDefault_currency(Integer default_currency) {
		this.default_currency = default_currency;
	}

	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}

	
	
	
	

}
