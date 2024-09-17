package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.NhanVienDAO;
import com.core.cafe_shop_maven.DTO.NhanVien;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

public class NhanVienBUS {

    private NhanVienDAO nvDAO;
    private ArrayList<NhanVien> listNhanVien;

    private static NhanVienBUS instance;

    public static NhanVienBUS getInstance() {
        if (instance == null)
            instance = new NhanVienBUS();
        return instance;
    }

    private NhanVienBUS() {
        nvDAO = NhanVienDAO.getInstance();
        this.listNhanVien = nvDAO.getDanhSachNhanVien();
    }

    public void docDanhSach() {
        this.listNhanVien = nvDAO.getDanhSachNhanVien();
    }

    public ArrayList<NhanVien> getDanhSachNhanVien() {
        docDanhSach();
        return this.listNhanVien;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public boolean themNhanVien(String ten, String ngaySinh, String diaChi, String sdt) {
        ten = ten.trim();
        ngaySinh = ngaySinh.trim();
        sdt = sdt.trim();
        if (ten.equals("")) {
            new Dialog("Tên không được để trống!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (sdt.equals("")) {
            new Dialog("Số điện thoại không được để trống!", Dialog.ERROR_DIALOG);
            return false;
        }

        NhanVien nv = new NhanVien();
        nv.setTen(ten);
        Date d;
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            d = sdf.parse(ngaySinh);
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            nv.setNgaySinh(sdf.format(d));
        } catch (Exception ex) {
            new Dialog("Ngày sinh không đúng định dạng!", Dialog.ERROR_DIALOG);
            return false;
        }
        nv.setDiaChi(diaChi);
        if (!kiemTraSoDienThoai(sdt)) {
            new Dialog("Số điện thoại không đúng định dạng!", Dialog.ERROR_DIALOG);
            return false;
        }
        nv.setSdt(sdt);
        boolean flag = nvDAO.themNhanVien(nv);
        if (!flag) {
            new Dialog("Thêm thất bại!", Dialog.ERROR_DIALOG);
        } else {
            new Dialog("Thêm thành công!", Dialog.SUCCESS_DIALOG);
        }
        return flag;
    }

    private boolean kiemTraSoDienThoai(String sdt) {
        String regex = "^0\\d{9}$";
        return sdt.matches(regex);
    }

    public boolean updateNhanVien(String ma, String ten, String ngaySinh, String diaChi, String sdt) {
        int maNV = Integer.parseInt(ma);
        ten = ten.trim();
        ngaySinh = ngaySinh.trim();
        diaChi = diaChi.trim();
        if (ten.equals("")) {
            new Dialog("Tên không được để trống!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (diaChi.equals("")) {
            new Dialog("Địa chỉ không được để trống!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (sdt.equals("")) {
            new Dialog("Số điện thoại không được để trống!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (!kiemTraSoDienThoai(sdt)) {
            new Dialog("Số điện thoại không đúng định dạng!", Dialog.ERROR_DIALOG);
            return false;
        }
        NhanVien nv = new NhanVien();
        nv.setMaNV(maNV);
        nv.setTen(ten);

        Date d;
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            d = sdf.parse(ngaySinh);
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            nv.setNgaySinh(sdf.format(d));
        } catch (Exception ex) {
            d = new Date();
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            nv.setNgaySinh(sdf.format(d));
        }

        nv.setDiaChi(diaChi);
        nv.setSdt(sdt);
        boolean flag = nvDAO.updateNhanVien(nv);
        if (!flag) {
            new Dialog("Cập nhập thất bại!", Dialog.ERROR_DIALOG);
        } else {
            new Dialog("Cập nhập thành công!", Dialog.SUCCESS_DIALOG);
        }
        return flag;
    }

    public ArrayList<NhanVien> timNhanVien(String tuKhoa) {
        tuKhoa = tuKhoa.toLowerCase();
        ArrayList<NhanVien> dsnv = new ArrayList<>();
        for (NhanVien nv : listNhanVien) {
            if (nv.getTen().toLowerCase().contains(tuKhoa) || nv.getNgaySinh().toLowerCase().contains(tuKhoa) ||
                    nv.getDiaChi().toLowerCase().contains(tuKhoa) || nv.getSdt().toLowerCase().contains(tuKhoa)) {
                dsnv.add(nv);
            }
        }
        return dsnv;
    }

    public boolean xoaNhanVien(String ma) {
        try {
            int maNV = Integer.parseInt(ma);
            Dialog dlg = new Dialog("Bạn có chắc chắn muốn xoá?", Dialog.WARNING_DIALOG);
            boolean flag = false;
            if (dlg.getAction() == Dialog.OK_OPTION) {
                flag = nvDAO.deleteNhanVien(maNV);
                if (flag) {
                    new Dialog("Xoá thành công!", Dialog.SUCCESS_DIALOG);
                } else {
                    new Dialog("Xoá thất bại!", Dialog.ERROR_DIALOG);
                }
            }
            return flag;
        } catch (Exception e) {
            new Dialog("Chưa chọn nhân viên!", Dialog.ERROR_DIALOG);
        }
        return false;
    }

    public boolean nhapExcel(String ho, String ten, String gioiTinh, String chucVu) {
        NhanVien nv = new NhanVien();
        nv.setTen(ho);
        nv.setNgaySinh(ten);
        nv.setDiaChi(gioiTinh);
        nv.setSdt(chucVu);
        boolean flag = nvDAO.nhapExcel(nv);
        return flag;
    }
}
