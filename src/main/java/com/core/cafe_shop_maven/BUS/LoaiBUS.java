package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.LoaiDAO;
import com.core.cafe_shop_maven.DTO.LoaiSP;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;
import java.util.ArrayList;

public class LoaiBUS {

    private LoaiDAO loaiDAO;
    private ArrayList<LoaiSP> listLoai;

    private static LoaiBUS instance;

    public static LoaiBUS getInstance() {
        if (instance == null)
            instance = new LoaiBUS();
        return instance;
    }

    private LoaiBUS() {
        loaiDAO = LoaiDAO.getInstance();
        this.listLoai = loaiDAO.getDanhSachLoai();
    }

    public void docDanhSachLoai() {
        this.listLoai = loaiDAO.getDanhSachLoai();
    }

    public ArrayList<LoaiSP> getDanhSachLoai() {
        if (listLoai == null) {
            docDanhSachLoai();
        }
        return this.listLoai;
    }

    public String getTenLoai(int ma) {
        for (LoaiSP loai : listLoai) {
            if (loai.getMaLoai() == ma) {
                return loai.getMaLoai() + " - " + loai.getTenLoai();
            }
        }
        return "";
    }

    public boolean themLoai(int maLoai, String tenLoai, String MoTa) {
        if (tenLoai.trim().equals("")) {
            new Dialog("Không được để trống tên loại!", Dialog.ERROR_DIALOG);
            return false;
        }
        maLoai += 1;
        LoaiSP loai = new LoaiSP(maLoai, tenLoai, MoTa);
        if (loaiDAO.themLoai(loai)) {
            new Dialog("Thêm thành công!", Dialog.SUCCESS_DIALOG);
            return true;
        } else {
            new Dialog("Thêm thất bại!", Dialog.ERROR_DIALOG);
            return false;
        }
    }

    public boolean xoaLoai(String ma) {
        if (ma.trim().equals("")) {
            new Dialog("Chưa chọn loại để xoá!", Dialog.SUCCESS_DIALOG);
            return false;
        }
        int maLoai = Integer.parseInt(ma);
        if (loaiDAO.xoaLoai(maLoai)) {
            new Dialog("Xoá thành công!", Dialog.SUCCESS_DIALOG);
            return true;
        } else {
            new Dialog("Xoá thất bại! Loại có sản phẩm con", Dialog.ERROR_DIALOG);
            return false;
        }
    }

    public boolean suaLoai(String ma, String ten, String moTa) {
        if (ten.trim().equals("")) {
            new Dialog("Không được để trống tên loại!", Dialog.ERROR_DIALOG);
            return false;
        }
        int maLoai = Integer.parseInt(ma);
        if (loaiDAO.suaLoai(maLoai, ten, moTa)) {
            new Dialog("Sửa thành công!", Dialog.SUCCESS_DIALOG);
            return true;
        } else {
            new Dialog("Sửa thất bại!", Dialog.ERROR_DIALOG);
            return false;
        }
    }

}
