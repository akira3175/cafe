package com.core.cafe_shop_maven.DTO;

public class LoaiSP {

    private int maLoai;
    private String tenLoai;
    private String Mota;
    private int TrangThai;

    public LoaiSP() {
    }

    public LoaiSP(int maLoai, String tenLoai, String Mota) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.Mota = Mota;
        TrangThai = 1;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String Mota) {
        this.Mota = Mota;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

}
