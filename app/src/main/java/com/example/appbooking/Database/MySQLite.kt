package com.example.appbooking.Database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.booking.Model.TaiKhoan
import com.example.appbooking.R

class MySQLite(context: Context, dbName: String, cursorFactory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, dbName, cursorFactory, version) {

    companion object {
        const val DATABASE_NAME = "Booking.sql"
        const val DEFAULT_PASSWORD = "123456"
    }

    init {
        createTable("TAI_KHOAN", """
            id INTEGER PRIMARY KEY AUTOINCREMENT, 
            username VARCHAR(255), password VARCHAR(255), 
            name VARCHAR(255), email VARCHAR(255), sdt VARCHAR(255), 
            cccd VARCHAR(255), address VARCHAR(255), role INTEGER, 
            hinh VARCHAR(255)
        """)

        createTable("PHONG", """
            ma_phong INTEGER PRIMARY KEY AUTOINCREMENT, 
            vi_tri VARCHAR(255), ma_loai_phong INTEGER, 
            FOREIGN KEY (ma_loai_phong) REFERENCES LOAI_PHONG(ma_loai_phong)
        """)

        createTable("LOAI_PHONG", """
            ma_loai_phong INTEGER PRIMARY KEY AUTOINCREMENT, 
            ten VARCHAR(255), gia INTEGER, 
            so_nguoi_toi_da INTEGER, mo_ta VARCHAR(255)
        """)

        // Insert default users if not exist
        insertDefaultUser("admin", "Nguyễn Văn A", "quantri@gmail.com", "0123456789", "001204014888", "Hà Nội", "notfound")
        insertDefaultUser("user1", "Trần Văn B", "nhanvien@gmail.com", "0123456989", "001204015888", "Hà Nội", "notfound")
    }

    private fun createTable(tableName: String, columns: String) {
        querySQL("CREATE TABLE IF NOT EXISTS $tableName ($columns);")
    }

    private fun insertDefaultUser(username: String, name: String, email: String, sdt: String, cccd: String, address: String, tenAnh: String) {
        val cursor = getDataFromSQL("SELECT COUNT(id) FROM TAI_KHOAN WHERE username = '$username';")
        if (cursor.moveToNext() && cursor.getInt(0) <= 0) {
            val sql = """
            INSERT INTO TAI_KHOAN 
            VALUES (NULL, '$username', '$DEFAULT_PASSWORD', '$name', '$email', '$sdt', '$cccd', '$address', 0, 'android.resource://${R::class.java.`package`.name}/$tenAnh');
        """
            querySQL(sql)
        }
    }


    private fun querySQL(sql: String) {
        writableDatabase.execSQL(sql)
    }

    private fun getDataFromSQL(sql: String): Cursor {
        return readableDatabase.rawQuery(sql, null)
    }

    fun kiemTraDangNhap(tenDangNhap: String, matKhau: String): TaiKhoan {
        val cursor = getDataFromSQL("SELECT * FROM TAI_KHOAN WHERE username = '$tenDangNhap' AND password = '$matKhau'")
        return if (cursor.moveToNext()) {
            TaiKhoan(
                cursor.getInt(0), cursor.getInt(8), cursor.getString(1), cursor.getString(2),
                cursor.getString(4), cursor.getString(7), cursor.getString(5), cursor.getString(6),
                cursor.getString(3), cursor.getString(9)
            )
        } else {
            TaiKhoan()
        }
    }

    fun docDuLieuTaiKhoan(sql: String): ArrayList<TaiKhoan> {
        val listTaiKhoan = ArrayList<TaiKhoan>()
        val cursor = getDataFromSQL(sql)
        while (cursor.moveToNext()) {
            listTaiKhoan.add(TaiKhoan(
                cursor.getInt(0), cursor.getInt(8), cursor.getString(1), cursor.getString(2),
                cursor.getString(4), cursor.getString(7), cursor.getString(5), cursor.getString(6),
                cursor.getString(3), cursor.getString(9)
            ))
        }
        return listTaiKhoan
    }

    override fun onCreate(db: SQLiteDatabase?) {}

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}
