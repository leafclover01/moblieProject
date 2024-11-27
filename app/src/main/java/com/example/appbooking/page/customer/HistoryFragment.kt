package com.example.appbooking.page.customer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.appbooking.Adapter.DonAdapter
import com.example.appbooking.Database.MySQLite
import com.example.appbooking.Model.Don
import com.example.appbooking.R

class HistoryFragment : Fragment() {
    private val db1 = MySQLite()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        // Initialize the views
        val tvLoaiPhongDat = view.findViewById<TextView>(R.id.tvLoaiPhongDat)
        val tvMaDon = view.findViewById<TextView>(R.id.tvMaDon)
        val tvError = view.findViewById<TextView>(R.id.tvError)
        val tvTongSoTien = view.findViewById<TextView>(R.id.tvTongSoTien)
        val btnDanhGia = view.findViewById<Button>(R.id.btnDanhGia)
        val btnChiTiet = view.findViewById<Button>(R.id.btnChiTiet)
        val lvDon = view.findViewById<ListView>(R.id.lvDon)

        // Example of a user ID, can be dynamic based on your app's logic
        val id_user = 2

        // Fetch the data for the user
        val listDon: List<Don> = db1.layDuLieuDonCuaUser(id_user)

        // Set up the adapter for the ListView
        val donAdapter = DonAdapter(requireContext(), R.layout.user_item_history, listDon)
        lvDon.adapter = donAdapter



        return view
    }
}