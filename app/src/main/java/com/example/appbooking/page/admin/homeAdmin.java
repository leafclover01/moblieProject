package com.example.appbooking.page.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbooking.R;
import com.example.appbooking.page.admin.adRoom.QuanLyDanhGia;
import com.example.appbooking.page.admin.adRoom.quanLyPhong;
import com.example.appbooking.page.admin.quanLyUser.quanLyUser;

public class homeAdmin extends AppCompatActivity {
    LinearLayout qlUser, qlPhong, qlDanhGia, qlMa, qlDon;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        qlUser = findViewById(R.id.qlUser);
        qlPhong = findViewById(R.id.qlPhong);
        qlDanhGia = findViewById(R.id.qlDanhGia);
        qlMa = findViewById(R.id.qlMa);
        qlDon = findViewById(R.id.qlDon);

        qlUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(homeAdmin.this, quanLyUser.class);
                startActivity(in1);
            }
        });

        qlPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in2 = new Intent(homeAdmin.this, quanLyPhong.class);
                startActivity(in2);
            }
        });

        qlDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in3 = new Intent(homeAdmin.this, QuanLyDanhGia.class);
                startActivity(in3);
            }
        });

        qlMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        qlDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}