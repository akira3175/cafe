package com.core.cafe_shop_maven.DTO;

public class CTPhieuNhap {
    private int maPN;
    private int maSP;
    private int soLuong;
    private int donGia;
    private int ThanhTien;

    public CTPhieuNhap() {
    }

    public CTPhieuNhap(int maPN, int maSP, int soLuong, int donGia) {
        this.maPN = maPN;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        ThanhTien = soLuong * donGia;
    }

    public int getMaPN() {
        return maPN;
    }

    public void setMaPN(int maPN) {
        this.maPN = maPN;
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
        ThanhTien = soLuong * donGia;
        return ThanhTien;
    }

    public void setThanhTien(int ThanhTien) {
        this.ThanhTien = ThanhTien;
    }
}
