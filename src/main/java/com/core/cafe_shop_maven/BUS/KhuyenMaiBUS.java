package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.KhuyenMaiDAO;
import com.core.cafe_shop_maven.DTO.KhuyenMai;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

public class KhuyenMaiBUS {

    private ArrayList<KhuyenMai> listGiamGia;
    private KhuyenMaiDAO giamGiaDAO;

    private static KhuyenMaiBUS instance;

    public static KhuyenMaiBUS getInstance() {
        if (instance == null)
            instance = new KhuyenMaiBUS();
        return instance;
    }

    private KhuyenMaiBUS() {
        giamGiaDAO = KhuyenMaiDAO.getInstance();
        this.listGiamGia = giamGiaDAO.getDanhSachKhuyenMai();
    }

    public void docDanhSach() {
        this.listGiamGia = giamGiaDAO.getDanhSachKhuyenMai();
    }

    public ArrayList<KhuyenMai> getDanhSachKhuyenMai() {
        if (this.listGiamGia == null)
            docDanhSach();
        return this.listGiamGia;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public boolean themKhuyenMai(String ten, String phanTram, String dieuKien, String ngayBD, String ngayKT) {
        ten = ten.trim();
        phanTram = phanTram.replace("%", "");
        dieuKien = dieuKien.replace(",", "");
        if (ten.equals("")) {
            new Dialog("Hãy nhập tên chương trình khuyến mãi!", Dialog.ERROR_DIALOG);
            return false;
        }

        boolean flag = false;
        try {

            Date ngayBDdt = sdf.parse(ngayBD);
            Date ngayKTdt = sdf.parse(ngayKT);

            if (ngayBDdt.compareTo(ngayKTdt) > 0 || ngayBDdt.compareTo(ngayKTdt) == 0) {
                new Dialog("Ngày kết thúc không hợp lệ!", Dialog.ERROR_DIALOG);
                return false;
            }
            int phanTramGiam = Integer.parseInt(phanTram);
            int dieuKienGiam = Integer.parseInt(dieuKien);

            KhuyenMai gg = new KhuyenMai();
            gg.setTenKM(ten);
            gg.setPhanTramKM(phanTramGiam);
            gg.setDieuKien(dieuKienGiam);
            gg.setNgayBD(ngayBD);
            gg.setNgayKT(ngayKT);

            flag = giamGiaDAO.themMaGiam(gg);
        } catch (Exception e) {
            new Dialog("Hãy nhập số nguyên hợp lệ!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (flag) {
            new Dialog("Thêm mới thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Thêm mới thất bại!", Dialog.ERROR_DIALOG);
        }
        return flag;
    }

    public boolean suaKhuyenMai(String ma, String ten, String phanTram, String dieuKien, String ngayBD, String ngayKT) {
        ten = ten.trim();
        phanTram = phanTram.replace("%", "");
        dieuKien = dieuKien.replace(",", "");

        if (ma.equals("")) {
            new Dialog("Chưa chọn mã để sửa!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (ten.equals("")) {
            new Dialog("Hãy nhập tên chương trình khuyến mãi!", Dialog.ERROR_DIALOG);
            return false;
        }

        boolean flag = false;
        try {

            Date ngayBDdt = sdf.parse(ngayBD);
            Date ngayKTdt = sdf.parse(ngayKT);

            if (ngayBDdt.compareTo(ngayKTdt) > 0 || ngayBDdt.compareTo(ngayKTdt) == 0) {
                new Dialog("Ngày kết thúc không hợp lệ!", Dialog.ERROR_DIALOG);
                return false;
            }

            int maGiam = Integer.parseInt(ma);
            int phanTramGiam = Integer.parseInt(phanTram);
            int dieuKienGiam = Integer.parseInt(dieuKien);

            KhuyenMai gg = new KhuyenMai();
            gg.setMaKM(maGiam);
            gg.setTenKM(ten);
            gg.setPhanTramKM(phanTramGiam);
            gg.setDieuKien(dieuKienGiam);
            gg.setNgayBD(ngayBD);
            gg.setNgayKT(ngayKT);

            flag = giamGiaDAO.suaMaGiam(gg);
        } catch (Exception e) {
            new Dialog("Hãy nhập số nguyên hợp lệ!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (flag) {
            new Dialog("Sửa thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Sửa thất bại!", Dialog.ERROR_DIALOG);
        }
        return flag;
    }
}
