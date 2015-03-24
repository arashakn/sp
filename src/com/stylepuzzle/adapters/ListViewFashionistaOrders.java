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
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.stylepuzzle.fashionista.CommentThreadActivity;
import com.stylepuzzle.fashionista.OrderComments;
import com.stylepuzzle.models.SPAddress;
import com.stylepuzzle.models.SPBooking;
import com.stylepuzzle.models.SPFashionistaList;
import com.stylepuzzle.models.SPImage;
import com.stylepuzzle.models.SPOrder;
import com.stylepuzzle.models.SPOrderFashionista;
import com.stylepuzzle.models.SPOrderItem;
import com.stylepuzzle.www.AppController;
import com.stylepuzzle.www.R;

public class ListViewFashionistaOrders extends ArrayAdapter<SPOrderFashionista> implements OnItemClickListener {
	private ImageLoader mImageLoader;
	private Context context;
	private AppController mApp;
	SPOrderFashionista entry;
	
	public ListViewFashionistaOrders(Context con, 
			int textViewResourceId, 
			List<SPOrderFashionista> objects,
			ImageLoader imageLoader, AppController app) 
	{
		super(con, textViewResourceId, objects);
		mImageLoader = imageLoader;
		context = con;
		mApp = app;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		//		Typeface myTypefaceBold = Typeface.createFromAsset(context.getAssets(), "GothamRnd-Bold.otf");
		//		Typeface myTypefaceBook = Typeface.createFromAsset(context.getAssets(), "GothamRnd-Book.otf");
		//		Typeface myTypefaceMed = Typeface.createFromAsset(context.getAssets(), "GothamRnd-Medium.otf");

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.item_fashionista_order, null);
		}
		
		entry = getItem(position);
		
		TextView tvOrderNumber = (TextView) v.findViewById(R.id.tvOrderNumber);
		tvOrderNumber.setText("order # : "+Integer.toString(entry.getId()));
		
		TextView tvOrderStatus = (TextView) v.findViewById(R.id.tvOrderStatus);
		tvOrderStatus.setText("Status : "+entry.getStatus());
		
//		TextView tvOrderName = (TextView) v.findViewById(R.id.tvOrderName);
//		tvOrderName.setText("Order name : "+entry.getItems().get(0).getCategory());
		
//		TextView tvOrderPrice = (TextView) v.findViewById(R.id.tvOrderPrice);
//		tvOrderPrice.setText("Budget : "+entry.getItems().get(0).getBudget());
		
		TextView tvBuyer = (TextView) v.findViewById(R.id.tvBuyer);
		tvBuyer.setText("Name : "+entry.getBuyer().getFullname());

		TextView tvOrderDate = (TextView) v.findViewById(R.id.tvOrderDate);
		tvOrderDate.setText("Date : "+entry.getCreated_at());
		
		TextView tvUnreadMessage = (TextView) v.findViewById(R.id.tvUnreadMessage);
		tvUnreadMessage.setText("Unread Comments : "+Integer.toString(entry.getNum_unread_comments()));
		
		Button btnStatus = (Button) v.findViewById(R.id.btnStatus);
		btnStatus.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, CommentThreadActivity.class); 
				Log.d("testsp", Integer.toString(entry.getId()));
				String id = Integer.toString(entry.getId());
				i.putExtra(AppController.ID_COMMENT,id);			
				context.startActivity(i);				
			}
		});
				
		//tvBookingItem.setText("11");

//		TextView tvBookingItemPrice = (TextView) v.findViewById(R.id.tvBookingItemPrice);
//		tvBookingItem.setText(entry.getStreet());
		//tvBookingItemPrice.setText(entry.getBudget());
		return v;
	}

	private class ViewHolder {
		NetworkImageView ivFashionista;
		TextView tvUser;
		public ViewHolder(View v) {
			ivFashionista = (NetworkImageView) v.findViewById(R.id.ivFashionista);
			tvUser = (TextView) v.findViewById(R.id.tvUser);
			v.setTag(this);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		System.out.println("the pos -->"+position );
	}



}
