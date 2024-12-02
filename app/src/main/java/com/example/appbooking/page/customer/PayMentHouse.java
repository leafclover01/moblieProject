package com.example.appbooking.page.customer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.appbooking.R;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PayMentHouse extends AppCompatActivity {

    private TextView tvCustomerName, tvCustomerContact, tvSubtotal, tvDiscount, tvTotal;
    private LinearLayout roomContainer; // Container for room details
    private Button btnEditCustomerInfo, btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Bind views
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerContact = findViewById(R.id.tvCustomerContact);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvTotal = findViewById(R.id.tvTotal);
        roomContainer = findViewById(R.id.roomContainer);
        btnEditCustomerInfo = findViewById(R.id.btnEditCustomerInfo);
        btnPay = findViewById(R.id.btnPay);

        // Mock data
        tvCustomerName.setText("Tên khách hàng: Nguyễn Văn A");
        tvCustomerContact.setText("Số điện thoại: 0987654321");
        tvSubtotal.setText("Tạm tính: 3,000,000 VND");
        tvDiscount.setText("Giảm giá: 500,000 VND");
        tvTotal.setText("Tổng thanh toán: 2,500,000 VND");

        // Add room details manually
        addRoomToContainer("Phòng Deluxe", 1500000);

        // Handle edit customer info button
        btnEditCustomerInfo.setOnClickListener(view -> {
            Intent intent = new Intent(PayMentHouse.this, EditCustomerInfoActivity.class);
            startActivity(intent);
        });

        // Handle payment button with confirmation dialog
        btnPay.setOnClickListener(view -> {
            showPaymentConfirmationDialog();
        });
    }

    // Helper method to add room information dynamically
    private void addRoomToContainer(String roomName, int price) {
        TextView roomView = new TextView(this);
        roomView.setText(roomName + " - " + price + " VND");
        roomView.setTextSize(16);
        roomView.setPadding(8, 8, 8, 8);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 8);
        roomView.setLayoutParams(params);
        roomContainer.addView(roomView);
    }

    // Show payment confirmation dialog
    private void showPaymentConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận thanh toán")
                .setMessage("Bạn chắc chắn muốn thanh toán không?\n\n" +
                        "Tiền phòng: 2,500,000 VND\n" +
                        "Giảm giá: 500,000 VND\n" +
                        "Tổng thanh toán: 2,500,000 VND\n\nLưu ý: Không thể hoàn lại sau khi thanh toán.")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Show payment details and proceed with payment
                        showPaymentDetails();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    // Show payment details after confirmation
    private void showPaymentDetails() {
        new AlertDialog.Builder(this)
                .setTitle("Thông tin thanh toán")
                .setMessage("Khách hàng: Nguyễn Văn A\nSố điện thoại: 0987654321\n\n" +
                        "Tiền phòng: 2,500,000 VND\n" +
                        "Giảm giá: 500,000 VND\n" +
                        "Tổng thanh toán: 2,500,000 VND\n\n" +
                        "Lưu ý: Không thể hoàn lại sau khi thanh toán.")
                .setPositiveButton("OK", null)
                .show();
    }
}
