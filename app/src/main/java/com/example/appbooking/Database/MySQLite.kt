package com.example.appbooking.Database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appbooking.Model.ChiTietUuDai
import com.example.appbooking.Model.TienNghi

import com.example.booking.Model.TaiKhoan
import java.util.Date

import java.text.SimpleDateFormat


class MySQLite(context: Context, dbName: String, cursorFactory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, dbName, cursorFactory, version) {

    companion object {
        const val DATABASE_NAME = "Booking.sql"
    }

    init {
        // create table
        createTable("TAI_KHOAN", """
            id INTEGER PRIMARY KEY AUTOINCREMENT, 
            username VARCHAR(255), password VARCHAR(255), 
            name VARCHAR(255), email VARCHAR(255), sdt VARCHAR(255), 
            cccd VARCHAR(255), address VARCHAR(255), role INTEGER, 
            hinh VARCHAR(255)
        """)

        createTable("LOAI_PHONG", """
            ma_loai_phong INTEGER PRIMARY KEY AUTOINCREMENT, 
            ten VARCHAR(255), 
            gia INTEGER, 
            so_nguoi_toi_da INTEGER, 
            mo_ta text
        """)

        createTable("PHONG", """
            ma_phong INTEGER PRIMARY KEY AUTOINCREMENT, 
            vi_tri VARCHAR(255),
            ma_loai_phong integer,
            FOREIGN KEY (ma_loai_phong) REFERENCES LOAI_PHONG(ma_loai_phong)
        """)

        createTable("CHI_TIET_LOAI_PHONG", """
            id INTEGER PRIMARY KEY AUTOINCREMENT, 
            ma_loai_phong INTEGER, 
            hinh VARCHAR(255), 
            FOREIGN KEY (ma_loai_phong) REFERENCES LOAI_PHONG(ma_loai_phong)
        """)

        createTable("TIEN_NGHI", """
            ma_tien_nghi integer primary key autoincrement,
            ten_tien_nghi varchar(255),
            ic_mo_ta varchar(255)
        """)

        createTable("CO_TIEN_NGHI", """
            ma_tien_nghi INTEGER, 
            ma_loai_phong INTEGER, 
            PRIMARY KEY (ma_tien_nghi, ma_loai_phong), 
            FOREIGN KEY (ma_tien_nghi) REFERENCES TIEN_NGHI(ma_tien_nghi), 
            FOREIGN KEY (ma_loai_phong) REFERENCES LOAI_PHONG(ma_loai_phong)
        """)

        createTable("DON", """
            ma_don INTEGER PRIMARY KEY AUTOINCREMENT, 
            check_in DATE, 
            ma_nguoi_dat INTEGER, 
            ngay_lap_phieu DATE, 
            FOREIGN KEY (ma_nguoi_dat) REFERENCES TAI_KHOAN(id)
        """)

        createTable("THUE", """
            ma_don INTEGER, 
            ma_phong INTEGER, 
            check_out DATE, 
            PRIMARY KEY (ma_don, ma_phong), 
            FOREIGN KEY (ma_don) REFERENCES DON(ma_don), 
            FOREIGN KEY (ma_phong) REFERENCES PHONG(ma_phong)
        """)

        createTable("HOA_DON", """
            ma_hoa_don INTEGER PRIMARY KEY AUTOINCREMENT, 
            ma_don INTEGER UNIQUE, 
            ngay_thanh_toan DATE, 
            so_tien_thanh_toan INTEGER, 
            FOREIGN KEY (ma_don) REFERENCES DON(ma_don)
        """)

        createTable("CHI_TIET_DANH_GIA", """
            ma_danh_gia INTEGER PRIMARY KEY AUTOINCREMENT, 
            ma_don INTEGER UNIQUE, 
            ngay_danh_gia DATE, 
            danh_gia_chat_luong_phong INTEGER, 
            danh_gia_sach_se INTEGER, 
            danh_gia_nhan_vien_phuc_vu INTEGER, 
            danh_gia_tien_nghi INTEGER, 
            mo_ta_chi_tiet VARCHAR, 
            FOREIGN KEY (ma_don) REFERENCES DON(ma_don)
        """)

        createTable("QUAN_LY", """
            ma_nhan_vien INTEGER, 
            ma_don INTEGER, 
            is_thanh_toan BOOLEAN, 
            is_duyet BOOLEAN, 
            is_dat_coc BOOLEAN, 
            PRIMARY KEY (ma_don, ma_nhan_vien), 
            FOREIGN KEY (ma_don) REFERENCES DON(ma_don), 
            FOREIGN KEY (ma_nhan_vien) REFERENCES TAI_KHOAN(id)
        """)

        createTable("UU_DAI", """
            ma_nhan_vien INTEGER, 
            ma_uu_dai INTEGER PRIMARY KEY AUTOINCREMENT, 
            ngay_bat_dau DATE, 
            ngay_het_han DATE, 
            giam DECIMAL(10, 2), 
            dieu_kien_ve_gia INTEGER, 
            FOREIGN KEY (ma_nhan_vien) REFERENCES TAI_KHOAN(id)
        """)

        createTable("CHI_TIET_UU_DAI", """
            id INTEGER PRIMARY KEY AUTOINCREMENT, 
            ma_uu_dai INTEGER, 
            hinh VARCHAR(255), 
            FOREIGN KEY (ma_uu_dai) REFERENCES UU_DAI(ma_uu_dai)
        """)

        createTable("AP_MA", """
            ma_don INTEGER, 
            ma_uu_dai INTEGER, 
            PRIMARY KEY (ma_don, ma_uu_dai), 
            FOREIGN KEY (ma_don) REFERENCES DON(ma_don), 
            FOREIGN KEY (ma_uu_dai) REFERENCES UU_DAI(ma_uu_dai)
        """)

        // insert data
       if(!isTableHasData("TAI_KHOAN")){
           insertDataTaiKhoan(1,"admin", "123456","Nguyễn Văn A", "quantri@gmail.com", "0123456789", "001204014888", "Hà Nội", "ic_avt")
           insertDataTaiKhoan(2,"user1", "123456","Trần Văn B", "nhanvien@gmail.com", "0123456989", "001204015888", "Hà Nội", "ic_avt")
           insertDataTaiKhoan(3,"user2","123456","Trần Văn C", "nhanvien1@gmail.com", "0123456988", "001204015488", "Hà Nội", "ic_avt")
           insertDataTaiKhoan(null,"user3","123456","Trần Văn D", "nhanvien3@gmail.com", "0121456988", "011204015488", "Hà Nội", "ic_avt")
           insertDataTaiKhoan(null,"admin2", "123456","Nguyễn Văn B", "quantri1@gmail.com", "0122456789", "001204024888", "Hà Nội", "ic_avt")
           // test th loi
           insertDataTaiKhoan(null,"user2", "123456","Trần Văn B", "nhanvien@gmail.com", "0123456989", "001204015888", "Hà Nội", "ic_avt")
           insertDataTaiKhoan(3,"user000","123456","Trần Văn C", "nhanvien1@gmail.com", "0123456988", "001204015488", "Hà Nội", "ic_avt")
       }

        if(!isTableHasData("LOAI_PHONG")){
            insertDataLoaiPhong(1,"Phòng Standard", 2000000, 3, """
            Loại phòng Vip 1
            """)
            insertDataLoaiPhong(2,"Phòng Superior", 2043000, 3, """
            Loại phòng Vip 2
            """)
            insertDataLoaiPhong(3,"Phòng Deluxe", 7043000, 3, """
            Loại phòng Vip 3
            """)
            insertDataLoaiPhong(4,"Phòng Suite", 10043000, 3, """
            Loại phòng Vip 4
            """)
            insertDataLoaiPhong(null,"Phòng Deluxe 2", 8043000, 3, """
            Loại phòng Vip 3+
            """)
            // loi
            insertDataLoaiPhong(3,"Phòng Suite 2", 10043000, 3, """
            Loại phòng Vip 5
            """)
        }

        if(!isTableHasData("PHONG")){
            insertDataPhong(1,"P101", 1)
            insertDataPhong(2,"P102", 1)
            insertDataPhong(3,"P103", 1)
            insertDataPhong(4,"P201", 2)
            insertDataPhong(5,"P202", 2)
            insertDataPhong(6,"P203", 2)
            insertDataPhong(7,"P301", 3)
            insertDataPhong(8, "P302", 3)
            insertDataPhong(9,"P303", 3)
            insertDataPhong(10,"P401", 4)
            insertDataPhong(11,"P402", 4)
            insertDataPhong(12,"P403", 4)
            insertDataPhong(null,"P501", 4)
            //erorr
            insertDataPhong(12,"P500", 4)
        }

        if(!isTableHasData("CHI_TIET_LOAI_PHONG")){
            insertDataChiTietLoaiPhong(1, "v1_1"); // v1 vip 1
            insertDataChiTietLoaiPhong(1, "v1_2");
            insertDataChiTietLoaiPhong(1, "v1_3");
            insertDataChiTietLoaiPhong(2, "v2_1");
            insertDataChiTietLoaiPhong(2, "v2_2");
            insertDataChiTietLoaiPhong(2, "v2_3");
            insertDataChiTietLoaiPhong(3, "v3_1");
            insertDataChiTietLoaiPhong(3, "v3_2");
            insertDataChiTietLoaiPhong(3, "v3_3");
            insertDataChiTietLoaiPhong(4, "v4_1");
            insertDataChiTietLoaiPhong(4, "v4_2");
            insertDataChiTietLoaiPhong(4, "v4_3");
        }

        if(!isTableHasData("DON")){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            insertDataDon(1,2, dateFormat.parse("2024-11-23 12:00"), dateFormat.parse("2024-11-27 12:00"))
            insertDataDon(2,3, dateFormat.parse("2024-11-24 11:00"), dateFormat.parse("2024-11-25 15:00"))
            insertDataDon(3,3, dateFormat.parse("2024-11-10 13:00"), dateFormat.parse("2024-12-24 17:00"))
            insertDataDon(4,2, dateFormat.parse("2024-11-11 9:40"), dateFormat.parse("2024-11-20 8:00"))

            // loi
            insertDataDon(4,3, dateFormat.parse("2024-11-11 9:40"), dateFormat.parse("2024-11-20 8:00"))
        }

        if(!isTableHasData("THUE")){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            insertDataThue(1, 1, dateFormat.parse("2024-11-28 9:00"))
            insertDataThue(2, 2, dateFormat.parse("2024-11-26 13:00"))
            insertDataThue(3, 1, dateFormat.parse("2024-12-26 7:00"))
            insertDataThue(4, 11, dateFormat.parse("2024-11-24 9:00"))
        }

        if(!isTableHasData("UU_DAI")){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            insertDataUuDai(100000,1, dateFormat.parse("2024-11-17 9:00"), dateFormat.parse("2024-12-30 9:00"), 0.1, 0)
            insertDataUuDai(100001,1, dateFormat.parse("2024-11-1 9:00"), dateFormat.parse("2024-11-15 9:00"), 0.15, 0)
            insertDataUuDai(null,1, dateFormat.parse("2024-12-01 9:00"), dateFormat.parse("2024-12-15 9:00"), 0.2, 4000000)
            // loi
            insertDataUuDai(100001,1, dateFormat.parse("2024-11-1 9:00"), dateFormat.parse("2024-11-15 9:00"), 0.5, 0)
        }

        if(!isTableHasData("CHI_TIET_UU_DAI")){
            insertChiTietUuDai(100000,"notfound")
            insertChiTietUuDai(100001,"notfound")
        }

        if(!isTableHasData("AP_MA")){
            insertApMa(1, 100000)
            insertApMa(2, 100000)
            insertApMa(3, 100001)
            insertApMa(4, 100001)
        }

        if(!isTableHasData("TIEN_NGHI")){
            insertTienNghi(1,"Wifi miễn phí", "ic_wifi") //1
            insertTienNghi(2,"Hồ bơi", "ic_water") //2
            insertTienNghi(3,"Để xe miễn phí", "ic_local_park") //3
            insertTienNghi(4,"Đưa đón", "ic_taxi") //4
            insertTienNghi(5,"Truyền thông - TV", "ic_tv") //5
        }
        if(!isTableHasData("CO_TIEN_NGHI")){
            insertCoTienNghi(1, 1)
            insertCoTienNghi(1, 3)

            insertCoTienNghi(2, 1)
            insertCoTienNghi(2, 3)
            insertCoTienNghi(2, 5)

            insertCoTienNghi(3, 1)
            insertCoTienNghi(3, 3)
            insertCoTienNghi(3, 5)
            insertCoTienNghi(3, 2)

            insertCoTienNghi(4, 1)
            insertCoTienNghi(4, 2)
            insertCoTienNghi(4, 3)
            insertCoTienNghi(4, 4)
            insertCoTienNghi(4, 5)
        }

    }

