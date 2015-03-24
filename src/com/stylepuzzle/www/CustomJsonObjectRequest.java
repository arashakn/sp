package com.stylepuzzle.www;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class CustomJsonObjectRequest extends JsonObjectRequest{

	public CustomJsonObjectRequest(int method, String url,
			JSONObject jsonRequest, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Map<String, String> getHeaders(){
	    Map<String, String> headers = new HashMap<String, String>();
	    //headers.put("User-agent", AppController.userAgentString);
	    return headers;
	}

}
