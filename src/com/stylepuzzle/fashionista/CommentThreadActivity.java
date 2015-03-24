/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stylepuzzle.fashionista;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.flurry.android.FlurryAgent;
import com.stylepuzzle.adapters.ListViewCommentsArrayAdapter;
import com.stylepuzzle.customs.PatchTask;
import com.stylepuzzle.customs.PostImageTask;
import com.stylepuzzle.customs.PostTask;
import com.stylepuzzle.interfaces.OnFinishPosting;
import com.stylepuzzle.models.SPComment;
import com.stylepuzzle.models.SPCommentsList;
import com.stylepuzzle.www.AppController;
import com.stylepuzzle.www.BaseActivity;
import com.stylepuzzle.www.CustomStringRequest;
import com.stylepuzzle.www.R;
import com.stylepuzzle.www.Utils;

/**
 * Demonstrates: 1. ListView which is populated by HTTP paginated requests; 2. Usage of NetworkImageView; 
 * 3. "Endless" ListView pagination with read-ahead
 * 
 * Please note that for production environment you will need to add functionality like handling rotation, 
 * showing/hiding (indeterminate) progress indicator while loading, indicating that there are no more records, etc...
 * @author Ognyan Bankov (ognyan.bankov@bulpros.com)
 */


public class CommentThreadActivity extends BaseActivity implements OnItemClickListener,OnClickListener,OnFinishPosting,OnScrollListener{
	private ListView mLvComments;
	private boolean mHasData = false;
	private boolean mInError = false;
	private ArrayList<SPComment> mEntries = new ArrayList<SPComment>();
	private ListViewCommentsArrayAdapter mAdapter;
	private String commentURL="",calledActivityName="",commentContent="",since="",vibeComment="";
	private EditText etSendingMessage ;
	private Button btnSend,btnTakePicture,btnCancelTakenPicture,btnLoadMoreComments;

	ScheduledExecutorService scheduler;
	private Bitmap bitmapCapturedImage;
	private ImageView ivCapturedImage;
	private FrameLayout flImageTaken;
	InputMethodManager imm;
	boolean isLoadingNewComments =false;
	boolean isFirstTimeLoading =true, isOnTopOftheList=false,isLoadMoreBeenClicked=false,isAddingNewCommentsToEndOfMessage=false;
	int width,height;

	SPCommentsList listOfComments;

