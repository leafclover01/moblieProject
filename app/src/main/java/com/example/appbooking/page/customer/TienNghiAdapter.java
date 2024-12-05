package com.example.appbooking.page.customer;

import android.content.Context;
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

    public TienNghiAdapter(ArrayList<TienNghi> dsTienNghi) {
        this.dsTienNghi = dsTienNghi;
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
        // Gán dữ liệu vào view (vd: tên tiện nghi, mô tả, icon...)
        holder.tenTienNghi.setText(tienNghi.getTenTienNghi());
        holder.moTaTienNghi.setText(tienNghi.getIc_mo_ta());

        // Nếu có icon tiện nghi, bạn có thể tải ảnh từ URL hoặc drawable ở đây
        // Glide.with(holder.iconTienNghi.getContext())
        //      .load(tienNghi.getIcMoTa())
        //      .into(holder.iconTienNghi);
    }

    @Override
    public int getItemCount() {
        return dsTienNghi.size();
    }

    public static class TienNghiViewHolder extends RecyclerView.ViewHolder {
        public TextView tenTienNghi;
        public TextView moTaTienNghi;
        // public ImageView iconTienNghi;

        public TienNghiViewHolder(View itemView) {
            super(itemView);
            tenTienNghi = itemView.findViewById(R.id.tenTienNghi);
//            moTaTienNghi = itemView.findViewById(R.id.moTaTienNghi);
            // iconTienNghi = itemView.findViewById(R.id.iconTienNghi);
        }
    }
}
