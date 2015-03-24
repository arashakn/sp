package com.stylepuzzle.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stylepuzzle.www.R;

public class GridViewCustomAdapter extends ArrayAdapter
{
         Context context;
       
    

     public GridViewCustomAdapter(Context context) 
     {
             super(context, 0);
             this.context=context;
             
     }
    
     @Override
	public int getCount() 
        {
                     return 9;
        }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) 
     {
             View row = convertView;
             
             if (row == null) 
             {
                     LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                     row = inflater.inflate(R.layout.grid_row, parent, false);


                     TextView textViewTitle = (TextView) row.findViewById(R.id.textView);
                     ImageView imageViewIte = (ImageView) row.findViewById(R.id.imageView);
                     
             		switch(position){
             		
             		case 0:
             			textViewTitle.setText("Accessories");
                        imageViewIte.setImageResource(R.drawable.empty_photo);
             		 break;
             		case 1:
             			textViewTitle.setText("Bags");
                        imageViewIte.setImageResource(R.drawable.empty_photo);
             			break;
             		case 2:
             			textViewTitle.setText("Dresses");
                        imageViewIte.setImageResource(R.drawable.empty_photo);
             			break;
             			
             		case 3:
             			textViewTitle.setText("Jackets");
                        imageViewIte.setImageResource(R.drawable.empty_photo);
             			break;
             			
             		case 4:
             			textViewTitle.setText("Pants");
                        imageViewIte.setImageResource(R.drawable.empty_photo);
             			break;
             		case 5:
             			textViewTitle.setText("Shorts");
                        imageViewIte.setImageResource(R.drawable.empty_photo);
             			break;
             			
             		case 6:
             			textViewTitle.setText("Skirts");
                        imageViewIte.setImageResource(R.drawable.empty_photo);
             			break;
             			
             		case 7:
             			textViewTitle.setText("Sweaters");
                        imageViewIte.setImageResource(R.drawable.empty_photo);
             			break;
             			
             		case 8:
             			textViewTitle.setText("Tops");
                        imageViewIte.setImageResource(R.drawable.empty_photo);
             			break;
             		}
                     
//                     if(position%2==0)
//                     {
//                             textViewTitle.setText("Facebook");
//                             imageViewIte.setImageResource(R.drawable.empty_photo);
//                     }
//                     else
//                     {
//                             textViewTitle.setText("Twitter");
//                              imageViewIte.setImageResource(R.drawable.empty_photo);
//                     }
             } 

    
      
      return row;

     }

}
