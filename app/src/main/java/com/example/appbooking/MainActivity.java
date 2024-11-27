package com.example.appbooking.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.Model.Don;
import com.example.appbooking.page.DashboardActivity;
import com.example.appbooking.Model.TaiKhoan;
import com.example.appbooking.page.admin.homeAdmin;
import com.example.appbooking.page.customer.HomeFragment;

import tech.turso.libsql.Database;
import tech.turso.libsql.Libsql;
import tech.turso.libsql.Rows;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // khai báo tạm thời
    EditText edtUsername, edtPassword;
    Button btnLogIn;
    MySQLite db;
    ImageView ivAnh;
    ArrayList<TaiKhoan> dstk = new ArrayList<>();
    TextView tvError;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        // Xử lý insets cho drawer layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /////////////////////// Test //////////////////////////////////////////////////////////
        db = new MySQLite();

//        db.insertDataDon(1, "2024-11-23 12:00", "2024-11-26 23:00");
//        TaiKhoan taiKhoan = new TaiKhoan();
//        String msg = db.insertDataHoaDon(4, "2024-11-11 9:40");
//        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

//        Long msg = db.tinhSoDem("2024-11-20 14:00", "2024-11-24 10:00");
        ArrayList<Don> msg = db.layDuLieuDonCuaUser(2);
//        HashMap<String, Object> map = db.tinhGiaPhong(4);
        tvError = findViewById(R.id.tvError);

        tvError.setText(msg.toString());


        String pathImg = db.getDrawableResourceUrl(MainActivity.this, "ic_avt");
        ivAnh = findViewById(R.id.ivAnh);
        ivAnh.setImageURI(Uri.parse(pathImg));


        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);


        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhap = edtUsername.getText().toString().trim();
                String matKhau = edtPassword.getText().toString().trim();
                if (tenDangNhap.length() > 0 && matKhau.length() > 0) {
                    TaiKhoan taiKhoan = db.kiemTraDangNhap(tenDangNhap, matKhau);
                    String msg = taiKhoan.getRole() == 0 ? "dung" : "sai";
//                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    if (taiKhoan.getId() >= 0 && taiKhoan.getRole() >= 0) {
                        if (taiKhoan.getRole() == 0) {
                            Intent intentQuanTri = new Intent(MainActivity.this, homeAdmin.class);
                            intentQuanTri.putExtra("taiKhoan", taiKhoan);
                            startActivity(intentQuanTri);
                        }
                        if (taiKhoan.getRole() == 1) {
                            Intent intentDatHang = new Intent(MainActivity.this, DashboardActivity.class);
                            intentDatHang.putExtra("taiKhoan", taiKhoan);
                            startActivity(intentDatHang);
                        }
                    } else Toast.makeText(MainActivity.this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(MainActivity.this, "Vui lòng nhập tài khoản và mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });


            /////////////////////// Test /////////////////////////////////////////////////////////


        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Thiết lập Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Thay đổi fragment mặc định
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Tự động thêm khoảng trống bên dưới thanh trạng thái
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setFitsSystemWindows(true);
    }

    // Menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        } else if (id == R.id.nav_settings) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SettingFragment())
                    .commit();
        } else if (id == R.id.nav_hotel) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new OrderHotelFragment())
                    .commit();
        } else if (id == R.id.nav_account) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AccountFragment())
                    .commit();
        } else if (id == R.id.nav_history_survey) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HistoryFragment())
                    .commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}