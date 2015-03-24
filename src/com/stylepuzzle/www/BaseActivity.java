package com.stylepuzzle.www;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.MenuItem;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;


public class BaseActivity  extends Activity{
	public AppController mApp;
	public ImageLoader mImageLoader;
	protected RequestQueue mQueue;
	Typeface myTypefaceBook;
	Typeface myTypefaceMed;
	ActionBar ab;
	public boolean isRemoving=false;
	public String capImagefullUrl="",imageCompletePath="";
	protected Uri fileUri; // file url to store image/video
	public File imageFile;


	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		mApp = (AppController)getApplication();
		ab = getActionBar();
		ab.setHomeButtonEnabled(true);
		mQueue = mApp.getmQueue();
		mImageLoader = mApp.getmImageLoader();
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case android.R.id.home:
//			Intent homeIntent = new Intent(this, LandingPage.class);
//			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(homeIntent);
		}
		return (super.onOptionsItemSelected(menuItem));
	}


	public boolean isDeviceSupportCamera() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}
	





	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("file_uri", fileUri);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		fileUri = savedInstanceState.getParcelable("file_uri");


	}


	
	@Override
	protected void onStart()
	{
		super.onStart();
		//FlurryAgent.onStartSession(this, AppController.API_KEY_FLURRY);
	}

	@Override
	protected void onStop()
	{
		super.onStop();		
		//FlurryAgent.onEndSession(this);
	}
	
	public Rect getImageSizeFromFile(Uri fileUri)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		//Returns null, sizes are in the options variable
		BitmapFactory.decodeFile(fileUri.getPath(), options);
		int width = options.outWidth;
		int height = options.outHeight;
		//If you want, the MIME type will also be decoded (if possible)


		return new Rect(0, 0, width, height);
	}

	/*
	 * Display image from a path to ImageView
	 */
	public Bitmap previewCapturedImage(Uri fileUri) {
		
		return this.getOrientedBitmapFromFile(fileUri, 8);
	}

	private Bitmap getOrientedBitmapFromFile(Uri fileUri, int inSampleSize)
	{
		try {
			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();
			if (inSampleSize > 1)
				options.inSampleSize = inSampleSize;
			
			Bitmap sourceImage = BitmapFactory.decodeFile(fileUri.getPath(),
					options);

			ExifInterface ei = new ExifInterface(fileUri.getPath());
			int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

			switch(orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				System.out.println("90");
				sourceImage=rotateImage(sourceImage, 90);
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				sourceImage=rotateImage(sourceImage, 90);
				break;
				// etc.
			}

			return sourceImage;
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
		
		return null;
	}
	
	private Bitmap getOrientedBitmapFromFile(Uri fileUri, int targetWidth, int targetHeight)
	{
		Rect imageBounds = getImageSizeFromFile(fileUri);
		
		int sampleSize = calculateSampleSize(imageBounds.width(), imageBounds.height(), 1024, 512);
		Bitmap sourceImage = this.getOrientedBitmapFromFile(fileUri, sampleSize);
		
		return sourceImage;
	}
	
	public static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {

        final float srcAspect = (float)srcWidth / (float)srcHeight;
        final float dstAspect = (float)dstWidth / (float)dstHeight;
 
        if (srcAspect > dstAspect) {
            return srcWidth / dstWidth;
        } else {
            return srcHeight / dstHeight;
        }
	   
	}

	
	public File createImageForHobnobPost(Uri fileUri)
	{
		Bitmap sourceImage = this.getOrientedBitmapFromFile(fileUri, 1024, 1024);
		
		String randomString = UUID.randomUUID().toString();
		String photoUploadEntryName = "com.bartrendr.uploadPhoto.".concat(randomString);
		
		File imageFile = null;
		try {
			imageFile = File.createTempFile(
					photoUploadEntryName,  /* prefix */
					".jpg",         /* suffix */
					mApp.getCacheDir()      /* directory */
					);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			sourceImage.compress(CompressFormat.JPEG, 70, bos);
			byte[] bitmapdata = bos.toByteArray();

			//write the bytes in file
			FileOutputStream fos = new FileOutputStream(imageFile);
			fos.write(bitmapdata);
			fos.flush();
			fos.close();
			
			return imageFile;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	public File createImageForTwitterPost(Uri fileUri)
	{
		Rect imageBounds = getImageSizeFromFile(fileUri);
		
		int sampleSize = calculateSampleSize(imageBounds.width(), imageBounds.height(), 1024, 512);
		Bitmap sourceImage = this.getOrientedBitmapFromFile(fileUri, sampleSize);
		
		String randomString = UUID.randomUUID().toString();
		String photoUploadEntryName = "com.bartrendr.uploadTwitter.".concat(randomString);
		
		File imageFile = null;
		try {
			imageFile = File.createTempFile(
					photoUploadEntryName,  /* prefix */
					".jpg",         /* suffix */
					mApp.getCacheDir()      /* directory */
					);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			sourceImage.compress(CompressFormat.JPEG, 70, bos);
			byte[] bitmapdata = bos.toByteArray();

			//write the bytes in file
			FileOutputStream fos = new FileOutputStream(imageFile);
			fos.write(bitmapdata);
			fos.flush();
			fos.close();
			
			return imageFile;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	// And to convert the image URI to the direct file system path of the image file
	public String getRealPathFromURI(Uri contentUri) {

		// can post image
		String [] proj={MediaColumns.DATA};
		Cursor cursor = managedQuery( contentUri,
				proj, // Which columns to return
				null,       // WHERE clause; which rows to return (all rows)
				null,       // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();

		return cursor.getString(column_index);
	}

	public Bitmap readBitmapFromDisk(Uri selectedImage) { 
		Bitmap bm = null; 
		BitmapFactory.Options options = new BitmapFactory.Options(); 
		options.inSampleSize = 5; 
		AssetFileDescriptor fileDescriptor =null; 
		try { 
			fileDescriptor = this.getContentResolver().openAssetFileDescriptor(selectedImage,"r"); 
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} 
		finally{ 
			try { 
				bm = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options); 
				fileDescriptor.close(); 
			} catch (IOException e) { 
				e.printStackTrace(); 
			} 
		} 
		return bm; 
	} 

	/*
	 * Capturing Camera Image will lauch camera app requrest image capture
	 */
	public void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getOutputMediaFileUri(AppController.MEDIA_TYPE_IMAGE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		// start the image capture Intent
		startActivityForResult(intent, AppController.REQUEST_CODE_CAMERA_CAPTURE_IMAGE);
	}



	public void caputreImageNew(){
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		ContentValues values = new ContentValues(3);

		values.put(MediaColumns.MIME_TYPE, "image/jpeg");
		fileUri = getContentResolver().insert(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(takePictureIntent,  AppController.REQUEST_CODE_CAMERA_CAPTURE_IMAGE);
	}


	void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {

			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, AppController.REQUEST_CODE_CAMERA_CAPTURE_IMAGE);
			}
		}
	}


	File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
				);

		// Save a file: path for use with ACTION_VIEW intents
		capImagefullUrl = "file:" + image.getAbsolutePath();
		return image;
	}
	/*
	 * Creating file uri to store image/video
	 */
	public Uri getOutputMediaFileUri(int type) {
		System.out.println("");
		imageFile=getOutputMediaFile(type);
		return Uri.fromFile(imageFile);
	}

	
	
	public static Bitmap rotateImage(Bitmap source, float angle)
	{
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}
	
	
	public static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				AppController.IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				//				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
				//						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == AppController.MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}
		return mediaFile;
	}
	
	
	
	public void takeOrSelectPictures(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select Picture")
		.setItems(R.array.string_array_take_picture, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which==0){//image
//							       			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//							       			photoPickerIntent.setType("image/*");
//							       			startActivityForResult(photoPickerIntent, AppController.REQUEST_CODE_SELECT_IMAGE);  
			Intent intent;
					
					
//					
//					
//				    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//				    intent.addCategory(Intent.CATEGORY_OPENABLE);
//				    intent.setType("image/jpeg");
//				    startActivityForResult(intent, AppController.REQUEST_CODE_SELECT_IMAGE);
//	       			startActivityForResult(photoPickerIntent, AppController.REQUEST_CODE_SELECT_IMAGE);  

//					
				    

					
					intent = new Intent(Intent.ACTION_PICK, Uri.parse(
							 "content://media/internal/images/media")); 
							 //startActivity(intent); 
								startActivityForResult(Intent.createChooser(intent,
								"Select Picture"), AppController.REQUEST_CODE_SELECT_IMAGE);
//					
//					intent.setType("image/*");
//					intent.setAction(Intent.ACTION_GET_CONTENT);
//					
//					
//					
//					startActivityForResult(Intent.createChooser(intent,
//							"Select Picture"), AppController.REQUEST_CODE_SELECT_IMAGE);
//					
					
//					if (Build.VERSION.SDK_INT <19){
//						intent = new Intent(); 
//					    intent.setType("image/jpeg");
//					    intent.setAction(Intent.ACTION_GET_CONTENT);
//						startActivityForResult(Intent.createChooser(intent,
//						"Select Picture"), AppController.REQUEST_CODE_SELECT_IMAGE);
//					} else {
//					    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//					    intent.addCategory(Intent.CATEGORY_OPENABLE);
//					    intent.setType("image/jpeg");
//					    startActivityForResult(intent, AppController.REQUEST_CODE_SELECT_IMAGE);
//					}

				}
				else if(which==1){//take picture
					if(isDeviceSupportCamera()){
						//dispatchTakePictureIntent();
						mycaptureImage();
					}
				}
				else if (which==2){
					imageCompletePath="";
					isRemoving=true;
					removingImage();
				}
			}
		});
		builder.create();
		builder.show();
	}
	
	public String getImagePath(Uri uri){
		   Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		   cursor.moveToFirst();
		   if(cursor.getCount()==0)
			   return null;
		   String document_id = cursor.getString(0);
		   document_id = document_id.substring(document_id.lastIndexOf(":")+1);
		   cursor.close();
		   cursor = getContentResolver().query( 
		   android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
		   null, BaseColumns._ID + " = ? ", new String[]{document_id}, null);
		   cursor.moveToFirst();
		   if(cursor.getColumnCount()==0)
			   return null;
		   String path = cursor.getString(cursor.getColumnIndex(MediaColumns.DATA));
		   cursor.close();
		   return path;
		}
	
	
	private void mycaptureImage() {
	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	fileUri = getOutputMediaFileUri(AppController.MEDIA_TYPE_IMAGE);
	intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	// start the image capture Intent
	startActivityForResult(intent, AppController.REQUEST_CODE_CAMERA_CAPTURE_IMAGE);
}
	
	public void removingImage(){
		
	}
	
	public void finishOK(){
		Bundle conData = new Bundle();
		Intent intent = new Intent();
		intent.putExtras(conData);
		setResult(RESULT_OK, intent);
		finish();
	}

}
