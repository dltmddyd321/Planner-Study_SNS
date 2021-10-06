package com.example.login_ex;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setting extends AppCompatActivity {

    String password ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        password = intent.getStringExtra("password");

        findViewById(R.id.btn_updateUserInfo).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_updateUserInfo:
                    UserInfo();
                    break;

            }
        }
    };

    private void UserInfo(){
        Intent intent = new Intent(Setting.this, Check_PersonInfo.class);
        intent.putExtra("password",password);
        startActivity(intent);
    }
}