package com.example.appbooking.page.admin.QuanLyDon;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.Model.Phong;
import com.example.appbooking.Model.Thue;
import com.example.appbooking.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuanLyDonThanhToan extends AppCompatActivity {

    MySQLite db1;
    ListView lvDanhSachPhong;
    ArrayList<Phong> phongList; // List for Phong
    ArrayList<Thue> thueList;   // List for Thue
    PhongAdapter quanLyDonAdapter;

    // Các view
    Spinner ad_dkLoc;
    ImageButton ad_btnLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_thanh_toan);

        // Ánh xạ các view
        ad_btnLoad = findViewById(R.id.ad_btnLoad);
        ad_dkLoc = findViewById(R.id.ad_dkLoc);
        lvDanhSachPhong = findViewById(R.id.ad_listDanhSachThue);

        // Khởi tạo MySQLite
        db1 = new MySQLite();

        // Khởi tạo danh sách mặc định
        phongList = new ArrayList<>(); // List phòng
        thueList = new ArrayList<>();  // List thuê
        quanLyDonAdapter = new PhongAdapter(this, R.layout.ad_item_admin_quan_ly_don, new ArrayList<>());  // Initialize with empty list

        // Gắn adapter vào ListView
        lvDanhSachPhong.setAdapter(quanLyDonAdapter);

        // Tạo danh sách lọc
        List<String> filterOptions = new ArrayList<>();
        filterOptions.add("Tất cả");
        filterOptions.add("Đang Thuê");
        filterOptions.add("Còn Trống");

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filterOptions);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad_dkLoc.setAdapter(filterAdapter);

        // Thiết lập sự kiện cho Spinner
        ad_dkLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = parentView.getItemAtPosition(position).toString();

                ArrayList<Object> filteredList = new ArrayList<>();

                if ("Đang Thuê".equals(selectedItem)) {
                    // Chỉ lấy phòng đã thuê
                    filteredList.addAll(thueList);
                } else if ("Còn Trống".equals(selectedItem)) {
                    // Chỉ lấy phòng còn trống
                    filteredList.addAll(phongList);
                } else {
                    // Lấy cả phòng và phòng đã thuê
                    filteredList.addAll(phongList);
                    filteredList.addAll(thueList);
                }

                // Show additional details for each room (Optional)
                StringBuilder roomDetails = new StringBuilder();
                for (Object room : filteredList) {
                    if (room instanceof Phong) {
                        Phong phong = (Phong) room;
                        roomDetails.append("Phòng: ").append(phong.getViTri()).append(" | Loại: ").append(phong.getMaLoaiPhong()).append("\n");
                    } else if (room instanceof Thue) {
                        Thue thue = (Thue) room;
                        roomDetails.append("Phòng: ").append(thue.getMaPhong()).append(" | Đã thuê | Check-out: ").append(thue.getCheckOut().toString()).append("\n");
                    }
                }

                // Update the adapter with the filtered data
                quanLyDonAdapter.updateData(filteredList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Không làm gì nếu không chọn
            }
        });

        // Sự kiện cho nút load lại
        ad_btnLoad.setOnClickListener(v -> {
            ad_dkLoc.setSelection(0); // Đặt Spinner về giá trị "Tất cả"
            Toast.makeText(QuanLyDonThanhToan.this, "Đã load lại", Toast.LENGTH_SHORT).show(); // Thông báo khi load lại
        });

        phongList.addAll(getPhong("SELECT * FROM PHONG;"));
        thueList.addAll(getPhongThue("SELECT * FROM THUE;"));
        Toast.makeText(QuanLyDonThanhToan.this, "thue list " + getPhongThue("SELECT * FROM THUE;"), Toast.LENGTH_SHORT).show();
    }

    // Phương thức lấy dữ liệu phòng
    private ArrayList<Phong> getPhong(String query) {
        ArrayList<Phong> phongList = new ArrayList<>();
        try {
            List<List<Object>> list = db1.executeQuery(query);
            for (List<Object> row : list) {
                Phong phong = new Phong(
                        Integer.parseInt(row.get(0).toString()),
                        row.get(1).toString(),
                        Integer.parseInt(row.get(2).toString())
                );
                phongList.add(phong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phongList;
    }

    // Phương thức lấy dữ liệu thuê
    private ArrayList<Thue> getPhongThue(String query) {
        ArrayList<Thue> thueList = new ArrayList<>();
        List<List<Object>> list = db1.executeQuery(query);
        //Toast.makeText(this, "Lấy " + list.size() + " thuê", Toast.LENGTH_SHORT).show(); // Thông báo số thuê
        for (List<Object> row : list) {

            Thue thue = new Thue(
                    Integer.parseInt(row.get(0).toString()),
                    Integer.parseInt(row.get(1).toString()),
                    db1.parseDate(row.get(2).toString())
            );
            thueList.add(thue);
        }
        return thueList;
    }

}
