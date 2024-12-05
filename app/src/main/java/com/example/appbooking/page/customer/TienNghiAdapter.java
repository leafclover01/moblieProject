package com.example.appbooking.page.customer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbooking.Model.TienNghi;
import com.example.appbooking.R;

import java.util.ArrayList;
import java.util.List;

public class TienNghiAdapter extends RecyclerView.Adapter<TienNghiAdapter.TienNghiViewHolder> {
    private ArrayList<TienNghi> dsTienNghi;

    public static class TienNghiViewHolder extends RecyclerView.ViewHolder {
        public TextView tenTienNghi;
        public TextView moTaTienNghi; // Đã bỏ comment ở đây

        public TienNghiViewHolder(View itemView) {
            super(itemView);
            tenTienNghi = itemView.findViewById(R.id.tenTienNghi);
            moTaTienNghi = itemView.findViewById(R.id.moTaTienNghi); // Đảm bảo có id trong layout
        }
    }


    @NonNull
    @Override
    public TienNghiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tien_nghi, parent, false);
        return new TienNghiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TienNghiViewHolder holder, int position) {
        TienNghi tienNghi = dsTienNghi.get(position);
        holder.tenTienNghi.setText(tienNghi.getTenTienNghi());
        holder.moTaTienNghi.setText(tienNghi.getIc_mo_ta());

        // Thêm sự kiện click để chuyển đến DetailsTypeRoomActivity
        holder.itemView.setOnClickListener(v -> {
            // Tạo intent để chuyển đến DetailsTypeRoomActivity
            Intent intent = new Intent(holder.itemView.getContext(), DetailsTypeRoomActivity.class);

            // Truyền dữ liệu tiện nghi vào Intent
            intent.putExtra("tenTienNghi", tienNghi.getTenTienNghi());
            intent.putExtra("moTaTienNghi", tienNghi.getIc_mo_ta());

            // Nếu có icon hoặc hình ảnh tiện nghi, bạn có thể truyền vào
            // intent.putExtra("iconTienNghi", tienNghi.getIcMoTa());

            // Khởi động activity mới
            holder.itemView.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return dsTienNghi.size();
    }

//    public static class TienNghiViewHolder extends RecyclerView.ViewHolder {
//        public TextView tenTienNghi;
//        public TextView moTaTienNghi;
//        // public ImageView iconTienNghi;
//
//        public TienNghiViewHolder(View itemView) {
//            super(itemView);
//            tenTienNghi = itemView.findViewById(R.id.tenTienNghi);
////            moTaTienNghi = itemView.findViewById(R.id.moTaTienNghi);
//            // iconTienNghi = itemView.findViewById(R.id.iconTienNghi);
//        }
//    }
}
