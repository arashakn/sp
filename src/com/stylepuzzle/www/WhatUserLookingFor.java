package com.stylepuzzle.www;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.stylepuzzle.adapters.GridViewCustomAdapter;
import com.stylepuzzle.customs.PostTask;
import com.stylepuzzle.customs.PutTask;
import com.stylepuzzle.interfaces.OnFinishPosting;
import com.stylepuzzle.models.SPBooking;
import com.stylepuzzle.models.SPCreateOrder;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class WhatUserLookingFor extends BaseActivity  implements OnItemClickListener,OnFinishPosting{
	
    GridView gridView;
    GridViewCustomAdapter grisViewCustomeAdapter;
    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.whatuserlookingfor);
    		ActionBar ab = getActionBar();
    		ab.setHomeButtonEnabled(true);
    		setTitle("What are you looking for?");
            
            gridView=(GridView)findViewById(R.id.gridViewCustom);
            // Create the Custom Adapter Object
            grisViewCustomeAdapter = new GridViewCustomAdapter(this);
            // Set the Adapter to GridView
            gridView.setAdapter(grisViewCustomeAdapter);
            gridView.setOnItemClickListener(this);
   
//            // Handling touch/click Event on GridView Item
//              gridView.setOnItemClickListener(new OnItemClickListener() {
//
//               @Override
//               public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
//                   String selectedItem;
//                   if(position%2==0)
//                       selectedItem="Facebook";
//                   else
//                       selectedItem="Twitter";
//                Toast.makeText(getApplicationContext(),"Selected Item: "+selectedItem, Toast.LENGTH_SHORT).show();
//                
//               }
//              });


       }


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        String selectedItem = null;
        //grisViewCustomeAdapter.getItem(position)
        
     //Toast.makeText(getApplicationContext(),"Selected Item: "+selectedItem, Toast.LENGTH_SHORT).show();		
	}
	
	

	//@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.menu_what_user_lookingfor, menu);
	    return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.menu_after_choosing_items:
	    	ArrayList<String> items = new ArrayList<String>();
	    	items.add("Tops");
	    	items.add("Shorts");
	    	items.add("Sweaters");
	    	String jsonFormat = createJsonEncodedOfData(items);
	    	
	        SPCreateOrder curOrder = mApp.getCurOrder();
	        int orderID = curOrder.getId();
	        String url = AppController.URL_MY_BOOKING+Integer.toString(orderID)+"/add-items/";
			new PutTask(WhatUserLookingFor.this, jsonFormat,url,this,"key").execute();

	        System.out.println("url --->"+url);	    	
	    	Intent i = new Intent();
	        break;
	    }

	    return true;
	}
	
	
	public String createJsonEncodedOfData(ArrayList<String> listOfItems)
	{
		JSONArray j_ar = new JSONArray();
		for(String item : listOfItems){
			j_ar.put(item);
		}
		
		JSONObject j_o= new JSONObject();
		String result="";
		try {
			j_o.put("categories", j_ar);
			result = j_o.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public void onFinishedPosting(String result) {
		System.out.println("result-->"+result);
		SPBooking booking = Utils.jsonToObject(result, SPBooking.class);
		if(booking!=null){
			mApp.setCurBookingItems(booking);
			System.out.println("------------>"+booking.getOrder().getId());
			Intent intent = new Intent(this, Booking.class);
//			intent.putExtra(AppController.ACTIVITY_CALLING_KEY, AppController.ACTIVITY_ID_BAR_SPECIFIC);
			startActivity(intent);
		}

	}
	
}
