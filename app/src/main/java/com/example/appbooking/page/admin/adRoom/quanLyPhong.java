package com.example.appbooking.page.admin.adRoom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.Model.ChiTietLoaiPhong;
import com.example.appbooking.Model.LoaiPhong;
import com.example.appbooking.Model.Phong;
import com.example.appbooking.R;
import com.example.appbooking.page.admin.adRoom.Adapter.adApdaterPhong;
import com.example.appbooking.page.admin.adRoom.Adapter.detalPhong;
import com.example.appbooking.page.admin.adRoom.Component.AddRoom;
import com.example.appbooking.page.admin.adRoom.Component.addRoomType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class quanLyPhong extends AppCompatActivity {
    // Spinner
    Spinner ad_dkl1;
    Spinner ad_dkl2;
    ArrayList<detalPhong> ldPhong;
    ArrayAdapter<String> adapter;
    // ImageButton
    ImageButton ad_btnLoad;
    adApdaterPhong adapP;
    // ListView
    ListView ad_listViewRT;
    MySQLite db1 = new MySQLite();
    // Button
    Button ad_themP;
    Button ad_themLP;
    ArrayList<String> items;
    HashMap<String, ArrayList<String>> subCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_phong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}