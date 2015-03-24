package com.stylepuzzle.customs;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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


public class PatchTask extends AsyncTask<Void, Void, String>{
	private String webAddressToPost = "http://httpbin.org/post";
	private ProgressDialog dialog;
	private Context cont;
	private String dataValue;
	private String placeURI,imageName,entityKey;
	private File imageFile;
	private OnFinishPosting finishPosting;

	public PatchTask(Context con, String dv, String urlRequest, OnFinishPosting ofuploading,String eKey){
		cont = con;
		dialog = new ProgressDialog(cont);
		dataValue = dv;
		webAddressToPost = urlRequest;
		finishPosting = ofuploading;
		entityKey = eKey;
	}

	// private ProgressDialog dialog;

	@Override
	protected void onPreExecute() {
//		dialog.setMessage("Uploading...");
//		dialog.show();
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPatch httpPost = new HttpPatch(webAddressToPost);
			System.out.println("-------------->"+webAddressToPost);
			StringEntity entity = new StringEntity(dataValue, HTTP.UTF_8);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			if(AppController.spTokken!=null)
				httpPost.addHeader("Authorization", "Token "+AppController.spTokken);


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
