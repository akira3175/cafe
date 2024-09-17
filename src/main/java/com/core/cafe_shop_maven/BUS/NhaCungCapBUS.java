package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.NhaCungCapDAO;
import com.core.cafe_shop_maven.DTO.NhaCungCap;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NhaCungCapBUS {

    private NhaCungCapDAO nhaCungCapDAO;
    private ArrayList<NhaCungCap> listNhaCungCap;

    private static NhaCungCapBUS instance;

    public static NhaCungCapBUS getInstance() {
        if (instance == null)
            instance = new NhaCungCapBUS();
        return instance;
    }

    private NhaCungCapBUS() {
        nhaCungCapDAO = NhaCungCapDAO.getInstance();
        this.listNhaCungCap = nhaCungCapDAO.getListNhaCungCap();
    }

    public void docDanhSach() {
        this.listNhaCungCap = nhaCungCapDAO.getListNhaCungCap();
    }

    public ArrayList<NhaCungCap> getListNhaCungCap() {
        if (this.listNhaCungCap == null) {
            docDanhSach();
        }
        return this.listNhaCungCap;
    }

    public boolean themNhaCungCap(String tenNCC, String diaChi, String dienThoai, String fax) {
        if (tenNCC.trim().equals("")) {
            new Dialog("Hãy nhập tên nhà cung cấp!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (diaChi.trim().equals("")) {
            new Dialog("Hãy nhập địa chỉ!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (dienThoai.trim().equals("")) {
            new Dialog("Hãy nhập số điện thoại!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (fax.trim().equals("")) {
            new Dialog("Hãy nhập fax!", Dialog.ERROR_DIALOG);
            return false;
        }
        Pattern pattern = Pattern.compile("^\\d{10}$");
        if (!pattern.matcher(dienThoai).matches()) {
            new Dialog("Hãy nhập số điện thoại hợp lệ!", Dialog.ERROR_DIALOG);
            return false;
        }

        NhaCungCap ncc = new NhaCungCap();
        ncc.setTenNCC(tenNCC);
        ncc.setDiaChi(diaChi);
        ncc.setDienThoai(dienThoai);
        ncc.setFax(fax);
        boolean flag = nhaCungCapDAO.addNCC(ncc);
        if (flag) {
            new Dialog("Thêm mới thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Thêm mới thất bại!", Dialog.ERROR_DIALOG);
        }
        return flag;
    }

    public boolean suaNhaCungCap(String maNCC, String tenNCC, String diaChi, String dienThoai, String fax) {
        if (tenNCC.trim().equals("")) {
            new Dialog("Hãy nhập tên Nhà cung cấp này!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (diaChi.trim().equals("")) {
            new Dialog("Hãy nhập địa chỉ!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (dienThoai.trim().equals("")) {
            new Dialog("Hãy nhập số điện thoại!", Dialog.ERROR_DIALOG);
            return false;
        }
        if (fax.trim().equals("")) {
            new Dialog("Hãy nhập fax!", Dialog.ERROR_DIALOG);
            return false;
        }
        Pattern pattern = Pattern.compile("^\\d{10}$");
        if (!pattern.matcher(dienThoai).matches()) {
            new Dialog("Hãy nhập số điện thoại hợp lệ!", Dialog.ERROR_DIALOG);
            return false;
        }

        int ma = Integer.parseInt(maNCC);

        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNCC(ma);
        ncc.setTenNCC(tenNCC);
        ncc.setDiaChi(diaChi);
        ncc.setDienThoai(dienThoai);
        ncc.setFax(fax);

        boolean flag = nhaCungCapDAO.updateNCC(ncc);

        if (flag) {
            new Dialog("Sửa thành công!", Dialog.SUCCESS_DIALOG);
        } else {
            new Dialog("Sửa thất bại!", Dialog.ERROR_DIALOG);
        }
        return flag;
    }

}
