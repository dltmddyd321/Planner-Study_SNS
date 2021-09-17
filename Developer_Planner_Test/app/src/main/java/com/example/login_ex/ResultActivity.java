package com.example.login_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ResultActivity extends AppCompatActivity {


    private TextView tv_result; // 닉네임 text
    private ImageView iv_profile; // 이미지 뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_result);

        Intent intent = getIntent();
        String nickname = intent.getStringExtra("nickName"); // MainActivity 부터 닉네임 전달받음.
        String photoUrl = intent.getStringExtra("photoUrl"); // MainActivity 부터 프로필사진 Url 전달받음.

        tv_result = findViewById(R.id.tv_result);
        tv_result.setText(nickname); // 닉네임 text를 텍스트 뷰에 세팅


        iv_profile = findViewById(R.id.iv_profile);
        Glide.with(this).load(photoUrl).into(iv_profile); // 프로필 Url을 이미지 뷰에 세팅

    }
}