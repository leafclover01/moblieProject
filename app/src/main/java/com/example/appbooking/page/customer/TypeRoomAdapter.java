package com.example.appbooking.page.customer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.Model.ChiTietLoaiPhong;
import com.example.appbooking.R;
import com.example.appbooking.Model.LoaiPhong;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TypeRoomAdapter extends RecyclerView.Adapter<TypeRoomAdapter.LoaiPhongViewHolder> {
    MySQLite db1 = new MySQLite();

    private final List<LoaiPhong> loaiPhongList;
    private final Context context;  // Thêm Context vào Adapter

    // Cập nhật constructor để nhận vào Context
    public TypeRoomAdapter(List<LoaiPhong> loaiPhongList, Context context) {
        this.loaiPhongList = loaiPhongList;
        this.context = context;
    }

    @NonNull
    @Override
    public LoaiPhongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loai_phong, parent, false);
        return new LoaiPhongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiPhongViewHolder holder, int position) {
        LoaiPhong loaiPhong = loaiPhongList.get(position);
        holder.tvLoaiPhong.setText(loaiPhong.getTen());
        holder.tvGiaPhong.setText("Giá: " + loaiPhong.getGia() + " VND");
        holder.tvSoNguoiToiDa.setText("Tối đa: " + loaiPhong.getSoNguoiToiDa() + " người");
        holder.tvMoTa.setText(loaiPhong.getMoTa());

        // Thêm sự kiện click cho từng item
        holder.itemView.setOnClickListener(v -> {
            // Tạo Intent để chuyển tới ChiTietLoaiPhongActivity
            Intent intent = new Intent(context, DetailsTypeRoomActivity.class);

            // Truyền dữ liệu chi tiết về loại phòng
//            intent.putExtra("checkIn", )

            Date checkIn = new Date();
            Date checkOut = new Date(System.currentTimeMillis() + 86400000);
            intent.putExtra("checkIn", checkIn.getTime());
            intent.putExtra("checkOut", checkOut.getTime());
            intent.putExtra("tenPhong", loaiPhong.getTen());
            intent.putExtra("giaPhong", String.valueOf(loaiPhong.getGia()));
            intent.putExtra("soNguoi", String.valueOf(loaiPhong.getSoNguoiToiDa()));
            intent.putExtra("moTaPhong", loaiPhong.getMoTa());
            intent.putExtra("imageResource", getchiTiet(loaiPhong.getMaLoaiPhong())); // intent gửi uri ảnh
            // Khởi động ChiTietLoaiPhongActivity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return loaiPhongList.size();
    }

    public static class LoaiPhongViewHolder extends RecyclerView.ViewHolder {
        TextView tvLoaiPhong;
        TextView tvGiaPhong;
        TextView tvSoNguoiToiDa;
        TextView tvMoTa;

        public LoaiPhongViewHolder(View itemView) {
            super(itemView);
            tvLoaiPhong = itemView.findViewById(R.id.tvLoaiPhong);
            tvGiaPhong = itemView.findViewById(R.id.tvGiaPhong);
            tvSoNguoiToiDa = itemView.findViewById(R.id.tvSoNguoiToiDa);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
        }
    }

    public String getchiTiet(int id){ // hàm lấy ảnh theo mã loại phòng chuyền mã loại phòng vào id
        String str = "";
        try{
            List<List<Object>> anh = db1.executeQuery("select hinh from CHI_TIET_LOAI_PHONG where ma_loai_phong = " + id + " limit 1;");
            for(List<Object> row: anh){
                str = row.get(0).toString();
            }
        }catch (Exception e){
            e.getMessage();
        }
        if(!str.isEmpty()){
            return db1.getDrawableResourceUrl(context, str);
        }else{
            return db1.getDrawableResourceUrl(context, "ad_anh_clone_phong");
        }
    }
}
