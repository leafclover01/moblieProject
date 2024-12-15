package com.example.appbooking.page.admin.QuanLyDon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.Model.TaiKhoan;
import com.example.appbooking.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RoomDetailDon extends AppCompatActivity {

    private TextView roomType, roomName, roomDetails, price, bookedBy, bookedAt, bookedOut, phone, cccd;
    private Button bntThoiGianBatDau, bntThoiGianKetThuc;
    private long startTimeInMillis = -1;
    private long endTimeInMillis = -1;
    private int userId;
    private TextView tvNgayBatDau, tvNgayHetHan;
    MySQLite db;
    private ImageButton backButton;
    double giaTien = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail_don);

        // Ánh xạ các view
        roomType = findViewById(R.id.roomType);
//        roomDetails = findViewById(R.id.roomDetails);
        price = findViewById(R.id.price);

        bookedBy = findViewById(R.id.bookedBy);
        bookedAt = findViewById(R.id.bookedAt);
        bookedOut = findViewById(R.id.bookedOut); // Ngày check-out
        phone = findViewById(R.id.phone); // Số điện thoại
        cccd = findViewById(R.id.cccd); // CCCD
        bntThoiGianBatDau = findViewById(R.id.bntThoiGianBatDau);
        bntThoiGianKetThuc = findViewById(R.id.bntThoiGianKetThuc);
        backButton = findViewById(R.id.back);
        roomName = findViewById(R.id.roomName);
        tvNgayBatDau = findViewById(R.id.tvNgayBatDau);
        tvNgayHetHan = findViewById(R.id.tvNgayHetHan);
        // Xử lý nút Back
        backButton.setOnClickListener(view -> finish());

        Intent intent = getIntent();
        db = new MySQLite();

        String MA_PHONG = intent.getStringExtra("MA_PHONG");
        String tenPhong = intent.getStringExtra("tenPhong");
        String username = intent.getStringExtra("username");
        String sdt = intent.getStringExtra("sdt");
        String cccdValue = intent.getStringExtra("cccd");
        String ma_loai_phong = intent.getStringExtra("ma_loai_phong");
        String doi_ten_loai_phong = getmaTenPhong(ma_loai_phong);
        Integer ma_don = Integer.parseInt(intent.getStringExtra("ma_don"));
        String check_in = intent.getStringExtra("check_in");
        String check_out = intent.getStringExtra("check_out");
        giaTien = Double.parseDouble(db.layDuLieuTienCuaPhongDo((ma_don)));
        roomType.setText(doi_ten_loai_phong);
        roomName.setText(tenPhong);
        // roomDetails.setText(); // Cập nhật nếu có chi tiết phòng
        // price.setText(); // Cập nhật giá phòng nếu có
        bookedBy.setText("Người đặt: " +username);
        price.setText("Gía cần thanh toán: " + giaTien);
        bookedAt.setText("Ngày đặt phòng: " + check_in);
        bookedOut.setText("Ngày trả phòng: " + check_out);
        phone.setText("Số điện thoại: " + sdt);
        cccd.setText("CCCD: " + cccdValue);
        setupListeners();

    }
    // Thiết lập sự kiện cho các nút
    private void setupListeners() {

        bntThoiGianBatDau.setOnClickListener(v -> pickDateTime(true));
        bntThoiGianKetThuc.setOnClickListener(v -> pickDateTime(false));
    }

    // Hàm chọn ngày giờ bắt đầu hoặc kết thúc
    private void pickDateTime(boolean isStartTime) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(isStartTime ? "Chọn ngày bắt đầu" : "Chọn ngày kết thúc")
                .setCalendarConstraints(new CalendarConstraints.Builder().build())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selection);

            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                    .setMinute(calendar.get(Calendar.MINUTE))
                    .setTitleText(isStartTime ? "Chọn giờ phút bắt đầu" : "Chọn giờ phút kết thúc")
                    .build();

            timePicker.addOnPositiveButtonClickListener(view -> {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
                String selectedDateTime = dateTimeFormat.format(calendar.getTime());

                if (isStartTime) {
                    startTimeInMillis = calendar.getTimeInMillis();
                    tvNgayBatDau.setText(selectedDateTime);
                    tvNgayBatDau.setTextColor(ContextCompat.getColor(this, R.color.brandBlue30));
                } else {
                    long selectedEndTime = calendar.getTimeInMillis();
                    if (startTimeInMillis != -1 && selectedEndTime <= startTimeInMillis) {
                        tvNgayHetHan.setText("Lỗi: Thời gian kết thúc phải lớn hơn thời gian bắt đầu!");
                        tvNgayHetHan.setTextColor(Color.RED);
                    } else {
                        endTimeInMillis = selectedEndTime;
                        tvNgayHetHan.setText(selectedDateTime);
                        tvNgayHetHan.setTextColor(ContextCompat.getColor(this, R.color.brandBlue30));
                    }
                }
            });

            timePicker.show(getSupportFragmentManager(), isStartTime ? "START_TIME_PICKER" : "END_TIME_PICKER");
        });

        datePicker.show(getSupportFragmentManager(), isStartTime ? "START_DATE_PICKER" : "END_DATE_PICKER");
    }

    // Hàm lấy thời gian hiện tại
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }


    private String getmaTenPhong(String tenPhong) {
        String maPhong = "None";

        switch (tenPhong) {
            case "1":
                maPhong = "Phòng Standard";
                break;
            case "2":
                maPhong = "Phòng Superior";
                break;
            case "3":
                maPhong = "Phòng Deluxe";
                break;
            case "4":
                maPhong = "Phòng Suite";
                break;
            default:
                maPhong = "None";
                break;
        }

        return maPhong;
    }
}

