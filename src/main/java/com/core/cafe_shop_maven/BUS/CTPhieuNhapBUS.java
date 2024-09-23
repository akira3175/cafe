package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.CTPhieuNhapDAO;
import com.core.cafe_shop_maven.DTO.CTPhieuNhap;
import com.core.cafe_shop_maven.DTO.SanPham;
import java.util.ArrayList;

public class CTPhieuNhapBUS {

    private static CTPhieuNhapBUS instance;

    public static CTPhieuNhapBUS getInstance() {
        if (instance == null)
            instance = new CTPhieuNhapBUS();
        return instance;
    }

    private ArrayList<CTPhieuNhap> listPhieuNhap;
    private CTPhieuNhapDAO ctPhieuNhapDAO;

    private CTPhieuNhapBUS() {
        ctPhieuNhapDAO = CTPhieuNhapDAO.getInstance();
        this.listPhieuNhap = ctPhieuNhapDAO.getListCTPhieuNhap();
    }

    public void docDanhSach() {
        this.listPhieuNhap = ctPhieuNhapDAO.getListCTPhieuNhap();
    }

    public ArrayList<CTPhieuNhap> getListPhieuNhap() {
        if (listPhieuNhap == null) {
            docDanhSach();
        }
        return listPhieuNhap;
    }

    public ArrayList<CTPhieuNhap> getListPhieuNhap(String maPN) {
        ArrayList<CTPhieuNhap> dsct = new ArrayList<>();
        int ma = Integer.parseInt(maPN);

        for (CTPhieuNhap ct : listPhieuNhap) {
            if (ct.getMaPN() == ma) {
                dsct.add(ct);
            }
        }
        return dsct;
    }

    public boolean luuCTPhieuNhap(CTPhieuNhap ctpn) {
        this.listPhieuNhap.add(ctpn);
        return ctPhieuNhapDAO.addCTPhieuNhap(ctpn);
    }
    
    // Trung -> add function 
    public int getTongSoLuongNhapCuaSanPhamById(int maSP) {
        return CTPhieuNhapDAO.getInstance().tongSoLuongNhapCuaSanPhamById(maSP);
    }
    
    // Trung -> add function lấy lô hàng tiếp theo
    public Object updateSoLuongVaMaPNTiepTheo(int maSP, int maPN, int soLuong) {
        // update lo hang dau tien cho sanpham moi
        if(maPN == 0) {
            Object temp = CTPhieuNhapDAO.getInstance().getLoHangDauTien(maSP, maPN);
            return temp;             
        }
        // update lo hang tiep theo cho sanpham hethang
        if(maPN != 0 && soLuong == 0) {
            Object temp = CTPhieuNhapDAO.getInstance().getLoHangTiepTheo(maSP, maPN);
            return temp;
        }           
        return null;
    }
}
