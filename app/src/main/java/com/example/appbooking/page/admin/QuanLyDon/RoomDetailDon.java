package com.example.appbooking.page.admin.QuanLyDon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appbooking.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RoomDetailDon extends AppCompatActivity {

    private TextView roomType, roomDetails, price, bookedBy, bookedAt;
    private Button btnCheckin, btnCheckout;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail_don);

        // Ánh xạ các view
        roomType = findViewById(R.id.roomType);
        roomDetails = findViewById(R.id.roomDetails);
        price = findViewById(R.id.price);
        bookedBy = findViewById(R.id.bookedBy);
        bookedAt = findViewById(R.id.bookedAt);
        btnCheckin = findViewById(R.id.btnCheckin);
        btnCheckout = findViewById(R.id.btnCheckout);
        backButton = findViewById(R.id.back);

        // Xử lý nút Back
        backButton.setOnClickListener(view -> finish());

        // Xử lý Check-in
        btnCheckin.setOnClickListener(view -> {
            String currentTime = getCurrentTime();
            Toast.makeText(RoomDetailDon.this, "Đã Check-in thành công!\nThời gian: " + currentTime, Toast.LENGTH_LONG).show();
        });

        // Xử lý Check-out
        btnCheckout.setOnClickListener(view -> {
            String currentTime = getCurrentTime();
            Toast.makeText(RoomDetailDon.this, "Đã Check-out thành công!\nThời gian: " + currentTime, Toast.LENGTH_LONG).show();
        });
    }

    // Hàm lấy thời gian hiện tại
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
}
