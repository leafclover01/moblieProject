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

    private List<TienNghi> dsTienNghi;

    public TienNghiAdapter(List<TienNghi> dsTienNghi) {
        this.dsTienNghi = dsTienNghi;
    }

    @NonNull
    @Override
    public TienNghiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tien_nghi, parent, false);
        return new TienNghiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TienNghiViewHolder holder, int position) {
        TienNghi tienNghi = dsTienNghi.get(position);
        holder.tenTienNghi.setText(tienNghi.getTenTienNghi());
        // Nếu bạn có hình ảnh dưới dạng URL hoặc resource, xử lý tại đây
        // holder.iconTienNghi.setImageResource(...);
    }

    @Override
    public int getItemCount() {
        return dsTienNghi.size();
    }

    public static class TienNghiViewHolder extends RecyclerView.ViewHolder {

        TextView tenTienNghi;
        ImageView iconTienNghi;

        public TienNghiViewHolder(@NonNull View itemView) {
            super(itemView);
            tenTienNghi = itemView.findViewById(R.id.tenTienNghi);
            iconTienNghi = itemView.findViewById(R.id.iconTienNghi);
        }
    }
}
