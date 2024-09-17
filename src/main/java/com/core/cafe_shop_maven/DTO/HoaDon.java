package com.core.cafe_shop_maven.DTO;

import java.util.Date;

public class HoaDon {
    private int maHD;
    private int maKH;
    private int maNV;
    private int maKM;
    private String ngayLap;
    private String gioLap;
    private int tongTien;

    public HoaDon() {
    }

    public HoaDon(int maHD, int maKH, int maNV, int maKM, String ngayLap, int tongTien) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.maKM = maKM;
        this.ngayLap = ngayLap;
        this.gioLap = ngayLap;
        this.tongTien = tongTien;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getMaKM() {
        return maKM;
    }

    public void setMaKM(int maKM) {
        this.maKM = maKM;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        gioLap = ngayLap;
        this.ngayLap = ngayLap;
    }

    public String getGioLap() {
        gioLap = ngayLap;
        return gioLap;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
