package com.stylepuzzle.fashionista;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.stylepuzzle.www.AppController;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;

public class BaseListFragment extends ListFragment{
	AppController mApp;
	ImageLoader mImageLoader;
	RequestQueue mQueue;
	ActionBar ab;
    @SuppressLint("NewApi") 
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mApp = (AppController)getActivity().getApplication();
		ab = getActivity().getActionBar();
		ab.setHomeButtonEnabled(true);
		mQueue = mApp.getmQueue();
		mImageLoader = mApp.getmImageLoader();
    }

}
