package com.example.calendartest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends ScheduleMemo  {

    CalendarView calendarView;
    TextView dateTextView, scheduleTitleText, dateTitle, dDayText;
    Calendar calendar;
    Button memoBtn, editMemoBtn, deleteMemoButton;
    ImageButton dDaySetting;
    String dDay;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dDayText = (TextView) findViewById(R.id.dDayText);
        dDaySetting = (ImageButton) findViewById(R.id.dDaySetting);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        dateTextView = (TextView)  findViewById(R.id.dateTextView);
        scheduleTitleText = (TextView) findViewById(R.id.scheduleTitle);
        memoBtn = (Button) findViewById(R.id.memoButton);
        editMemoBtn = (Button) findViewById(R.id.editMemoButton);
        deleteMemoButton = findViewById(R.id.deleteMemoButton);
        dateTitle = (TextView) findViewById(R.id.dateTitle);
        String title = getIntent().getStringExtra("title");

        calendar = Calendar.getInstance();
        dateTextView.setText(calendar.get(Calendar.YEAR) +"년 " + (calendar.get(Calendar.MONTH)+1) + "월 " + calendar.get(Calendar.DATE) + "일");

        dDay = getIntent().getStringExtra("dDay");
        setDDayText();

        dDaySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearPref();
                Intent intent = new Intent(getApplicationContext(), DdayActivity.class);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                memoBtn.setVisibility(View.VISIBLE);
                String day = String.format("%d년 %d월 %d일",year,month+1,dayOfMonth);
                dateTextView.setText(day);

                if(day.equals(dbHelper.getDay(day))) {
                    scheduleTitleText.setText(dbHelper.getTitle(day));
                } else {
                    scheduleTitleText.setText("");
                }
                if(scheduleTitleText.getText().toString().equals("")) {
                    memoBtn.setVisibility(View.VISIBLE);
                    memoBtn.setEnabled(true);
                    editMemoBtn.setVisibility(View.INVISIBLE);
                    editMemoBtn.setEnabled(false);
                    deleteMemoButton.setVisibility(View.INVISIBLE);
                    deleteMemoButton.setEnabled(false);
                } else {
                    memoBtn.setVisibility(View.INVISIBLE);
                    memoBtn.setEnabled(false);
                    editMemoBtn.setVisibility(View.VISIBLE);
                    editMemoBtn.setEnabled(true);
                    deleteMemoButton.setVisibility(View.VISIBLE);
                    deleteMemoButton.setEnabled(true);
                }
                memoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, ScheduleMemo.class);
                        intent.putExtra("date",dateTextView.getText().toString());
                        startActivity(intent);
                    }
                });
                editMemoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, ScheduleMemo.class);
                        intent.putExtra("date",dateTextView.getText().toString());
                        startActivity(intent);
                    }
                });
                deleteMemoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dbHelper.deleteData(day);
                        Toast.makeText(getApplicationContext(),"일정 삭제 완료!",Toast.LENGTH_SHORT).show();
                        scheduleTitleText.setText(dbHelper.getTitle(day));
                        memoBtn.setVisibility(View.VISIBLE);
                        memoBtn.setEnabled(true);
                        editMemoBtn.setVisibility(View.INVISIBLE);
                        editMemoBtn.setEnabled(false);
                        deleteMemoButton.setVisibility(View.INVISIBLE);
                        deleteMemoButton.setEnabled(false);
                    }
                });
            }
        });
    }

    private void setDDayText(){
        if (dDay == null){
            dDayText.setText("D-Day 설정");
        }else{
            dDayText.setText(dDay);
        }
    }

    @Override
    protected void onPause() { // Activity가 보이지 않을때 값을 저장
        super.onPause();
        saveState();
    }

    @Override
    protected void onStart() {  // Activity가 보이기 시작할때 값을 저장
        super.onStart();
        restoreState();
        if(dDay != null)
            dDayText.setText(dDay);
    }

    protected void saveState(){ // 데이터를 저장
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("text", dDay);
        editor.commit();
    }

    protected void restoreState(){  // 데이터를 복구
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if((pref!=null) && (pref.contains("text"))){
            dDay = pref.getString("text", "");
        }
    }

    protected void clearPref(){  // sharedpreference에 쓰여진 데이터 지우기
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        dDay = null;
        editor.commit();
    }
}