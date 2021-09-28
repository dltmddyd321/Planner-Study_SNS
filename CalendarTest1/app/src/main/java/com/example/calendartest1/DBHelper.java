package com.example.calendartest1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE CALENDER (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, date TEXT, alarm TEXT, memo TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CALENDER");
        onCreate(db);
    }

    public void insertData(String title, String date, String alarm, String memo) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("INSERT INTO CALENDER VALUES(null, '" + title + "', '" + date + "', '" + alarm + "','" + memo + "');");
        sqLiteDatabase.close();
    }

    public void deleteData(String date) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM CALENDER WHERE date='" + date + "';");
        sqLiteDatabase.close();
    }

    public String getTitle(String date) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String title = "";

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CALENDER WHERE date='"+ date +"';",null);
        while (cursor.moveToNext()) {
            title = cursor.getString(1);
        }
        return title;
    }

    public String getDay(String date) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String day = "";

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CALENDER WHERE date='"+ date +"';",null);
        while (cursor.moveToNext()) {
            day = cursor.getString(2);
        }
        return day;
    }
}
