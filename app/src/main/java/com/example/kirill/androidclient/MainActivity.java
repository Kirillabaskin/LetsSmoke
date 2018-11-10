package com.example.kirill.androidclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kirill.androidclient.Model.Room;
import com.example.kirill.androidclient.Model.RoomAdaptor;
import com.example.kirill.androidclient.Model.TcpClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> arrayList;
    private TcpClient mTcpClient;
    private ListView listView;
    final String SAVED_TEXT = "saved_text";
    private static final String TAG = "RootLog";
    private Context context;

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private GoogleApiClient mGoogleApiClient;

    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, this.getClass().getName() + "/onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        progressBar = findViewById(R.id.prgBrOnMain);
        progressBar.setVisibility(View.VISIBLE);
        context = getApplicationContext();
        TCPServer();
        // listView = findViewById(R.id.listView);
        // List<Room> item = initData("");

        // RoomAdaptor adapter = new RoomAdaptor(this, item);

        //listView.setAdapter(adapter);
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RoomActivity.class);
                intent.putExtra("RoomName", ((TextView) itemClicked.findViewById(R.id.txtTitle)).getText());
                startActivity(intent);
            }
        });*/

    }

    private void TCPServer() {
        arrayList = new ArrayList<String>();
        new ConnectTask().execute("");
    }

    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {
            //Toast.makeText(getApplicationContext(),"ConnectTask",Toast.LENGTH_SHORT).show();
            //we create a TCPClient object and
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {

                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            //Toast.makeText(getApplicationContext(),"ConnectTask",Toast.LENGTH_SHORT).show();
            mTcpClient.run();


            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //in the arrayList we add the messaged received from server
            setContentView(R.layout.activity_main);
            listView = findViewById(R.id.listView);
            List<Room> item = initData(values[0]);
            RoomAdaptor adapter = new RoomAdaptor(context, item);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), RoomActivity.class);
                    intent.putExtra("RoomName", ((TextView) itemClicked.findViewById(R.id.txtTitle)).getText());
                    startActivity(intent);
                }
            });
            progressBar.setVisibility(View.GONE);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit:
                Toast.makeText(this, "Complite", Toast.LENGTH_LONG).show();
                signOut();
                break;
        }
    }

    private void signOut() {
       /* mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });*/
    }

    @Override
    protected void onPause() {
        Log.d(TAG, this.getClass().getName() + "/onPause");
        super.onPause();
        mTcpClient.stopClient();
        mTcpClient = null;
    }

    @Override
    protected void onResume() {
        Log.d(TAG, this.getClass().getName() + "/onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
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


    private List<Room> initData(String message) {
        Log.d(TAG, this.getClass().getName() + "/initData");
        List<Room> list = new ArrayList<Room>();
        try {
            JSONArray json = new JSONArray(message);
            JSONObject js = (JSONObject) json.get(0);
            JSONArray jsa = (JSONArray) js.get("events");
            JSONObject jso;
            for (int i = 0; i < jsa.length(); i++) {
                if (((JSONObject) jsa.get(i)).getBoolean("flag")) {
                    jso = (JSONObject) jsa.get(i);
                    list.add(new Room(js.getString("roomName"), jso.getString("author"), jso.getString("event")));
                    break;
                }
            }
            return list;
        } catch (Exception e) {
            Log.e("MainActivity: ", " Eroor", e);
            return list;
        }
    }

    public void onRoom(View view) {
        Log.d(TAG, this.getClass().getName() + "/onRoom");
        Intent intent = new Intent(this, RoomActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, this.getClass().getName() + "/onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    public void onSettings(MenuItem menu) {

    }

    public void onExit(MenuItem menu) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                });
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
