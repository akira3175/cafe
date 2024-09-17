package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.PhanQuyenDAO;
import com.core.cafe_shop_maven.DTO.PhanQuyen;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;

import java.util.ArrayList;

public class PhanQuyenBUS {

    public static PhanQuyen quyenTK;
    private PhanQuyenDAO phanQuyenDAO;
    private ArrayList<PhanQuyen> listPhanQuyen;

    private static PhanQuyenBUS instance;

    public static PhanQuyenBUS getInstance() {
        if (instance == null)
            instance = new PhanQuyenBUS();
        return instance;
    }

    private PhanQuyenBUS() {
        phanQuyenDAO = PhanQuyenDAO.getInstance();
        this.listPhanQuyen = phanQuyenDAO.getListQuyen();
    }

    public void docDanhSachQuyen() {
        this.listPhanQuyen = phanQuyenDAO.getListQuyen();
    }

    public void kiemTraQuyen(String quyen) {
        quyenTK = phanQuyenDAO.getQuyen(quyen);
    }

    public ArrayList<PhanQuyen> getListQuyen() {
        if (listPhanQuyen == null)
            docDanhSachQuyen();
        return this.listPhanQuyen;
    }

    public boolean suaQuyen(int maQuyen, String tenQuyen, int nhapHang, int sanPham, int nhanVien, int khachHang,
            int thongKe) {
        PhanQuyen phanQuyen = new PhanQuyen(maQuyen, tenQuyen, nhapHang, sanPham, nhanVien, khachHang, thongKe);
        boolean flag = phanQuyenDAO.suaQuyen(phanQuyen);
        if (flag) {
            new Dialog("Sửa thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Sửa thất bại!", Dialog.ERROR_DIALOG);
        }
        return flag;
    }

    public boolean themQuyen(String tenQuyen) {
        if (tenQuyen == null || tenQuyen.trim().equals("")) {
            return false;
        }

        if (kiemTonTaiTraQuyen(tenQuyen)) {
            new Dialog("Thêm thất bại! Quyền đã tồn tại", Dialog.ERROR_DIALOG);
            return false;
        }

        PhanQuyen phanQuyen = new PhanQuyen(0, tenQuyen, 0, 0, 0, 0, 0);
        boolean flag = phanQuyenDAO.themQuyen(phanQuyen);
        if (flag) {
            new Dialog("Thêm thành công! Hãy hiệu chỉnh quyền", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Thêm thất bại! Quyền đã tồn tại", Dialog.ERROR_DIALOG);
        }
        return flag;
    }

    private boolean kiemTonTaiTraQuyen(String tenQuyen) {
        docDanhSachQuyen();
        for (PhanQuyen q : listPhanQuyen) {

            if (q.getQuyen().equalsIgnoreCase(tenQuyen))
                return true;

        }
        return false;
    }

    public boolean xoaQuyen(String tenQuyen) {
        boolean flag = phanQuyenDAO.xoaQuyen(tenQuyen);
        if (flag) {
            new Dialog("Xoá thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Xoá thất bại!", Dialog.ERROR_DIALOG);
        }
        return flag;
    }
    public boolean kiemTraMaQuyenCoTaiKhoanNaoKhong(String tenQuyen) {
        docDanhSachQuyen();
        int maQuyen = PhanQuyenDAO.getInstance().getMaQuyen(tenQuyen);
        return phanQuyenDAO.kiemTraMaQuyenCoTaiKhoanNaoKhong(maQuyen);
    }
}
