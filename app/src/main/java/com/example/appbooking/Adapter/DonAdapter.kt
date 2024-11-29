package com.example.appbooking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.appbooking.Database.MySQLite
import com.example.appbooking.Model.Don
import com.example.appbooking.R

class DonAdapter(
    context: Context,
    private val resource: Int,
    private var listDon: ArrayList<Don>
) : ArrayAdapter<Don>(context, resource, listDon) {
    val mySQLite = MySQLite()

    override fun getCount(): Int {
        return listDon.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        // Bind views
        val tvLoaiPhongDat = view.findViewById<TextView>(R.id.tvLoaiPhongDat)
        val tvMaDon = view.findViewById<TextView>(R.id.tvMaDon)
        val tvTongSoTien = view.findViewById<TextView>(R.id.tvTongSoTien)
        val btnDanhGia = view.findViewById<Button>(R.id.btnDanhGia)
        val btnChiTiet = view.findViewById<Button>(R.id.btnChiTiet)

        // Get the current Don
        val don = listDon[position]

        // Set data to views
        tvLoaiPhongDat.text = mySQLite.traVeLoaiPhongTuMaDon(don.maDon)
        tvMaDon.text = "Mã đơn: ${don.maDon}"



        return view
    }
}
