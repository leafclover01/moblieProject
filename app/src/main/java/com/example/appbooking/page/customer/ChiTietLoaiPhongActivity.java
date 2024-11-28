package com.example.appbooking.page.customer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbooking.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChiTietLoaiPhongActivity extends AppCompatActivity {

    private ImageView imageLoaiPhong;
    private TextView tenLoaiPhong;
    private TextView giaLoaiPhong;
    private TextView soNguoiToiDa;
    private TextView moTa;
    private Button btnCheckIn;
    private Button btnCheckOut;
    private Button btnChonThoiGian;
    private TextView textViewThoiGian;

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

            DatePickerDialog datePickerDialog = new DatePickerDialog(ChiTietLoaiPhongActivity.this,
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

            DatePickerDialog datePickerDialog = new DatePickerDialog(ChiTietLoaiPhongActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) ->
                            btnCheckOut.setText("Check-out: " + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear),
                    year, month, day);
            datePickerDialog.show();
        });

        // Xử lý sự kiện khi người dùng nhấn nút chọn thời gian
        btnChonThoiGian.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(ChiTietLoaiPhongActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Chọn ngày thành công, hiển thị ngày đã chọn
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        Date date = parseDate(selectedDate);
                        if (date != null) {
                            // Cập nhật TextView với thời gian đã chọn
                            textViewThoiGian.setText("Thời gian đã chọn: " + new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date));
                        }
                    },
                    year, month, day);
            datePickerDialog.show();
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