    fun querySQL(sql: String) {
        writableDatabase.execSQL(sql)
    }

    fun getDataFromSQL(sql: String): Cursor {
        return readableDatabase.rawQuery(sql, null)
    }

    fun createTable(tableName: String, columns: String) {
        querySQL("CREATE TABLE IF NOT EXISTS $tableName ($columns);")
    }
    fun insertCoTienNghi(maLoaiPhong: Int, maTienNghi: Int) {
        val sql = """
                INSERT INTO CO_TIEN_NGHI
                VALUES ($maTienNghi, $maLoaiPhong);
                """
        querySQL(sql)
    }
    fun insertDataTaiKhoan(id: Int?, username: String, password: String, name: String, email: String, sdt: String, cccd: String, address: String, tenAnh: String) {
        val cursor = getDataFromSQL("SELECT COUNT(id) FROM TAI_KHOAN WHERE username = '$username';")
        if (cursor.moveToNext() && cursor.getInt(0) <= 0) {
            try {
                val sql = """
                    INSERT INTO TAI_KHOAN 
                    VALUES ($id, '$username', '$password', '$name', '$email', '$sdt', '$cccd', '$address', 0, '$tenAnh');
                    """
                querySQL(sql)
            } catch (e: Exception) {
                println("Lỗi xảy ra khi chèn dữ liệu: ${e.message}")
            }
        }
    }
    fun insertDataUuDai(id: Int?, maNhanVien: Int, ngayBatDau: java.util.Date?, ngayHetHan: java.util.Date?, giam: Double, dieuKienVeGia: Int) {
//            var id_max: Int = 100000
//            var cursor = getDataFromSQL("SELECT MAX(ma_uu_dai) from UU_DAI")
//            while (cursor.moveToNext()){
//                id_max = if (cursor.getInt(0) >= id_max) {
//                    cursor.getInt(0) + 1
//                } else {
//                    id_max
//                }
//            }
//            val sql = """
//                INSERT INTO UU_DAI
//                VALUES ($maNhanVien, $id_max, '$ngayBatDau', '$ngayHetHan', $giam, $dieuKienVeGia);
//                """
//            querySQL(sql)
        val cursor = getDataFromSQL("SELECT COUNT(*) FROM UU_DAI WHERE ma_uu_dai = '$id';")
        if (cursor.moveToNext() && cursor.getInt(0) <= 0) {
            val sql = """
                INSERT INTO UU_DAI 
                VALUES ($maNhanVien, $id, '$ngayBatDau', '$ngayHetHan', $giam, $dieuKienVeGia);
                """
            querySQL(sql)
        }
    }
    fun insertChiTietUuDai(maUuDai: Int, hinh: String){
        val sql = """
                INSERT INTO CHI_TIET_UU_DAI 
                VALUES (NULL, $maUuDai, '$hinh');
                """
        querySQL(sql)
    }
    fun insertApMa(maDon: Int, maUuDai: Int){
        val sql = """
                INSERT INTO AP_MA 
                VALUES ($maDon, $maUuDai);
                """
        querySQL(sql)
    }
    fun insertTienNghi(maTienNghi: Int?, tenTienNghi: String, ic_mo_ta: String) {
        val cursor = getDataFromSQL("SELECT COUNT(*) FROM TIEN_NGHI WHERE ma_tien_nghi = $maTienNghi;")
        if (cursor.moveToNext() && cursor.getInt(0) <= 0) {
            val sql = """
                INSERT INTO TIEN_NGHI
                VALUES ($maTienNghi, "$tenTienNghi", "$ic_mo_ta");
                """
            querySQL(sql)
        }
    }
    fun insertDataLoaiPhong(maLoaiPhong: Int?, ten: String, gia: Int, soNguoiToiDa: Int, moTa: String) {
        val cursor = getDataFromSQL("SELECT COUNT(*) FROM LOAI_PHONG WHERE ma_loai_phong = '$maLoaiPhong';")
        if (cursor.moveToNext() && cursor.getInt(0) <= 0) {
            val sql = """
                INSERT INTO LOAI_PHONG 
                VALUES ($maLoaiPhong, '$ten', $gia, $soNguoiToiDa, '$moTa');
                """
            querySQL(sql)
        }
    }

