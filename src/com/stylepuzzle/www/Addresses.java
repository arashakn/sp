package com.stylepuzzle.www;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.stylepuzzle.adapters.ListViewFashionistaProfile;
import com.stylepuzzle.customs.PostTask;
import com.stylepuzzle.interfaces.OnFinishPosting;
import com.stylepuzzle.models.SPFashionista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Addresses extends BaseActivity implements OnClickListener,OnFinishPosting{
	Button btnAddAddress;
	

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addresses);
		btnAddAddress = (Button)findViewById(R.id.btnAddAddress);
		btnAddAddress.setOnClickListener(this);
		//makeRequest(url)
	}
	
	public void makeRequest(final String url){
		StringRequest myReq = new StringRequest(Method.GET, 
				url,
				createMyReqSuccessListener(),
				createMyReqErrorListener());
		mQueue.add(myReq);
	}
	
	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
//				if(error!=null)
//				System.out.println(error.getMessage());
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnAddAddress:
			Intent intent = new Intent(this, AddAddress.class);
//			intent.putExtra(AppController.ACTIVITY_CALLING_KEY, AppController.ACTIVITY_ID_BAR_SPECIFIC);
			startActivity(intent);
			break;
		}
	}


	@Override
	public void onFinishedPosting(String result) {
		System.out.println("result --->"+result);
	}

	
	private Response.Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				//fashionista = Utils.jsonToObject(response, SPFashionista.class);

			}
		};
	}
	

}
