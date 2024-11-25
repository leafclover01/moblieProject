package com.example.appbooking.Database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appbooking.Model.ChiTietUuDai
import com.example.appbooking.Model.TaiKhoan
import tech.turso.libsql.Database
import tech.turso.libsql.Libsql
import tech.turso.libsql.Row

import java.util.Date

import java.text.SimpleDateFormat
import java.util.ArrayList


class MySQLite {
    var db: Database

    init {
        db = Libsql.open(
            url = "libsql://dbtest-haitrn.turso.io",
            authToken = "eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MzI1MTY0MjEsImlkIjoiYjI3M2E5MzQtZjZjZi00MTI3LTk3MTUtMTI0MDYwZTUwZDY4In0.6oocORFkIYkFD47on365JbRwDoT6dmyhC4VPqYTqhUeO7V5gUSviOCCx2bjI6tdctWqmu9L2oxddNn1kY5hKDg"
        )
    }

    fun insertDataTaiKhoan(username: String, password: String, name: String, email: String, sdt: String, cccd: String, address: String, role: Int, tenAnh: String): String {
        val query = "Select * from TAI_KHOAN where username = '$username';"
        val rows = db.connect().query(query)
        if(rows.firstOrNull() == null){
            return try {
                val sql = """
                    INSERT INTO TAI_KHOAN (id, username, password, name, email, sdt, cccd, address, role, hinh)
                    VALUES (NULL, '$username', '$password', '$name', '$email', '$sdt', '$cccd', '$address', $role, '$tenAnh');
                """
                db.connect().query(sql)
                "Thêm thành công"
            } catch (e: Exception) {
                "Thêm không thành công: ${e.message}"
            }
        }
        return "Tài khoản đã tồn tại"
    }

    fun insertCoTienNghi(maLoaiPhong: Int, maTienNghi: Int): String {
        return try {
            val sql = "INSERT INTO CO_TIEN_NGHI (ma_tien_nghi, ma_loai_phong) VALUES ($maTienNghi, $maLoaiPhong);"
            db.connect().query(sql)
            "Thêm thành công"
        } catch (e: Exception) {
            "Thêm không thành công: ${e.message}"
        }
    }

    fun insertDataUuDai(maNhanVien: Int, ngayBatDau: String, ngayHetHan: String, giam: Double, dieuKienVeGia: Int): String {
        return try {
            val sql = """
            INSERT INTO UU_DAI (ma_uu_dai, ma_nhan_vien, ngay_bat_dau, ngay_het_han, giam, dieu_kien_ve_gia)
            VALUES (null, $maNhanVien, '$ngayBatDau', '$ngayHetHan', '$giam', '$dieuKienVeGia');
        """
            db.connect().query(sql)
            "Thêm thành công"
        } catch (e: Exception) {
            "Thêm không thành công: ${e.message}"
        }
    }

    fun insertChiTietUuDai(maUuDai: Int, hinh: String): String {
        return try {
            val sql = "INSERT INTO CHI_TIET_UU_DAI (id, ma_uu_dai, hinh) VALUES (NULL, $maUuDai, '$hinh');"
            db.connect().execute(sql)
            "Thêm thành công"
        } catch (e: Exception) {
            "Thêm không thành công: ${e.message}"
        }
    }

    fun insertApMa(maDon: Int, maUuDai: Int): String {
        return try {
            val sql = "INSERT INTO AP_MA (ma_don, ma_uu_dai) VALUES ($maDon, $maUuDai);"
            db.connect().execute(sql)
            "Thêm thành công"
        } catch (e: Exception) {
            "Thêm không thành công: ${e.message}"
        }
    }

    fun insertTienNghi(tenTienNghi: String, ic_mo_ta: String): String {
        return try {
            val sql = """
            INSERT INTO TIEN_NGHI (ma_tien_nghi, ten_tien_nghi, ic_mo_ta)
            VALUES (null, '$tenTienNghi', '$ic_mo_ta');
        """
            db.connect().execute(sql)
            "Thêm thành công"
        } catch (e: Exception) {
            "Thêm không thành công: ${e.message}"
        }
    }

    fun insertDataLoaiPhong(ten: String, gia: Int, soNguoiToiDa: Int, moTa: String): String {
        return try {
            val sql = """
            INSERT INTO LOAI_PHONG (ma_loai_phong, ten, gia, so_nguoi_toi_da, mo_ta)
            VALUES (NULL, '$ten', $gia, $soNguoiToiDa, '$moTa');
        """
            db.connect().execute(sql)
            "Thêm thành công"
        } catch (e: Exception) {
            "Thêm không thành công: ${e.message}"
        }
    }

    fun insertDataPhong(viTri: String, maLoaiPhong: Int): String {
        return try {
            val sql = """
            INSERT INTO PHONG (ma_phong, vi_tri, ma_loai_phong)
            VALUES (NULL, '$viTri', $maLoaiPhong);
        """
            db.connect().execute(sql)
            "Thêm thành công"
        } catch (e: Exception) {
            "Thêm không thành công: ${e.message}"
        }
    }

