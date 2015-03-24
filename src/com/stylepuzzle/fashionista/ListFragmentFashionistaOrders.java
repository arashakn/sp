package com.stylepuzzle.fashionista;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.stylepuzzle.adapters.ListViewFashionistaOrders;
import com.stylepuzzle.adapters.ListViewFashionistaProfile;
import com.stylepuzzle.models.SPFashionistaList;
import com.stylepuzzle.models.SPOrderFashionista;
import com.stylepuzzle.www.AppController;
import com.stylepuzzle.www.CustomStringRequest;
import com.stylepuzzle.www.FashionistaProfile;
import com.stylepuzzle.www.R;
import com.stylepuzzle.www.Utils;

public class ListFragmentFashionistaOrders extends BaseListFragment implements OnClickListener{

	ArrayList<SPOrderFashionista> listOfFashionistaOrders;
	private ProgressBar pbNews;
	private ListViewFashionistaOrders mAdapter;
	SPOrderFashionista entry;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = AppController.URL_MY_ORDERS;
        makeRequest(url);
    }
    
	public void makeRequest(final String url){
		CustomStringRequest myReq = new CustomStringRequest(Method.GET, 
				url,
				createMyReqSuccessListener(),
				createMyReqErrorListener());
		mQueue.add(myReq);
	}
	
	private Response.Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				//fashionista = Utils.jsonToObject(response, SPFashionista.class);
				System.out.println("resp -->"+response);
				SPFashionistaList fashionistaList = Utils.jsonToObject(response, SPFashionistaList.class);
				listOfFashionistaOrders = fashionistaList.getResult();
				setAdapterInListNewFragment();
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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

//		if(listOfFashionistaOrders!=null){
//		setAdapterInListNewFragment();
//		}
	}

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle bundle=getArguments();
		//typeOfEvent=bundle.getString("type");
		View view = inflater.inflate(R.layout.listfragment_fashionista_orders, container, false);
		//pbNews = (ProgressBar)view.findViewById(R.id.pbNewsPage);

		return view;
	}

//	@SuppressLint("NewApi")
//	public void setActionBar(){
//		Drawable d=getResources().getDrawable(R.drawable.action_bar);  
//		getActivity().getActionBar().setBackgroundDrawable(d);
//		getActivity().getActionBar().setHomeButtonEnabled(true);
//	}


	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public void onClick(View arg0) {

	}

	public void setAdapterInListNewFragment() {
		mAdapter = new ListViewFashionistaOrders(getActivity(), 0, listOfFashionistaOrders, mImageLoader,mApp);
		setListAdapter(mAdapter);
	}


	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.d("testsp", Integer.toString(position));
		
		Intent i = new Intent(getActivity(), CommentThreadActivity.class);
		entry = listOfFashionistaOrders.get(position);
		Log.d("testsp", Integer.toString(entry.getId()));
		String idOfOrder = Integer.toString(entry.getId());
		i.putExtra(AppController.ID_COMMENT,idOfOrder);			
		getActivity().startActivity(i);	
//		Intent iNew = new Intent(getActivity(),ListOfTicketsFromConcertActivity.class);
//		EventInfo eInfo = listOfEvents.get(position);
//		cbApp.setCurEvent(eInfo);
//		iNew.putExtra("imageVenue", eInfo.venue_image);
//		iNew.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//		startActivityForResult(iNew,2);	
	}

}