package com.example.kirill.androidclient.Model;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Description
 *
 * @author Catalin Prata
 * Date: 2/12/13
 */
public class TcpClient {

    public static final String SERVER_IP = "51.15.70.131"; //your computer IP address
    public static final int SERVER_PORT = 1488;
    // message to send to the server
    private String mServerMessage;
    // sends message received notifications
    private OnMessageReceived mMessageListener = null;
    // while this is true, the server will continue running
    private boolean mRun = false;
    // used to send messages
    private PrintWriter mBufferOut;
    // used to read messages from the server
    private BufferedReader mBufferIn;

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TcpClient(OnMessageReceived listener) {
        Log.e("TCP Client", " method TcpClient");
        mMessageListener = listener;
    }

    /**
     * Sends the message entered by client to the server
     *
     * @param message text entered by client
     */
    public void sendMessage(String message) {
        Log.e("TCP Client", " method Send Message");
        try {
            String msg = new JSONObject()
                    .put("action", 0)
                    .put("login", "testUser")
                    .put("roomcode", "-")
                    .put("data", "-")
                    .toString();
            if (mBufferOut != null && !mBufferOut.checkError()) {
                mBufferOut.println(msg);
                mBufferOut.flush();
            }
        } catch (Exception e) {
            Log.e("TCP send", " Error", e);
        }
    }

    /**
     * Close the connection and release the members
     */
    public void stopClient() {
        Log.e("TCP Client", " method StopClient");
        // send mesage that we are closing the connection
        sendMessage(Constants.CLOSED_CONNECTION + "Kazy");

        mRun = false;

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }

        mMessageListener = null;
        mBufferIn = null;
        mBufferOut = null;
        mServerMessage = null;
    }

    public void run() {
        Log.e("TCP Client", " method Run");
        mRun = true;

        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

            Log.e("TCP Client", "C: Connecting...");
            Log.e("TCP Client", ":" + serverAddr.toString());
            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVER_PORT);
            try {

                //sends the message to the server
                mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                Log.e("TCP Client", "C: out yes...");
                //receives the message which the server sends back
                mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.e("TCP Client", "C: in yes...");
                // send login name
                sendMessage(" ");
                //sendMessage(Constants.LOGIN_NAME + "Kazy");
                Log.e("TCP Client", "C: message sends...");
                //in this while the client listens for the messages sent by the server
                while (mRun) {
                    mServerMessage = mBufferIn.readLine();
                    if (mServerMessage != null && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(mServerMessage);
                    }
                }

                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + mServerMessage + "'");

            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}