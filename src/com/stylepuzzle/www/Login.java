package com.stylepuzzle.www;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.stylepuzzle.customs.PostTask;
import com.stylepuzzle.interfaces.OnFinishPosting;
import com.stylepuzzle.models.SPOrderItem;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class Login extends BaseActivity implements OnClickListener,OnFinishPosting{
	EditText etPhoneNumber,etPhoneNumberCode;
	Button btnSend,btnLogin;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_user);
		setTitle("Login");
		etPhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);
		etPhoneNumberCode = (EditText)findViewById(R.id.etPhoneNumberCode);
		
		btnSend = (Button) findViewById(R.id.btnSend);
		btnSend.setOnClickListener(this);
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		String jsonFormat;
		switch (v.getId()) {
		case R.id.btnSend:
			jsonFormat = createJsonEncodedOPhoneNumber("13108493322");
			System.out.println("--->"+AppController.URL_MY_SEND_SMS_AUTH);
			new PostTask(this, jsonFormat,AppController.URL_MY_SEND_SMS_AUTH,this,"key").execute();
			break;
			
		case R.id.btnLogin:
			System.out.println("login--->");
			String code = etPhoneNumberCode.getText().toString();
			jsonFormat = createJsonEncodedOPhoneNumberWithCode("13108493322",code);
			System.out.println("--->"+AppController.URL_MY_SEND_SMS_AUTH);
			new PostTask(this, jsonFormat,AppController.URL_MY_LOGIN_PHONE_NUMBER,this,"key").execute();
			break;

		default:
			break;
		}
		
	}
	
	public String createJsonEncodedOPhoneNumber(String phoneNumber)
	{
		JSONObject j_o= new JSONObject();
		String result="";
		try {
			j_o.put("phone", phoneNumber);
			result = j_o.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String createJsonEncodedOPhoneNumberWithCode(String phoneNumber,String code)
	{
		JSONObject j_o= new JSONObject();
		String result="";
		try {
			j_o.put("phone", phoneNumber);
			j_o.put("code", code);
			result = j_o.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public void onFinishedPosting(String result) {
		System.out.println("result --> "+result);
	}


}
