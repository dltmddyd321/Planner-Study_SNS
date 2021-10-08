package com.example.login_ex.eventpart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login_ex.R;

public class EventCheck extends AppCompatActivity {

    Button openBtn;
    TextView infoText;
    ImageView notOpenImg, openImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_check);
        openBtn = findViewById(R.id.openBtn);
        infoText = findViewById(R.id.infoText);
        notOpenImg = findViewById(R.id.notOpenImg);
        openImg = findViewById(R.id.openImg);

        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBtn.setVisibility(View.INVISIBLE);
                openBtn.setEnabled(false);
                infoText.setVisibility(View.VISIBLE);
                notOpenImg.setVisibility(View.INVISIBLE);
                openImg.setVisibility(View.VISIBLE);
            }
        });
    }
}