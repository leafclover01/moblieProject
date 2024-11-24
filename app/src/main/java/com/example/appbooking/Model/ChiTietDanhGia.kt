package com.example.appbooking.Model

import java.io.Serializable
import java.sql.Date


class ChiTietDanhGia : Serializable {
    var maDanhGia: Int
    var maDon: Int
    var danhGiaChatLuongPhong: Int = 0
    var danhGiaSachSe: Int = 0
    var danhGiaNhanVienPhucVu: Int = 0
    var danhGiaTienNghi: Int = 0
    var moTaChiTiet: String? = null
    var ngayDanhGia: Date? = null
//            ma_danh_gia INTEGER PRIMARY KEY AUTOINCREMENT,
//            ma_don INTEGER UNIQUE,
//            ngay_danh_gia DATE,
//            danh_gia_chat_luong_phong INTEGER,
//            danh_gia_sach_se INTEGER,
//            danh_gia_nhan_vien_phuc_vu INTEGER,
//            danh_gia_tien_nghi INTEGER,
//            mo_ta_chi_tiet VARCHAR,
    constructor() {
        this.maDanhGia = -1
        this.maDon = -1
    }

    constructor(
        maDanhGia: Int, maDon: Int, ngayDanhGia: Date?, danhGiaChatLuongPhong: Int,
        danhGiaChatLuongDichVu: Int, danhGiaSachSe: Int, danhGiaNhanVienPhucVu: Int,
        danhGiaTienNghi: Int, moTaChiTiet: String?
    ) {
        this.maDanhGia = maDanhGia
        this.maDon = maDon
        this.ngayDanhGia = ngayDanhGia
        this.danhGiaChatLuongPhong = danhGiaChatLuongPhong

        this.danhGiaSachSe = danhGiaSachSe
        this.danhGiaNhanVienPhucVu = danhGiaNhanVienPhucVu
        this.danhGiaTienNghi = danhGiaTienNghi
        this.moTaChiTiet = moTaChiTiet
    }

    override fun toString(): String {
        return "ChiTietDanhGia{" +
                "maDanhGia=" + maDanhGia +
                ", maDon=" + maDon +
                ", ngayDanhGia=" + ngayDanhGia +
                ", danhGiaChatLuongPhong=" + danhGiaChatLuongPhong +

                ", danhGiaSachSe=" + danhGiaSachSe +
                ", danhGiaNhanVienPhucVu=" + danhGiaNhanVienPhucVu +
                ", danhGiaTienNghi=" + danhGiaTienNghi +
                ", moTaChiTiet='" + moTaChiTiet + '\'' +
                '}'
    }
}
