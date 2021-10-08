package com.example.login_ex;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        findViewById(R.id.btn_updateUserInfo).setOnClickListener(onClickListener);
        findViewById(R.id.btn_updatePersonInfo).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_updateUserInfo:
                    CheckPassWord();
                    break;

                case R.id.btn_updatePersonInfo:
                    updateUserInfo();
                    break;

            }
        }
    };

    private void updateUserInfo() {
        Intent intent = new Intent(Setting.this, Modify_User_Info.class);
        startActivity(intent);
    }

    private void CheckPassWord() {
        Intent intent = new Intent(Setting.this, Check_Password.class);
        startActivity(intent);

    }
}