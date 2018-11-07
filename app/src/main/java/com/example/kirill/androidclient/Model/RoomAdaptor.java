package com.example.kirill.androidclient.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kirill.androidclient.R;

import java.util.List;

public class RoomAdaptor extends BaseAdapter {
    private final static String TAG = "RootLog";

    private List<Room> list;
    private LayoutInflater layoutInflater;

    public RoomAdaptor(Context context, List<Room> list) {
        Log.d(TAG, this.getClass().getName() + "/RoomAdaptor");
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        Log.d(TAG, this.getClass().getName() + "/getCount");
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d(TAG, this.getClass().getName() + "/getItem");
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, this.getClass().getName() + "/getItemId");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, this.getClass().getName() + "/getView");
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        }

        Room room = getRoom(position);

        TextView textView = (TextView) view.findViewById(R.id.txtTitle);
        textView.setText(room.getTitle());
        view.findViewById(R.id.txtTitle);

        textView = (TextView) view.findViewById(R.id.txtAutor);
        textView.setText(room.getLastAutor());
        view.findViewById(R.id.txtAutor);

        textView = (TextView) view.findViewById(R.id.txtMessage);
        textView.setText(room.getLastMessage());
        view.findViewById(R.id.txtMessage);

        return view;
    }

    private Room getRoom(int position) {
        Log.d(TAG, this.getClass().getName() + "/getRoom");
        return (Room) getItem(position);
    }
}
