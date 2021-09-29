package com.example.calendartest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends ScheduleMemo {

    CalendarView calendarView;
    TextView dateTextView, scheduleTitleText, dateTitle;
    Calendar calendar;
    Button memoBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        dateTextView = (TextView)  findViewById(R.id.dateTextView);
        scheduleTitleText = (TextView) findViewById(R.id.scheduleTitle);
        memoBtn = (Button) findViewById(R.id.memoButton);
        dateTitle = (TextView) findViewById(R.id.dateTitle);
        String title = getIntent().getStringExtra("title");

        calendar = Calendar.getInstance();
        dateTextView.setText(calendar.get(Calendar.YEAR) +"년 " + (calendar.get(Calendar.MONTH)+1) + "월 " + calendar.get(Calendar.DATE) + "일");

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
                memoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, ScheduleMemo.class);
                        intent.putExtra("date",dateTextView.getText().toString());
                        startActivity(intent);
                    }
                });
            }
        });
    }
}