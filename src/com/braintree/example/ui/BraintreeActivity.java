package com.braintree.example.ui;

import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.braintree.example.net.BraintreeHttpsClient;
import com.braintreegateway.encryption.Braintree;
import com.stylepuzzle.www.R;

public class BraintreeActivity extends Activity {
    static final int DIALOG_RESPONSE_ID = 1;
    private String publicKey = "your_client_side_encryption_public_key";
    private EditText creditCard;
    private EditText expirationDate;
    private EditText cvv;
    private Button submitButton;
    private String lastResponseText;
    private AsyncTask<String, Void, String> postTask;
    private String client_token="eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI0NzRhNWJkMzFlY2ZiZmE5Zjg2MmExZjIyNjVkZTUwZTY2YWFkNzA1NDlmZDAxOWUzMDQyMDRiZjc0NjY0YmVjfGNyZWF0ZWRfYXQ9MjAxNS0wMS0xM1QwMjoyMToyMC4yNTY2NzM5NzErMDAwMFx1MDAyNm1lcmNoYW50X2lkPTRjZms5eTN3a3czejVwanBcdTAwMjZwdWJsaWNfa2V5PWpwbTJ3cjljd3Y2Ynk3dm0iLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvNGNmazl5M3drdzN6NXBqcC9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbImN2diJdLCJwYXltZW50QXBwcyI6W10sImNsaWVudEFwaVVybCI6Imh0dHBzOi8vYXBpLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb206NDQzL21lcmNoYW50cy80Y2ZrOXkzd2t3M3o1cGpwL2NsaWVudF9hcGkiLCJhc3NldHNVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImF1dGhVcmwiOiJodHRwczovL2F1dGgudmVubW8uc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbSIsImFuYWx5dGljcyI6eyJ1cmwiOiJodHRwczovL2NsaWVudC1hbmFseXRpY3Muc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbSJ9LCJ0aHJlZURTZWN1cmVFbmFibGVkIjpmYWxzZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiU3R5bGVQdXp6bGUsIEluYyIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJtZXJjaGFudEFjY291bnRJZCI6IjlrdmJubTJzMnoyZnY4d20iLCJjdXJyZW5jeUlzb0NvZGUiOiJVU0QifSwiY29pbmJhc2VFbmFibGVkIjpmYWxzZSwibWVyY2hhbnRJZCI6IjRjZms5eTN3a3czejVwanAiLCJ2ZW5tbyI6Im9mZiJ9";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);
        publicKey=client_token;
        creditCard = (EditText) findViewById(R.id.credit_card);
        expirationDate = (EditText) findViewById(R.id.expiration_date);
        cvv = (EditText) findViewById(R.id.cvv);
        submitButton = (Button) findViewById(R.id.submit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.settings:
            startActivity(new Intent(this, PreferencesActivity.class));
            return true;
        }
        return false;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        switch (id) {
        case DIALOG_RESPONSE_ID:
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Braintree Response")
                    .setCancelable(true)
                    .setMessage(lastResponseText)
                    .setPositiveButton("OK", null);
            dialog = builder.create();
            break;
        default:
            dialog = null;
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
        case DIALOG_RESPONSE_ID:
            ((AlertDialog) dialog).setMessage(lastResponseText);
        }
    }

    public void submitForm(View view) {
        String ccNumber = encryptFormField(creditCard);
        String ccExpDate = encryptFormField(expirationDate);
        String ccCvv = encryptFormField(cvv);

        postTask = new PostToMerchantServerTask();
        postTask.execute(new String[] { ccNumber, ccExpDate, ccCvv });
    }

    private String getFieldText(EditText field) {
        return new String(field.getText().toString());
    }

    private String encryptFormField(View formField) {
        String formFieldText = getFieldText((EditText) formField);
        Braintree braintree = new Braintree(publicKey);
        return braintree.encrypt(formFieldText);
    }

    private class PostToMerchantServerTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            disableUI();
        }

        @Override
        protected String doInBackground(String... formFieldValues) {
            String merchantServerURL = PreferencesActivity.getMerchantServerURL(getBaseContext());
            InputStream keyStore = getBaseContext().getResources().openRawResource(R.raw.keystore);
            String keyStorePassword = "password1";
            BraintreeHttpsClient client = new BraintreeHttpsClient(merchantServerURL, keyStore, keyStorePassword);
            client.addParam("cc_number", formFieldValues[0]);
            client.addParam("cc_exp_date", formFieldValues[1]);
            client.addParam("cc_cvv", formFieldValues[2]);
            client.post();
            return client.prettyResponse();
        }

        @Override
        protected void onPostExecute(String result) {
            enableUI();
            lastResponseText = result;
            showDialog(DIALOG_RESPONSE_ID);
        }
    }

    private void disableUI() {
        creditCard.setEnabled(false);
        expirationDate.setEnabled(false);
        cvv.setEnabled(false);
        submitButton.setEnabled(false);
        setProgressBarIndeterminateVisibility(true);
    }

    private void enableUI() {
        creditCard.setEnabled(true);
        expirationDate.setEnabled(true);
        cvv.setEnabled(true);
        submitButton.setEnabled(true);
        setProgressBarIndeterminateVisibility(false);
    }
}
