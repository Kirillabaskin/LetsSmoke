package com.example.kirill.androidclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "RootLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, this.getClass().getName() + "/onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, this.getClass().getName() + "/onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, this.getClass().getName() + "/onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, this.getClass().getName() + "/onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, this.getClass().getName() + "/onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, this.getClass().getName() + "/onDestroy");
        super.onDestroy();
    }
}