    fun insertDataPhong(maPhong: Int?, viTri: String, maLoaiPhong: Int) {
        val cursor = getDataFromSQL("SELECT COUNT(*) FROM PHONG WHERE ma_phong = '$maPhong';")
        if (cursor.moveToNext() && cursor.getInt(0) <= 0) {
            val sql = """
                INSERT INTO PHONG 
                VALUES ($maPhong, '$viTri', $maLoaiPhong);
                """
            querySQL(sql)
        }
    }

    fun insertDataDon(maDon: Int?, ma_nguoi_dat: Int, ngayLapPhieu: java.util.Date?, checkIn: java.util.Date?) {
        val cursor = getDataFromSQL("SELECT COUNT(*) FROM DON WHERE ma_don = '$maDon';")
        if (cursor.moveToNext() && cursor.getInt(0) <= 0) {
            val sql = """
                INSERT INTO DON
                VALUES ($maDon, '$checkIn', $ma_nguoi_dat, '$ngayLapPhieu');
                """
            querySQL(sql)
        }
    }

    fun insertDataThue(maDon: Int, maPhong: Int, checkOut: java.util.Date?) {
        val sql = """
                INSERT INTO THUE
                VALUES ($maDon, $maPhong, '$checkOut');
                """
        querySQL(sql)
    }

