package com.example.appbooking.page.admin.quanLyUser;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.Model.TaiKhoan;
import com.example.appbooking.R;

import java.util.ArrayList;

public class quanLyUser extends AppCompatActivity {

    MySQLite db1;
    ImageButton ad_btnLoad;
    Spinner ad_dkLoc;
    ListView ad_listV;
    ArrayAdapter<String> taiKhoanAdapter;

    ArrayList listTK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Ánh xạ các thành phần giao diện
        ad_btnLoad = findViewById(R.id.ad_btnLoad);
        ad_dkLoc = findViewById(R.id.ad_dkLoc);
        ad_listV = findViewById(R.id.ad_listV);

        // Khởi tạo MySQLite
        db1 = new MySQLite();

        // Khởi tạo adapter
        taiKhoanAdapter = new ArrayAdapter<>(this, R.layout.ad_item_user, listTK);
        ad_listV.setAdapter(taiKhoanAdapter);

        // Làm mới dữ liệu
    }

}