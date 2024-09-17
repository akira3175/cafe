package com.core.cafe_shop_maven.DTO;

public class TaiKhoan {

    private int maTK;
    private String tenTK;
    private String matKhau;
    private String quyen;

    public TaiKhoan() {
    }

    public TaiKhoan(int maNhanVien, String tenDangNhap, String matKhau, String quyen) {
        this.maTK = maNhanVien;
        this.tenTK = tenDangNhap;
        this.matKhau = matKhau;
        this.quyen = quyen;
    }

    public int getMaTK() {
        return maTK;
    }

    public void setMaTK(int maTK) {
        this.maTK = maTK;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }
}
