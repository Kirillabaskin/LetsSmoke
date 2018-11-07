package com.example.kirill.androidclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kirill.androidclient.R;

public class RoomActivity extends AppCompatActivity {

    TextView txtTitle;
    private static final String TAG = "RootLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, this.getClass().getName() + "/onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();
        ((Toolbar) findViewById(R.id.toolbar)).setTitle(intent.getStringExtra("RoomName"));
        setSupportActionBar(toolbar);
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
