package com.example.appdubaott;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThoiTietAdapter extends BaseAdapter {
    Context context;
    ArrayList<thoitiet> arrayList;

    public ThoiTietAdapter(Context context, ArrayList<thoitiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.don_lv,null);
        thoitiet thoitiet = arrayList.get(position);
        TextView txtngaythang = (TextView) convertView.findViewById(R.id.tvNgayThang);
        TextView txtTT = (TextView) convertView.findViewById(R.id.tvTTHAI);
        TextView txtMax = (TextView) convertView.findViewById(R.id.tvMax);
        TextView txtMin = (TextView) convertView.findViewById(R.id.tvMin);
        ImageView imgTT = (ImageView) convertView.findViewById(R.id.imgTT);

        txtngaythang.setText(thoitiet.Day);
        txtTT.setText(thoitiet.TT);
        txtMax.setText(thoitiet.Max+"°C");
        txtMin.setText(thoitiet.Min+"°C");

        Picasso.with(context).load("http://openweathermap.org/img/wn/"+thoitiet.Hinh+".png").into(imgTT);
        return convertView;
    }
}
