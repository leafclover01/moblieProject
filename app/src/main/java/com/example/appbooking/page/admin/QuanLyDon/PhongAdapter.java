package com.example.appbooking.page.admin.QuanLyDon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appbooking.Model.Phong;
import com.example.appbooking.R;

import java.util.ArrayList;

public class PhongAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private ArrayList<Phong> danhSachPhong;

    public PhongAdapter(Context context, int resource, ArrayList<Phong> danhSachPhong) {
        super(context, resource, danhSachPhong);
        this.context = context;
        this.resource = resource;
        this.danhSachPhong = danhSachPhong;
    }

    @Override
    public int getCount() {
        return this.danhSachPhong.size();
    }

    @Override
    public Object getItem(int position) {
        return this.danhSachPhong.get(position); // Trả về item (Phong) như kiểu Object
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Sử dụng ViewHolder để tối ưu hiệu suất
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflating layout cho mỗi item
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);

            // Khởi tạo ViewHolder và gán các view
            viewHolder = new ViewHolder();
            viewHolder.viTriTextView = convertView.findViewById(R.id.viTriTextView);

            // Gán ViewHolder vào convertView để tránh gọi findViewById nhiều lần
            convertView.setTag(viewHolder);
        } else {
            // Lấy ViewHolder từ convertView đã tái sử dụng
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lấy đối tượng Phong từ danh sách
        Phong phong = (Phong) getItem(position);

        // Gán dữ liệu vào các view
        if (phong != null) {
            viewHolder.viTriTextView.setText(phong.getViTri());
        }

        return convertView;
    }

    // Class ViewHolder để tối ưu hiệu suất
    static class ViewHolder {
        TextView viTriTextView;
    }

    // Phương thức cập nhật dữ liệu trong adapter
    public void updateData(ArrayList<Phong> newList) {
        this.danhSachPhong.clear();  // Xóa danh sách hiện tại
        this.danhSachPhong.addAll(newList);  // Thêm dữ liệu mới
        notifyDataSetChanged();  // Thông báo adapter cập nhật giao diện
    }
}
