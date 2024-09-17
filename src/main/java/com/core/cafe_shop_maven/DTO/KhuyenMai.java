package com.core.cafe_shop_maven.DTO;

import java.util.Date;

public class KhuyenMai {

    private int maKM;
    private String tenKM;
    private int phanTramKM;
    private int dieuKien;
    private String ngayBD;
    private String ngayKT;

    public KhuyenMai() {
    }

    public KhuyenMai(int maGiam, String tenGiamGia, int phanTramGiam, int dieuKien, String ngayBD, String ngayKT) {
        this.maKM = maGiam;
        this.tenKM = tenGiamGia;
        this.phanTramKM = phanTramGiam;
        this.dieuKien = dieuKien;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
    }

    public int getMaKM() {
        return maKM;
    }

    public void setMaKM(int maGiam) {
        this.maKM = maGiam;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenGiamGia) {
        this.tenKM = tenGiamGia;
    }

    public int getPhanTramKM() {
        return phanTramKM;
    }

    public void setPhanTramKM(int phanTramGiam) {
        this.phanTramKM = phanTramGiam;
    }

    public int getDieuKien() {
        return dieuKien;
    }

    public void setDieuKien(int dieuKien) {
        this.dieuKien = dieuKien;
    }

    public String getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(String ngayBD) {
        this.ngayBD = ngayBD;
    }

    public String getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(String ngayKT) {
        this.ngayKT = ngayKT;
    }
}
