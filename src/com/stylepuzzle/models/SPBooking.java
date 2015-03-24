package com.stylepuzzle.models;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SPBooking {
	@SerializedName("order")
	SPOrder order;
	
	@SerializedName("fashionnista_budgets")
	ArrayList<SPFashionistaBudget> fashionnista_budgets;
	
	
	public SPOrder getOrder() {
		return order;
	}

	public void setOrder(SPOrder order) {
		this.order = order;
	}

	public ArrayList<SPFashionistaBudget> getFashionnista_budgets() {
		return fashionnista_budgets;
	}

	public void setFashionnista_budgets(
			ArrayList<SPFashionistaBudget> fashionnista_budgets) {
		this.fashionnista_budgets = fashionnista_budgets;
	}	
	
}
