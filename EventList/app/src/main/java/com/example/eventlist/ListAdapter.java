package com.example.eventlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Event> {

    public ListAdapter(Context context, ArrayList<Event> eventArrayList) {
        super(context, R.layout.event_item, eventArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Event event = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.eventImg);
        TextView eventName = convertView.findViewById(R.id.eventName);
        TextView eventInfo = convertView.findViewById(R.id.eventInfo);
        TextView eventTime = convertView.findViewById(R.id.eventTime);

        imageView.setImageResource(event.imageId);
        eventName.setText(event.name);
        eventInfo.setText(event.info);
        eventTime.setText(event.time);

        return convertView;
    }
}
