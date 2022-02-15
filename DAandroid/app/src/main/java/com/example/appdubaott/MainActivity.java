package com.example.appdubaott;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch, btnNgayTT;
    TextView txtTP, txtQG, txtNhietDo, txtTrangThai, txtDoAm, txtmay, txtGio, txtNgayCN;
    ImageView imgicon;
    String city1 ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addView();
        GetCurrentWearTherData("tra vinh");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                if(city.equals("")){
                    city1 ="tra vinh";
                    GetCurrentWearTherData(city1);
                }else{
                    city1=city;
                    GetCurrentWearTherData(city1);
                }
            }
        });
        btnNgayTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("name", city);
                startActivity(intent);
            }
        });
    }

    public void GetCurrentWearTherData(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=1f79aee68d330e9d85ad08a57c8edb05";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtTP.setText("Tên Thành Phố: "+name);
                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);

                            txtNgayCN.setText(Day);

                            JSONArray jsonArrayweather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectweather = jsonArrayweather.getJSONObject(0);
                            String TT = jsonObjectweather.getString("main");
                            String icon = jsonObjectweather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/wn/"+icon+".png").into(imgicon);
                            txtTrangThai.setText(TT);

                            JSONObject jsonObjectmain =jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectmain.getString("temp");
                            String doam = jsonObjectmain.getString("humidity");

                            Double a = Double.valueOf(nhietdo);
                            String ND =String.valueOf(a.intValue());
                            txtDoAm.setText(doam+"%");
                            txtNhietDo.setText(ND+"°C");

                            JSONObject jsonObjectGio = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectGio.getString("speed");
                            txtGio.setText(gio+"m/s");

                            JSONObject jsonObjectMay = jsonObject.getJSONObject("clouds");
                            String May = jsonObjectMay.getString("all");
                            txtmay.setText(May+"%");

                            JSONObject jsonObjectSYS =jsonObject.getJSONObject("sys");
                            String QG = jsonObjectSYS.getString("country");
                            txtQG.setText("Tên Quốc Gia: "+QG);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    private void addView() {
        edtSearch = findViewById(R.id.textTK);
        btnSearch = findViewById(R.id.buttonTK);
        btnNgayTT = findViewById(R.id.btnNgayTT);
        txtTP = findViewById(R.id.tvTen);
        txtQG = findViewById(R.id.tvQG);
        txtNhietDo = findViewById(R.id.tvND);
        txtTrangThai = findViewById(R.id.tvTT);
        txtDoAm = findViewById(R.id.tvDOAM);
        txtmay = findViewById(R.id.tvMay);
        txtGio = findViewById(R.id.tvGIO);
        imgicon = findViewById(R.id.imgicon);
        txtNgayCN = findViewById(R.id.tvNgay);
    }
}