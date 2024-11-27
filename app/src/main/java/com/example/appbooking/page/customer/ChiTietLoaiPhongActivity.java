package com.example.appbooking.page.customer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbooking.R;

public class ChiTietLoaiPhongActivity extends AppCompatActivity {

    private ImageView imageLoaiPhong;
    private TextView tenLoaiPhong;
    private TextView giaLoaiPhong;
    private TextView soNguoiToiDa;
    private TextView moTa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_loai_phong); // Layout chi tiết loại phòng

        // Ánh xạ các View
        imageLoaiPhong = findViewById(R.id.imageLoaiPhong);
        tenLoaiPhong = findViewById(R.id.tenLoaiPhong);
        giaLoaiPhong = findViewById(R.id.giaLoaiPhong);
        soNguoiToiDa = findViewById(R.id.soNguoiToiDa);
        moTa = findViewById(R.id.moTa);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String tenPhong = intent.getStringExtra("tenPhong");
        String giaPhong = intent.getStringExtra("giaPhong");
        String soNguoi = intent.getStringExtra("soNguoi");
        String moTaPhong = intent.getStringExtra("moTaPhong");
        int imageResource = intent.getIntExtra("imageResource", R.drawable.anh_phong); // Giá trị mặc định là ảnh placeholder

        // Cập nhật các View với dữ liệu nhận được
        tenLoaiPhong.setText(tenPhong);
        giaLoaiPhong.setText(giaPhong);
        soNguoiToiDa.setText(soNguoi);
        moTa.setText(moTaPhong);
        imageLoaiPhong.setImageResource(imageResource);
    }
}
