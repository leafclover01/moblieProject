package com.example.appbooking.Model

import java.io.Serializable


class ChiTietLoaiPhong : Serializable {
    var id: Int
    var maLoaiPhong: Int
    var hinh: String? = null

    constructor() {
        this.id = -1
        this.maLoaiPhong = -1
    }

    constructor(id: Int, maLoaiPhong: Int, hinh: String?) {
        this.id = id
        this.maLoaiPhong = maLoaiPhong
        this.hinh = hinh
    }

    override fun toString(): String {
        return "ChiTietLoaiPhong{" +
                "id=" + id +
                ", maLoaiPhong=" + maLoaiPhong +
                ", hinh='" + hinh + '\'' +
                '}'
    }
}
//        createTable("CHI_TIET_LOAI_PHONG", """
//            id INTEGER PRIMARY KEY AUTOINCREMENT,
//            ma_loai_phong INTEGER,
//            hinh VARCHAR(255),
//            FOREIGN KEY (ma_loai_phong) REFERENCES LOAI_PHONG(ma_loai_phong)
//        """)