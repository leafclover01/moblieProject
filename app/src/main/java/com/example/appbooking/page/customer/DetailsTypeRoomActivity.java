package com.example.appbooking.page.customer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.Model.TienNghi;
import com.example.appbooking.R;
import com.example.appbooking.Model.Phong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

//import androidx.recyclerview.widget.GridLayoutManager;
public class DetailsTypeRoomActivity extends AppCompatActivity {
    private ImageView imageLoaiPhong;
    private TextView tenLoaiPhong, giaLoaiPhong, soNguoiToiDa, moTa, moTaChiTiet;
    private Button btnCheckIn, btnCheckOut, btnChonThoiGian;
    private RecyclerView recyclerViewPhongTrong;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private String timeCheckIn = "", timeCheckOut = "";
    MySQLite db1 = new MySQLite();
    private Button btnNext;
    public String maPhong = "";
    private RecyclerView recyclerViewTienNghi;
    private TienNghiAdapter tienNghiAdapter;
    private ArrayList<TienNghi> dsTienNghi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_loai_phong);
//        recyclerViewPhongTrong.setLayoutManager(new GridLayoutManager(this, 2));
        // Ánh xạ các view
        ImageView imageLoaiPhong = findViewById(R.id.imageLoaiPhong);
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
            onBackPressed();
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

////        // Xử lý chọn ngày Check-in
        btnCheckIn.setOnClickListener(v -> showDatePickerDialog(true));

        // Xử lý chọn ngày Check-out
        btnCheckOut.setOnClickListener(v -> showDatePickerDialog(false));


        btnChonThoiGian.setOnClickListener(v -> {
            if (timeCheckIn.isEmpty() || timeCheckOut.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ngày Check-in và Check-out!", Toast.LENGTH_SHORT).show();
            } else {
                // Sử dụng thời gian đã được thêm giờ

                ArrayList<Phong> dsPhongTrong = db1.layDuLieuPhongKhongCoNguoiDat(timeCheckIn, timeCheckOut, 1); // Ví dụ mã loại phòng = "1"
                loadRoomList(dsPhongTrong);
//                Toast.makeText(this, dsPhongTrong.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // Setup RecyclerView
        recyclerViewPhongTrong.setLayoutManager(new LinearLayoutManager(this));





        // Kiểm tra và đảm bảo adapter đã được thiết lập cho RecyclerView
        RecyclerView recyclerViewTienNghi = findViewById(R.id.recyclerViewTienNghi);
        recyclerViewTienNghi.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        TienNghiAdapter tienNghiAdapter = new TienNghiAdapter(dsTienNghi); // dsTienNghi là danh sách tiện nghi từ SQL
        recyclerViewTienNghi.setAdapter(tienNghiAdapter);

        // Lấy mã loại phòng từ Intent
        int maLoaiPhong = getIntent().getIntExtra("maLoaiPhong", -1);

// Kiểm tra mã loại phòng và lấy dữ liệu tiện nghi
        if (maLoaiPhong != -1) {
            // Gọi hàm lấy dữ liệu tiện nghi từ SQL
            dsTienNghi = layDuLieuTienNghi(maLoaiPhong);

            // In ra log để kiểm tra danh sách tiện nghi
            Log.d("TienNghi", "Số tiện nghi: " + dsTienNghi.size());

            // Kiểm tra nếu danh sách tiện nghi không rỗng
            if (!dsTienNghi.isEmpty()) {
                // Gắn adapter vào RecyclerView
                tienNghiAdapter = new TienNghiAdapter(dsTienNghi);
                recyclerViewTienNghi.setAdapter(tienNghiAdapter);
            } else {
                // Nếu không có tiện nghi nào, hiển thị thông báo
                Toast.makeText(this, "Không có tiện nghi cho loại phòng này!", Toast.LENGTH_SHORT).show();
            }
    }

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
                    intent.putExtra("moTaChiTietPhong", moTaChiTietPhong);
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
    private void showDatePickerDialog(boolean isCheckIn) {
        if (isCheckIn && !timeCheckIn.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày Check-in!", Toast.LENGTH_SHORT).show();
        } else if (!isCheckIn && !timeCheckOut.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày Check-out!", Toast.LENGTH_SHORT).show();
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo một DatePickerDialog
        new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Chọn ngày từ DatePicker
            String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);

            // Tạo thời gian mặc định cho check-in và check-out
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay);

            String formattedDateTime;
            if (isCheckIn) {
                //  Check-in, giờ sẽ là 14:00 (mặc định)
                selectedCalendar.set(Calendar.HOUR_OF_DAY, 14);
                selectedCalendar.set(Calendar.MINUTE, 0);
            } else {
                //  Check-out, giờ sẽ là 10:00 (mặc định)
                selectedCalendar.set(Calendar.HOUR_OF_DAY, 10);
                selectedCalendar.set(Calendar.MINUTE, 0);
            }
            try {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                Calendar checkInCalendar = Calendar.getInstance();
                if (!timeCheckIn.isEmpty()) {
                    checkInCalendar.setTime(dateFormatter.parse(timeCheckIn));
                }

                // Kiểm tra ngày Check-out (chỉ áp dụng khi chọn Check-out)
                if (!isCheckIn) {
                    long diffInMillis = selectedCalendar.getTimeInMillis() - checkInCalendar.getTimeInMillis();

                    // Kiểm tra điều kiện cách nhau không quá 7 ngày
                    long diffInDays = diffInMillis / (1000 * 60 * 60 * 24); // Chuyển đổi khoảng cách thành số ngày
                    if (diffInDays > 7) {
                        Toast.makeText(this, "Quý khách vui lòng liên hệ Room nếu muốn đặt trn 7 ngày", Toast.LENGTH_SHORT).show();
                        btnChonThoiGian.setEnabled(false);
                        btnNext.setEnabled(false);
                        return;
                    }

                    // Kiểm tra nếu ngày Check-out trước ngày Check-in
                    if (diffInMillis < 0) {
                        Toast.makeText(this, "Quý khách vui lòng đặt tổi thiếu 1 ngày", Toast.LENGTH_SHORT).show();
                        btnChonThoiGian.setEnabled(false);
                        btnNext.setEnabled(false);
                        return;
                    }
                }

                // Bật nút nếu thời gian hợp lệ
                btnChonThoiGian.setEnabled(true);
                btnNext.setEnabled(true);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Đã xảy ra lỗi khi chọn ngày!", Toast.LENGTH_SHORT).show();
            }

            // Chuyển đổi lại thành định dạng yyyy-MM-dd HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            formattedDateTime = sdf.format(selectedCalendar.getTime());

            if (isCheckIn) {
                timeCheckIn = formattedDateTime;
                btnCheckIn.setText("Check-in: " + formattedDateTime);

//            Toast.makeText(DetailsTypeRoomActivity.this, "Ngày Check-in: " + formattedDateTime, Toast.LENGTH_SHORT).show();
            } else {
                timeCheckOut = formattedDateTime;
                btnCheckOut.setText("Check-out: " + formattedDateTime);
//            Toast.makeText(DetailsTypeRoomActivity.this, "Ngày Check-out: " + formattedDateTime, Toast.LENGTH_SHORT).show();
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
    public ArrayList<TienNghi> layDuLieuTienNghi(int maLoaiPhong) {
        // Gọi hàm này trong lớp chứa hàm kết nối với database của bạn
        // Ví dụ bạn đã định nghĩa hàm này trong lớp MySQLite:
        return db1.layDuLieuTienNghi(maLoaiPhong);
    }

}