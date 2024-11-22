package com.example.appbooking.page.customer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appbooking.R;

public class HomeFragment extends Fragment {

    Button a;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ Button từ XML
        a = view.findViewById(R.id.button2);

        // Thêm sự kiện cho Button
        a.setOnClickListener(v -> {
            // Xử lý khi Button được nhấn
            Toast.makeText(getContext(), "logout!", Toast.LENGTH_SHORT).show();
        });

        // Trả về view đã được ánh xạ
        return view;
    }

}
