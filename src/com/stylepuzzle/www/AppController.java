package com.stylepuzzle.www;

import java.io.File;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.github.volley_examples.toolbox.BitmapLruCache;
import com.stylepuzzle.models.SPBooking;
import com.stylepuzzle.models.SPCheckout;
import com.stylepuzzle.models.SPCreateOrder;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class AppController  extends Application {
	
	public static final String URL_BASE = "http://test.stylepuzzle.com/api/";
	//public static final String URL_QA = "https://stylepuzzle.com/api/";
	public static final String URL_FASHIONISTA_PROFILE = URL_BASE+"fashionistas/";
	public static final String URL_MY_ORDERS = URL_BASE +"my-orders/";
	public static final String URL_MY_BOOKING = URL_BASE +"orders/";
	public static final String URL_MY_ADDRESS = URL_BASE +"address/";
	public static final String URL_MY_SEND_SMS_AUTH = URL_BASE+ "users/smsauth/send/";
	public static final String URL_MY_LOGIN_PHONE_NUMBER = URL_BASE+ "users/login/phone/";
	public static final String URL_ORDER_COMMENT = URL_BASE+ "orders/";
	public static final String ID_COMMENT="id_comment";
	public static final String ID_IMAGE_URL="id_image_url";
	
	//Request Code
	public static final int REQUEST_CODE_CAMERA_CAPTURE_IMAGE = 111;
	public static final int REQUEST_CODE_SELECT_IMAGE = 112;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final String IMAGE_DIRECTORY_NAME = "StylePuzzleCamera";


	public static final String TEST_URL_GET = "http://httpbin.org/get";
	public static final String TEST_URL_POST = "http://httpbin.org/post";
	public static final String TEST_URL_PUT = "http://httpbin.org/put";
	public static String spTokken = "f709b164148dc8e8ee7806990bad9313639c7c81";
	public static int ACTIVITY_ID_FASHIONISTA_PROFILE=1001;
	public static final int ACTIVITY_TIME_DELAY_FOR_FEED_SECOND = 10;

	private RequestQueue mRequestQueue;
	
	private static final String DEFAULT_CACHE_DIR = "photos";
	private static final int DEFAULT_DISK_USAGE_BYTES = 25 * 1024 * 1024;
	
	
	public static final int USER_ID = 3761;

	
	ImageLoader mImageLoader;
	RequestQueue mQueue;
	SPCreateOrder curOrder;
	SPBooking curBookingItems;
	SPCheckout curCheckout;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (mQueue == null) {
			mQueue = Volley.newRequestQueue(getApplicationContext());
		}
		int memClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
				.getMemoryClass();
		int cacheSize = 1024 * 1024 * memClass / 8;
		mImageLoader = new ImageLoader(mQueue, new BitmapLruCache(cacheSize));
		
		setPushNotification();
		
		
	}

	private void setPushNotification() {
		
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {

			File rootCache = getApplicationContext().getExternalCacheDir();
			if (rootCache == null) {
				rootCache = getApplicationContext().getCacheDir();
			}

			File cacheDir = new File(rootCache, DEFAULT_CACHE_DIR);
			cacheDir.mkdirs();

			HttpStack stack = new HurlStack();
			Network network = new BasicNetwork(stack);
			DiskBasedCache diskBasedCache = new DiskBasedCache(cacheDir, DEFAULT_DISK_USAGE_BYTES);
			RequestQueue queue = new RequestQueue(diskBasedCache, network);
			queue.start();

			return queue;
		}

		return mRequestQueue;
	}

	public ImageLoader getmImageLoader() {
		return mImageLoader;
	}

	public void setmImageLoader(ImageLoader mImageLoader) {
		this.mImageLoader = mImageLoader;
	}

	public RequestQueue getmQueue() {
		return mQueue;
	}

	public void setmQueue(RequestQueue mQueue) {
		this.mQueue = mQueue;
	}
	
	public SPCreateOrder getCurOrder() {
		return curOrder;
	}

	public void setCurOrder(SPCreateOrder curOrder) {
		this.curOrder = curOrder;
	}

	public SPBooking getCurBookingItems() {
		return curBookingItems;
	}

	public void setCurBookingItems(SPBooking curBookingItems) {
		this.curBookingItems = curBookingItems;
	}
	
	public SPCheckout getCheckout() {
		return curCheckout;
	}

	public void setCheckout(SPCheckout checkout) {
		this.curCheckout = checkout;
	}
	
	
}