package com.example.appbooking.page.customer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailsTypeRoomActivity extends AppCompatActivity {
    MySQLite db1 = new MySQLite();
    private ImageView imageLoaiPhong;
    private TextView tenLoaiPhong;
    private TextView giaLoaiPhong;
    private TextView soNguoiToiDa;
    private TextView moTa;
    private Button btnCheckIn;
    private Button btnCheckOut;
    Button btnChonThoiGian;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private TextView textViewThoiGian;
    String timeCheckIn, timeCheckOut;

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
        btnCheckIn = findViewById(R.id.btnCheckIn);
        btnCheckOut = findViewById(R.id.btnCheckOut);
        btnChonThoiGian = findViewById(R.id.btnChonThoiGian);
//        textViewThoiGian = findViewById(R.id.textViewThoiGian);

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

        // Xử lý chọn ngày Check-in
        btnCheckIn.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            timeCheckIn = formatter.format(calendar.getTime());
            DatePickerDialog datePickerDialog = new DatePickerDialog(DetailsTypeRoomActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) ->
                            btnCheckIn.setText("Check-in: " + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear),
                    year, month, day);
            datePickerDialog.show();
        });

        // Xử lý chọn ngày Check-out
        btnCheckOut.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            timeCheckOut = formatter.format(calendar.getTime());


            DatePickerDialog datePickerDialog = new DatePickerDialog(DetailsTypeRoomActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) ->
                            btnCheckOut.setText("Check-out: " + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear),
                    year, month, day);
            datePickerDialog.show();
        });

        // Xử lý sự kiện khi người dùng nhấn nút chọn thời gian
        btnChonThoiGian.setOnClickListener(v -> {
            Toast.makeText(this, timeCheckIn + " -- " + timeCheckOut, Toast.LENGTH_SHORT).show();
//            db1.layDuLieuPhongTrong()
        });
    }

    // Hàm parseDate() để chuyển đổi String thành Date
    public Date parseDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}