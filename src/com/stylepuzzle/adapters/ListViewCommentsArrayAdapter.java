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

package com.stylepuzzle.adapters;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.stylepuzzle.models.SPBuyer;
import com.stylepuzzle.models.SPComment;
import com.stylepuzzle.www.AppController;
import com.stylepuzzle.www.FullImageActivity;
import com.stylepuzzle.www.R;


public class ListViewCommentsArrayAdapter extends ArrayAdapter<SPComment> implements OnItemClickListener {
	private ImageLoader mImageLoader;
	private Context context;
	private AppController mApp;
	NetworkImageView ivUser;
	NetworkImageView ivPicInsideComment;
	TextView tvComment,tvUser;
	TextView tvDate;
	int curPosition;
	// some fields
	public static final int FIRST_TYPE = 0;
	public static final int SECOND_TYPE = 1;

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		SPComment item = getItem(position);
		int user_id = item.getOwner().getId();
		if (user_id==AppController.USER_ID) {
			return FIRST_TYPE;
		} else {
			return SECOND_TYPE; 
		} 
	}

	public ListViewCommentsArrayAdapter(Context con, 
			int textViewResourceId, 
			List<SPComment> objects,
			ImageLoader imageLoader, AppController app
			) {
		super(con, textViewResourceId, objects);
		mImageLoader = imageLoader;
		context = con;
		mApp = app;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		//		Typeface myTypefaceBold = Typeface.createFromAsset(context.getAssets(), "GothamRnd-Bold.otf");
		//		Typeface myTypefaceBook = Typeface.createFromAsset(context.getAssets(), "GothamRnd-Book.otf");
		//		Typeface myTypefaceMed = Typeface.createFromAsset(context.getAssets(), "GothamRnd-Medium.otf");
		int type = getItemViewType(position);

		if (v == null) {

			if (type == FIRST_TYPE) {
				LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.item_comment_me, null);
			}
			else{
				LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.item_comment, null);
			}
		}

		curPosition = position;
		ivUser = (NetworkImageView) v.findViewById(R.id.ivUser);
		ivPicInsideComment = (NetworkImageView) v.findViewById(R.id.ivPicInsideComment);
		tvComment = (TextView) v.findViewById(R.id.tvComment);
		tvDate = (TextView) v.findViewById(R.id.tvDate);
		tvUser = (TextView) v.findViewById(R.id.tvUser);

		final SPComment entry = getItem(position);


		if (entry.getOwner()!= null) {
			SPBuyer btUser= entry.getOwner();
			String imageUrl="";
			tvUser.setText(btUser.getName());
			//tvUser.setTypeface(myTypefaceMed);

			String entryUserPhotoUrl = entry.getOwner().getProfile_pic_url();

			if(entryUserPhotoUrl != null){
				ivUser.setImageUrl(entryUserPhotoUrl, mImageLoader);
			}
			else{
				//ivUser.setImageUrl(AppController.URL_HOLDER_PROFILE, mImageLoader);
			}
		} else {

		}
		ivPicInsideComment.setVisibility(View.GONE);

		if(entry.getImages()!=null){
			if(entry.getImages().size()>0){
				ivPicInsideComment.setVisibility(View.VISIBLE);
				final String imgURL = entry.getImages().get(0).getImage_url();
				ivPicInsideComment.setImageUrl(imgURL, mImageLoader);
				ivPicInsideComment.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent i = new Intent(context, FullImageActivity.class);
						System.out.println(">>>>>ini >>>>"+imgURL);
						i.putExtra(AppController.ID_IMAGE_URL, imgURL);
						context.startActivity(i);				
					}
				});
			}
		}
		tvComment.setVisibility(View.GONE);
		System.out.println("the comment-->"+entry.getContent());
		String comment = entry.getContent();

		if(!comment.equals("")){
			tvComment.setText(comment);
			tvComment.setVisibility(View.VISIBLE);
		}

		//		if(entry.getHobnobImage()!=null){
		//			holder.ivPicInsideComment.setVisibility(View.VISIBLE);
		//			holder.ivPicInsideComment.setImageUrl(entry.getHobnobImage().get(0).getUrl(), mImageLoader);
		//			
		//			holder.ivPicInsideComment.setOnClickListener(
		//					new View.OnClickListener() {
		//						@Override
		//						public void onClick(View arg0) {
		//							System.out.println("clciking image >>" +entry.getUser().getFull_name());
		//							
		//							BTImage btImage = entry.getHobnobImage().get(0);
		//							mApp.setBtCurImage(btImage);
		//							Intent intent = new Intent(ListViewCommentsArrayAdapter.this.context, FullImageActivity.class);
		//							//intent.putExtra(AppController.ACTIVITY_CALLING_KEY, AppController.ACTIVITY_ID_BAR_SPECIFIC);
		//							context.startActivity(intent);
		//							
		//							//						Intent intent = new Intent(ListViewCommentsArrayAdapter.this.context, ProfileActvity.class);
		//							//						ListViewCommentsArrayAdapter.this.context.mApp.setBtCurUser(btUser);
		//							//						//intent.putExtra(mApp.STRING_BUNDLE_PLACE_ID, btPlaceID);
		//							//						start(intent, 22);
		//						}
		//					});
		//		}


		//		holder.ivUser.setOnClickListener(
		//				new View.OnClickListener() {
		//					@Override
		//					public void onClick(View arg0) {
		//												//Intent intent = new Intent(ListViewCommentsArrayAdapter.this.context, ProfileActvity.class);
		//												mApp.setBtCurUser(entry.getUser());
		//												int status = TrendCentral.getInstance().isBuddyWithUser(entry.getUser());
		//												if(status==1){
		//													Intent intent = new Intent(ListViewCommentsArrayAdapter.this.context, FeedsActivity.class);
		//													intent.putExtra(AppController.ACTIVITY_CALLING_KEY,AppController.ACTIVITY_ID_USER_ACTIVTY);
		//													context.startActivity(intent);
		//												}
		//												else{
		//													Intent intent = new Intent(ListViewCommentsArrayAdapter.this.context, ProfileActvity.class);
		//													context.startActivity(intent);
		//												}						
		//					}
		//				});



		//tvDate.setText(entry.getTimeDifToShow());
		//holder.tvDate.setTypeface(myTypefaceBook);

		return v;
	}


	private class ViewHolder {


		public ViewHolder(View v) {

			//            secondLine = (TextView) v.findViewById(R.id.secondLine);
			//            thirdLine = (TextView) v.findViewById(R.id.thirdLine);
			//            fourthLine = (TextView) v.findViewById(R.id.fourthLine);

			v.setTag(this);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		System.out.println("the pos -->"+position );

	}


	//	@Override
	//	public void onClick(View v) {
	//		switch (v.getId()) {
	//		case R.id.ivPicInsideComment:
	//			System.out.println("clicking!!");
	//			Intent i  = new Intent(context, FullImageActivity.class);
	//			context.startActivity(i);
	//			break;
	//
	//		default:
	//			break;
	//		}
	//		
	//
	//		
	//	}




}
