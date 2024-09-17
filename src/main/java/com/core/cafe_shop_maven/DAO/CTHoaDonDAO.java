/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.DTO.CTHoaDon;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class CTHoaDonDAO {

    private static CTHoaDonDAO instance;

    public static CTHoaDonDAO getInstance() {
        if (instance == null) {
            instance = new CTHoaDonDAO();
        }
        return instance;
    }

    private CTHoaDonDAO() {
    }

    public ArrayList<CTHoaDon> getListCTHoaDon() {
        ArrayList<CTHoaDon> dscthd = new ArrayList<>();
        try {
            String sql = "SELECT * FROM chitiethoadon";
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CTHoaDon cthd = new CTHoaDon();
                cthd.setDonGia(rs.getInt(1));
                cthd.setSoLuong(rs.getInt(2));
                cthd.setMaHD(rs.getInt(3));
                cthd.setMaSP(rs.getInt(4));
                dscthd.add(cthd);
            }
        } catch (SQLException ex) {
        }
        return dscthd;
    }

    public ArrayList<CTHoaDon> getListCTHoaDonTheoMaHD(int maHD) {
        ArrayList<CTHoaDon> dscthd = new ArrayList<>();
        try {
            String sql = "SELECT * FROM chitiethoadon WHERE MaHD=" + maHD;
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CTHoaDon cthd = new CTHoaDon();
                cthd.setDonGia(rs.getInt(1));
                cthd.setSoLuong(rs.getInt(2));
                cthd.setMaHD(rs.getInt(3));
                cthd.setMaSP(rs.getInt(4));
                dscthd.add(cthd);
            }
        } catch (SQLException ex) {
            return null;
        }
        return dscthd;
    }

    public ArrayList<CTHoaDon> getListCTHoaDonTheoMaSP(int maSP) {
        ArrayList<CTHoaDon> dscthd = new ArrayList<>();
        try {
            String sql = "SELECT * FROM chitiethoadon WHERE MaSP=" + maSP;
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CTHoaDon cthd = new CTHoaDon();
                cthd.setDonGia(rs.getInt(1));
                cthd.setSoLuong(rs.getInt(2));
                cthd.setMaHD(rs.getInt(3));
                cthd.setMaSP(rs.getInt(4));

                dscthd.add(cthd);
            }
        } catch (SQLException ex) {
            return null;
        }
        return dscthd;
    }

    public boolean addCTHoaDon(CTHoaDon cthd) {
        boolean result = false;
        try {
            String sql = "INSERT INTO chitiethoadon VALUES(?,?,?,?)";
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setInt(1, cthd.getDonGia());
            prep.setInt(2, cthd.getSoLuong());
            prep.setInt(3, cthd.getMaHD());
            prep.setInt(4, cthd.getMaSP());

            result = prep.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return result;
    }

    public boolean updateCTHoaDon(int maHD, int maSP, CTHoaDon cthd) {
        boolean result = false;
        try {
            String sql = "UPDATE chitiethoadon SET MaHD=?, MaSP=?, SoLuong=?, DonGia=? "
                    + "WHERE MaHD=? AND MaSP=?";
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setInt(1, cthd.getMaHD());
            prep.setInt(2, cthd.getMaSP());
            prep.setInt(3, cthd.getSoLuong());
            prep.setInt(4, cthd.getDonGia());
            prep.setInt(5, maHD);
            prep.setInt(6, maSP);

            result = prep.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }
}
