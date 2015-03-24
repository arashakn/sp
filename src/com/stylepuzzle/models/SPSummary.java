package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPSummary {

	@SerializedName("total")
	Integer  total;
	
	@SerializedName("subtotal")
	Integer subtotal;

	@SerializedName("shipping")
	Integer shipping;
	
	@SerializedName("service_fee")
	Integer service_fee;
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}


	public Integer getService_fee() {
		return service_fee;
	}

	public void setService_fee(Integer service_fee) {
		this.service_fee = service_fee;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

}
