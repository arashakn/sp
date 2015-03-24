package com.stylepuzzle.fashionista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.stylepuzzle.models.SPFashionistaList;
import com.stylepuzzle.www.AppController;
import com.stylepuzzle.www.CustomStringRequest;
import com.stylepuzzle.www.R;
import com.stylepuzzle.www.Utils;

/**
 * A fragment that launches other parts of the demo application.
 */

public  class LaunchpadSectionFragment extends BaseFragment {
	
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_launchpad, container, false);

        // Demonstration of a collection-browsing activity.
        rootView.findViewById(R.id.demo_collection_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), CollectionDemoActivity.class);
                        startActivity(intent);
                    }
                });

        // Demonstration of navigating to external activities.
        rootView.findViewById(R.id.demo_external_activity)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Create an intent that asks the user to pick a photo, but using
                        // FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, ensures that relaunching
                        // the application from the device home screen does not return
                        // to the external activity.
                        Intent externalActivityIntent = new Intent(Intent.ACTION_PICK);
                        externalActivityIntent.setType("image/*");
                        externalActivityIntent.addFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        startActivity(externalActivityIntent);
                    }
                });

        return rootView;
    }
}
