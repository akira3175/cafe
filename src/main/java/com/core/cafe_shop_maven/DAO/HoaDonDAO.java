package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.DTO.HoaDon;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HoaDonDAO {
    private static HoaDonDAO instance;

    public static HoaDonDAO getInstance() {
        if (instance == null)
            instance = new HoaDonDAO();
        return instance;
    }

    private HoaDonDAO() {
    }

    public ArrayList<HoaDon> getListHoaDon() {
        ArrayList<HoaDon> dshd = new ArrayList<>();
        try {
            String sql = "SELECT * FROM hoadon";
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getInt(1));
                hd.setNgayLap(rs.getString(2));
                hd.setTongTien(rs.getInt(4));
                hd.setMaKH(rs.getInt(5));
                hd.setMaKM(rs.getInt(6));
                hd.setMaNV(rs.getInt(7));
                dshd.add(hd);
            }
        } catch (SQLException ex) {
            return null;
        }
        return dshd;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public boolean addHoaDon(HoaDon hd) {
        boolean result = false;
        try {
            String sql = "INSERT INTO hoadon(MaKH, MaNV, NgayLap, GioLap, TongTien, TrangThai) VALUES(?, ?, ?, ?, ?, 1)";
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setInt(1, hd.getMaKH());
            prep.setInt(2, hd.getMaNV());
            Date date = new Date();
            prep.setString(3, sdf.format(date));
            prep.setString(4, sdf.format(date));
            prep.setInt(5, hd.getTongTien());
            result = prep.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return result;
    }

    public int getMaHoaDonMoiNhat() {
        try {
            String sql = "SELECT MAX(maHD) FROM hoadon";
            Statement st = MyConnect.conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<HoaDon> getListHoaDon(String dateMin, String dateMax) {
        try {
            String sql = "SELECT * FROM hoadon WHERE NgayLap >= ? AND NgayLap <= ?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, dateMin);
            pre.setString(2, dateMax);
            ResultSet rs = pre.executeQuery();

            ArrayList<HoaDon> dshd = new ArrayList<>();

            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getInt(1));
                hd.setNgayLap(rs.getString(2));
                hd.setTongTien(rs.getInt(4));
                hd.setMaKH(rs.getInt(5));
                hd.setMaKM(rs.getInt(6));
                hd.setMaNV(rs.getInt(7));
                dshd.add(hd);
            }
            return dshd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
