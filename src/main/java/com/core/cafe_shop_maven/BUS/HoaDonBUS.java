package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.HoaDonDAO;
import com.core.cafe_shop_maven.DTO.HoaDon;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HoaDonBUS {
    private ArrayList<HoaDon> listHoaDon;
    private HoaDonDAO hoaDonDAO;

    private static HoaDonBUS instance;

    public static HoaDonBUS getInstance() {

        if (instance == null)
            instance = new HoaDonBUS();
        return instance;
    }

    private HoaDonBUS() {
        hoaDonDAO = HoaDonDAO.getInstance();
        listHoaDon = hoaDonDAO.getListHoaDon();
    }

    public ArrayList<HoaDon> getListHoaDon() {
        listHoaDon = hoaDonDAO.getListHoaDon();
        return listHoaDon;
    }

    public void luuHoaDon(int maKH, String nhanVien, int tongTien) {
        HoaDon hd = new HoaDon();
        String[] arrNV = nhanVien.split(" - ");
        int maNV = Integer.parseInt(arrNV[0]);
        hd.setMaNV(maNV);
        hd.setMaKH(maKH);
        hd.setTongTien(tongTien);

        hoaDonDAO.addHoaDon(hd);
    }

    public int getMaHoaDonMoiNhat() {
        return hoaDonDAO.getMaHoaDonMoiNhat();
    }

    public HoaDon getHoaDon(String maHD) {
        int ma = Integer.parseInt(maHD);
        for (HoaDon hd : listHoaDon) {
            if (hd.getMaHD() == ma)
                return hd;
        }
        return null;
    }

    public ArrayList<HoaDon> getListHoaDonTheoGia(String min, String max) {
        try {
            int minPrice = Integer.parseInt(min);
            int maxPrice = Integer.parseInt(max);
            ArrayList<HoaDon> dshd = new ArrayList<>();
            for (HoaDon hd : listHoaDon) {
                if (hd.getTongTien() > minPrice && hd.getTongTien() < maxPrice)
                    dshd.add(hd);
            }
            return dshd;
        } catch (Exception e) {
            new Dialog("Hãy nhập khoảng giá hợp lệ", Dialog.ERROR_DIALOG);
        }
        return null;
    }

    public ArrayList<HoaDon> getListHoaDonTheoNgay(String min, String max) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date minDate = sdf.parse(min);
            Date maxDate = sdf.parse(max);

            sdf = new SimpleDateFormat("yyyy-MM-dd");

            min = sdf.format(minDate) + " 00:00:00";
            max = sdf.format(maxDate) + " 23:59:59";

            ArrayList<HoaDon> dshd = hoaDonDAO.getListHoaDon(min, max);
            return dshd;
        } catch (Exception e) {
            new Dialog("Hãy nhập khoảng ngày hợp lệ theo định dạng dd/MM/yyyy!", Dialog.ERROR_DIALOG);
        }
        return null;
    }
}
