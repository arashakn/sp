package com.stylepuzzle.www;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.stylepuzzle.customs.PostTask;
import com.stylepuzzle.interfaces.OnFinishPosting;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AddAddress extends BaseActivity implements OnClickListener,OnFinishPosting{
	EditText etRName,etStreet,etCity,etState,etZipCode,etPhone;
	Button btnSave;
	String recipient_name,street,city,state,zipCode,phone,country="US";

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_address);
		etRName = (EditText) findViewById(R.id.etRName);
		etStreet = (EditText) findViewById(R.id.etStreet);
		etCity = (EditText) findViewById(R.id.etCity);
		etState = (EditText) findViewById(R.id.etState);
		etZipCode = (EditText) findViewById(R.id.etZipCode);
		etPhone = (EditText) findViewById(R.id.etPhone);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnSave:
			System.out.println("clciking>>>");
			String jsonEncoded = createJsonEncodedOfData();
			System.out.println(">>>"+jsonEncoded);
			if(!jsonEncoded.equals("")){
				String url = AppController.URL_MY_ADDRESS;
				new PostTask(this, jsonEncoded,url,this,"key").execute();

			}
			break;
		}
	}

	public String createJsonEncodedOfData()
	{

		JSONObject message= new JSONObject();
		String result="";
		recipient_name = etRName.getText().toString();
		street = etStreet.getText().toString();
		city = etCity.getText().toString();
		state = etState.getText().toString();
		zipCode = etZipCode.getText().toString();
		phone = etPhone.getText().toString();

		try {
			message.put("recipient_name", recipient_name);
			message.put("street", street);
			message.put("city", city);
			message.put("state", state);
			message.put("zipcode", zipCode);
			message.put("phone", phone);
			message.put("country", country);
			result=message.toString();

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void onFinishedPosting(String result) {
		System.out.println("result --->"+result);
	}


}
