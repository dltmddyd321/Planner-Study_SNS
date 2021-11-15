package com.example.login_ex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login_ex.communityadapter.SearchImageAdapter;

public class FullImage extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        imageView = (ImageView) findViewById(R.id.image);

        getSupportActionBar().hide();
        getSupportActionBar().setTitle("Full Screen");

        Intent intent = getIntent();
        int position = intent.getExtras().getInt("id");

        SearchImageAdapter searchImageAdapter = new SearchImageAdapter(this);

        imageView.setImageResource(searchImageAdapter.allImageArray[position]);
    }
}