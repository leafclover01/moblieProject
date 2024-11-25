package com.example.appbooking.Database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appbooking.Model.ChiTietUuDai
import com.example.appbooking.Model.TaiKhoan
import tech.turso.libsql.Database
import tech.turso.libsql.Libsql

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

//    fun insertCoTienNghi(maLoaiPhong: Int, maTienNghi: Int) {
//        val sql = "INSERT INTO CO_TIEN_NGHI VALUES ($maTienNghi, $maLoaiPhong);"
//        executeQuery(sql)
//    }
//
//    fun insertDataTaiKhoan(id: Int?, username: String, password: String, name: String, email: String, sdt: String, cccd: String, address: String, tenAnh: String) {
//        val sql = """
//        INSERT INTO TAI_KHOAN
//        VALUES ($id, '$username', '$password', '$name', '$email', '$sdt', '$cccd', '$address', 0, '$tenAnh')
//        ON CONFLICT(username) DO NOTHING;
//    """
//        executeQuery(sql)
//    }
//
    fun insertDataUuDai(maNhanVien: Int, ngayBatDau: String, ngayHetHan: String, giam: Double, dieuKienVeGia: Int) {
        val sql = """
                INSERT INTO UU_DAI ( ma_uu_dai, ma_nhan_vien, ngay_bat_dau, ngay_het_han, giam, dieu_kien_ve_gia) VALUES
                (null, $maNhanVien, '$ngayBatDau', '$ngayHetHan', '$giam', '$dieuKienVeGia');
                """
        db.connect().query(sql)
    }
//
//    fun insertChiTietUuDai(maUuDai: Int, hinh: String) {
//        val sql = "INSERT INTO CHI_TIET_UU_DAI VALUES (NULL, $maUuDai, '$hinh');"
//        executeQuery(sql)
//    }
//
//    fun insertApMa(maDon: Int, maUuDai: Int) {
//        val sql = "INSERT INTO AP_MA VALUES ($maDon, $maUuDai);"
//        executeQuery(sql)
//    }
//
//    fun insertTienNghi(maTienNghi: Int?, tenTienNghi: String, ic_mo_ta: String) {
//        val sql = """
//        INSERT INTO TIEN_NGHI
//        VALUES ($maTienNghi, '$tenTienNghi', '$ic_mo_ta')
//        ON CONFLICT(ma_tien_nghi) DO NOTHING;
//    """
//        executeQuery(sql)
//    }
//
    fun insertDataLoaiPhong(ten: String, gia: Int, soNguoiToiDa: Int, moTa: String) {
        val sql = """
            INSERT INTO LOAI_PHONG (ma_loai_phong, ten, gia, so_nguoi_toi_da, mo_ta)
            VALUES (NULL, '$ten', $gia, $soNguoiToiDa, '$moTa');
            """
        db.connect().execute(sql)
    }

    fun insertDataPhong(viTri: String, maLoaiPhong: Int) {
        val sql = """
            INSERT INTO PHONG (ma_phong, vi_tri, ma_loai_phong)
            VALUES (NULL, '$viTri', $maLoaiPhong);
        """
        db.connect().execute(sql)
    }

    fun insertDataDon(ma_nguoi_dat: Int, ngayLapPhieu: String, checkIn: String) {
        val sql = """
            INSERT INTO DON (ma_don, check_in, ma_nguoi_dat, ngay_lap_phieu)
            VALUES (NULL, '$checkIn', $ma_nguoi_dat, '$ngayLapPhieu');
        """
        db.connect().execute(sql)
    }

    fun insertDataThue(maDon: Int, maPhong: Int, checkOut: String) {
        val sql = "INSERT INTO THUE VALUES ($maDon, $maPhong, '$checkOut');"
        db.connect().execute(sql)
    }

    fun insertDataChiTietLoaiPhong(maLoaiPhong: Int, hinh: String) {
        val sql = """
                INSERT INTO CHI_TIET_LOAI_PHONG (id, ma_loai_phong, hinh)
                VALUES (NULL, $maLoaiPhong, '$hinh');
            """
        db.connect().execute(sql)
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

    fun getDrawableResourceUrl(context: Context, imageName: String): String? {
        val resourceId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        return if (resourceId != 0) {
            "android.resource://${context.packageName}/$resourceId"
        } else {
            null
        }
    }

}
