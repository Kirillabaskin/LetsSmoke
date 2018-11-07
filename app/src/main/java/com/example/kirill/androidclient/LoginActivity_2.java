package com.example.kirill.androidclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity_2 extends AppCompatActivity {


    String login;
    String password;
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";
    private static final String TAG = "RootLog";
    //Thread mainThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  mainThread=Thread.currentThread();
        Log.d(TAG, this.getClass().getName() + "/onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        if (isCheck()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
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

    public void onLogin(View view) {
        Log.d(TAG, this.getClass().getName() + "/onLogin");
        login = ((EditText) findViewById(R.id.pltLogin)).getText().toString();
        password = ((EditText) findViewById(R.id.pltPassword)).getText().toString();
        if (login.equals("kirillabaskin") && password.equals("qqqq")) {
            SaveCheck();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Не правильный логин или пароль", Toast.LENGTH_SHORT).show();
        }
    }

    private void SaveCheck() {
        Log.d(TAG, this.getClass().getName() + "/SaveCheck");
        sPref = getSharedPreferences("AppPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(SAVED_TEXT, true);
        ed.commit();
    }

    private boolean isCheck() {
        Log.d(TAG, this.getClass().getName() + "/isCheck");
        sPref = getSharedPreferences("AppPref", MODE_PRIVATE);
        return sPref.getBoolean(SAVED_TEXT, false);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, this.getClass().getName() + "/onBackPressed");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
