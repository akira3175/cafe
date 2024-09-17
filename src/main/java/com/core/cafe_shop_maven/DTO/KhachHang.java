package com.core.cafe_shop_maven.DTO;

public class KhachHang {
    private int maKH;
    private String ten;
    private String diaChi;
    private String sdt;
    private int TrangThai;

    public KhachHang() {
    }

    public KhachHang(int maKH, String ten, String diaChi, String sdt) {
        this.maKH = maKH;
        this.ten = ten;
        this.diaChi = diaChi;
        this.sdt = sdt;
        TrangThai = 1;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

}
