package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.CustomFunctions.Dialog;
import com.core.cafe_shop_maven.DAO.PhieuNhapDAO;
import com.core.cafe_shop_maven.DTO.PhieuNhap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhieuNhapBUS {

    private PhieuNhapDAO phieuNhapDAO;
    private ArrayList<PhieuNhap> listPhieuNhap;

    private static PhieuNhapBUS instance;

    public static PhieuNhapBUS getInstance() {
        if (instance == null)
            instance = new PhieuNhapBUS();
        return instance;
    }

    private PhieuNhapBUS() {
        phieuNhapDAO = PhieuNhapDAO.getInstance();
        this.listPhieuNhap = phieuNhapDAO.getListPhieuNhap();
    }

    public void docDanhSach() {
        this.listPhieuNhap = phieuNhapDAO.getListPhieuNhap();
    }

    public ArrayList<PhieuNhap> getListPhieuNhap() {
        if (listPhieuNhap == null) {
            docDanhSach();
        }
        return listPhieuNhap;
    }

    public boolean themPhieuNhap(String nhaCungCap, String nhanVien, int tongTien) {
        String[] NCC = nhaCungCap.split(" - ");
        String[] NV = nhanVien.split(" - ");

        int maNCC = Integer.parseInt(NCC[0]);
        int maNV = Integer.parseInt(NV[0]);

        PhieuNhap pn = new PhieuNhap();
        pn.setMaNCC(maNCC);
        pn.setMaNV(maNV);
        pn.setTongTien(tongTien);

        return phieuNhapDAO.themPhieuNhap(pn);
    }

    public int getLastID() {
        return phieuNhapDAO.getLastID();
    }

    public PhieuNhap timPhieuNhap(String maPN) {
        int ma = Integer.parseInt(maPN);
        for (PhieuNhap pn : listPhieuNhap) {
            if (pn.getMaPN() == ma) {
                return pn;
            }
        }
        return null;
    }

    public ArrayList<PhieuNhap> getListPhieuNhapTheoGia(String giaThap, String giaCao) {
        try {
            int min = Integer.parseInt(giaThap);
            int max = Integer.parseInt(giaCao);
            if (max < min) {
                new Dialog("Hãy nhập khoảng giá phù hợp!", Dialog.ERROR_DIALOG);
                return null;
            }
            ArrayList<PhieuNhap> result = new ArrayList<>();
            for (PhieuNhap pn : listPhieuNhap) {
                if (pn.getTongTien() <= max && pn.getTongTien() >= min) {
                    result.add(pn);
                }
            }
            return result;
        } catch (Exception e) {
            new Dialog("Hãy nhập số nguyên cho khoảng giá!", Dialog.ERROR_DIALOG);
        }
        return null;
    }

    public ArrayList<PhieuNhap> getListPhieuNhapTheoNgay(String tuNgay, String denNgay) {
        Date min, max;
        String minSt, maxSt;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            min = sdf.parse(tuNgay);
            max = sdf.parse(denNgay);

            sdf = new SimpleDateFormat("yyyy-MM-dd");

            minSt = sdf.format(min);
            maxSt = sdf.format(max);

            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            min = sdf.parse(minSt + " 00:00:00");
            max = sdf.parse(maxSt + " 23:59:59");

            if (max.before(min)) {
                new Dialog("Hãy nhập khoảng ngày phù hợp theo định dạng dd/MM/yyyy!", Dialog.ERROR_DIALOG);
                return null;
            }
            ArrayList<PhieuNhap> result = new ArrayList<>();
            for (PhieuNhap pn : listPhieuNhap) {
                Date thoiGianNhap = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pn.getNgayLap());
                if (thoiGianNhap.after(min) && thoiGianNhap.before(max)) {
                    result.add(pn);
                }
            }
            return result;
        } catch (Exception e) {
            new Dialog("Hãy nhập ngày hợp lệ theo định dạng dd/MM/yyyy!", Dialog.ERROR_DIALOG);
        }
        return null;
    }

}
