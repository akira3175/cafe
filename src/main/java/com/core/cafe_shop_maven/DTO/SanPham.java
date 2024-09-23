package com.core.cafe_shop_maven.DTO;

public class SanPham {
    private int maSP;
    private String tenSP;
    private int maLoai;
    private int soLuong;
    private String hinhAnh;
    private int donGia;
    private int phanTramLoi;
    private int trangThai;
    private int maPN;

    public SanPham() {

    }

    public SanPham(int maSP, String tenSP, int maLoai, int soLuong, String hinhAnh, int donGia, int phanTramLoi, int maPN) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maLoai = maLoai;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
        this.donGia = donGia;
        this.phanTramLoi = phanTramLoi;
        this.trangThai = 1;
        this.maPN = maPN;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getPhanTramLoi() {
        return phanTramLoi;
    }

    public void setPhanTramLoi(int phanTramLoi) {
        this.phanTramLoi = phanTramLoi;
    }
    
    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String toString() {
        return String.format("Ten: %s, ma: %d, sl: %d", this.tenSP, this.maSP, this.soLuong);
    }
    
    public int getMaPN() {
        return maPN;
    }

    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }

}
