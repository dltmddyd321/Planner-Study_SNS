package com.example.login_ex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.login_ex.calendarpart.Calendar;
import com.example.login_ex.eventpart.EventMain;
import com.example.login_ex.memo.MemoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ImageView weatherIcon;
    private long backBtnTime = 0;

    //데이터를 호출할 서비스의 기본 주소
    private final String url = "http://api.openweathermap.org/data/2.5/weather";

    //서비스에서 제공받은 API KEY 등록
    private final String appid = "80b3b408e398e420f91d1e3fe08b5328";
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();

        weatherIcon = findViewById(R.id.mainWeatherIcon);
        findViewById(R.id.logoutButton).setOnClickListener(onClickListener);
        findViewById(R.id.mainWeatherIcon).setOnClickListener(onClickListener);
        findViewById(R.id.settingButton).setOnClickListener(onClickListener);
        findViewById(R.id.calendarButton).setOnClickListener(onClickListener);
        findViewById(R.id.eventButton).setOnClickListener(onClickListener);
        findViewById(R.id.memo).setOnClickListener(onClickListener);

        //GPS 시스템 활용을 위한 Location Manager 선언
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        getWeatherDetails();

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 앱이 실행될 때 로그인이 되어있지 않으면 실행
        if (mAuth.getCurrentUser() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void getWeatherDetails() {
        //위치에 따른 날씨 정보를 가져오는 함수로서, 먼저 사용자 위치 권한을 허가받기
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            //GPS Provider 통해 위도와 경도 값 받기
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            //몇 초, 얼마의 거리마다 정보를 갱신 받을 것인지 지정
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);

            //사용자가 JSON 데이터를 받아올 주소 형식 지정
            String tempUrl = "";
            tempUrl = url + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + appid;

            //지정한 주소 형식을 요청하여 리소스를 생성하고 받아오기
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String icon = jsonObjectWeather.getString("icon");
                        String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

                        Picasso.get().load(iconUrl).into(weatherIcon);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.logoutButton:
                    Logout();
                    break;

                case R.id.mainWeatherIcon:
                    GoWeather();
                    break;

                case R.id.settingButton:
                    GoSetting();
                    break;

                case R.id.calendarButton:
                    GoCalendar();
                    break;

                case R.id.eventButton:
                    GoEvent();
                    break;

                case R.id.memo:
                    GoMemo();
                    break;

            }
        }
    };

    private void ToastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    private void Logout(){
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void GoWeather(){
        Intent intent = new Intent(MainActivity.this, WeatherInfo.class);
        startActivity(intent);
    }

    private void GoSetting(){
        Intent intent = new Intent(MainActivity.this, Setting.class);
        startActivity(intent);
    }

    private void GoCalendar(){
        Intent intent = new Intent(MainActivity.this, Calendar.class);
        startActivity(intent);
    }

    private void GoEvent(){
        Intent intent = new Intent(MainActivity.this, EventMain.class);
        startActivity(intent);
    }

    private void GoMemo(){
        Intent intent = new Intent(MainActivity.this, MemoActivity.class);
        startActivity(intent);
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) { }

        public void onProviderEnabled(String provider) { }

        public void onProviderDisabled(String provider) { }
    };

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        } else {
            backBtnTime = curTime;
            ToastMessage("한번 더 누르면 종료됩니다.");
        }
    }
}