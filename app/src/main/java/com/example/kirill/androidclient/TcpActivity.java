package com.example.kirill.androidclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kirill.androidclient.Model.TcpClient;

import java.util.ArrayList;

public class TcpActivity extends AppCompatActivity {

    private ListView mList;
    private ArrayList<String> arrayList;
    private TcpClient mTcpClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp);

        arrayList = new ArrayList<String>();
        Log.d("TCP ", "onCreate");
        final EditText editText = (EditText) findViewById(R.id.editText);
        Button send = (Button) findViewById(R.id.btnSend);

        Button conn = findViewById(R.id.btnCon);
        Button dis = findViewById(R.id.btnDis);
        //relate the listView from java to the one created in xml
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = editText.getText().toString();

                //add the text in the arrayList
                arrayList.add("c: " + message);

                //sends the message to the server
                if (mTcpClient != null) {
                    message = "action : 0, login : testUser, roomcode: , data :"; //TODO json objects
                    mTcpClient.sendMessage(message);
                    Toast.makeText(getApplicationContext(), "Complite", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(), "smh", Toast.LENGTH_LONG).show();
                //refresh the list
                editText.setText("");
            }
        });
    }

    public void onConn(View v) {
        new ConnectTask().execute("");
    }

    public void onGo(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SERVER_MES", arrayList.get(arrayList.size() - 1));
        startActivity(intent);
    }

    public void onDis(View v) {
        if (mTcpClient != null) {
            mTcpClient.stopClient();
            mTcpClient = null;
        }
        arrayList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TCP ", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TCP ", "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TCP ", "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TCP ", "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();

        // disconnect
        mTcpClient.stopClient();
        mTcpClient = null;

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
            arrayList.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
        }
    }
}

