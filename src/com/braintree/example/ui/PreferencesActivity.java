package com.braintree.example.ui;

import com.stylepuzzle.www.R;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PreferencesActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    public static String getMerchantServerURL(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("MerchantServer", "");
    }
}
