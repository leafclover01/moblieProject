package com.example.appbooking.page.admin.QuanLyDon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.Model.Phong;
import com.example.appbooking.R;

import java.util.ArrayList;
import java.util.List;

public class QuanLyDonThanhToan extends AppCompatActivity {
    ImageButton admin_tai_lai;
    ListView ad_listDanhSachThue;
    ArrayList<Phong> danhSachPhong;  // Danh sách phòng
    MySQLite dbHelper = new MySQLite();  // Trợ giúp kết nối DB

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_thanh_toan);

        // Gắn các phần tử UI
        admin_tai_lai = findViewById(R.id.admin_tai_lai);
        ad_listDanhSachThue = findViewById(R.id.ad_listDanhSachThue);

        // Dữ liệu checkIn, checkOut và maLoaiPhong (có thể lấy từ UI hoặc dữ liệu nào đó)
        String checkIn = "2024-12-01";  // Ví dụ, ngày check-in
        String checkOut = "2024-12-05"; // Ví dụ, ngày check-out
        String maLoaiPhong = "1"; // Loại phòng (ID hoặc mã loại phòng)

        // Lấy danh sách phòng trống từ cơ sở dữ liệu
        danhSachPhong = layDuLieuPhongTrong(checkIn, checkOut, maLoaiPhong);

        if (danhSachPhong.isEmpty()) {
            // Nếu không có phòng, hiển thị thông báo
            Toast.makeText(this, "Không có phòng trống.", Toast.LENGTH_LONG).show();
        } else {
            // Hiển thị tổng số phòng và số phòng trống
            int tongSoPhong = danhSachPhong.size();
            int phongTrong = countPhongTrong(); // Sử dụng phương thức countPhongTrong() nếu cần
            String thongBao = "Tổng số phòng: " + tongSoPhong + "\nPhòng còn trống: " + phongTrong;
            Toast.makeText(this, thongBao, Toast.LENGTH_LONG).show();
        }

        // Cập nhật ListView với danh sách phòng
        updateListView(danhSachPhong);

        // Xử lý sự kiện click để làm mới danh sách phòng
        admin_tai_lai.setOnClickListener(v -> lamMoiDanhSachPhong());
    }

    // Đếm số phòng còn trống từ cơ sở dữ liệu
    private int countPhongTrong() {
        int count = 0;
        String query = "SELECT getSoPhongTrong()";  // Giả sử hàm getSoPhongTrong() trả về số phòng trống

        try {
            // Kết nối đến cơ sở dữ liệu và thực hiện truy vấn
            List<List<Object>> ketQua = dbHelper.executeQuery(query);

            // Lấy kết quả từ câu truy vấn (giả sử chỉ có 1 dòng với 1 giá trị đếm)
            if (!ketQua.isEmpty()) {
                count = Integer.parseInt(ketQua.get(0).get(0).toString()); // Lấy số lượng phòng trống từ kết quả trả về
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nếu có
        }
        return count;
    }

    // Tải danh sách phòng trống từ cơ sở dữ liệu
    private ArrayList<Phong> layDuLieuPhongTrong(String checkIn, String checkOut, String maLoaiPhong) {
        ArrayList<Phong> ds = new ArrayList<>();

        // Câu truy vấn SQL để lấy danh sách phòng trống
        String query = String.format("SELECT * FROM PHONG WHERE ma_phong NOT IN (" +
                        "SELECT P.ma_phong FROM PHONG AS P LEFT JOIN THUE AS T ON P.ma_phong = T.ma_phong " +
                        "LEFT JOIN DON AS D ON D.ma_don = T.ma_don LEFT JOIN QUAN_LY AS Q ON Q.ma_don = D.ma_don " +
                        "WHERE ((D.check_in <= '%s' AND T.check_out >= '%s') OR (Q.check_in_thuc_te <= '%s' AND Q.check_out_thuc_te >= '%s')) " +
                        "GROUP BY P.ma_phong) AND ma_loai_phong = '%s';",
                checkOut, checkIn, checkOut, checkIn, maLoaiPhong);

        try {
            // Thực hiện truy vấn và lấy kết quả từ cơ sở dữ liệu
            List<List<Object>> ketQua = dbHelper.executeQuery(query);

            for (List<Object> row : ketQua) {
                Phong phong = new Phong(
                        Integer.parseInt(row.get(0).toString()),  // ID phòng
                        row.get(1).toString(),  // Vị trí phòng
                        Integer.parseInt(row.get(2).toString())   // Loại phòng
                );
                ds.add(phong);  // Thêm phòng vào danh sách
            }
        } catch (Exception e) {
            e.printStackTrace();  // In lỗi nếu có
        }
        return ds;
    }

    // Làm mới danh sách phòng bằng cách tải lại từ cơ sở dữ liệu
    void lamMoiDanhSachPhong() {
        ArrayList<Phong> danhSachLamMoi = loadDanhSachPhong("SELECT * FROM PHONG;");
        updateListView(danhSachLamMoi);
    }

    // Cập nhật ListView với danh sách phòng
    void updateListView(ArrayList<Phong> danhSachPhong) {
        PhongAdapter phongAdapter = new PhongAdapter(this, R.layout.ad_item_phong, danhSachPhong);
        ad_listDanhSachThue.setAdapter(phongAdapter);
    }

    // Tải danh sách phòng từ cơ sở dữ liệu theo câu truy vấn
    ArrayList<Phong> loadDanhSachPhong(String querySQL) {
        ArrayList<Phong> danhSachPhong = new ArrayList<>();
        try {
            // Thực thi câu truy vấn SQL và lấy kết quả
            List<List<Object>> ketQua = dbHelper.executeQuery(querySQL);
            for (List<Object> dong : ketQua) {
                // Lấy dữ liệu từ các cột trong bảng 'PHONG'
                Phong phong = new Phong(
                        Integer.parseInt(dong.get(0).toString()),  // ID phòng
                        dong.get(1).toString(),  // Vị trí phòng
                        Integer.parseInt(dong.get(2).toString())   // Loại phòng
                );
                danhSachPhong.add(phong);  // Thêm phòng vào danh sách
            }
        } catch (Exception e) {
            e.printStackTrace();  // In lỗi nếu có
        }
        return danhSachPhong;
    }
}
