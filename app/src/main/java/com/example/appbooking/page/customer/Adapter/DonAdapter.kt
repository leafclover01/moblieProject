package com.example.appbooking.page.customer.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.appbooking.Database.MySQLite
import com.example.appbooking.Model.ChiTietDanhGia
import com.example.appbooking.Model.Don
import com.example.appbooking.Model.TaiKhoan
import com.example.appbooking.R
import com.example.appbooking.page.customer.DanhGiaUserFragment
import tech.turso.libsql.toValue
import java.text.SimpleDateFormat
import java.util.Date
import android.widget.Toast
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
//        val tvLoaiPhongDat = view.findViewById<TextView>(R.id.tvLoaiPhongDat)
        val tvMaDon = view.findViewById<TextView>(R.id.tvMaDon)
//        val tvTongSoTien = view.findViewById<TextView>(R.id.tvTongSoTien)
        val btnDanhGia = view.findViewById<Button>(R.id.btnDanhGia)
        val btnChiTiet = view.findViewById<Button>(R.id.btnChiTiet)

        // Get the current Don
        val don = listDon[position]

        // Set data to views
        tvMaDon.text = "Mã đơn: ${don.maDon}"
        btnDanhGia.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("maDon", don.maDon.toString())

                val danhGiaUserFragment = DanhGiaUserFragment()
                danhGiaUserFragment.arguments = bundle

                val fragmentActivity = context as FragmentActivity
                val fragmentTransaction: FragmentTransaction = fragmentActivity.supportFragmentManager.beginTransaction()

                fragmentTransaction.replace(R.id.fragment_container, danhGiaUserFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
        }
        return view
    }



}


