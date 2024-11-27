package com.example.appbooking.page.customer;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.appbooking.Model.LoaiPhong;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.appbooking.R;
import com.example.appbooking.page.customer.LoaiPhongAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewLoaiPhong;
    Button a, btn3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the buttons
        a = view.findViewById(R.id.button2);
        btn3 = view.findViewById(R.id.btn3);

        recyclerViewLoaiPhong = view.findViewById(R.id.recyclerViewLoaiPhong);
        recyclerViewLoaiPhong.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<LoaiPhong> loaiPhongList = getLoaiPhongList();
        LoaiPhongAdapter adapter = new LoaiPhongAdapter(loaiPhongList, getActivity());
        recyclerViewLoaiPhong.setAdapter(adapter);

        // Button event handling
        a.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Logout!", Toast.LENGTH_SHORT).show();
        });


        // Handle btn3 click event
        btn3.setOnClickListener(v -> {
            Intent in = new Intent(getContext(), homeAdmin.class);
            startActivity(in);
        });

        // Return the view after setting up listeners and logic
        return view;
    }

    private List<LoaiPhong> getLoaiPhongList() {
        List<LoaiPhong> loaiPhongList = new ArrayList<>();
        loaiPhongList.add(new LoaiPhong(1, "Phòng La Vela 1 giường lớn", 500000, 2, "View mặt đường", "v1_1.jpg"));
        loaiPhongList.add(new LoaiPhong(2, "Phòng La Vela 2 giường", 700000, 3, "View thành phố", "v2_2.jpg"));
        loaiPhongList.add(new LoaiPhong(3, "Phòng La Vela giường đôi", 1500000, 5, "View bãi biển", "v3_1.jpg"));
        loaiPhongList.add(new LoaiPhong(4, "Phòng La Vela giường đôi lớn", 5000000, 5, "View thành phố toàn cảnh", "v4_1.jpg"));
        return loaiPhongList;
    }
}