    fun insertDataDon(ma_nguoi_dat: Int, ngayLapPhieu: String, checkIn: String): String {
        return try {
            val sql = """
            INSERT INTO DON (ma_don, check_in, ma_nguoi_dat, ngay_lap_phieu)
            VALUES (NULL, '$checkIn', $ma_nguoi_dat, '$ngayLapPhieu');
        """
            db.connect().execute(sql)
            "Thêm thành công"
        } catch (e: Exception) {
            "Thêm không thành công: ${e.message}"
        }
    }

    fun insertDataThue(maDon: Int, maPhong: Int, checkOut: String): String {
        return try {
            val sql = "INSERT INTO THUE VALUES ($maDon, $maPhong, '$checkOut');"
            db.connect().execute(sql)
            "Thêm thành công"
        } catch (e: Exception) {
            "Thêm không thành công: ${e.message}"
        }
    }

    fun insertDataChiTietLoaiPhong(maLoaiPhong: Int, hinh: String): String {
        return try {
            val sql = """
            INSERT INTO CHI_TIET_LOAI_PHONG (id, ma_loai_phong, hinh)
            VALUES (NULL, $maLoaiPhong, '$hinh');
        """
            db.connect().execute(sql)
            "Thêm thành công"
        } catch (e: Exception) {
            "Thêm không thành công: ${e.message}"
        }
    }



    fun kiemTraDangNhap(username: String, password: String): TaiKhoan{
        var taiKhoan = TaiKhoan()
        db.connect().use { conn ->
            val sql = "SELECT * FROM TAI_KHOAN WHERE username = '$username' AND password = '$password';"
            val rows = conn.query(sql)
            rows.forEach { row ->
                taiKhoan = TaiKhoan(
                    row.get(0).toString().toInt(),
                    row.get(8).toString().toInt(),
                    row.get(1).toString(),
                    row.get(2).toString(),
                    row.get(4).toString(),
                    row.get(7).toString(),
                    row.get(5).toString(),
                    row.get(6).toString(),
                    row.get(3).toString(),
                    row.get(9).toString()
                )
            }
        }
        return taiKhoan
    }

    fun getTaiKhoan(id: Int): TaiKhoan{
        var taiKhoan = TaiKhoan()
        db.connect().use { conn ->
            val sql = "SELECT * FROM TAI_KHOAN WHERE id='$id';"
            val rows = conn.query(sql)
            rows.forEach { row ->
                taiKhoan = TaiKhoan(
                    row.get(0).toString().toInt(),
                    row.get(8).toString().toInt(),
                    row.get(1).toString(),
                    row.get(2).toString(),
                    row.get(4).toString(),
                    row.get(7).toString(),
                    row.get(5).toString(),
                    row.get(6).toString(),
                    row.get(3).toString(),
                    row.get(9).toString()
                )
            }
        }
        return taiKhoan
    }
    fun layDanhSachTaiKhoan(): ArrayList<TaiKhoan>{
        var list = ArrayList<TaiKhoan>()
        db.connect().use { conn ->
            val sql = "SELECT * FROM TAI_KHOAN;"
            val rows = conn.query(sql)
            rows.forEach { row ->
                var taiKhoan : TaiKhoan = TaiKhoan(
                    row.get(0).toString().toInt(),
                    row.get(8).toString().toInt(),
                    row.get(1).toString(),
                    row.get(2).toString(),
                    row.get(4).toString(),
                    row.get(7).toString(),
                    row.get(5).toString(),
                    row.get(6).toString(),
                    row.get(3).toString(),
                    row.get(9).toString()
                )
                list.add(TaiKhoan())
            }
        }
        return list
    }
    fun executeQuery(sql: String): List<Row> {
        val resultList = mutableListOf<Row>()
        db.connect().use { conn ->
            val rows = conn.query(sql)
            rows.forEach { row ->
                // Thêm từng Row vào danh sách resultList
                resultList.add(row)
            }
        }
        return resultList
    }

    fun updateSQL(sql: String) {
        db.connect().execute(sql)
    }

    fun getDrawableResourceUrl(context: Context, imageName: String): String? {
        val resourceId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        return if (resourceId != 0) {
            "android.resource://${context.packageName}/$resourceId"
        } else {
            null
        }
    }

    fun executeQuery(sql: String): List<Row> {
        val resultList = mutableListOf<Row>()
        db.connect().use { conn ->
            val rows = conn.query(sql)
            rows.forEach { row ->
                // Thêm từng Row vào danh sách resultList
                resultList.add(row)
            }
        }
        return resultList
    }

    fun updateSQL(sql: String) {
        db.connect().execute(sql)
    }


}
