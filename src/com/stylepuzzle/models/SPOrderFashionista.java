package com.stylepuzzle.models;

import com.google.gson.annotations.SerializedName;

public class SPOrderFashionista extends SPOrder {
	
	@SerializedName("buyer")
	SPBuyer buyer;
	
	@SerializedName("shopper_num_unread_comments")
	Integer num_unread_comments;
	
	
	public Integer getNum_unread_comments() {
		return num_unread_comments;
	}

	public void setNum_unread_comments(Integer num_unread_comments) {
		this.num_unread_comments = num_unread_comments;
	}
	
	public SPBuyer getBuyer() {
		return buyer;
	}

	public void setBuyer(SPBuyer buyer) {
		this.buyer = buyer;
	}

}
