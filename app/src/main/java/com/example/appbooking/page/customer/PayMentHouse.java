package com.example.appbooking.page.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.Model.UuDai;
import com.example.appbooking.R;
import com.example.appbooking.page.DashboardActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PayMentHouse extends AppCompatActivity {
    MySQLite db;
    TextView tvCustomerName, tvCustomerContact, tvCustomerSdt, tvSubtotal, tvDiscount, tvTotal, roomContainer;
    Button btnEditCustomerInfo, btnPay;
    Spinner spnMgg;
    ArrayList<UuDai> listMaUD;
    ArrayAdapter<String> discountAdapter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    double total = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Bind views
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerContact = findViewById(R.id.tvCustomerCCCD);
        tvCustomerSdt = findViewById(R.id.tvCustomerCCCD);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvTotal = findViewById(R.id.tvTotal);
        roomContainer = findViewById(R.id.roomContainer);
        btnEditCustomerInfo = findViewById(R.id.btnEditCustomerInfo);
        btnPay = findViewById(R.id.btnPay);
        spnMgg = findViewById(R.id.spnMgg);

        // Get data from Intent
        Intent intent = getIntent();
        String tenKhachHang = "NguyenTanHai";
        String cccd = "06666666666";
        String sdt = "0348929676";
        String tenPhong = intent.getStringExtra("tenPhong");
        String giaPhong = intent.getStringExtra("giaPhong");
        String maPhong = intent.getStringExtra("maPhong");
        String timeCheckIn = intent.getStringExtra("timeCheckIn");
        String timeCheckOut = intent.getStringExtra("timeCheckOut");

        // Display customer and room information
        tvCustomerName.setText("Tên khách hàng: " + (tenKhachHang != null ? tenKhachHang : "Không rõ"));
        tvCustomerContact.setText("CCCD: " + (cccd != null ? cccd : "Không rõ"));
        roomContainer.setText(tenPhong + " - " + timeCheckIn + " - " + timeCheckOut + giaPhong);
        tvCustomerSdt.setText(sdt);
        // Initialize database
        db = new MySQLite();

        // Fetch active discount data
        listMaUD = new ArrayList<>();
        listMaUD = getall("SELECT * FROM UU_DAI WHERE ngay_het_han >= strftime('%Y/%m/%d %H:%M', datetime('now', '+7 hours')) AND dieu_kien_ve_gia ==" + getmaTenPhong(tenPhong));

        // Populate Spinner with active discount codes
        List<String> discountNames = new ArrayList<>();
        for (UuDai discount : listMaUD) {
            discountNames.add(discount.getTenMa());
        }

        discountAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, discountNames);
        discountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMgg.setAdapter(discountAdapter);

        // Handle Spinner selection
        spnMgg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!listMaUD.isEmpty()) {
                    UuDai selectedDiscount = listMaUD.get(position);
                    tvDiscount.setText("Giảm giá: " + selectedDiscount.getGiam() + "%");

                    // Handle price string with formatting like "2.000.000 VND"
                    double subtotal = parsePriceString(giaPhong);
                    double discount = selectedDiscount.getGiam();
                    total = subtotal - discount;
                    tvTotal.setText("Tổng thanh toán: " + total + " VND");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tvDiscount.setText("Giảm giá: 0 VND");
            }
        });

        btnEditCustomerInfo.setOnClickListener(view -> {
            Intent editIntent = new Intent(PayMentHouse.this, EditCustomerInfoActivity.class);
            editIntent.putExtra("customerName", tenKhachHang);
            editIntent.putExtra("customerContact", cccd);
            startActivityForResult(editIntent, 1);
        });

        btnPay.setOnClickListener(view -> {
            Toast.makeText(PayMentHouse.this, "Đang xử lý thanh toán...", Toast.LENGTH_SHORT).show();
            int maUser = 2;
            showPaymentConfirmationDialog(maUser, timeCheckIn, tenKhachHang, maPhong, timeCheckOut, total);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String updatedName = data.getStringExtra("customerName");
            String updatedContact = data.getStringExtra("customerContact");
            tvCustomerName.setText("Tên khách hàng: " + updatedName);
            tvCustomerContact.setText("CCCD: " + updatedContact);
        }
    }

    private String getmaTenPhong(String tenPhong) {
        String maPhong = "0";

        switch (tenPhong) {
            case "Phòng Standard":
                maPhong = "1";
                break;
            case "Phòng Superior":
                maPhong = "2";
                break;
            case "Phòng Deluxe":
                maPhong = "3";
                break;
            case "Phòng Suite":
                maPhong = "4";
                break;
            default:
                maPhong = "0";  // Trường hợp không có tên phòng trùng khớp
                break;
        }

        return maPhong;
    }

    // Parse price string like "2.000.000 VND" to a number
    private double parsePriceString(String priceString) {
        String numericString = priceString.replaceAll("[^0-9]", "");
        try {
            return Double.parseDouble(numericString);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }


    private void showPaymentConfirmationDialog(int maUser, String checkIn, String tenKhachHang, String maPhong, String checkOut, double totalAmount) {
        if (checkIn == null || checkOut == null || tenKhachHang == null || maPhong == null || totalAmount <= 0) {
            Toast.makeText(this, "Thông tin thanh toán không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận thanh toán")
                .setMessage("Bạn chắc chắn muốn thanh toán không?\n\n" +
                        "Tổng thanh toán: " + totalAmount + " VND")
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    Toast.makeText(PayMentHouse.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                    showPaymentDetails(maUser, checkIn, tenKhachHang, maPhong, checkOut, totalAmount);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private String getCurrentTimeFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }

    private void showPaymentDetails(int maUser, String checkIn, String tenKhachHang, String maPhong, String checkOut, double totalAmount) {
        String message = "Cảm ơn bạn đã thanh toán!\n\n" +
                "Khách hàng: " + tenKhachHang + "\n" +
                "Phòng: " + maPhong + "\n" +
                "Check-in: " + checkIn + "\n" +
                "Check-out: " + checkOut + "\n" +
                "Tổng tiền: " + totalAmount + " VND\n\n" +
                "Xin hãy chuyển khoản tới tài khoản: xxxxxx VietComBank.\n" +
                "Chúng tôi sẽ liên hệ với bạn sớm. Xin hãy giữ liên lạc qua điện thoại.";

        new AlertDialog.Builder(this)
                .setTitle("Cảm ơn bạn đã thanh toán")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();

        String currentTime = getCurrentTimeFormatted();
        try {
            int maDon = Integer.parseInt(db.insertDataDon(maUser, currentTime, checkIn));  // assuming insertDataDon returns int or Integer

            if (maDon > 0) {
                db.insertDataThue(maDon, Integer.parseInt(maPhong), checkOut);
                Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PayMentHouse.this, DashboardActivity.class);
                intent.putExtra("totalAmount", totalAmount);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Lỗi khi tạo mã đơn!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khi lưu thông tin thanh toán!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    ArrayList<UuDai> getall(String sql) {
        ArrayList<UuDai> listMa = new ArrayList<>();
        try {
            List<List<Object>> list = db.executeQuery(sql);
            for (List<Object> row : list) {
                UuDai uuDai = new UuDai();
                uuDai.setMaNhanVien(Integer.parseInt(row.get(0).toString()));
                uuDai.setMaUuDai(Integer.parseInt(row.get(1).toString()));
                uuDai.setTenMa(row.get(2).toString());
                try {
                    Date ngayBatDau = row.get(3) != null ? dateFormat.parse(row.get(3).toString()) : null;
                    uuDai.setNgayBatDau(ngayBatDau);
                } catch (ParseException e) {
                    uuDai.setNgayBatDau(null);
                }
                try {
                    Date ngayHetHan = row.get(4) != null ? dateFormat.parse(row.get(4).toString()) : null;
                    uuDai.setNgayHetHan(ngayHetHan);
                } catch (ParseException e) {
                    uuDai.setNgayHetHan(null);
                }
                uuDai.setGiam(Double.parseDouble(row.get(5).toString()));
                uuDai.setDieuKienVeGia(Integer.parseInt(row.get(6).toString()));
                listMa.add(uuDai);
            }
            return listMa;
        } catch (Exception e) {
            return null;
        }
    }
}
