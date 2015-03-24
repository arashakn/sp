package com.stylepuzzle.models;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SPCommentsList {


	@SerializedName("comments")
	ArrayList<SPComment> comments;

	public ArrayList<SPComment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<SPComment> comments) {
		this.comments = comments;
	}
	
}
