package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.DTO.PhieuNhap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhieuNhapDAO {
    private static PhieuNhapDAO instance;

    public static PhieuNhapDAO getInstance() {
        if (instance == null) {
            instance = new PhieuNhapDAO();
        }
        return instance;
    }

    private PhieuNhapDAO() {
    }

    public ArrayList<PhieuNhap> getListPhieuNhap() {
        ArrayList<PhieuNhap> dspn = new ArrayList<>();
        try {
            String sql = "SELECT * FROM phieunhap WHERE TrangThai = 1";
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PhieuNhap pn = new PhieuNhap();
                pn.setMaPN(rs.getInt(1));
                pn.setNgayLap(rs.getString(2));
                pn.setTongTien(rs.getInt(3));
                pn.setMaNCC(rs.getInt(4));
                pn.setMaNV(rs.getInt(5));
                dspn.add(pn);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
        return dspn;
    }

    public boolean themPhieuNhap(PhieuNhap pn) {
        boolean result = false;
        try {
            String sql = "INSERT INTO phieunhap(MaNCC, MaNV, ThoiGianNhap, TongTien, TrangThai) "
                    + "VALUES(?,?,?,?,1)";
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setInt(1, pn.getMaNCC());
            prep.setInt(2, pn.getMaNV());

            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = format.format(date);

            prep.setString(3, currentDateTime);
            prep.setInt(4, pn.getTongTien());
            result = prep.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public PhieuNhap getPhieuNhap(int maPN) {
        PhieuNhap pn = null;
        try {
            String sql = "SELECT * FROM phieunhap WHERE TrangThai = 1 AND MaPN=" + maPN;
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                pn = new PhieuNhap();
                pn.setMaPN(rs.getInt(1));
                pn.setNgayLap(rs.getString(2));
                pn.setTongTien(rs.getInt(3));
                pn.setMaNCC(rs.getInt(2));
                pn.setMaNV(rs.getInt(3));
            }
        } catch (SQLException ex) {
            return null;
        }
        return pn;
    }

    public boolean deletePhieuNhap(int maPN) {
        boolean result = false;
        try {
            String sql = "UPDATE phieunhap "
                    + "SET TrangThai = 0"
                    + "WHERE MaPN=" + maPN;
            Statement stmt = MyConnect.conn.createStatement();
            result = stmt.executeUpdate(sql) > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean updatePhieuNhap(int maPN, PhieuNhap pn) {
        boolean result = false;
        try {
            String sql = "UPDATE phieunhap "
                    + "SET MaPN=?, "
                    + "MaNCC=?, "
                    + "MaNV=?, "
                    + "ThoiGianNhap=?, "
                    + "TongTien=? "
                    + "WHERE MaPN=" + maPN;
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setInt(1, pn.getMaPN());
            prep.setInt(2, pn.getMaNCC());
            prep.setInt(3, pn.getMaNV());
            prep.setString(4, pn.getNgayLap());
            prep.setInt(5, pn.getTongTien());
            result = prep.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public int getLastID() {
        try {
            String sql = "SELECT MAX(maPN) FROM phieunhap";
            Statement st = MyConnect.conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
