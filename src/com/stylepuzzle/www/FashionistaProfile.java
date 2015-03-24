package com.stylepuzzle.www;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.volley_examples.toolbox.BitmapLruCache;


import com.stylepuzzle.adapters.ListViewFashionistaProfile;
import com.stylepuzzle.customs.PostTask;
import com.stylepuzzle.interfaces.OnFinishPosting;
import com.stylepuzzle.models.MLRoundedImageView;
import com.stylepuzzle.models.SPCreateOrder;
import com.stylepuzzle.models.SPFashionista;
import com.stylepuzzle.models.SPImage;

public class FashionistaProfile extends BaseActivity implements OnClickListener, OnFinishPosting{
	SPFashionista fashionista;
	//RequestQueue queue;
	private TextView mTvResult;
	private ListViewFashionistaProfile mAdapter;
	private ArrayList<SPImage> mEntries = new ArrayList<SPImage>();
	private ListView mLvFashionsitaImages;
	//ImageLoader mImageLoader;
	View header;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fashionista_profile);
		header = getLayoutInflater().inflate(R.layout.fashionista_profile_header, null,true); 
		Button btnBook = (Button) header.findViewById(R.id.btnBook);
		btnBook.setOnClickListener(this);
		
		String url =AppController.URL_FASHIONISTA_PROFILE+"fengwan/";
		System.out.println("url------------>"+url);
		mLvFashionsitaImages = (ListView) findViewById(R.id.lv_fasionistaImages);
		mLvFashionsitaImages.addHeaderView(header); 
		
//		int memClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
//				.getMemoryClass();
//		int cacheSize = 1024 * 1024 * memClass / 8;
//		if (queue == null) {
//			queue = Volley.newRequestQueue(getApplicationContext());
//		}
//		mImageLoader = new ImageLoader(queue, new BitmapLruCache(cacheSize));
		makeRequest(url);

	}


	public void makeRequest(final String url){
		StringRequest myReq = new StringRequest(Method.GET, 
				url,
				createMyReqSuccessListener(),
				createMyReqErrorListener());
		mQueue.add(myReq);
	}


	public void setHeader(SPFashionista fashionista){

		MLRoundedImageView ivUser = (MLRoundedImageView) findViewById(R.id.ivFstaImage);
		ivUser.setImageUrl(fashionista.getProfile_pic_url(),mImageLoader);
		TextView tvName = (TextView) header.findViewById(R.id.tvFstaName);
		tvName.setText(fashionista.getName());
		TextView tvCity = (TextView) header.findViewById(R.id.tvFstaCity);
		tvCity.setText(fashionista.getLocation());
		TextView tvDesc = (TextView) header.findViewById(R.id.tvFstaDesc);
		tvDesc.setText(fashionista.getTagline());
	}


	private Response.Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				fashionista = Utils.jsonToObject(response, SPFashionista.class);
				setHeader(fashionista);
				mEntries = fashionista.getShopper_images();
				System.out.println("resp -->"+fashionista.getLocation());
				mAdapter = new ListViewFashionistaProfile(FashionistaProfile.this, 0, mEntries, mImageLoader,mApp);
				mLvFashionsitaImages.setAdapter(mAdapter);
			}
		};
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
		case R.id.btnBook:
			bookOrder();
			break;
		}

	}


	private void bookOrder() {
		String userJson = createJsonEncodedOfData();
		//public PostTask(Context con, String dv, String urlRequest, OnFinishPosting ofuploading,String eKey){
		String urlToPost = AppController.URL_MY_ORDERS;
		System.out.println("the url --->"+urlToPost);
		new PostTask(FashionistaProfile.this, userJson,urlToPost,this,"key").execute();
		//new PostTask(FashionistaProfile.this, userJson,AppController.URL_MY_ORDERS+AppController.spTokken,this,"key").execute();

        
//        CustomStringRequest myReq = new CustomStringRequest(Method.POST,
//                "http://httpbin.org/post",
//                createMyReqSuccessListenerForPost(),
//                createMyReqErrorListenerForPost()) {
//			@Override
//			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
//				Map<String, String> params = new HashMap<String, String>();
//				params.put("user",userJson);
//				return params;
//			};
//		};
//		queue.add(myReq);
	}
	
	private Response.Listener<String> createMyReqSuccessListenerForPost() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println(response);
			}
		};
	}


	private Response.ErrorListener createMyReqErrorListenerForPost() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
//				if(error!=null)
//				System.out.println(error.getMessage());
			}
		};
	}
	
	public String createJsonEncodedOfData()
	{
		JSONArray j_ar = new JSONArray();
		JSONObject message= new JSONObject();
		String result="";
		try {
			message.put("shopper", fashionista.getId().toString());
			j_ar.put(message);
			result = j_ar.get(0).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public void onFinishedPosting(String result) {
		System.out.println("result--->"+result);
		if(result!=null){
			SPCreateOrder createOrder = Utils.jsonToObject(result, SPCreateOrder.class);
			mApp.setCurOrder(createOrder);
			Intent i = new Intent(this, WhatUserLookingFor.class);
			startActivityForResult(i,AppController.ACTIVITY_ID_FASHIONISTA_PROFILE);
			
		}
	}

}
