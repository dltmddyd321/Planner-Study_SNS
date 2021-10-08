package com.example.login_ex.eventpart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login_ex.R;

public class EventCheck extends AppCompatActivity {

    String[] str = {"당신이 얼마나 행복한가는 당신의 감사의 깊이에 달려있다.",
                    "다음 생은 없어요. 그러니 하고 싶은 것을 하세요.",
                    "시작하는 모든 존재는 늘 아프고 불안합니다. 하지만 기억하세요. 당신은 눈부시고 아름답습니다." ,
                    "영원히 살 것처럼 꿈꾸고 오늘 죽을 것처럼 살아라",
                    "나 자신을 좋은 사람으로 바꾸려고 노력하면 좋은 사람이 옵니다.",
                    "일찍 출발한다고 반드시 이기는 것이 아니며 늦게 출발한다고반드시 지는 것도 아닙니다.",
                    "내가 하고 싶은 말보다 상대방이 진정 듣고자 하는 말을 하세요."};
    TextView infoText;
    ImageView notOpenImg, openImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_check);
        openImg = findViewById(R.id.openImg);
        infoText = findViewById(R.id.infoText);
        notOpenImg = findViewById(R.id.notOpenImg);


        notOpenImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int r = (int)(Math.random()*7);
                infoText.setText(str[r]);
                notOpenImg.setEnabled(false);
                infoText.setVisibility(View.VISIBLE);
                notOpenImg.setVisibility(View.INVISIBLE);
                openImg.setVisibility(View.VISIBLE);
            }
        });


    }
}