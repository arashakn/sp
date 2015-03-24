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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.stylepuzzle.models.SPImage;
import com.stylepuzzle.www.AppController;
import com.stylepuzzle.www.R;

public class ListViewFashionistaProfile extends ArrayAdapter<SPImage> implements OnItemClickListener {
	private ImageLoader mImageLoader;
	private Context context;
	private AppController mApp;

	public ListViewFashionistaProfile(Context con, 
			int textViewResourceId, 
			List<SPImage> objects,
			ImageLoader imageLoader, AppController app
			) {
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
			v = vi.inflate(R.layout.item_fashionista_profile, null);
		}

		//ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);       

//		if (holder == null) {
//			holder = new ViewHolder(v);
//			v.setTag(R.id.id_holder, holder);
//		}        

//		final SPImage entry = getItem(position);
//		if (entry.getImage_url()!= null) {
//			String imageUrl="";
//			holder.tvUser.setText("name");
//			//holder.tvUser.setTypeface(myTypefaceMed);
//			holder.ivFashionista.setImageUrl(entry.getImage_url(), mImageLoader);
//		}
//
//		else {
//
//		}
		
		final SPImage entry = getItem(position);
		NetworkImageView ivFashionista = (NetworkImageView) v.findViewById(R.id.ivFashionista);
		TextView tvUser = (TextView) v.findViewById(R.id.tvUser);
		tvUser.setText("LA, CA");
		ivFashionista.setImageUrl(entry.getImage_url(), mImageLoader);

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
