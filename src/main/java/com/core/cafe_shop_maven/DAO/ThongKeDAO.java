package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.BUS.SanPhamBUS;
import com.core.cafe_shop_maven.DTO.SanPham;
import com.core.cafe_shop_maven.DTO.ThongKe;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author User
 */
public class ThongKeDAO {
    private static ThongKeDAO instance;

    public static ThongKeDAO getInstance() {
        if (instance == null) {
            instance = new ThongKeDAO();
        }
        return instance;
    }

    private ThongKeDAO() {
    }

    public ThongKe getThongKe(int nam) {
        ThongKe thongKe = new ThongKe();
        int[] tongThuQuy = new int[4];
        thongKe.setSoLuongSP(getTongSoLuongSP());
        thongKe.setSoLuongKH(getSoLuongKhachHang());
        thongKe.setSoLuongNV(getSoLuongNhanVien());
        tongThuQuy[0] = getTongThuQuy(nam, 1);
        tongThuQuy[1] = getTongThuQuy(nam, 2);
        tongThuQuy[2] = getTongThuQuy(nam, 3);
        tongThuQuy[3] = getTongThuQuy(nam, 4);
        thongKe.setTongThuQuy(tongThuQuy);
        thongKe.setTopSanPhamBanChay(getTopBanChay());
        return thongKe;
    }

    private ResultSet getTopBanChay() {
        try {
            String sql = "SELECT MaSP, DaBan FROM (SELECT MaSP, SUM(SoLuong) AS DaBan FROM chitiethoadon GROUP BY MaSP) temp ORDER BY DaBan DESC LIMIT 5";
            Statement st = MyConnect.conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getTongSoLuongSP() {
        try {
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM sanpham");
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            return -1;
        }
        return 0;
    }

    private String[] thangTrongQuy(int quy) {
        String[] kq = new String[2];
        switch (quy) {
            case 1:
                kq[0] = "1";
                kq[1] = "3";
                break;
            case 2:
                kq[0] = "4";
                kq[1] = "6";
                break;
            case 3:
                kq[0] = "7";
                kq[1] = "9";
                break;
            case 4:
                kq[0] = "10";
                kq[1] = "12";
                break;
            default:
                break;
        }
        return kq;
    }

    private int getTongThuQuy(int nam, int quy) {
        String[] thangTrongQuy = thangTrongQuy(quy);
        try {
            PreparedStatement prep = MyConnect.conn.prepareStatement("SELECT SUM(TongTien) FROM hoadon "
                    + "WHERE MONTH(NgayLap) BETWEEN ? AND ? AND YEAR(NgayLap) = ?");
            prep.setString(1, thangTrongQuy[0]);
            prep.setString(2, thangTrongQuy[1]);
            prep.setInt(3, nam);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            return -1;
        }
        return 0;
    }

    private int getSoLuongNhanVien() {
        try {
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM nhanvien");
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            return -1;
        }
        return 0;
    }

    private int getSoLuongKhachHang() {
        try {
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM khachhang");
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            return -1;
        }
        return 0;
    }

    public double getDoanhThuThang(int thang, int nam) {
        try {
            String thangBD = nam + "-" + thang + "-01";
            String thangKT = nam + "-" + (thang + 1) + "-01";
            String sql = "SELECT SUM(TongTien) FROM HoaDon WHERE NgayLap BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, thangBD);
            pre.setString(2, thangKT);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return Double.parseDouble(rs.getInt(1) + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nam;
    }

    public double abc(int thang, int nam) {
        try {
            String d1 = nam + "-" + thang + "-01";
            String d2 = nam + "-" + (thang + 1) + "-01";
            String sql = "SELECT SUM(TongTien) FROM HoaDon WHERE NgayLap BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)";

            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, d1);
            pre.setString(2, d2);
            ResultSet rs = pre.executeQuery();

            while (rs.next())
                return rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0f;
    }
}
