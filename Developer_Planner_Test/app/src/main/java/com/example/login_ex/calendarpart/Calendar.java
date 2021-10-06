package com.example.login_ex.calendarpart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.login_ex.MainActivity;
import com.example.login_ex.R;

public class Calendar extends ScheduleMemo {

    CalendarView calendarView;
    TextView dateTextView, scheduleTitleText, dateTitle, dDayText;
    java.util.Calendar calendar;
    Button memoBtn, editMemoBtn, deleteMemoButton;
    ImageButton dDaySetting;
    String dDay;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

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

        calendar = java.util.Calendar.getInstance();
        dateTextView.setText(calendar.get(java.util.Calendar.YEAR) +"년 " + (calendar.get(java.util.Calendar.MONTH)+1) + "월 " + calendar.get(java.util.Calendar.DATE) + "일");

        dDay = getIntent().getStringExtra("dDay");
        setDDayText();

        dDaySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        Intent intent = new Intent(Calendar.this, ScheduleMemo.class);
                        intent.putExtra("date",dateTextView.getText().toString());
                        startActivity(intent);
                    }
                });
                editMemoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Calendar.this, ScheduleMemo.class);
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
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);

        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        String text = sf.getString("text","D-Day 설정");
        dDayText.setText(text);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Calendar.this, MainActivity.class);
        startActivity(intent);
    }
}
