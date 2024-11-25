// Trang chủ
package com.example.appbooking.page.customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appbooking.R;
import com.example.appbooking.page.admin.homeAdmin;

public class HomeFragment extends Fragment {

    //TODO:  Khởi tạo
    Button a, btn3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Tạo một view bắt buộc các trang ()
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // TODO:  CODE như bình thường .....
        // Ánh xạ Button từ UI
        a = view.findViewById(R.id.button2);
        btn3 = view.findViewById(R.id.btn3);
        // Thêm sự kiện cho Button
        a.setOnClickListener(v -> {
            // Xử lý khi Button được nhấn
            Toast.makeText(getContext(), "logout!", Toast.LENGTH_SHORT).show();
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), homeAdmin.class);
                startActivity(in);
            }
        });

        // Trả về view đã được ánh xạ
        return view;
    }

}
