package com.example.kirill.androidclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class LoginActivity extends AppCompatActivity {


    String login;
    String password;
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";
    private static final String TAG = "RootLog";
    private static int RC_SIGN_IN;

    GoogleSignInClient mGoogleSignInClient;

    SignInButton signInButton;
    //Thread mainThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  mainThread=Thread.currentThread();
        Log.d(TAG, this.getClass().getName() + "/onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            Intent intent = new Intent(this, TcpActivity.class);
            intent.putExtra("ACCOUNT", account);
            startActivity(intent);
        }
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completeTask) {
        try {
            GoogleSignInAccount account = completeTask.getResult(ApiException.class);

            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

}
