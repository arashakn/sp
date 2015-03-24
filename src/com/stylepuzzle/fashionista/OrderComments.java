package com.stylepuzzle.fashionista;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.stylepuzzle.adapters.ListViewCommentsArrayAdapter;
import com.stylepuzzle.customs.PostTask;
import com.stylepuzzle.interfaces.OnFinishPosting;
import com.stylepuzzle.models.SPBooking;
import com.stylepuzzle.models.SPComment;
import com.stylepuzzle.models.SPCommentsList;
import com.stylepuzzle.models.SPCreateOrder;
import com.stylepuzzle.www.AddAddress;
import com.stylepuzzle.www.AppController;
import com.stylepuzzle.www.BaseActivity;
import com.stylepuzzle.www.Booking;
import com.stylepuzzle.www.CustomStringRequest;
import com.stylepuzzle.www.R;
import com.stylepuzzle.www.Utils;

public class OrderComments extends BaseActivity implements OnClickListener,OnFinishPosting{

	private ListView mLvComments;
	private Button btnSend,btnTakePicture,btnCancelTakenPicture,btnLoadMoreComments;


	private ArrayList<SPComment> mEntries = new ArrayList<SPComment>();
	private ListViewCommentsArrayAdapter mAdapter;
	String comment_id = null,comment_url=null;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if(extras == null) {
			} else {
				comment_id= extras.getString(AppController.ID_COMMENT);
			}
		}
		setContentView(R.layout.comment_thread);
		//comment_id="345";
		mLvComments = (ListView) findViewById(R.id.lv_comments);
		btnSend =(Button) findViewById(R.id.btnSendComment);
		btnSend.setOnClickListener(this);
		if(comment_id!=null){
			comment_url = AppController.URL_ORDER_COMMENT+comment_id+"/comments/";
			setTitle("Comments");
			makeRequest(comment_url);
		}
	}

	public void makeRequest(final String url){
		CustomStringRequest myReq = new CustomStringRequest(Method.GET, 
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

	private Response.Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				//String keyValueComments = Utils.addKeyToJson("comments", response);
				//System.out.println("------->"+keyValueComments);
				SPCommentsList listOfComments = Utils.jsonToObject(response, SPCommentsList.class);
				if(listOfComments!=null){
					System.out.println("*****------>"+listOfComments.getComments().size());
					mEntries = listOfComments.getComments();
					mAdapter = new ListViewCommentsArrayAdapter(OrderComments.this, 0, mEntries, mImageLoader,mApp);
					mLvComments.setAdapter(mAdapter);
				}
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSendComment:
			System.out.println("sending!");
			String json_string = createJsonEncodedForComment("test my app");
			System.out.println("url ----->"+comment_url);
			new PostTask(this, json_string,comment_url,this,"key").execute();
			break;

		default:
			break;
		}
	}

	public String createJsonEncodedForComment(String content)
	{
		JSONObject j_o= new JSONObject();
		String result="";
		try {
			j_o.put("content", content);
			result = j_o.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void onFinishedPosting(String result) {
		System.out.println("comment result----->"+result);

	}


}
