package com.core.cafe_shop_maven.DTO;

public class CTHoaDon {
    private int maHD;
    private int maSP;
    private int soLuong;
    private int donGia;
    private int thanhTien;

    public CTHoaDon() {
    }

    public CTHoaDon(int maHD, int maSP, int soLuong, int donGia) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = soLuong * donGia;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getThanhTien() {
        thanhTien = soLuong * donGia;
        return thanhTien;
    }
}
