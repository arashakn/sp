package com.stylepuzzle.fashionista;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.stylepuzzle.www.AppController;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseFragmentActivity extends FragmentActivity {
	AppController mApp;
	ImageLoader mImageLoader;
	RequestQueue mQueue;
	ActionBar ab;
    @SuppressLint("NewApi") 
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mApp = (AppController)getApplication();
		ab = getActionBar();
		ab.setHomeButtonEnabled(true);
		mQueue = mApp.getmQueue();
		mImageLoader = mApp.getmImageLoader();
    }

}
