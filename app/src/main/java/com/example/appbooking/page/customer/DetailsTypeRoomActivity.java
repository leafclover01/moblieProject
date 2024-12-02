package com.example.appbooking.page.customer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.R;
import com.example.appbooking.Model.Phong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
//import androidx.recyclerview.widget.GridLayoutManager;
public class DetailsTypeRoomActivity extends AppCompatActivity {
    private ImageView imageLoaiPhong;
    private TextView tenLoaiPhong, giaLoaiPhong, soNguoiToiDa, moTa;
    private Button btnCheckIn, btnCheckOut, btnChonThoiGian;
    private RecyclerView recyclerViewPhongTrong;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private String timeCheckIn = "", timeCheckOut = "";
    private MySQLite db1 = new MySQLite();
    private Button btnNext;
    public String maPhong = "";
    TextView moTaChiTiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_loai_phong);
//        recyclerViewPhongTrong.setLayoutManager(new GridLayoutManager(this, 2));
        // Ánh xạ các view
        imageLoaiPhong = findViewById(R.id.imageLoaiPhong);
        tenLoaiPhong = findViewById(R.id.tenLoaiPhong);
        giaLoaiPhong = findViewById(R.id.giaLoaiPhong);
        soNguoiToiDa = findViewById(R.id.soNguoiToiDa);
        moTa = findViewById(R.id.moTa);
        btnCheckIn = findViewById(R.id.btnCheckIn);
        btnCheckOut = findViewById(R.id.btnCheckOut);
        btnChonThoiGian = findViewById(R.id.btnChonThoiGian);
        recyclerViewPhongTrong = findViewById(R.id.recyclerViewPhongTrong);
        btnNext = findViewById(R.id.btnNext);
        moTaChiTiet = findViewById(R.id.moTaChiTiet);
        ImageButton btnBack = findViewById(R.id.btn_back);
        // button quay lại
        btnBack.setOnClickListener(view -> {
            // Quay lại màn hình trước đó
            onBackPressed(); // Dùng phương thức này nếu Activity trước đó được gọi bằng Intent
        });

        // Nhận dữ liệu từ Intent
        String tenPhong = getIntent().getStringExtra("tenPhong");
        String giaPhong = getIntent().getStringExtra("giaPhong");
        String soNguoi = getIntent().getStringExtra("soNguoi");
        String moTaPhong = getIntent().getStringExtra("moTaPhong");
        String moTaChiTietPhong = getIntent().getStringExtra("moTaChiTiet");

        int imageResource = getIntent().getIntExtra("imageResource", R.drawable.anh_phong);

        // Cập nhật UI
        tenLoaiPhong.setText(tenPhong);
        giaLoaiPhong.setText(giaPhong);
        soNguoiToiDa.setText(soNguoi);
        moTa.setText(moTaPhong);
        moTaChiTiet.setText(moTaChiTietPhong);

        imageLoaiPhong.setImageResource(imageResource);

        // Xử lý chọn ngày Check-in
        btnCheckIn.setOnClickListener(v -> showDatePickerDialog(true));

        // Xử lý chọn ngày Check-out
        btnCheckOut.setOnClickListener(v -> showDatePickerDialog(false));

        // Tìm phòng trống
        btnChonThoiGian.setOnClickListener(v -> {
            if (timeCheckIn.isEmpty() || timeCheckOut.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ngày Check-in và Check-out!", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<Phong> dsPhongTrong = db1.layDuLieuPhongTrong(timeCheckIn, timeCheckOut, "1"); // Ví dụ mã loại phòng = "1"
                loadRoomList(dsPhongTrong);
            }
        });

        // Setup RecyclerView
        recyclerViewPhongTrong.setLayoutManager(new LinearLayoutManager(this));


        // Xử lý nút "Tiếp theo"
//        btnNext.setOnClickListener(new View.OnClickListener()
//
//        {
//            public void onClick (View v){
//                // Xử lý chuyển trang hoặc hành động khác khi bấm nút "Tiếp theo"
//                Toast.makeText(DetailsTypeRoomActivity.this, "Chắc chắn đi thì thanh toán", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(DetailsTypeRoomActivity.this, PayMentHouse.class);
//                startActivity(intent);
//            }
//        });
        // Truyền thông tin phòng từ DetailsTypeRoomActivity
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailsTypeRoomActivity.this, "Chắc chắn đi thì thanh toán", Toast.LENGTH_SHORT).show();
                // Kiểm tra xem người dùng đã chọn phòng chưa
                if (maPhong != null && !maPhong.isEmpty()) {
                    // Truyền thông tin qua Intent
                    Intent intent = new Intent(DetailsTypeRoomActivity.this, PayMentHouse.class);
                    intent.putExtra("tenPhong", tenPhong);  // Cập nhật giá trị này
                    intent.putExtra("giaPhong", giaPhong);  // Cập nhật giá trị này
                    intent.putExtra("maPhong", maPhong);  // Mã phòng cần có giá trị hợp lệ
                    intent.putExtra("moTaPhong", moTaPhong);
                    intent.putExtra("moTaChiTiet", moTaChiTietPhong);
                    intent.putExtra("timeCheckIn", timeCheckIn);
                    intent.putExtra("timeCheckOut", timeCheckOut);

                    // Chuyển sang màn hình tiếp theo
                    startActivity(intent);
                } else {
                    Toast.makeText(DetailsTypeRoomActivity.this, "Vui lòng chọn phòng!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    // Hàm hiển thị DatePickerDialog
    private void showDatePickerDialog(boolean isCheckIn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
            if (isCheckIn) {
                timeCheckIn = selectedDate;
                btnCheckIn.setText("Check-in: " + selectedDate);
            } else {
                timeCheckOut = selectedDate;
                btnCheckOut.setText("Check-out: " + selectedDate);
            }
        }, year, month, day).show();
    }

    // Hàm load danh sách phòng trống vào RecyclerView
    private void loadRoomList(ArrayList<Phong> dsPhongTrong) {
        if (dsPhongTrong.isEmpty()) {
            Toast.makeText(this, "Không có phòng trống trong khoảng thời gian này!", Toast.LENGTH_SHORT).show();
        } else {

            RoomAdapter adapter = new RoomAdapter(dsPhongTrong, phong -> {
                // Lấy mã phòng từ đối tượng phong
                maPhong = String.valueOf(phong.getMaPhong());  // Lưu mã phòng vào biến maPhong

                // Cập nhật giao diện hoặc làm gì đó khi người dùng chọn phòng
                Toast.makeText(DetailsTypeRoomActivity.this, "Chọn phòng: " + maPhong, Toast.LENGTH_SHORT).show();
            });

            recyclerViewPhongTrong.setAdapter(adapter);
        }
    }


}
