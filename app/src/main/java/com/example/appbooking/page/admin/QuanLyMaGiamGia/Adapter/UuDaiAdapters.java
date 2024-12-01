package com.example.appbooking.page.admin.QuanLyMaGiamGia.Adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.Model.UuDai;
import com.example.appbooking.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UuDaiAdapters extends ArrayAdapter {
    MySQLite db = new MySQLite();
    Activity context;
    int resource;
    ArrayList<UuDai> listMaUuDai;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    public UuDaiAdapters(Activity context, int resource, ArrayList<UuDai> listMaUuDai) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.listMaUuDai = listMaUuDai;
    }

    @Override
    public int getCount() {
        return listMaUuDai.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView = inflater.inflate(this.resource, null);

        TextView tvTenMa = customView.findViewById(R.id.tvTenMaGG);
        TextView tvGiam = customView.findViewById(R.id.tvGiamGia);
        TextView tvTrangThai = customView.findViewById(R.id.tvTrangThai);
        ImageView imEdit = customView.findViewById(R.id.ivEdit);
        ImageView imDelete = customView.findViewById(R.id.ivDelete);

        UuDai uuDai = this.listMaUuDai.get(position);

        tvTenMa.setText(uuDai.getTenMa());
        tvGiam.setText(uuDai.getGiam() + "");

        // TODO : xu ly trang thai
        if (uuDai.getNgayHetHan() != null) {
            Date now = new Date();
            if (uuDai.getNgayHetHan().compareTo(now) <= 0) {
                tvTrangThai.setText("Hết Hạn");
                tvTrangThai.setTextColor(Color.RED);
            } else {
                tvTrangThai.setText("Còn Hạn");
                tvTrangThai.setTextColor(Color.GREEN);
            }
        } else {
            tvTrangThai.setText("Không xác định");
            tvTrangThai.setTextColor(Color.GRAY);
        }


        // Xử lý edit
        imEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : xu ly
            }
        });

        // Xử lý xóa
        imDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogthoat = new AlertDialog.Builder(getContext());
                dialogthoat.setTitle("Xóa mã giảm giá ? ");
                dialogthoat.setMessage("Vui lòng chọn :");
                dialogthoat.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            db.updateSQL("DELETE FROM UU_DAI WHERE id = " + uuDai.getMaUuDai() + ";");
                            ArrayList<UuDai> list = getall("SELECT * FROM UU_DAI;");
                            updateData(list);

                        }catch(Exception e){
                            Toast.makeText(context, "Lỗi khi xóa dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogthoat.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialogthoat.create().show();
            }
        });

        return  customView;
    }

    ArrayList<UuDai> getall(String sql){
        ArrayList<UuDai> listMa = new ArrayList<>();
        try{
            List<List<Object>> list = db.executeQuery(sql);
            for (List<Object> row : list) {
                UuDai uuDai = new UuDai();
                uuDai.setMaNhanVien(Integer.parseInt(row.get(0).toString()));
                uuDai.setMaUuDai(Integer.parseInt(row.get(1).toString()));
                uuDai.setTenMa(row.get(2).toString());
                try {
                    Date ngayBatDau = row.get(3) != null ? dateFormat.parse(row.get(3).toString()) : null;
                    uuDai.setNgayBatDau(ngayBatDau);
                } catch (ParseException e) {
                    uuDai.setNgayBatDau(null);
                }
                try {
                    Date ngayBatDau = row.get(4) != null ? dateFormat.parse(row.get(4).toString()) : null;
                    uuDai.setNgayHetHan(ngayBatDau);
                } catch (ParseException e) {
                    uuDai.setNgayHetHan(null);
                }
                uuDai.setGiam(Double.parseDouble(row.get(5).toString()));
                uuDai.setDieuKienVeGia(Integer.parseInt(row.get(6).toString()));
                listMa.add(uuDai);
            }
            return listMa;
        }catch(Exception e){
            return null;
        }
    }

    public void updateData(ArrayList<UuDai> newList) {
        if (newList != null && !newList.equals(this.listMaUuDai)) {
            this.listMaUuDai.clear();
            this.listMaUuDai.addAll(newList);
            notifyDataSetChanged();
        }
    }


}
