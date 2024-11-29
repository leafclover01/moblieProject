package com.example.appbooking.page.customer;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.appbooking.R;

public class PayMentHouse extends AppCompatActivity {

    TextView tvCustomerName, tvSubtotal, tvTotal;
    EditText edtCustomerName, edtCustomerPhone, edtCustomerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);  // Set the layout for the Activity

        // Link views to their corresponding XML elements
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvTotal = findViewById(R.id.tvTotal);

        // Set an OnClickListener on the customer name to open the dialog
        tvCustomerName.setOnClickListener(v -> showEditCustomerDialog());
    }

    void showEditCustomerDialog() {
        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_edit_customer_info, null);

        // Link EditTexts to the dialog layout
        edtCustomerName = dialogView.findViewById(R.id.edtCustomerName);
        edtCustomerPhone = dialogView.findViewById(R.id.edtCustomerPhone);
        edtCustomerEmail = dialogView.findViewById(R.id.edtCustomerEmail);

        // Set default values (you can replace with real data)
        edtCustomerName.setText("Nguyễn Văn A"); // Default name
        edtCustomerPhone.setText("0123456789"); // Default phone number
        edtCustomerEmail.setText("nguyenvana@gmail.com"); // Default email

        // Create and show the dialog
        new AlertDialog.Builder(this)
                .setTitle("Chỉnh sửa thông tin khách hàng")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialogInterface, i) -> {
                    // Get the new customer name and update the TextView
                    String newName = edtCustomerName.getText().toString();
                    tvCustomerName.setText("Tên khách hàng: " + newName);  // Update UI
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
