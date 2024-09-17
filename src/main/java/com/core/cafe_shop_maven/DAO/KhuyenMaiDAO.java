package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.DTO.KhuyenMai;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class KhuyenMaiDAO {

    private static KhuyenMaiDAO instance;

    public static KhuyenMaiDAO getInstance() {
        if (instance == null) {
            instance = new KhuyenMaiDAO();
        }
        return instance;
    }

    private KhuyenMaiDAO() {
    }

    public ArrayList<KhuyenMai> getDanhSachKhuyenMai() {
        try {
            String sql = "SELECT * FROM khuyenmai WHERE TrangThai = 1";
            Statement st = MyConnect.conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ArrayList<KhuyenMai> dskm = new ArrayList<>();
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKM(rs.getInt(1));
                km.setTenKM(rs.getString(2));
                km.setDieuKien(rs.getInt(3));
                km.setNgayBD(rs.getString(4));
                km.setPhanTramKM(rs.getInt(5));
                km.setNgayKT(rs.getString(6));
                dskm.add(km);
            }
            return dskm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean themMaGiam(KhuyenMai km) {
        try {
            String sql = "INSERT INTO khuyenmai(TenKM, PhanTramKM, DieuKienKM, NgayBD, NgayKT, TrangThai) " +
                    "VALUES (?, ?, ?, ?, ?, 1)";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, km.getTenKM());
            pre.setInt(2, km.getPhanTramKM());
            pre.setInt(3, km.getDieuKien());

            pre.setString(4, km.getNgayBD());
            pre.setString(5, km.getNgayKT());

            return pre.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean suaMaGiam(KhuyenMai km) {
        try {
            String sql = "UPDATE khuyenmai SET TenKM=?, PhanTramKM=?, DieuKienKM=?, NgayBD=?, NgayKT=? WHERE TrangThai = 1 AND MaKM=?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, km.getTenKM());
            pre.setInt(2, km.getPhanTramKM());
            pre.setInt(3, km.getDieuKien());

            pre.setString(4, km.getNgayBD());
            pre.setString(5, km.getNgayKT());

            pre.setInt(6, km.getMaKM());
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
