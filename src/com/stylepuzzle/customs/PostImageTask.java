package com.stylepuzzle.customs;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.stylepuzzle.interfaces.OnFinishPosting;
import com.stylepuzzle.www.AppController;


public class PostImageTask extends AsyncTask<Void, Void, String>{
	private String webAddressToPost = "http://httpbin.org/post";
	private ProgressDialog dialog;
	private Context cont;
	private String dataValue;
	private String placeURI,imageName;
	private File imageFile;
	private OnFinishPosting finishPosting;

	public PostImageTask(Context con, String dv, String urlRequest, OnFinishPosting ofuploading,File imgFile){
		cont = con;
		dialog = new ProgressDialog(cont);
		dataValue = dv;
		webAddressToPost = urlRequest;
		finishPosting = ofuploading;
		imageFile = imgFile;
	}

	// private ProgressDialog dialog;

	@Override
	protected void onPreExecute() {
		dialog.setMessage("Uploading...");
		dialog.show();
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(webAddressToPost);
			System.out.println("-------------->"+webAddressToPost);
			//StringEntity entity = new StringEntity(dataValue, HTTP.UTF_8);

			if(AppController.spTokken!=null)
				httpPost.addHeader("Authorization", "Token "+AppController.spTokken);

			
			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);

			if(imageFile!=null){
				FileBody cbFile = new FileBody(imageFile, "image/jpeg");
				entity.addPart("picture", cbFile);
			}
			else{
				System.out.println("the imagefile/imagename is null!!!!");
			}



			httpPost.setEntity(entity);
			
			
			

			HttpResponse response = httpClient.execute(httpPost,
					localContext);
			
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(
							response.getEntity().getContent(), "UTF-8"));

			//String sResponse = reader.readLine();

			StringBuilder sb = new StringBuilder();
			String s;
			while(true )
			{
				s = reader.readLine();
				if(s==null || s.length()==0)
					break;
				sb.append(s);

			}
			reader.close();
			return sb.toString();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			// something went wrong. connection with the server error
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		dialog.dismiss();
		if(result!=null){
			finishPosting.onFinishedPosting(result);
		}
		else{
			System.out.println("null!!!");  
			Toast.makeText(cont, "post not uploaded!",
					Toast.LENGTH_LONG).show();
		}

	}

}
