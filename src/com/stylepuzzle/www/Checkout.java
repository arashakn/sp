package com.stylepuzzle.www;

import com.stylepuzzle.models.SPCheckout;
import com.stylepuzzle.models.SPSummary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Checkout extends BaseActivity implements OnClickListener{

	TextView tvSubtotalValue,tvServiceFeeValue,tvShippingValue,tvTotalValue;
	RelativeLayout rlAddress,rlPayWith;
	SPCheckout checkout;
	SPSummary summary;
	String currency;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_out);
		checkout = mApp.getCheckout();
		summary = checkout.getSummary();
		currency = checkout.getCurrency().getCurrency_symbol();

		tvSubtotalValue = (TextView) findViewById(R.id.tvSubtotalValue);
		tvSubtotalValue.setText(currency+Integer.toString(summary.getSubtotal()));

		tvServiceFeeValue = (TextView) findViewById(R.id.tvServiceFeeValue);
		tvServiceFeeValue.setText(currency+Integer.toString(summary.getService_fee()));

		tvShippingValue = (TextView) findViewById(R.id.tvShippingValue);
		if(summary.getShipping()!=null)
			tvShippingValue.setText(currency+Integer.toString(summary.getShipping()));

		tvTotalValue = (TextView) findViewById(R.id.tvTotalValue);
		if(summary.getTotal()!=null)
			tvTotalValue.setText(currency+Integer.toString(summary.getTotal()));

		rlAddress = (RelativeLayout)findViewById(R.id.rlAddress);
		rlAddress.setOnClickListener(this);

		rlPayWith = (RelativeLayout)findViewById(R.id.rlPayWith);
		rlPayWith.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rlAddress:
			Intent intent = new Intent(this, Addresses.class);
//			intent.putExtra(AppController.ACTIVITY_CALLING_KEY, AppController.ACTIVITY_ID_BAR_SPECIFIC);
			startActivity(intent);
			break;

		case R.id.rlPayWith:
			System.out.println("clicking!");
			break;	

		default:
			break;
		}
	}


}
