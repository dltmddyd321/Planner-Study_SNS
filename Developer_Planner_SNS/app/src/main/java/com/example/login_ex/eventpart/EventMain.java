package com.example.login_ex.eventpart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.login_ex.R;
import com.example.login_ex.databinding.ActivityEventMainBinding;
import com.example.login_ex.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class EventMain extends AppCompatActivity {

    ActivityEventMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] imageId = {R.drawable.cookiewhat, R.drawable.none};
        String[] name = {"포츈쿠키", "미정"};
        String[] info = {"오늘의 운세는 과연?", "추후 공개 예정"};
        String[] time = {"상시 진행", "미정"};

        ArrayList<Event> eventArrayList = new ArrayList<>();

        for(int i = 0; i< imageId.length; i++) {
            Event event = new Event(name[i], info[i], time[i], imageId[i]);
            eventArrayList.add(event);
        }

        ListAdapter listAdapter = new ListAdapter(EventMain.this, eventArrayList);

        binding.eventList.setAdapter(listAdapter);
        binding.eventList.setClickable(true);
        binding.eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(EventMain.this, EventCheck.class);
                startActivity(intent);
            }
        });
    }
}