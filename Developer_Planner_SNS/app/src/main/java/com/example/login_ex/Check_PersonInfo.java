package com.example.login_ex;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Check_PersonInfo extends AppCompatActivity {

    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_personinfo);

        Intent intent = getIntent();
        password = intent.getStringExtra("password");

        findViewById(R.id.initInfoButton).setOnClickListener(onClickListener);
        findViewById(R.id.updateUserInfo).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.initInfoButton:
                    GoInitInfo();
                    break;

                case R.id.updateUserInfo:
                    CheckPassWord();
                    break;
            }
        }
    };

    private void GoInitInfo() {
        Intent intent = new Intent(Check_PersonInfo.this, Member_InitInfo.class);
        startActivity(intent);
    }

    private void CheckPassWord() {
        Intent intent = new Intent(Check_PersonInfo.this, Check_Password.class);
        intent.putExtra("password", password);
        startActivity(intent);

    }
}