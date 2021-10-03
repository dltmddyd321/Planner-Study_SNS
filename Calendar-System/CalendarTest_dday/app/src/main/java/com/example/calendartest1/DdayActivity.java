package com.example.calendartest1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DdayActivity extends AppCompatActivity {

    Calendar tCal;
    CalendarView calendarView;
    Button dDayButton;
    TextView todayDate, dDayDate;
    String day, dDay;
    int tYear, tMonth, tDay;
    private final int ONE_DAY = 24 * 60 * 60 * 1000;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dday);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        dDayButton = (Button) findViewById(R.id.dDaySettingButton);
        todayDate = (TextView) findViewById(R.id.todayDate);
        dDayDate = (TextView) findViewById(R.id.dDayDate);

        tCal = Calendar.getInstance();
        tYear = tCal.get(Calendar.YEAR);
        tMonth = tCal.get(Calendar.MONTH)+1;
        tDay = tCal.get(Calendar.DATE);

        todayDate.setText(tYear+ "년 " + tMonth + "월 " + tDay + "일");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                day = String.format("%d년 %d월 %d일",year,month+1,dayOfMonth);
                dDay = getDday(year, month, dayOfMonth);
                dDayDate.setText(day);
            }
        });

        dDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "D-day 설정 완료!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("dDay", dDay);
                startActivity(intent);
            }
        });
    }

    // Calendar 선택된 D-day Date 값을 불러와 today 와 dDay 를 비교하여 D-Day 값을 구함
    private String getDday(int year, int month, int dayOfMonth) {
        // D-day 설정
        final Calendar dCal = Calendar.getInstance();
        dCal.set(year, month, dayOfMonth);

        // D-day 를 구하기 위해 millisecond 으로 환산하여 d-day 에서 today 의 차를 구함.
        final long dDay = dCal.getTimeInMillis() / ONE_DAY;
        final long today = tCal.getTimeInMillis() / ONE_DAY;
        long result = dDay - today;

        // 출력 시 d-day 에 맞게 표시
        final String strFormat;
        if (result > 0) {
            strFormat = "D-%d";
        } else if (result == 0) {
            strFormat = "D-Day";
        } else {
            result *= -1;
            strFormat = "D+%d";
        }

        final String strCount = (String.format(strFormat, result));
        return strCount;
    }
}

