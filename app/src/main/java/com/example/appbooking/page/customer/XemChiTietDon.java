package com.example.appbooking.page.customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.MainActivity;
import com.example.appbooking.R;

import java.util.ArrayList;
import java.util.HashMap;

public class XemChiTietDon extends AppCompatActivity {

    private TextView roomType, roomName, price, bookedBy, bookedAt, bookedOut, phone, cccd, result;
    private int userId;
    private MySQLite db;
    private ImageButton backButton;
    private double giaTien;
    private Integer ma_don;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_chi_tiet_don_user);

        roomType = findViewById(R.id.roomType);
        roomName = findViewById(R.id.roomName);
        price = findViewById(R.id.price);
        bookedBy = findViewById(R.id.bookedBy);
        bookedAt = findViewById(R.id.bookedAt);
        bookedOut = findViewById(R.id.bookedOut);
        phone = findViewById(R.id.phone);
        cccd = findViewById(R.id.cccd);
        result = findViewById(R.id.result);
        backButton = findViewById(R.id.back);

        backButton.setOnClickListener(view -> finish());

        checkToken();

        Intent intent = getIntent();
        ma_don = Integer.parseInt(intent.getStringExtra("ma_don"));
//        ma_don = 2;


        db = new MySQLite();

        layVaHienThiThongTin(ma_don);
        kiem_tra_thanh_toan(ma_don);
    }

    private void layVaHienThiThongTin(int ma_don) {
        ArrayList<HashMap<String, Object>> dsPhong = db.layDuLieuPhong(ma_don);
        if (dsPhong.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy thông tin đơn!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiển thị thông tin từ phòng đầu tiên (giả sử đơn liên quan đến một phòng)
        HashMap<String, Object> phong = dsPhong.get(0);
        String viTri = phong.get("vi_tri").toString();
        String nguoiDat = phong.get("name").toString();
        String checkIn = phong.get("check_in").toString();
        String checkOut = phong.get("check_out").toString();
        String sdtValue = phong.get("sdt").toString();
        String cccdValue = phong.get("cccd").toString();

        roomName.setText(viTri);
        bookedBy.setText("Người đặt: " + nguoiDat);
        bookedAt.setText("Ngày nhận phòng: " + checkIn);
        bookedOut.setText("Ngày trả phòng: " + checkOut);
        phone.setText("Số điện thoại: " + sdtValue);
        cccd.setText("CCCD: " + cccdValue);

        giaTien = Double.parseDouble(db.layDuLieuTienCuaPhongDo(ma_don));
        price.setText("Giá cần thanh toán: " + giaTien);
    }

    private void kiem_tra_thanh_toan(Integer ma_don) {
        Integer kiemtrathanhtoan = Integer.parseInt(db.kiemTraThanhToan(ma_don));
        if (kiemtrathanhtoan == 1) {
            result.setText("Đã Thanh Toán");
        } else  {
            result.setText("Chưa Thanh Toán");
        }
    }

    private void checkToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
