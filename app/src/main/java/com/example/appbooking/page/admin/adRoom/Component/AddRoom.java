package com.example.appbooking.page.admin.adRoom.Component;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.R;

public class AddRoom extends AppCompatActivity {
    ImageView imgRoom;
    EditText edtViTriPhong;
    Spinner spLoaiPhong;
    Button btnLuuPhong;
    MySQLite db1 = new MySQLite();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_room);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Ánh xạ các view từ layout
        imgRoom = findViewById(R.id.ad_r_img);
        edtViTriPhong = findViewById(R.id.ad_r_viTri);
        spLoaiPhong = findViewById(R.id.ad_r_sp);
        btnLuuPhong = findViewById(R.id.ad_r_btn);

    }
}