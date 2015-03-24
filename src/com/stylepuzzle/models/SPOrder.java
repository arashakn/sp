package com.stylepuzzle.models;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SPOrder {
	@SerializedName("id")
	Integer id;
	
	@SerializedName("status")
	String status;
	
	@SerializedName("total")
	String total;

	@SerializedName("address")
	SPAddress address;

	@SerializedName("payment")
	SPPayment payment;	
	
	@SerializedName("created_at")
	String created_at;

	@SerializedName("items")
	ArrayList<SPOrderItem> items;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}




	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public ArrayList<SPOrderItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<SPOrderItem> items) {
		this.items = items;
	}

	public SPAddress getAddress() {
		return address;
	}

	public void setAddress(SPAddress address) {
		this.address = address;
	}
	
	
	public SPPayment getPayment() {
		return payment;
	}

	public void setPayment(SPPayment payment) {
		this.payment = payment;
	}



}
