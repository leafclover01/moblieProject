package com.example.appbooking.page.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.appbooking.R;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class EditCustomerInfoActivity extends AppCompatActivity {

    private EditText etCustomerName, etCustomerContact;
    private Button btnSaveCustomerInfo, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer_info);

        // Bind views
        etCustomerName = findViewById(R.id.etCustomerName);
        etCustomerContact = findViewById(R.id.etCustomerContact);
        btnSaveCustomerInfo = findViewById(R.id.btnSaveCustomerInfo);
        btnCancel = findViewById(R.id.btnCancel);

        // Mock data (load customer data here if needed)
        etCustomerName.setText("Nguyễn Văn A");
        etCustomerContact.setText("0987654321");

        // Save button click event
        btnSaveCustomerInfo.setOnClickListener(view -> {
            String newName = etCustomerName.getText().toString().trim();
            String newContact = etCustomerContact.getText().toString().trim();

            if (newName.isEmpty() || newContact.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pass updated data back to PayMenHouse activity (if needed)
            Intent intent = new Intent();
            intent.putExtra("customerName", newName);
            intent.putExtra("customerContact", newContact);
            setResult(RESULT_OK, intent);

            Toast.makeText(this, "Thông tin khách hàng đã được cập nhật!", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Cancel button click event
        btnCancel.setOnClickListener(view -> {
            finish(); // Close the activity without saving
        });
    }
}
