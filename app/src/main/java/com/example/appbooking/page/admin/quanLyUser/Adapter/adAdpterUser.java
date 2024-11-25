package com.example.appbooking.page.admin.quanLyUser.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbooking.Model.TaiKhoan;
import com.example.appbooking.R;

import java.util.ArrayList;

public class adAdpterUser extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<TaiKhoan> listTK;
    public adAdpterUser(@NonNull Context context, int resource, ArrayList<TaiKhoan> listTK) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.listTK = listTK;
    }

    public int getCount(){
        return this.listTK.size();
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Sử dụng convertView để tái sử dụng View
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource, parent, false);
        }

        // Ánh xạ các thành phần trong layout
        ImageView ad_img1 = convertView.findViewById(R.id.ad_img1);
        Button ad_btnEdit = convertView.findViewById(R.id.ad_btnEdit);
        Button ad_btnDel = convertView.findViewById(R.id.ad_btnDel);
        TextView ad_tv1 = convertView.findViewById(R.id.ad_tv1);
        TextView ad_tv2 = convertView.findViewById(R.id.ad_tv2);
        TextView ad_tv3 = convertView.findViewById(R.id.ad_tv3);

        // Gán dữ liệu cho các thành phần
        TaiKhoan tk = listTK.get(position);
        ad_tv1.setText(tk.getName());
        ad_tv2.setText(tk.getEmail()+ " --- " + tk.getSdt());
        ad_tv3.setText(tk.getRole() == 0 ? "Admin" : "User");
        if (tk.getHinh() != null && !tk.getHinh().isEmpty()) {
            Uri imageUri = Uri.parse(tk.getHinh()); // Chuyển chuỗi URI thành đối tượng Uri
            ad_img1.setImageURI(imageUri); // Hiển thị ảnh từ URI
        }

        ad_btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ad_btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }
}
