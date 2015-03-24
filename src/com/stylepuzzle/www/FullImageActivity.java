package com.stylepuzzle.www;

import com.android.volley.toolbox.NetworkImageView;
import com.stylepuzzle.models.SPImageBase;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FullImageActivity extends BaseActivity implements OnClickListener{
	AppController mApp;
	private SPImageBase curBTImage;
	private Button btnCancel;
	String imageURL;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.full_image);
		mApp = (AppController) getApplication();
		//curBTImage = mApp.getBtCurImage();
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if(extras == null) {
			} else {
				imageURL= extras.getString(AppController.ID_IMAGE_URL);
			}
		}
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>"+imageURL);
		//String imageURL = "https://d2i5khuir6rc3s.cloudfront.net/media/comment_pics/74657ea9-f463-4b1c-b58f-a300b094c182.jpg";
		NetworkImageView imageView = (NetworkImageView) findViewById(R.id.ivFullImage);
		btnCancel= (Button) findViewById(R.id.btnCancelFullImage);
		btnCancel.setOnClickListener(this);
		imageView.setImageUrl(imageURL,mImageLoader);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCancelFullImage:
			finish();
			break;

		default:
			break;
		}

	}


}
