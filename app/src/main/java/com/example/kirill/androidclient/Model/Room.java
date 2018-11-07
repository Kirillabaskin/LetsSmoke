package com.example.kirill.androidclient.Model;

import android.util.Log;

public class Room {
    private final static String TAG = "RootLog";
    private String title;
    private String lastAutor;
    private String lastMessage;


    public Room(String title, String lastAutor, String lastMessage) {
        Log.d(TAG, this.getClass().getName() + "/Room");
        this.title = title;
        this.lastAutor = lastAutor;
        this.lastMessage = lastMessage;
    }

    public String getTitle() {
        Log.d(TAG, this.getClass().getName() + "/getTitle");
        return title;
    }

    public void setTitle(String title) {
        Log.d(TAG, this.getClass().getName() + "/setTitle()");
        this.title = title;
    }

    public String getLastAutor() {
        Log.d(TAG, this.getClass().getName() + "/getLastAutor");
        return lastAutor;
    }

    public void setLastAutor(String lastAutor) {
        Log.d(TAG, this.getClass().getName() + "/setLastAutor");
        this.lastAutor = lastAutor;
    }

    public String getLastMessage() {
        Log.d(TAG, this.getClass().getName() + "/getLastMessage");
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        Log.d(TAG, this.getClass().getName() + "/setLastMessage");
        this.lastMessage = lastMessage;
    }
}
