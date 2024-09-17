/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.DTO.KhachHang;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author User
 */
public class KhachHangDAO {
    private static KhachHangDAO instance;

    public static KhachHangDAO getInstance() {
        if (instance == null) {
            instance = new KhachHangDAO();
        }
        return instance;
    }

    private KhachHangDAO() {
    }

    public ArrayList<KhachHang> getListKhachHang() {
        try {
            String sql = "SELECT * FROM KhachHang WHERE TrangThai=1";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            ArrayList<KhachHang> dskh = new ArrayList<>();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getInt(1));
                kh.setTen(rs.getString(2));
                kh.setDiaChi(rs.getString(3));
                kh.setSdt(rs.getString(4));
                dskh.add(kh);
            }
            return dskh;
        } catch (SQLException ex) {
        }
        return null;
    }

    public KhachHang getKhachHang(int maKH) {
        KhachHang kh = null;
        try {
            String sql = "SELECT * FROM khachhang WHERE MaKH=? AND TrangThai=1";
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setInt(1, maKH);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                kh = new KhachHang();
                kh.setMaKH(rs.getInt(1));
                kh.setTen(rs.getString(2));
                kh.setDiaChi(rs.getString(3));
                kh.setSdt(rs.getString(4));
            }
        } catch (SQLException ex) {
            return null;
        }
        return kh;
    }

    public boolean addKhachHang(KhachHang kh) {
        boolean result = false;
        try {
            String sql = "INSERT INTO khachhang VALUES (?,?,?,?,1)";
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setInt(1, kh.getMaKH());
            prep.setString(2, kh.getTen());
            prep.setString(3, kh.getDiaChi());
            prep.setString(4, kh.getSdt());

            result = prep.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean deleteKhachHang(int maKH) {
        boolean result = false;
        try {
            String sql = "UPDATE khachhang SET TrangThai=0 WHERE MaKH=?";
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setInt(1, maKH);
            result = prep.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean updateKhachHang(int maKH, KhachHang kh) {
        boolean result = false;
        try {
            String sql = "UPDATE khachhang SET TenKH=?, DiaChi=?, SDT=? WHERE MaKH=?";
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setString(1, kh.getTen());
            prep.setString(2, kh.getDiaChi());
            prep.setString(3, kh.getSdt());
            prep.setInt(4, maKH);
            result = prep.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }
}
