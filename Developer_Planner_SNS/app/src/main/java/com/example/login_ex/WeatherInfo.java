package com.example.login_ex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class WeatherInfo extends AppCompatActivity {

    TextView tvResult, tvResult2, res2, weatherText, actionText;
    ImageView weatherIcon;
    String [] enWeatherList = {
            "clear sky",
            "few clouds",
            "broken clouds",
            "scattered clouds",
            "overcast clouds",
            "snow",
            "Thunderstorm",
            "Rain",
            "shower rain",
            "light rain",
            "moderate rain",
            "mist",
            "haze"
    };
    String [] krWeatherList = {
            "맑음",
            "구름 약간",
            "구름 많음",
            "구름 개임",
            "흐린 구름",
            "눈내림",
            "뇌우",
            "비내림",
            "소나기",
            "이슬비",
            "적절한 비",
            "옅은 안개",
            "실안개"
    };

    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    //데이터를 호출할 서비스의 기본 주소
    private final String url = "http://api.openweathermap.org/data/2.5/weather";

    //서비스에서 제공받은 API KEY 등록
    private final String appid = "80b3b408e398e420f91d1e3fe08b5328";
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {
            checkRunTimePermission();
        }

        gpsTracker = new GpsTracker(WeatherInfo.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        tvResult = findViewById(R.id.tvResult);
        tvResult2 = findViewById(R.id.tvResult2);
        weatherIcon = findViewById(R.id.weatherIcon);
        weatherText = findViewById(R.id.weatherText);
        actionText = findViewById(R.id.actionText);
        res2 = findViewById(R.id.res2);

        getWeatherDetails(latitude, longitude);
    }

    public void getWeatherDetails(double latitude, double longitude) {
            //사용자가 JSON 데이터를 받아올 주소 형식 지정
            String tempUrl = "";
            tempUrl = url + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + appid;

            //지정한 주소 형식을 요청하여 리소스를 생성하고 받아오기
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String output = "";
                    String output2 = "";
                    String output3 = "";
                    String output4 = "";
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        String icon = jsonObjectWeather.getString("icon");
                        String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

                        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        int humidity = jsonObjectMain.getInt("humidity");

                        JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");

                        JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonObject.getString("name");

                        for(int i=0; i<enWeatherList.length; i++) {
                            if(description.equals(enWeatherList[i])) {
                                description = krWeatherList[i];
                            }
                        }

                        if(description.equals("구름 약간"))
                        {
                            if( 10 < temp && temp < 20){
                                actionText.setText("운동");
                            }
                        }
                        output = description;
                        output4 +="   ⦁ 체감온도: " + decimalFormat.format(feelsLike) + " ℃"
                                + "   ⦁ 습도: " + humidity + "%"
                                + "   ⦁ 풍속: " + wind + "m/s";
                        output2 = cityName + " (" + countryName + ")";
                        output3 = decimalFormat.format(temp) + " ℃";
                        tvResult.setText(output);
                        weatherText.setText(output3);
                        res2.setText(output2);
                        tvResult2.setText(output4);
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

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(WeatherInfo.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(WeatherInfo.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(WeatherInfo.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(WeatherInfo.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우
            if (ActivityCompat.shouldShowRequestPermissionRationale(WeatherInfo.this, REQUIRED_PERMISSIONS[0])) {

                Toast.makeText(WeatherInfo.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청
                ActivityCompat.requestPermissions(WeatherInfo.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청
                // 요청 결과는 onRequestPermissionResult에서 수신
                ActivityCompat.requestPermissions(WeatherInfo.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }

    //여기부터는 GPS 활성화를 위한 메소드
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(WeatherInfo.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}