package com.example.appbooking.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbooking.Database.MySQLite;
import com.example.appbooking.MainActivity;
import com.example.appbooking.R;

public class SignUpActivity extends AppCompatActivity {
    EditText edtName, edtUsername, edtPassword, edtConfirmPassword;
    ImageView ivTogglePassword, ivToggleConfirmPassword;
    Button btnSignUp;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isPasswordVisible = false;
    MySQLite db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        //Khởi tạo
        edtName = findViewById(R.id.edtName);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
//        imgLogout = findViewById(R.id.imgLogout);
        ivTogglePassword = findViewById(R.id.ivTogglePassword);
        ivToggleConfirmPassword = findViewById(R.id.ivToggleConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Khởi tạo SharedPreferences
        db = new MySQLite();
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Sự kiện ẩn/hiện mật khẩu cho edtPassword
        ivTogglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivTogglePassword.setImageResource(R.drawable.hidden_password);
                } else {
                    edtPassword.setTransformationMethod(null);
                    ivTogglePassword.setImageResource(R.drawable.show_password);
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });

        // Sự kiện ẩn/hiện mật khẩu cho edtConfirmPassword
        ivToggleConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivToggleConfirmPassword.setImageResource(R.drawable.hidden_password);
                } else {
                    edtConfirmPassword.setTransformationMethod(null);
                    ivToggleConfirmPassword.setImageResource(R.drawable.show_password);
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });

        // Set sự kiện khi ấn Logout
//        imgLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.clear().apply();
//                Intent intetLogIn = new Intent(SignUpActivity.this, MainActivity.class);
//                startActivity(intetLogIn);
//                finish();
//            }
//        });

        // Set sự kiện khi ấn btnSignUp
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = edtName.getText().toString().trim();
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();

                // Các thông tin thêm khác (có thể để trống hoặc lấy từ input)
                String email = "example@gmail.com";  // Chỉnh lại với giá trị thực tế
                String sdt = "0123456789";
                String cccd = "123456789012";
                String address = "Địa chỉ mặc định";
                String tenAnh = "default.jpg";
                int role = 1;

                // Kiểm tra hợp lệ
                boolean isValid = true;

                if (fullName.isEmpty()) {
                    edtName.setError("Vui lòng điền đầy đủ họ tên!");
                    isValid = false;
                }
                if (username.isEmpty()) {
                    edtUsername.setError("Vui lòng điền tên đăng nhập!");
                    isValid = false;
                } else if (username.length() < 2) {
                    edtUsername.setError("Tên đăng nhập phải có ít nhất 2 ký tự!");
                    isValid = false;
                }
                if (password.isEmpty()) {
                    edtPassword.setError("Vui lòng điền mật khẩu");
                    isValid = false;
                } else if (password.length() < 6) {
                    edtPassword.setError("Mật khẩu phải có độ dài tối thiểu là 6 ký tự");
                    isValid = false;
                }
                if (confirmPassword.isEmpty()) {
                    edtConfirmPassword.setError("Vui lòng nhập lại mật khẩu");
                    isValid = false;
                } else if (!confirmPassword.equals(password)) {
                    edtConfirmPassword.setError("Mật khẩu không khớp");
                    isValid = false;
                }

                if (isValid) {
                    // Gọi phương thức thêm tài khoản từ MySQLite
                    String result = db.insertDataTaiKhoan(username, password, fullName, email, sdt, cccd, address, role, tenAnh);

                    // Hiển thị thông báo với Toast
                    Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_SHORT).show();

                    if (result.equals("Thêm thành công")) {
                        // Tùy chọn: Chuyển về MainActivity sau khi thêm thành công
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });


    }

    // Kiểm tra sự tồn tại của tài khoản, CCCD hoặc số điện thoại
    private boolean isAccountExists(String value, String field) {
        String existingValue = sharedPreferences.getString(field, null);
        return existingValue != null && existingValue.equals(value);
    }

    // Lưu thông tin tài khoản vào SharedPreferences
    private void saveAccountToSharedPreferences(String username, String fullName, String password) {
        editor.putString("username", username);
        editor.putString("fullName", fullName);
        editor.putString("password", password);
        editor.apply();

        // Chuyển tới MainActivity sau khi đăng ký thành công
        Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();  // Đảm bảo đóng màn hình đăng ký sau khi chuyển
    }
}
