package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.KhachHangDAO;
import com.core.cafe_shop_maven.DTO.KhachHang;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;

import java.util.ArrayList;
import java.util.Locale;

public class KhachHangBUS {
    private ArrayList<KhachHang> listKhachHang;
    private KhachHangDAO khachHangDAO;

    private static KhachHangBUS instance;

    public static KhachHangBUS getInstance() {
        if (instance == null)
            instance = new KhachHangBUS();
        return instance;
    }

    private KhachHangBUS() {
        khachHangDAO = KhachHangDAO.getInstance();
        this.listKhachHang = khachHangDAO.getListKhachHang();
    }

    public void docDanhSach() {
        this.listKhachHang = khachHangDAO.getListKhachHang();
    }

    public ArrayList<KhachHang> getListKhachHang() {
        return listKhachHang;
    }

    public ArrayList<KhachHang> timKiemKhachHang(String tuKhoa) {
        tuKhoa = tuKhoa.toLowerCase();
        ArrayList<KhachHang> dskh = new ArrayList<>();
        for (KhachHang kh : listKhachHang) {
            String ten = kh.getTen().toLowerCase();
            String diaChi = kh.getDiaChi().toLowerCase();
            String sdt = kh.getSdt().toLowerCase();
            if (ten.contains(tuKhoa) || diaChi.contains(tuKhoa) || sdt.contains(sdt)) {
                dskh.add(kh);
            }
        }
        return dskh;
    }

    public boolean themKhachHang(String ten, String diaChi, String sdt) {
        if (ten.trim().equals("")) {
            new Dialog("Không được để trống tên!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (diaChi.trim().equals("")) {
            new Dialog("Không được để trống địa chỉ!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (sdt.trim().equals("")) {
            new Dialog("Không được để trống số điện thoại!", Dialog.ERROR_DIALOG);
            return false;
        }
        KhachHang kh = new KhachHang();
        kh.setTen(ten);
        kh.setDiaChi(diaChi);
        kh.setSdt(sdt);
        boolean flag = khachHangDAO.addKhachHang(kh);
        if (flag) {
            new Dialog("Thêm thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Thêm thất bại!", Dialog.ERROR_DIALOG);
        }
        return flag;
    }

    public boolean suaKhachHang(String ma, String ten, String diaChi, String sdt) {
        if (ten.trim().equals("")) {
            new Dialog("Không được để trống tên!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (diaChi.trim().equals("")) {
            new Dialog("Không được để trống địa chỉ!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (sdt.trim().equals("")) {
            new Dialog("Không được để trống số điện thoại!", Dialog.ERROR_DIALOG);
            return false;
        }
        KhachHang kh = new KhachHang();
        kh.setTen(ten);
        kh.setDiaChi(diaChi);
        kh.setSdt(sdt);
        boolean flag = khachHangDAO.updateKhachHang(Integer.parseInt(ma), kh);
        if (flag) {
            new Dialog("Sửa thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Sửa thất bại!", Dialog.ERROR_DIALOG);
        }
        return flag;
    }

    public boolean xoaKhachHang(String ma) {
        boolean flag = false;
        try {
            int maKH = Integer.parseInt(ma);
            Dialog dlg = new Dialog("Bạn có chắc chắn muốn xoá?", Dialog.WARNING_DIALOG);
            if (dlg.getAction() == Dialog.CANCEL_OPTION)
                return false;
            flag = khachHangDAO.deleteKhachHang(maKH);
        } catch (Exception e) {
            new Dialog("Chưa chọn khách hàng!", Dialog.ERROR_DIALOG);
        }
        if (flag) {
            new Dialog("Xoá thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Xoá thất bại!", Dialog.ERROR_DIALOG);
        }
        return flag;
    }
}
