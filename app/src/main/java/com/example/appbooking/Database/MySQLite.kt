package com.example.appbooking.Database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appbooking.Model.ChiTietUuDai
import com.example.appbooking.Model.TaiKhoan
import com.example.appbooking.Model.LoaiPhong
import com.example.appbooking.Model.Phong
import com.example.appbooking.Model.Don
import tech.turso.libsql.Database
import tech.turso.libsql.Libsql
import tech.turso.libsql.Row

import java.util.Date

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoUnit
import java.util.Locale


import kotlin.time.Duration.Companion.days

class MySQLite {
    var db: Database

    init {
        db = Libsql.open(
            url = "libsql://booking-hotel-haitrn.turso.io",
            authToken = "eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MzI2MTkyMTksImlkIjoiMDgzMTMzOTQtZmM5NS00NTlhLWI1YTktODQ0ODlhMzQ5OTg1In0.WjpH3E9Kj1zJ2EjDecqib53VpjbGBe1ynstH9Iorvonqo8jqfKvNLYb7Lpa9rk_2CGnaZZqAjotpFDKvPzuMBg"
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
    fun insertDataLoaiPhong(ten: String, gia: Int, soNguoiToiDa: Int, moTa: String, moTaChiTiet: String): String {
        return try {
            val sql = """
            INSERT INTO LOAI_PHONG (ma_loai_phong, ten, gia, so_nguoi_toi_da, mo_ta, mo_ta_chi_tiet)
            VALUES (NULL, '$ten', $gia, $soNguoiToiDa, '$moTa', '$moTaChiTiet');
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
    fun insertDataHoaDon(ma_don: Int, ngay_thanh_toan: String): String {

        return try {
            var tien: Any? = tinhGiaPhong(ma_don).get("tongTien")
            val sql = """
            INSERT INTO HOA_DON (ma_hoa_don, ma_don, ngay_thanh_toan, so_tien_thanh_toan)
            VALUES (NULL, $ma_don, '$ngay_thanh_toan', $tien);
        """
            db.connect().execute(sql)
            "Thêm thành công"
        } catch (e: Exception) {
            "Thêm không thành công: ${e} }"
        }
    }
    fun insertDataQuanLy(ma_nhan_vien: Int, ma_don: Int): String {
        return try {
            var is_ma_don = -1
            db.connect().use { conn ->
                var sql1 = """
                     select D.ma_don, H.ma_hoa_don from DON AS D
                            left join HOA_DON AS H ON D.ma_don = H.ma_don
                            where H.ma_hoa_don is null and D.ma_don = $ma_don;
                        """
                val rows = conn.query(sql1)
                rows.forEach { row ->
                        is_ma_don = row.get(0).toString().toInt()
                }
            }

            var sql = """
            INSERT INTO QUAN_LY (ma_nhan_vien, ma_don, is_thanh_toan, is_duyet)
            VALUES ($ma_nhan_vien, '$ma_don', ${ma_don != is_ma_don}, '0');
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
    fun formatDateTime(dateTime: String): String {   // dinh dang H -> HH
        val input = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm")
        val output = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val parsedDateTime = LocalDateTime.parse(dateTime, input)
        return parsedDateTime.format(output)
    }

    fun formatDateTime2(dateTime: String): String {
        val inputFormatter = DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm"))
            .toFormatter()

        return LocalDateTime.parse(dateTime, inputFormatter)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    }



    fun tinhGiaPhong(ma_don: Int): HashMap<String, Any> {
        var map = HashMap<String, Any>()
        var giaThuePhong: Double = 0.0
        var query = """
        SELECT D.ma_don, D.check_in, T.check_out, LP.gia, U.ngay_bat_dau, U.ngay_het_han, U.giam, U.dieu_kien_ve_gia
        FROM DON AS D 
            JOIN THUE AS T ON D.ma_don = T.ma_don 
            JOIN PHONG AS P ON P.ma_phong = T.ma_phong 
            JOIN LOAI_PHONG AS LP ON LP.ma_loai_phong = P.ma_loai_phong 
            LEFT JOIN AP_MA AS A ON A.ma_don = D.ma_don 
            LEFT JOIN UU_DAI AS U ON U.ma_uu_dai = A.ma_uu_dai
            WHERE D.ma_don = $ma_don;
    """
        var checkIn:String = ""
        var checkOut:String = ""
        var giaPhong = 0
        var ngayHetHan: String? = null
        var ngayBatDau: String? = null
        var giam: Double? = null
        var dieuKienVeGia: Int? = null

        db.connect().use { conn ->
            val rows = conn.query(query)
            rows.forEach { row ->
                checkIn = row.get(1).toString()
                checkOut = row.get(2).toString()
                giaPhong = row.get(3).toString().toInt()
                ngayHetHan = row.get(5)?.toString()
                ngayBatDau = row.get(4)?.toString()
                giam = row.get(6)?.toString()?.toDouble() ?: 0.0
                dieuKienVeGia = row.get(7)?.toString()?.toInt() ?: 0

            }
        }

        giaThuePhong = giaPhong * tinhSoDem(checkIn, checkOut).toDouble()
        var thueVAT = giaThuePhong*0.1 // thue vat 10%
        var tienGiam:Double = 0.0


        var tongTien = giaThuePhong + thueVAT - tienGiam
        map["giaThuePhong"] = giaThuePhong.toInt()
        map["thueVAT"] = thueVAT.toInt()
        map["tienGiam"] = tienGiam.toInt()
        map["tongTien"] = tongTien.toInt()
        map["soDem"] = tinhSoDem(checkIn, checkOut)
        return map
    }
    fun tinhSoDem(checkIn: String, checkOut: String): Long {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val checkInDate = LocalDate.parse(formatDateTime2(checkIn), formatter)
        val checkOutDate = LocalDate.parse(formatDateTime2(checkOut), formatter)
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate)
    }

    fun layDuLieuLoaiPhong(): ArrayList<LoaiPhong> {
        var ds = ArrayList<LoaiPhong>()
        db.connect().use { conn ->
            var sql = """
                Select * from LOAI_PHONG;
            """
            var rows = conn.query(sql)

            rows.forEach{ row ->
                ds.add(LoaiPhong(
                    row.get(0).toString().toInt(),
                    row.get(1).toString(),
                    row.get(2).toString().toInt(),
                    row.get(3).toString().toInt(),
                    row.get(4).toString(),
                    row.get(5).toString()
                ))
            }

        }
        return  ds
    }

    fun layDuLieuPhongTrong(checkIn: String, checkOut: String, maLoaiPhong: String): ArrayList<Phong> {
        var ds = ArrayList<Phong>()
        db.connect().use {conn ->
            var sql = """
                select * from PHONG
                    where ma_phong not in 
                        (SELECT P.ma_phong
                            FROM PHONG AS P
                            LEFT JOIN THUE AS T ON P.ma_phong = T.ma_phong
                            LEFT JOIN DON AS D ON D.ma_don = T.ma_don
                            LEFT JOIN QUAN_LY AS Q ON Q.ma_don = D.ma_don
                            WHERE (
                                (D.check_in <= $checkOut AND T.check_out >= $checkIn)
                                OR
                                (Q.check_in_thuc_te <= $checkOut AND Q.check_out_thuc_te >= $checkIn)
                                ) 
                            group by P.ma_phong)
                        And ma_loai_phong = $maLoaiPhong;
            """
            var rows = conn.query(sql).forEach{ row ->
                ds.add(Phong(
                    row.get(0).toString().toInt(),
                    row.get(1).toString(),
                    row.get(2).toString().toInt()
                ))
            }
        }
        return ds
    }

    fun layDuLieuCacAnhCuaLoaiPhong(maLoaiPhong: String): ArrayList<String>{
        var ds = ArrayList<String>()
        db.connect().use {conn ->
            var sql = """
                select * from PHONG
                    where ma_phong not in 
                        (SELECT hinh from LOAI_PHONG AS L
                            JOIN CHI_TIET_LOAI_PHONG AS C ON L.ma_loai_phong = C.ma_loai_phong
                            WHERE ma_loai_phong = $maLoaiPhong;
            """
            var rows = conn.query(sql).forEach{ row ->
                ds.add(
                    row.get(0).toString()
                )
            }
        }
        return ds
    }

    fun traVeLoaiPhongTuMaDon(ma_don: Int): String {
        var ds = ""
        db.connect().use {conn ->
            var sql = """
                select ten from PHONG as P
                    join LOAI_PHONG as L on P.ma_loai_phong = L.ma_loai_phong
                    join THUE as D on D.ma_phong = P.ma_phong
                    where D.ma_don = $ma_don
            """
            var rows = conn.query(sql).forEach{ row ->
                ds = row.get(0).toString()
            }
        }
        return ds
    }
    fun layDuLieuDonCuaUser(id: Int): ArrayList<Don> {
        var ds = ArrayList<Don>()
        db.connect().use {conn ->
            var sql = """
                    SELECT * from DON
                        WHERE ma_nguoi_dat = $id
            """
            var rows = conn.query(sql).forEach{ row ->
                ds.add(Don(
                    row.get(0).toString().toInt(),
                    parseDate(row.get(1).toString()),
                    row.get(2).toString().toInt(),
                    parseDate(row.get(3).toString())
                ))
            }
        }
        return ds
    }

    fun parseDate(dateStr: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return dateFormat.parse(dateStr)
    }
}
//    var maDon: Int
//    var ma_nguoi_dat: Int
//    var checkIn: Date? = null
//    var ngayLapPhieu: Date? = null
