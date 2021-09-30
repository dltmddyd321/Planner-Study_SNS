package com.example.calendartest1;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class ScheduleMemo extends AppCompatActivity {
    EditText titleEditText, memoEditText;
    CheckBox AlarmCheckBox;
    TextView timeTextView, selectedTimeText, dbTitle, dbDay, dateEditText;
    ImageButton selectTimeButton;
    Button updateButton;
    int alarmHour = 0, alarmMinute = 0;
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_memo);

        dbHelper = new DBHelper(getApplicationContext(), "Calendar.db", null, 1);
        updateButton = (Button) findViewById(R.id.updateButton);
        dateEditText = (TextView) findViewById(R.id.dateEditText);
        AlarmCheckBox = (CheckBox) findViewById(R.id.AlarmCheckBox);
        selectedTimeText = (TextView) findViewById(R.id.selectedTimeText);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        selectTimeButton = (ImageButton) findViewById(R.id.selectTimeButton);
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        memoEditText = (EditText) findViewById(R.id.memoEditText);
        dbTitle = (TextView) findViewById(R.id.dbTitle);
        dbDay = (TextView) findViewById(R.id.dbDay);

        String date = getIntent().getStringExtra("date");
        dateEditText.setText(date);

        if(dateEditText.getText().toString().equals(dbHelper.getDay(date))) {
            titleEditText.setText(dbHelper.getTitle(date));
            memoEditText.setText(dbHelper.getMemo(date));
        } else {
            titleEditText.setText("");
            memoEditText.setText("");
        }

        AlarmCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeTextView.setVisibility(View.VISIBLE);
                selectedTimeText.setVisibility(View.VISIBLE);
                selectTimeButton.setVisibility(View.VISIBLE);
            }
        });

        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ScheduleMemo.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        selectedTimeText.setText(String.format("  %d시 %d분",hour, minute));
                    }
                },alarmHour, alarmMinute, false);
                timePickerDialog.show();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String alarm = selectedTimeText.getText().toString();
                String memo = memoEditText.getText().toString();
                if(title.equals("") || memo.equals("")) {
                    Toast.makeText(getApplicationContext(), "빈칸 불가",Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.insertData(title,date,alarm,memo);
                    dbTitle.setText(dbHelper.getTitle(date));
                    dbDay.setText(dbHelper.getDay(date));
                    Intent intent = new Intent(ScheduleMemo.this, MainActivity.class);
                    intent.putExtra("date",dbDay.getText().toString());
                    intent.putExtra("title",titleEditText.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
