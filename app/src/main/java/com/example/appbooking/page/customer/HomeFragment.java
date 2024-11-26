package com.example.appbooking.page.customer;

import android.os.Bundle;
import androidx.fragment.app.Fragment;  // Thêm dòng này
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbooking.Model.LoaiPhong;
import com.example.appbooking.R;
import com.example.appbooking.page.customer.LoaiPhongAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewLoaiPhong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewLoaiPhong = view.findViewById(R.id.recyclerViewLoaiPhong);
        recyclerViewLoaiPhong.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<LoaiPhong> loaiPhongList = getLoaiPhongList();
        LoaiPhongAdapter adapter = new LoaiPhongAdapter(loaiPhongList, getActivity());
        recyclerViewLoaiPhong.setAdapter(adapter);

        return view;
    }

    private List<LoaiPhong> getLoaiPhongList() {
        List<LoaiPhong> loaiPhongList = new ArrayList<>();
        loaiPhongList.add(new LoaiPhong(1, "Phòng La Vela 1 giường lớn", 500000, 2, "View mặt đường"));
        loaiPhongList.add(new LoaiPhong(2, "Phòng La Vela 2 giường", 700000, 3, "View thành phố"));
        loaiPhongList.add(new LoaiPhong(3, "Phòng La Vela giường đôi", 1500000, 5, "View bãi biển"));
        loaiPhongList.add(new LoaiPhong(4, "Phòng La Vela giường đôi lớn", 5000000, 5, "View thành phố toàn cảnh"));
        return loaiPhongList;
    }
}
