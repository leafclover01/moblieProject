package com.example.appbooking.page.admin.QuanLyDon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.appbooking.MainActivity;
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
    private Button bntThoiGianBatDau, bntThoiGianKetThuc, Luu;
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
        price = findViewById(R.id.price);
        checkToken();

        bookedBy = findViewById(R.id.bookedBy);
        bookedAt = findViewById(R.id.bookedAt);
        bookedOut = findViewById(R.id.bookedOut); // Ngày check-out
        phone = findViewById(R.id.phone); // Số điện thoại
        cccd = findViewById(R.id.cccd); // CCCD
        bntThoiGianBatDau = findViewById(R.id.bntThoiGianBatDau);
        bntThoiGianKetThuc = findViewById(R.id.bntThoiGianKetThuc);
        backButton = findViewById(R.id.back);
        Luu = findViewById(R.id.Luu);
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
        bookedBy.setText("Người đặt: " +username);
        price.setText("Gía cần thanh toán: " + giaTien);
        bookedAt.setText("Ngày nhận phòng: " + check_in);
        bookedOut.setText("Ngày trả phòng: " + check_out);
        phone.setText("Số điện thoại: " + sdt);
        cccd.setText("CCCD: " + cccdValue);

        setupListeners(ma_don, check_in, check_out);
        layThongTinThuc(ma_don);

        if (startTimeInMillis != -1) {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
            String formattedStartTime = dateTimeFormat.format(new Date(startTimeInMillis));  // Đổi tên biến thành formattedStartTime
            tvNgayBatDau.setText(formattedStartTime);
        }

        if (endTimeInMillis != -1) {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
            String formattedEndTime = dateTimeFormat.format(new Date(endTimeInMillis));  // Sử dụng endTimeInMillis đúng
            tvNgayHetHan.setText(formattedEndTime);
        }
    }

    private void setupListeners(Integer ma_don, String bookedAt, String bookedOut) {
        bntThoiGianBatDau.setOnClickListener(v -> pickDateTime(true));
        bntThoiGianKetThuc.setOnClickListener(v -> pickDateTime(false));
        Luu.setOnClickListener(v -> luuduLieu(ma_don, bookedAt, bookedOut));
    }

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
                        Luu.setText("Thanh Toan");
                        tvNgayHetHan.setTextColor(ContextCompat.getColor(this, R.color.brandBlue30));

                    }
                }
            });

            timePicker.show(getSupportFragmentManager(), isStartTime ? "START_TIME_PICKER" : "END_TIME_PICKER");
        });

        datePicker.show(getSupportFragmentManager(), isStartTime ? "START_DATE_PICKER" : "END_DATE_PICKER");
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    public String layThongTinThuc(int maDon) {
        try {
            // Lấy giá trị check-in
            db = new MySQLite();
            long checkIn = Long.parseLong(db.hamlaycheckinthuc(maDon));
            startTimeInMillis = checkIn != -1 ? checkIn : -1;

            // Lấy giá trị check-out
            long checkOut = Long.parseLong(db.hamlaycheckoutthuc(maDon));
            endTimeInMillis = checkOut != -1 ? checkOut : -1;

            // Trả về thông tin
            if (startTimeInMillis != -1 || endTimeInMillis != -1) {
                return "Dữ liệu: startTimeInMillis = " + startTimeInMillis + ", endTimeInMillis = " + endTimeInMillis;
            } else {
                return "Không tìm thấy dữ liệu. startTimeInMillis = -1, endTimeInMillis = -1";
            }
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }


    private void luuduLieu(Integer ma_don, String bookedAt, String bookedOut) {
        try {
            Integer kiemtratontai = Integer.parseInt(db.hamlaymadon(ma_don));

            // Kiểm tra xem thời gian bắt đầu có hợp lệ hay không
            if (startTimeInMillis != -1) {
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
                String start = dateTimeFormat.format(new Date(startTimeInMillis));

                if (kiemtratontai == -1) { // Trường hợp đơn hàng chưa tồn tại
                    // Kiểm tra thời gian kết thúc
                    if (endTimeInMillis != -1) {
                        String end = dateTimeFormat.format(new Date(endTimeInMillis));
                        String info = db.insertDataQuanLyNCaHai(userId, ma_don, start, end, bookedAt, bookedOut);
                        Toast.makeText(this, "Đã lưu dữ liệu quản lý ca hai", Toast.LENGTH_SHORT).show();
                    } else { // Không có thời gian kết thúc
                        String info = db.insertDataQuanLyNgayBatDau(userId, ma_don, start, bookedAt, bookedOut);
                        Toast.makeText(this, "Đã lưu dữ liệu quản lý ngày bắt đầu " + info, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    db.updateThoigianCheckInThuc(ma_don, start);
                    // Nếu có thời gian kết thúc thì cập nhật và chốt hóa đơn
                    if (endTimeInMillis != -1) {
                        String end = dateTimeFormat.format(new Date(endTimeInMillis));
                        String info =  db.insertDataCheckOutThuc(ma_don, bookedAt, bookedOut);
                        db.updateDataHoaDon(kiemtratontai, ma_don, end);
                        Toast.makeText(this, "Đã chốt hóa đơn và lưu dữ liệu check-out", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Chưa có thời gian kết thúc, chỉ cập nhật thời gian check-in", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Bạn cần chọn thời gian bắt đầu", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi A: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
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

