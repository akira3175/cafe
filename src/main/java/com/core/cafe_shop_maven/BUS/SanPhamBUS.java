package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.SanPhamDAO;
import com.core.cafe_shop_maven.DTO.SanPham;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SanPhamBUS {

    private ArrayList<SanPham> listSanPham;
    private SanPhamDAO spDAO;

    private static SanPhamBUS instance;

    public static SanPhamBUS getInstance() {
        if (instance == null) {
            instance = new SanPhamBUS();
        }
        return instance;
    }

    private SanPhamBUS() {
        spDAO = SanPhamDAO.getInstance();
//        listSanPham = spDAO.getListSanPham();
        this.getListSanPham();
    }

//    public void docListSanPham() {
//        listSanPham = spDAO.getListSanPham();
//    }

    // Trung -> fix quy trinh doc va kiem tra de cap nhat so luong va don gia cho san pham
    public ArrayList<SanPham> getListSanPham() {
        listSanPham = spDAO.getListSanPham();  // Lấy danh sách sản phẩm từ DAO
        return listSanPham;  // Trả về danh sách đã cập nhật
    }

    // Trung -> add function
    public void updateLoHangTiepTheo() {
        ArrayList<SanPham> listSanPham = spDAO.getListSanPham();  // Lấy danh sách sản phẩm từ DAO
        // Sử dụng vòng lặp for thông thường để có thể cập nhật danh sách
        for (int i = 0; i < listSanPham.size(); i++) {
            SanPham sp = listSanPham.get(i);  // Lấy sản phẩm tại vị trí i
            // Trường hợp sản phẩm mới dùng lô hàng đầu tiên
            if (sp.getMaPN() == 0) {
                Object res = CTPhieuNhapBUS.getInstance().updateSoLuongVaMaPNTiepTheo(sp.getMaSP(), sp.getMaPN(), sp.getSoLuong());
                // Cập nhật số lượng và mã lô hàng đầu tiên nếu có
                if (res instanceof SanPham) {
                    SanPham temp = (SanPham) res;  // Gán giá trị mới cho sp
                    // Cập nhật lại danh sách tại vị trí i
                    sp.setSoLuong(temp.getSoLuong());
                    sp.setDonGia(temp.getDonGia());
                    sp.setMaPN(temp.getMaPN());
                    this.handleThongTinSanPhamCuaLoTiepTheo(sp.getMaSP(), sp.getMaPN(), sp.getSoLuong(), sp.getDonGia());
                }
            }
            // Trường hợp sản phẩm đã dùng lô hàng nhưng đã hết số lượng
            if (sp.getMaPN() != 0 && sp.getSoLuong() == 0) {
                Object res = CTPhieuNhapBUS.getInstance().updateSoLuongVaMaPNTiepTheo(sp.getMaSP(), sp.getMaPN(), sp.getSoLuong());
                // Cập nhật số lượng và mã lô hàng tiếp theo
                if (res instanceof SanPham) {
                    SanPham temp = (SanPham) res;  // Gán giá trị mới cho sp
                    // Cập nhật lại danh sách tại vị trí i
                    sp.setSoLuong(temp.getSoLuong());
                    sp.setDonGia(temp.getDonGia());
                    sp.setMaPN(temp.getMaPN());
                    this.handleThongTinSanPhamCuaLoTiepTheo(sp.getMaSP(), sp.getMaPN(), sp.getSoLuong(), sp.getDonGia());
                }
            }
        }
    }
    
    // Trung -> add function cap nhat lai thong tin san pham cua lo moi
    public boolean handleThongTinSanPhamCuaLoTiepTheo(int maSP, int maPN, int soLuong, int donGia) {    
        return SanPhamDAO.getInstance().updateThongTinSanPhamLoTiepTheo(maSP, maPN, soLuong, donGia);
    }

    public SanPham getSanPham(String ma) {
        if (!ma.trim().equals("")) {
            try {
                int maSP = Integer.parseInt(ma);
                for (SanPham sp : listSanPham) {
                    if (sp.getMaSP() == maSP) {
                        return sp;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<SanPham> getSanPhamTheoTen(String ten) {
        ArrayList<SanPham> dssp = new ArrayList<>();
        for (SanPham sp : listSanPham) {
            String tenSP = sp.getTenSP().toLowerCase();
            if (tenSP.toLowerCase().contains(ten.toLowerCase())) {
                dssp.add(sp);
            }
        }
        return dssp;
    }

    public ArrayList<SanPham> getSanPhamTheoLoai(String ma) {
        if (!ma.trim().equals("")) {
            ArrayList<SanPham> dssp = new ArrayList<>();
            try {
                int maLoai = Integer.parseInt(ma);
                for (SanPham sp : listSanPham) {
                    if (sp.getMaLoai() == maLoai) {
                        dssp.add(sp);
                    }
                }
                return dssp;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getAnh(String ma) {
        int maSP = Integer.parseInt(ma);
        return spDAO.getAnh(maSP);
    }

    public void capNhatSoLuongSP(int ma, int soLuongMat) {
//        SanPham sp = getSanPham(ma + "");
//        sp.setSoLuong(sp.getSoLuong() + soLuongMat);
        spDAO.capNhatSoLuongSP(ma, soLuongMat);
    }

    public boolean themSanPham(String ten,
            String loai,
            String anh
    ) {
        if (ten.trim().equals("")) {
            new Dialog("Tên SP không được để rỗng!", Dialog.ERROR_DIALOG);
            return false;
        }
        try {
            String[] loaiTmp = loai.split(" - ");
            int maLoai = Integer.parseInt(loaiTmp[0]);
            if (maLoai == 0) {
                new Dialog("Vui lòng chọn Loại sản phẩm!", Dialog.ERROR_DIALOG);
                return false;
            }
            SanPham sp = new SanPham();
            sp.setTenSP(ten);
            sp.setMaLoai(maLoai);
            sp.setSoLuong(0);
            sp.setHinhAnh(anh);
            sp.setDonGia(0);
            sp.setPhanTramLoi(0);
            
            listSanPham.add(sp);
            if (spDAO.themSanPham(sp)) {
                new Dialog("Thêm thành công!", Dialog.SUCCESS_DIALOG);
                return true;
            } else {
                new Dialog("Thêm thất bại!", Dialog.ERROR_DIALOG);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean nhapSanPhamTuExcel(String ten,
            String loai,
            String soLuong,
            String anh,
            String donGia) {

        try {
            String[] loaiTmp = loai.split(" - ");
            int maLoai = Integer.parseInt(loaiTmp[0]);
            int soLuongSP = Integer.parseInt(soLuong);
            donGia = donGia.replace(",", "");
            int donGiaSP = Integer.parseInt(donGia);

            SanPham sp = new SanPham();
            sp.setTenSP(ten);
            sp.setMaLoai(maLoai);
            sp.setSoLuong(soLuongSP);
            sp.setHinhAnh(anh);
            sp.setDonGia(donGiaSP);

            spDAO.nhapSanPhamTuExcel(sp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaSanPham(String ma) {
        if (ma.trim().equals("")) {
            new Dialog("Chưa chọn sản phẩm để xoá!", Dialog.ERROR_DIALOG);
            return false;
        }

        int maSP = Integer.parseInt(ma);
        listSanPham.removeIf(sp -> sp.getMaSP() == maSP);
        if (spDAO.xoaSanPham(maSP)) {
            new Dialog("Xoá thành công!", Dialog.SUCCESS_DIALOG);
            return true;
        }

        new Dialog("Xoá thất bại!", Dialog.ERROR_DIALOG);
        return false;
    }
    // kiem tra chuoi co phai so
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public boolean suaSanPham(String ma,
            String ten,
            String loai,
            String soLuong,
            String anh,
            String donGia,
            String phanTramLoi) {
        try {
            if (ma.trim().equals("")) {
                new Dialog("Chưa chọn sản phẩm để sửa!", Dialog.ERROR_DIALOG);
                return false;
            }
            donGia = donGia.replace(",", "");
            int maSP = Integer.parseInt(ma);
            String[] loaiTmp = loai.split(" - ");
            int maLoai = Integer.parseInt(loaiTmp[0]);
            int soLuongSP = Integer.parseInt(soLuong);
            int donGiaSP = Integer.parseInt(donGia);
            int phanTramLoiSP = 0;
            if(isNumeric(phanTramLoi)) {
                phanTramLoiSP = Integer.parseInt(phanTramLoi);
            } else {
                new Dialog("Phần trăm lời khong phai so!", Dialog.ERROR_DIALOG);
                return false;
            }
            // check input hop le
            if (maLoai == 0) {
                new Dialog("Vui lòng chọn Loại sản phẩm!", Dialog.ERROR_DIALOG);
                return false;
            }
            if (ten.trim().equals("")) {
                new Dialog("Tên SP không được để rỗng!", Dialog.ERROR_DIALOG);
                return false;
            }
            if(phanTramLoiSP < 0 || phanTramLoiSP > 100) {
                new Dialog("Phần trăm lời không hợp lệ!", Dialog.ERROR_DIALOG);
                return false;
            }

            SanPham sp = getSanPham(ma);
            sp.setTenSP(ten);
            sp.setMaLoai(maLoai);
            sp.setSoLuong(soLuongSP);
            sp.setHinhAnh(anh);
            sp.setDonGia(donGiaSP);
            sp.setPhanTramLoi(phanTramLoiSP);

            if (spDAO.suaSanPham(sp)) {
                new Dialog("Sửa thành công!", Dialog.SUCCESS_DIALOG);
                return true;
            } else {
                new Dialog("Sửa thất bại!", Dialog.ERROR_DIALOG);
                return false;
            }
        } catch (Exception e) {
            new Dialog("Nhập số hợp lệ cho Đơn giá và Số lượng!", Dialog.ERROR_DIALOG);
        }
        return false;
    }

    public String getTenSP(int maSP) {
        for (SanPham sp : listSanPham) {
            if (sp.getMaSP() == maSP) {
                return sp.getTenSP();
            }
        }
        return "";
    }
    
    // Trung -> add function
    public String getMaSP() {
        return SanPhamDAO.getInstance().getMaSanPhamMoi();
    }
}