    fun insertDataChiTietDanhGia(maDon: Int,
                                 danhGiaChatLuongPhong: Int,
                                 danhGiaSachSe: Int,
                                 danhGiaNhanVienPhucVu: Int,
                                 danhGiaTienNghi: Int,
                                 moTaChiTiet: String,
                                 ngayDanhGia: java.util.Date?
                                 ) {
        val sql = """
                INSERT INTO CHI_TIET_DANH_GIA
                VALUES (null, $maDon, '$ngayDanhGia', $danhGiaChatLuongPhong,
                        $danhGiaSachSe, $danhGiaNhanVienPhucVu, $danhGiaTienNghi, $moTaChiTiet
                );
                """
        querySQL(sql)
    }


    fun insertDataChiTietLoaiPhong(maLoaiPhong: Int, hinh: String){
        val cursor = getDataFromSQL("SELECT COUNT(*) FROM CHI_TIET_LOAI_PHONG WHERE ma_loai_phong = $maLoaiPhong and hinh = '$hinh';")

        if (cursor.moveToNext() && cursor.getInt(0) <= 0) {
            val sql = """
                INSERT INTO CHI_TIET_LOAI_PHONG 
                VALUES (NULL, $maLoaiPhong, '$hinh');
                """
            querySQL(sql)
        }
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
            listTaiKhoan.add(
                TaiKhoan(
                cursor.getInt(0), cursor.getInt(8), cursor.getString(1), cursor.getString(2),
                cursor.getString(4), cursor.getString(7), cursor.getString(5), cursor.getString(6),
                cursor.getString(3), cursor.getString(9)
                )
            )
        }
        return listTaiKhoan
    }

    fun tinhSoTienThanhToan(maDon: Int){

    }
    fun isTableExists(tableName: String): Boolean {
        val query = "SELECT name FROM sqlite_master WHERE type='table' AND name='$tableName'"
        val cursor = getDataFromSQL(query)
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun isTableHasData(tableName: String): Boolean {
        if (!isTableExists(tableName)) return false
        val query = "SELECT COUNT(*) FROM $tableName"
        val cursor = getDataFromSQL(query)
        var hasData = false
        if (cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            hasData = count > 0
        }
        cursor.close()
        return hasData
    }
    fun getDrawableResourceUrl(context: Context, imageName: String): String? {
        val resourceId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        return if (resourceId != 0) {
            "android.resource://${context.packageName}/$resourceId"
        } else {
            null
        }
    }
    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}
