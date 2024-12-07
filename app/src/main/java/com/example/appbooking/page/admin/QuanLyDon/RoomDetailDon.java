package com.example.appbooking.page.admin.QuanLyDon;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appbooking.R;

public class RoomDetailDon extends AppCompatActivity {

    TextView roomName, roomType, roomDetails, price, bookedBy, bookedAt;
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail_don);

        // Initialize views
//        roomName = findViewById(R.id.roomName);
        roomType = findViewById(R.id.roomType);
        roomDetails = findViewById(R.id.roomDetails);
        price = findViewById(R.id.price);
        bookedBy = findViewById(R.id.bookedBy);
        bookedAt = findViewById(R.id.bookedAt);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Retrieve the data passed from the previous activity
        String roomNameText = getIntent().getStringExtra("roomName");
        String roomTypeText = getIntent().getStringExtra("roomType");
        String roomDetailsText = getIntent().getStringExtra("roomDetails");
        String priceText = getIntent().getStringExtra("price");
        String bookedByText = getIntent().getStringExtra("bookedBy");
        String bookedAtText = getIntent().getStringExtra("bookedAt");

        // Set the data to the views
        roomName.setText(roomNameText);
        roomType.setText("Loại phòng: " + roomTypeText);
        roomDetails.setText("Chi tiết phòng: " + roomDetailsText);
        price.setText("Giá: " + priceText);
        bookedBy.setText("Đặt bởi: " + bookedByText);
        bookedAt.setText("Đặt vào: " + bookedAtText);

        // Set button click listener (checkout logic can be implemented)
        btnCheckout.setOnClickListener(v -> {
            // Implement checkout logic here, for example, updating the database
            // For now, you can display a message or perform other actions
        });
    }
}
