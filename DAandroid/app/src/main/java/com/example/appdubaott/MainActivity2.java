package com.example.appdubaott;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    String tenTP ="";
    ImageView imgback;
    TextView txtTenTP;
    ListView Lv;
    ThoiTietAdapter thoiTietAdapter;
    ArrayList<thoitiet> ArrTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        if(city.equals("")){
            tenTP = "tra vinh";
            Get7Ngay(tenTP);
        }else {
            tenTP = city;
            Get7Ngay(tenTP);
        }
        addView();
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void addView() {
        imgback = findViewById(R.id.imgvBack);
        txtTenTP = findViewById(R.id.tvTenTP);
        Lv = findViewById(R.id.Lv);
        ArrTT =new ArrayList<thoitiet>();
        thoiTietAdapter =new ThoiTietAdapter(MainActivity2.this, ArrTT);
        Lv.setAdapter(thoiTietAdapter);
    }

    private void Get7Ngay(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=16&appid=1f79aee68d330e9d85ad08a57c8edb05";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectcity =jsonObject.getJSONObject("city");
                            String name = jsonObjectcity.getString("name");
                            txtTenTP.setText(name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i<jsonArrayList.length(); i++){
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt");

                                long l = Long.valueOf(ngay);
                                Date date = new Date(l*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy/MM/dd HH:mm:ss");
                                String Day = simpleDateFormat.format(date);

                                JSONObject jsonObjectNhietdo = jsonObjectList.getJSONObject("main");
                                String max = jsonObjectNhietdo.getString("temp_max");
                                String min = jsonObjectNhietdo.getString("temp_min");
                                Double a = Double.valueOf(max);
                                Double b = Double.valueOf(min);
                                String NDmax =String.valueOf(a.intValue());
                                String NDmin =String.valueOf(b.intValue());

                                JSONArray jsonArrayTT =jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectTT = jsonArrayTT.getJSONObject(0);
                                String Tthai = jsonObjectTT.getString("description");
                                String icon = jsonObjectTT.getString("icon");

                                ArrTT.add(new thoitiet(Day, Tthai,icon, NDmax, NDmin));
                            }
                            thoiTietAdapter.notifyDataSetChanged();
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
}