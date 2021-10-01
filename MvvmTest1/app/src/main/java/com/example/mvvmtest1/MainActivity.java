package com.example.mvvmtest1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView listRV;
    List<ListViewModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listRV = findViewById(R.id.listRV);
        listRV.setLayoutManager(new LinearLayoutManager(this));

        ListViewModel listViewModel = new ListViewModel();
        listViewModel.name = "LeeSY";
        listViewModel.mail = "dltmddyd321@naver.com";
        data.add(listViewModel);

        ListViewModel listViewModel2 = new ListViewModel();
        listViewModel2.name = "Cal";
        listViewModel2.mail = "cal@naver.com";
        data.add(listViewModel2);

        ListAdapter listAdapter = new ListAdapter(data, MainActivity.this);
        listRV.setAdapter(listAdapter);
    }
}