	String comment_id = "341",comment_url=null,url_post_comment=null,url_post_pic=null;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_thread);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		//actionBar.setBackgroundDrawable(new ColorDrawable(0xff50386b));

		imm = (InputMethodManager)getSystemService(
				Context.INPUT_METHOD_SERVICE);
		Intent intent = getIntent();
		ivCapturedImage = (ImageView) findViewById(R.id.ivCapturedImage);
		btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
		btnCancelTakenPicture = (Button) findViewById(R.id.btnCancelTakenPicture);
		flImageTaken = (FrameLayout) findViewById(R.id.flImageTaken);
		btnTakePicture.setOnClickListener(this);
		btnCancelTakenPicture.setOnClickListener(this);
		View header = getLayoutInflater().inflate(R.layout.header_comment, null,true); 
		TextView tvMainComment = (TextView) header.findViewById(R.id.tvMainHobnobComment);
		getDisplay();

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if(extras == null) {
			} else {
				comment_id= extras.getString(AppController.ID_COMMENT);
			}
		}


		if(true){//curBTHobnob!=null
			setTitle(" Comments for Order #"+ comment_id);
			//commentURL = curBTHobnob.getCommentsURL();
			mLvComments = (ListView) findViewById(R.id.lv_comments);
			//mLvComments.setStackFromBottom(true);
			mLvComments.setOnScrollListener(this);
			etSendingMessage = (EditText) findViewById(R.id.etComment);
			//etSendingMessage.addTextChangedListener(watcher); 
			btnSend =(Button) findViewById(R.id.btnSendComment);
			btnSend.setOnClickListener(this);
			mLvComments.addHeaderView(header); 
			//			NetworkImageView ivMainComment = (NetworkImageView) header.findViewById(R.id.ivMainImageComment);
			//			ivMainComment.setOnClickListener(this);
			btnLoadMoreComments = (Button) header.findViewById(R.id.btnLoadMoreComments);
			btnLoadMoreComments.setOnClickListener(this);
			//btnLoadMoreComments.setTypeface(myTypefaceMed);
			//			if(curBTHobnob.getComments_count()==0){
			//				btnLoadMoreComments.setVisibility(View.GONE);
			//			}

			//			if(curBTHobnob.getComment()!=null || curBTHobnob.getBtVibeIcons().size()>0){
			//				tvMainComment.setText(curBTHobnob.getComment());
			//				tvMainComment.setVisibility(View.VISIBLE);
			//			}
			//			else{
			//				tvMainComment.setVisibility(View.GONE);
			//			}
			//
			//
			//			NetworkImageView ivUserImage = (NetworkImageView) header.findViewById(R.id.ivUserImage);
			//			String profilePhotoUrl = mainUser.getProfilePhotoUrl("thumb");
			//			ivUserImage.setOnClickListener(this);
			//			if(profilePhotoUrl != null){
			//				ivUserImage.setImageUrl(profilePhotoUrl, mImageLoader);
			//			}
			//			else{
			//				ivUserImage.setImageUrl(AppController.URL_HOLDER_PROFILE,mImageLoader);
			//			}
			//
			//			TextView tvUserName = (TextView) header.findViewById(R.id.tvUserName);
			//			tvUserName.setText(mainUser.getName());
			//			TextView tvTime = (TextView) header.findViewById(R.id.tvTime);
			//			tvTime.setText(getDiftime(curBTHobnob.getCreated_time()));
			//			NetworkImageView ivMain = (NetworkImageView) header.findViewById(R.id.ivMainImageComment);
			//			if(curBTHobnob.getHobnobImage()!=null){
			//				ivMain.setImageUrl(curBTHobnob.getHobnobImage().get(0).getUrl(),mImageLoader);
			//				if(width!=0){
			//					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height/2);
			//					ivMain.setLayoutParams(layoutParams);
			//				}
			//			}
			//
			//			else{
			//				ivMain.setVisibility(View.GONE);
			//			}

			url_post_comment=AppController.URL_ORDER_COMMENT+comment_id+"/comments/";
			url_post_pic=AppController.URL_ORDER_COMMENT+comment_id+"/images/";
		}

		mAdapter = new ListViewCommentsArrayAdapter(this, 0, mEntries, mImageLoader,mApp);
		mLvComments.setAdapter(mAdapter);
		mLvComments.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {
				if(pos>0){
					//alertOnLongClick(pos-1);
				}
				return true;
			}
		}); 
		mLvComments.setOnItemClickListener(this);
		//registerForContextMenu(mLvPicasa);

		isLoadingNewComments = true;
		loadPage(false);
		scheduler = Executors.newScheduledThreadPool(1);
	}






	//	private void scrollMyListViewToBottom() {
	//		mLvComments.post(new Runnable() {
	//			@Override
	//			public void run() {
	//				// Select the last row so it will scroll into view...
	//				mLvComments.setSelection(mLvComments.getCount() - 1);
	//			}
	//		});
	//	}




	@Override
	protected void onResume() {
		super.onResume();
		//		scheduler = Executors.newScheduledThreadPool(1);
		//		scheduler.scheduleWithFixedDelay(new Runnable() {
		//			@Override
		//			public void run() {
		//				//loadPage();		
		//			}
		//		},AppController.ACTIVITY_TIME_DELAY_FOR_FEED_SECOND, AppController.ACTIVITY_TIME_DELAY_FOR_FEED_SECOND, TimeUnit.SECONDS);

		this.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));



	}


	@Override
	protected void onPause() {
		super.onPause();
		this.unregisterReceiver(mMessageReceiver);

		//scheduler.shutdown();
		//FlurryAgent.endTimedEvent("View  Activity");
	}
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Extract data included in the Intent
			String message = intent.getStringExtra("message");
			System.out.println("message-->"+message);
			//do other stuff here
			isAddingNewCommentsToEndOfMessage=true;
			loadPage(true);
		}
	};


	private void loadPage(Boolean loadNewComments) {
		comment_url = AppController.URL_ORDER_COMMENT+comment_id+"/comments/";
		if(listOfComments==null){
			comment_url = comment_url+"?show_latest=true";
		}
		else{
			if(mEntries.size()>0){
				if(!loadNewComments){
					String lowestID = Integer.toString(mEntries.get(0).getId());
					comment_url = comment_url+"?comment_lt="+lowestID;
				}
				else{
					String biggestID = Integer.toString(mEntries.get(mEntries.size()-1).getId());
					comment_url = comment_url+"?comment_gt="+biggestID;

				}
				//comment_url = listOfComments.getPrevious();
			}
		}
		Log.d("testsp", comment_url);
		CustomStringRequest myReq = new CustomStringRequest(Method.GET, 
				comment_url,
				createMyReqSuccessListener(),
				createMyReqErrorListener());
		mQueue.add(myReq);

		//		String url = AppController.buildMainURL(commentURL);
		//
		//		if(mEntries.size()>0){//this is the first time
		//			if(loadNewComments){
		//				url = url + "&since_id="+mEntries.get(mEntries.size()-1).getUuid();
		//			}
		//			else{
		//				url = url + "&until_id="+mEntries.get(0).getUuid();
		//			}
		//		}
		//		if(!url.equals("")){
		//			url = AppController.signURLWithSecret(url);
		//			System.out.println("the url is ---->"+url);
		//			CustomJsonObjectRequest myReq = new CustomJsonObjectRequest(Method.GET,
		//					url,
		//					null,
		//					createMyReqSuccessListener(),
		//					createMyReqErrorListener());
		//			mQueue.add(myReq);
		//		}
	}


	//	private Response.Listener<JSONObject> createMyReqSuccessListener() {
	//
	//		return new Response.Listener<JSONObject>() {
	//			@Override
	//			public void onResponse(JSONObject response) {
	////				try {
	////					System.out.println("resp--->"+response);
	////				} catch (JSONException e) {
	////					System.out.println(e.toString());
	////					showErrorDialog();
	////				}
	//			}
	//		};
	//	}
	//
	//
	//	private Response.ErrorListener createMyReqErrorListener() {
	//		return new Response.ErrorListener() {
	//			@Override
	//			public void onErrorResponse(VolleyError error) {
	//				//showErrorDialog();
	//			}
	//		};
	//	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				//				if(error!=null)
				//				System.out.println(error.getMessage());
				isLoadMoreBeenClicked=false;
			}
		};
	}

	private Response.Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				//String keyValueComments = Utils.addKeyToJson("comments", response);
				System.out.println("------->"+response);
				if(response.equals("[]")){
					btnLoadMoreComments.setVisibility(View.GONE);

				}
				String listOfcomments = Utils.addKeyToJson("comments", response);
				Log.d("testsp", listOfcomments);
				listOfComments = Utils.jsonToObject(listOfcomments, SPCommentsList.class);
				if(listOfComments!=null)
				{	
					//					if(listOfComments.getPrevious()==null){
					//						btnLoadMoreComments.setVisibility(View.GONE);
					//					}

					if(mEntries.size()==0){
						System.out.println("*****------>"+listOfComments.getComments().size());
						mEntries = listOfComments.getComments();
						mAdapter = new ListViewCommentsArrayAdapter(CommentThreadActivity.this, 0, mEntries, mImageLoader,mApp);
						mLvComments.setAdapter(mAdapter);
						int lastCommentID = mEntries.get(mEntries.size()-1).getId(); 
						String json_string = createJsonEncodedForCommentInteger("shopper_last_read_comment",lastCommentID);
						String urlToPatch =  AppController.URL_ORDER_COMMENT+comment_id+"/set-last-read-comment/";
						new PatchTask(CommentThreadActivity.this, json_string,urlToPatch,CommentThreadActivity.this,"key").execute();
					}
					else{
						if(isAddingNewCommentsToEndOfMessage){
							mEntries.addAll(listOfComments.getComments());
							isAddingNewCommentsToEndOfMessage=false;
						}
						else{
							ArrayList<SPComment> allUpToNow = new ArrayList<SPComment>();
							allUpToNow.addAll(mEntries);
							mEntries.clear();
							ArrayList<SPComment> mNewEntries = listOfComments.getComments();
							mEntries.addAll(mNewEntries);
							mEntries.addAll(allUpToNow);
						}
						mAdapter.notifyDataSetChanged();
					}
					isOnTopOftheList=false;
				}
				isLoadMoreBeenClicked=false;
			}
		};
	}


	private void showErrorDialog() {
		mInError = true;
		AlertDialog.Builder b = new AlertDialog.Builder(CommentThreadActivity.this);
		b.setMessage("Error occured");
		b.show();
	}


	public String createJsonEncodedForComment(String key, String value)
	{
		JSONObject j_o= new JSONObject();
		String result="";
		try {
			j_o.put(key, value);
			result = j_o.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String createJsonEncodedForCommentInteger(String key, int value)
	{
		JSONObject j_o= new JSONObject();
		String result="";
		try {
			j_o.put(key, value);
			result = j_o.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}




	//	@Override
	//	public void onFinishedPosting(String result, BTPost post) {
	//		// TODO Auto-generated method stub
	//		System.out.println("result!");
	//		Toast.makeText(this, "post is uploaded!",
	//				Toast.LENGTH_LONG).show();
	//		//createNewBTCommentAndAddToListView();
	//		commentContent="";
	//		isLoadingNewComments =true;
	//		loadPage(true);
	//	}


	public void createNewBTCommentAndAddToListView(){
		SPComment newCommentByUser = new SPComment();
		//		String currentTime = BTUtils.GetUTCdatetimeAsString();
		//		newCommentByUser.setComment(commentContent);
		//		newCommentByUser.setModified_time(currentTime);
		mEntries.add(newCommentByUser);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
		switch (item.getItemId()) {
		case android.R.id.home: 
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}


	/***
	 * important>>>>>>>>>>
	 * @author arashalikhani
	 *
	 */

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		if (requestCode == AppController.REQUEST_CODE_CAMERA_CAPTURE_IMAGE) {
			if (resultCode == RESULT_OK) {
				bitmapCapturedImage = previewCapturedImage(fileUri);
				imageCompletePath= fileUri.toString();
				if(bitmapCapturedImage!=null){
					ivCapturedImage.setImageBitmap(bitmapCapturedImage);
					ivCapturedImage.setVisibility(View.VISIBLE);
					flImageTaken.setVisibility(View.VISIBLE);

				}
			} else if (resultCode == RESULT_CANCELED) {
				// user cancelled Image capture

			} else {
				// failed to capture image
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
						.show();
			}
		}

		else if (requestCode == AppController.REQUEST_CODE_SELECT_IMAGE) {
			if (resultCode == RESULT_OK) {
				Uri selectedImageURI = data.getData();
				fileUri=selectedImageURI;
				imageCompletePath= getImagePath(fileUri);
				//imageFile = new File(fileUri.getPath());
				imageFile = new File(imageCompletePath);
				//bitmapCapturedImage = readBitmapFromDisk(selectedImageURI);
				bitmapCapturedImage = previewCapturedImage(Uri.fromFile(new File(imageCompletePath)));
				if(bitmapCapturedImage!=null){
					ivCapturedImage.setImageBitmap(bitmapCapturedImage);
					ivCapturedImage.setVisibility(View.VISIBLE);
					flImageTaken.setVisibility(View.VISIBLE);
				}
			} else if (resultCode == RESULT_CANCELED) {

			} else {
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}




	//	private TextWatcher watcher = new TextWatcher(){  
	//		String str="";
	//		            @Override  
	//		            public void afterTextChanged(Editable s) {  
	//		                // TODO Auto-generated method stub  
	//		               //Check here for prevent typing of special characters in EditText
	//		                Log.d("testtag","[afterTextChanged][afterTextChanged]"+s);  
	//
	//		            }  
	//		            @Override  
	//		            public void beforeTextChanged(CharSequence s, int start, int count,  
	//		                    int after) {  
	//		                // TODO Auto-generated method stub  
	//
	//		            }  
	//		            @Override  
	//		            public void onTextChanged(CharSequence s, int start, int before,  
	//		                    int count) {  
	//		                    //Check Here s EditText value for Chars
	//		            	if(s.toString().endsWith("@")){
	//			                Log.d("testtag","fire--->");  
	//							Intent intent = new Intent(CommentThreadActivity.this, ManageBuddiesActivity.class);
	//							//intent.putExtra(mApp.ACTIVITY_ID_COMMENT, AppController.ACTIVITY_ID_COMMENT);
	//							intent.putExtra(AppController.ACTIVITY_CALLING_KEY, AppController.ACTIVITY_ID_COMMENT);
	//
	//							startActivityForResult(intent,0);
	//
	//		            	}
	//		                Log.d("testtag","[TextWatcher][onTextChanged]"+s);  
	//		            }  
	//		        }; 

	static class postReportCompletion implements Response.Listener<JSONObject>, Response.ErrorListener
	{

		public postReportCompletion()
		{


		}

		@Override
		public void onErrorResponse(VolleyError error) {

		}

		@Override
		public void onResponse(JSONObject response) {
			System.out.println("resp-->"+response);

		}
	}

	public void getDisplay(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width=dm.widthPixels;
		height=dm.heightPixels;
		int dens=dm.densityDpi;
		double wi=(double)width/(double)dens;
		double hi=(double)height/(double)dens;
		double x = Math.pow(wi,2);
		double y = Math.pow(hi,2);
		double screenInches = Math.sqrt(x+y);
	}




	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//		case R.id.ivMainImageComment:
		//			System.out.println("clicking image");
		//			if(CommentThreadActivity.this.curBTHobnob.getHobnobImage()!=null){
		//				BTImage btImage = CommentThreadActivity.this.curBTHobnob.getHobnobImage().get(0);
		//				mApp.setBtCurImage(btImage);
		//				Intent intent = new Intent(this, FullImageActivity.class);
		//				//intent.putExtra(AppController.ACTIVITY_CALLING_KEY, AppController.ACTIVITY_ID_BAR_SPECIFIC);
		//				startActivityForResult(intent, 22);
		//			}
		//			break;
		//
		//		case R.id.ivUserImage:
		//			System.out.println("main user");
		//			BTUserForHobnob curBTUser = mainUser;
		//			int status = TrendCentral.getInstance().isBuddyWithUser(curBTUser);
		//			mApp.setBtCurUser(curBTUser);
		//
		//			if(status==1){
		//				Intent intent = new Intent(this, FeedsActivity.class);
		//				intent.putExtra(AppController.ACTIVITY_CALLING_KEY,AppController.ACTIVITY_ID_USER_ACTIVTY);
		//				startActivityForResult(intent, 0);
		//			}
		//			else{
		//				Intent intent = new Intent(this, ProfileActvity.class);
		//				startActivityForResult(intent,0);
		//			}
		//
		//			break;


		case R.id.btnSendComment:
			commentContent = etSendingMessage.getText().toString();
			imm.hideSoftInputFromWindow(etSendingMessage.getWindowToken(), 0);
			etSendingMessage.setText("");

			if(!commentContent.equals("")){
				String json_string = createJsonEncodedForComment("content",commentContent);
				System.out.println("url ----->"+url_post_comment);
				new PostTask(this, json_string,url_post_comment,this,"key").execute();
			}

			else if(imageFile!=null){
				Log.d("testsp", url_post_pic);
				imageFile  = imageToFile(bitmapCapturedImage);
				new PostImageTask(this, null,url_post_pic,this,imageFile).execute();
				removingImage();
				fileUri=null;
			}


			//			ivCapturedImage.setImageDrawable(null);
			//			flImageTaken.setVisibility(View.GONE);
			//			if(commentContent.equals("") && imageFile==null){
			//				Toast.makeText(getApplicationContext(), "Please add comment.", Toast.LENGTH_SHORT).show();
			//			}
			//			else{
			//
			//				if(true){//if(AppController.btMe.getBttoken()!=null){
			//
			//					System.out.println("sending comment!");
			//					String jsonEncoded = Utils.createJsonEncodedOfData(commentContent,null,null,fileUri);
			//					System.out.println(commentContent);
			//					//String postUrl=AppController.API_URL_BASE+commentURL+"/?bttoken="+AppController.btMe.getBttoken()+"&format=json"+"&loc="+AppController.getLoc()+"&udid="+mApp.getUDID();
			//					//postUrl = AppController.signURLWithSecret(postUrl);
			//					String postUrl = AppController.buildMainURL(commentURL+"/");
			//					postUrl = AppController.signURLWithSecret(postUrl);
			//					new ImageUploadTask(CommentThreadActivity.this, null, jsonEncoded,postUrl,null,imageFile, "imagename",this).execute();
			//					fileUri= null;
			//					imageFile = null;
			//				}
			//
			//			}
			break;

		case R.id.btnTakePicture:
			takeOrSelectPictures();

			break;

		case R.id.btnCancelTakenPicture:
			removingImage();
			break;
		case R.id.btnLoadMoreComments:
			System.out.println("clicking!!");
			if(!isLoadMoreBeenClicked){
				isLoadMoreBeenClicked=true;
				loadPage(false);
			}

			break;
		default:
			break;
		}
	}

	@Override
	public void onScroll(AbsListView lw, final int firstVisibleItem,
			final int visibleItemCount, final int totalItemCount) {

		switch(lw.getId()) {
		case R.id.lv_comments:     

			// Make your calculation stuff here. You have all your
			// needed info from the parameters of this function.

			// Sample calculation to determine if the last 
			// item is fully visible.
			// final int lastItem = firstVisibleItem + visibleItemCount;

			//System.out.println("last item --->"+ lastItem);
			//			System.out.println("first -->"+firstVisibleItem);
			//			if(firstVisibleItem==0 && !isOnTopOftheList){
			//				isOnTopOftheList=true;
			//				loadPage(false);
			//
			//			}
			//	           if(lastItem == totalItemCount) {
			//	              if(preLast!=lastItem){ //to avoid multiple calls for last item
			//	                Log.d("Last", "Last");
			//	                preLast = lastItem;
			//	              }
			//	           }
		}
	}


	public void removingImage(){
		imageFile=null;
		imageCompletePath="";
		isRemoving=true;
		ivCapturedImage.setImageDrawable(null);
		flImageTaken.setVisibility(View.GONE);
	}



	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}




	public File imageToFile(Bitmap bitmap){
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES);
		File imageFile = null;
		try {
			imageFile = File.createTempFile(
					imageFileName,  /* prefix */
					".jpg",         /* suffix */
					storageDir      /* directory */
					);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 100, bos);
			byte[] bitmapdata = bos.toByteArray();

			//write the bytes in file
			FileOutputStream fos = new FileOutputStream(imageFile);
			fos.write(bitmapdata);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return imageFile;
	}

	@Override
	public void onFinishedPosting(String result) {
		SPComment comment = Utils.jsonToObject(result, SPComment.class);
		if(comment.getOwner()!=null){
			mEntries.add(comment);
			mAdapter.notifyDataSetChanged();
		}
		System.out.println("result --->"+result);
		Log.d("testsp", result);
	}

}
