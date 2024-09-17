package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.CTPhieuNhapDAO;
import com.core.cafe_shop_maven.DTO.CTPhieuNhap;
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
}
