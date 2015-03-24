package com.stylepuzzle.www;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.stylepuzzle.adapters.ListViewFashionistaProfile;
import com.stylepuzzle.adapters.ListViewOrderItems;
import com.stylepuzzle.customs.PostTask;
import com.stylepuzzle.customs.PutTask;
import com.stylepuzzle.interfaces.OnFinishPosting;
import com.stylepuzzle.models.SPBooking;
import com.stylepuzzle.models.SPCheckout;
import com.stylepuzzle.models.SPCreateOrder;
import com.stylepuzzle.models.SPFashionista;
import com.stylepuzzle.models.SPImage;
import com.stylepuzzle.models.SPOrderItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Booking extends BaseActivity implements OnItemClickListener,OnFinishPosting{
	SPBooking booking;
	private ListViewOrderItems mAdapter;
	private ArrayList<SPOrderItem> mEntries = new ArrayList<SPOrderItem>();
	private ListView mLvBookingItems;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking);
		setTitle("Booking");
		booking = mApp.getCurBookingItems();
		mEntries = booking.getOrder().getItems();
		mAdapter = new ListViewOrderItems(Booking.this, 0, mEntries, mImageLoader,mApp);
		mLvBookingItems = (ListView) findViewById(R.id.lv_BookingItems);
		mLvBookingItems.setAdapter(mAdapter);
		mLvBookingItems.setOnItemClickListener(this);
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		System.out.println("clicking--->"+arg2);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_next_page:
			updateOrderBudgets();

			break;
		}
		return true;
	}

	//@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_booking, menu);

		return true;
	}

	public void updateOrderBudgets(){
		String jsonFormat = createJsonEncodedOfData(mEntries);
		String url = AppController.URL_MY_BOOKING+Integer.toString(booking.getOrder().getId())+"/update-item-budget/";

		new PostTask(Booking.this, jsonFormat,url,this,"key").execute();

	}


	public String createJsonEncodedOfData(ArrayList<SPOrderItem> mEntries)
	{
		JSONArray j_ar = new JSONArray();
		for(SPOrderItem item : mEntries){
			JSONObject j_Object_Order_item = new JSONObject();
			try {
				j_Object_Order_item.put("item_id", item.getId());
				j_Object_Order_item.put("budget", item.getBudget());

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			j_ar.put(j_Object_Order_item);

		}

		JSONObject j_o= new JSONObject();
		String result="";
		try {
			j_o.put("budgets", j_ar);
			result = j_o.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public void onFinishedPosting(String result) {
		System.out.println("result --->"+result);
		SPCheckout checkout = Utils.jsonToObject(result, SPCheckout.class);
		if(checkout!=null){
			System.out.println(checkout.getId());
			mApp.setCheckout(checkout);
			Intent intent = new Intent(this, Checkout.class);
			startActivity(intent);
		}

	}



}
