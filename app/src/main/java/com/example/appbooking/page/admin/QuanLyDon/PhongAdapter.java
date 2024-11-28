package com.example.appbooking.page.admin.QuanLyDon;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appbooking.Model.Phong;
import com.example.appbooking.Model.Thue;
import com.example.appbooking.R;

import java.util.ArrayList;

public class PhongAdapter extends ArrayAdapter<Object> {

    private Activity context;
    private int resource;
    private ArrayList<Object> dataList;

    public PhongAdapter(Activity context, int resource, ArrayList<Object> dataList) {
        super(context, resource, dataList);
        this.context = context;
        this.resource = resource;
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = dataList.get(position);
        return (item instanceof Phong) ? 0 : 1;  // Return 0 for Phong and 1 for Thue
    }

    @Override
    public int getViewTypeCount() {
        return 2;  // We have two view types: Phong and Thue
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        int type = getItemViewType(position);
        View customView;

        // Recycle the view if it's available (use convertView)
        if (convertView == null) {
            customView = inflater.inflate(R.layout.ad_item_admin_quan_ly_don, parent, false);
        } else {
            customView = convertView;
        }

        if (type == 0) {  // Phong
            Phong phong = (Phong) dataList.get(position);
            ((TextView) customView.findViewById(R.id.maPhong)).setText(String.valueOf(phong.getMaPhong()));
            ((TextView) customView.findViewById(R.id.tenPhong)).setText(phong.getViTri());
            ((TextView) customView.findViewById(R.id.soLuong)).setText(String.valueOf(phong.getMaLoaiPhong()));
        } else {  // Thue
            Thue thue = (Thue) dataList.get(position);
            ((TextView) customView.findViewById(R.id.maPhong)).setText(String.valueOf(thue.getMaPhong()));
            ((TextView) customView.findViewById(R.id.tenPhong)).setText("Đã thuê");  // Display status for rented rooms
            ((TextView) customView.findViewById(R.id.soLuong)).setText(thue.getCheckOut().toString());  // Display check-out time
        }

        return customView;
    }

    public void updateData(ArrayList<Object> newData) {
        // Clear old data and update with new data
        dataList.clear();
        dataList.addAll(newData);
        notifyDataSetChanged();
    }
}
