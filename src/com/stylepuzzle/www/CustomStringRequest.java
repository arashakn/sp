package com.stylepuzzle.www;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class CustomStringRequest extends StringRequest{
	
	public CustomStringRequest(int method, String url,
			Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}
	@Override
	public Map<String, String> getHeaders(){
		Map<String, String> headers = new HashMap<String, String>();
		//headers.put("User-agent", "BARTRENDr "+AppController.versionName + " ("+"Android "+AppController.versionAndroid+ ")");
		headers.put("Content-Type", "application/json");
		if(AppController.spTokken!=null){
			headers.put("Authorization", "Token "+AppController.spTokken);
		}
		return headers;
	}

}
