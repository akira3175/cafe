package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.DTO.NhanVien;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhanVienDAO {
    private static NhanVienDAO instance;

    public static NhanVienDAO getInstance() {
        if (instance == null) {
            instance = new NhanVienDAO();
        }
        return instance;
    }

    private NhanVienDAO() {
    }

    public ArrayList<NhanVien> getDanhSachNhanVien() {
        try {
            String sql = "SELECT * FROM nhanvien WHERE TrangThai = 1";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            ArrayList<NhanVien> dssv = new ArrayList<>();
            while (rs.next()) {
                NhanVien nv = new NhanVien();

                nv.setMaNV(rs.getInt(1));
                nv.setTen(rs.getString(2));
                nv.setNgaySinh(rs.getString(3));
                nv.setDiaChi(rs.getString(4));
                nv.setSdt(rs.getString(5));
                nv.setMaTK(rs.getInt(7));

                dssv.add(nv);
            }
            return dssv;
        } catch (SQLException e) {
        }

        return null;
    }

    public NhanVien getNhanVien(int maNV) {
        NhanVien nv = null;
        try {
            String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setInt(1, maNV);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                nv = new NhanVien();
                nv.setMaNV(rs.getInt(1));
                nv.setTen(rs.getString(2));
                nv.setNgaySinh(rs.getString(3));
                nv.setDiaChi(rs.getString(4));
                nv.setSdt(rs.getString(5));
                nv.setMaTK(rs.getInt(7));
            }
        } catch (SQLException e) {
            return null;
        }

        return nv;
    }

    public NhanVien getNhanVienTheoMaTK(int maTK) {
        NhanVien nv = null;
        try {
            String sql = "SELECT * FROM NhanVien WHERE MaTK=?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setInt(1, maTK);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                nv = new NhanVien();
                nv.setMaNV(rs.getInt(1));
                nv.setTen(rs.getString(2));
                nv.setNgaySinh(rs.getString(3));
                nv.setDiaChi(rs.getString(4));
                nv.setSdt(rs.getString(5));
                nv.setMaTK(rs.getInt(7));
            }
        } catch (SQLException e) {
            return null;
        }

        return nv;
    }

    public boolean updateNhanVien(NhanVien nv) {
        boolean result = false;
        try {
            String sql = "UPDATE nhanvien SET TenNV=?, NgaySinh=?, DiaChi=?, SDT=? WHERE MaNV=?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, nv.getTen());
            pre.setString(2, nv.getNgaySinh());
            pre.setString(3, nv.getDiaChi());
            pre.setString(4, nv.getSdt());
            pre.setInt(5, nv.getMaNV());
            result = pre.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean updateTaiKhoanNV(int maNV, String tenTaiKhoan) {
        boolean result = false;
        try {
            String sql = "UPDATE nhanvien SET MaTK=? WHERE MaNV=?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);

            pre.setInt(1, TaiKhoanDAO.getInstance().getMaTK(tenTaiKhoan));
            pre.setInt(2, maNV);

            result = pre.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean deleteNhanVien(int maNV) {
        boolean result = false;
        try {
            String sql = "UPDATE nhanvien "
                    + "SET TrangThai = 0 "
                    + "WHERE MaNV=?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setInt(1, maNV);
            result = pre.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean themNhanVien(NhanVien nv) {
        boolean result = false;
        try {
            String sql = "INSERT INTO nhanvien(TenNV, NgaySinh, DiaChi, SDT, MaTK, TrangThai) " +
                    "VALUES(?, ?, ?, ?, -1, 1)";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, nv.getTen());
            pre.setString(2, nv.getNgaySinh());
            pre.setString(3, nv.getDiaChi());
            pre.setString(4, nv.getSdt());
            result = pre.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean deleteAllNhanVien() {
        boolean result = false;
        try {
            String sql = "DELETE * FROM NhanVien;";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            result = pre.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean nhapExcel(NhanVien nv) {
        try {
            String sql = "INSERT INTO nhanvien(TenNV, NgaySinh, DiaChi, SDT, MaTK, TrangThai) " +
                    "VALUES(?, ?, ?, ?, -1, 1)";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, nv.getTen());
            pre.setString(2, nv.getNgaySinh());
            pre.setString(3, nv.getDiaChi());
            pre.setString(4, nv.getSdt());
            return true;
        } catch (SQLException ex) {
        }
        return false;
    }
}
