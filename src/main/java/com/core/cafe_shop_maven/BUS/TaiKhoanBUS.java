package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.TaiKhoanDAO;
import com.core.cafe_shop_maven.DTO.NhanVien;
import com.core.cafe_shop_maven.DTO.TaiKhoan;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;
import com.core.cafe_shop_maven.DAO.NhanVienDAO;
import com.core.cafe_shop_maven.DAO.PhanQuyenDAO;

public class TaiKhoanBUS {

    private TaiKhoanDAO taiKhoanDAO;

    private static TaiKhoanBUS instance;

    public static TaiKhoanBUS getInstance() {
        if (instance == null)
            instance = new TaiKhoanBUS();
        return instance;
    }

    private TaiKhoanBUS() {
        taiKhoanDAO = TaiKhoanDAO.getInstance();
    }

    public String getTenDangNhapTheoMa(String ma) {
        int maNV = Integer.parseInt(ma);
        int maTK = NhanVienDAO.getInstance().getNhanVien(maNV).getMaTK();
        return taiKhoanDAO.getTenDangNhapTheoMa(maTK);
    }

    public String getQuyenTheoMa(String ma) {
        int maTK = Integer.parseInt(ma);
        return taiKhoanDAO.getQuyenTheoMa(maTK);
    }

    public void datLaiMatKhau(String ma, String tenDangNhap) {
        int maNV = Integer.parseInt(ma);
        int maTK = NhanVienDAO.getInstance().getNhanVien(maNV).getMaTK();
        boolean flag = taiKhoanDAO.datLaiMatKhau(maTK, tenDangNhap);
        if (flag) {
            new Dialog("Đặt lại thành công! Mật khẩu mới là: " + tenDangNhap, Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Đặt lại thất bại!", Dialog.ERROR_DIALOG);
        }
    }

    public void datLaiQuyen(String ma, String quyen) {
        int maNV = Integer.parseInt(ma);
        int maTK = NhanVienDAO.getInstance().getNhanVien(maNV).getMaTK();
        boolean flag = taiKhoanDAO.datLaiQuyen(maTK, PhanQuyenDAO.getInstance().getMaQuyen(quyen));
        if (flag) {
            new Dialog("Đặt lại thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Đặt lại thất bại!", Dialog.ERROR_DIALOG);
        }
    }

    public boolean kiemTraTrungTenDangNhap(String tenDangNhap) {
        return taiKhoanDAO.kiemTraTrungTenDangNhap(tenDangNhap);
    }

    public boolean themTaiKhoan(String ma, String tenDangNhap, String quyen) {
        int maNV = Integer.parseInt(ma);
        if (tenDangNhap.trim().equals("")) {
            new Dialog("Không được để trống Tên đăng nhập!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (kiemTraTrungTenDangNhap(tenDangNhap)) {
            Dialog dlg = new Dialog("Tên đăng nhập bị trùng! Có thể tài khoản bị khoá, thực hiện mở khoá?",
                    Dialog.WARNING_DIALOG);
            if (dlg.getAction() == Dialog.OK_OPTION) {
                moKhoaTaiKhoan(ma);
                return true;
            }
            return false;
        }
        boolean flag = taiKhoanDAO.themTaiKhoan(tenDangNhap, tenDangNhap);
        if (flag) {

            NhanVienDAO.getInstance().updateTaiKhoanNV(maNV, tenDangNhap);
            taiKhoanDAO.themQuyen(taiKhoanDAO.getMaTK(tenDangNhap), PhanQuyenDAO.getInstance().getMaQuyen(quyen));

            new Dialog("Cấp tài khoản thành công! Mật khẩu là " + tenDangNhap, Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Cấp tài khoản thất bại! Tài khoản đã tồn tại", Dialog.ERROR_DIALOG);
        }
        return flag;
    }

    public void khoaTaiKhoan(String ma) {
        int maTK = Integer.parseInt(ma);
        boolean flag = taiKhoanDAO.khoaTaiKhoan(maTK);
        if (flag) {
            new Dialog("Khoá tài khoản thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Khoá tài khoản thất bại!", Dialog.ERROR_DIALOG);
        }
    }

    public void khoaTaiKhoan(int ma) {
        int maTK = ma;
        boolean flag = taiKhoanDAO.khoaTaiKhoan(maTK);
        if (flag) {
            new Dialog("Khoá tài khoản thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Khoá tài khoản thất bại!", Dialog.ERROR_DIALOG);
        }
    }

    public void moKhoaTaiKhoan(String ma) {
        int maNV = Integer.parseInt(ma);
        int maTK = NhanVienDAO.getInstance().getNhanVien(maNV).getMaTK();
        boolean flag = taiKhoanDAO.moKhoaTaiKhoan(maTK);
        if (flag) {
            new Dialog("Mở khoá tài khoản thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Mở khoá tài khoản thất bại!", Dialog.ERROR_DIALOG);
        }
    }

    public boolean doiMatKhau(String matKhauCu, String matKhauMoi, String nhapLaiMatKhau) {
        if (!matKhauMoi.equals(nhapLaiMatKhau)) {
            new Dialog("Mật khẩu mới không khớp!", Dialog.ERROR_DIALOG);
            return false;
        }
        boolean flag = taiKhoanDAO.doiMatKhau(matKhauCu, matKhauMoi);
        if (flag) {
            new Dialog("Đổi thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Mật khẩu cũ nhập sai!", Dialog.ERROR_DIALOG);
        }
        return flag;
    }

    public int getTrangThai(String maNV) {
        int ma = Integer.parseInt(maNV);
        return taiKhoanDAO.getTrangThai(ma);
    }

}
