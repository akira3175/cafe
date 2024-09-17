package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.DTO.LoaiSP;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoaiDAO {
    private static LoaiDAO instance;

    public static LoaiDAO getInstance() {
        if (instance == null) {
            instance = new LoaiDAO();
        }
        return instance;
    }

    private LoaiDAO() {
    }

    public ArrayList<LoaiSP> getDanhSachLoai() {
        try {
            String sql = "SELECT * FROM loaisanpham WHERE TrangThai = 1";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            ArrayList<LoaiSP> dsl = new ArrayList<>();
            while (rs.next()) {
                LoaiSP loai = new LoaiSP();
                loai.setMaLoai(rs.getInt(1));
                loai.setTenLoai(rs.getString(2));
                loai.setMota(rs.getString(3));
                loai.setTrangThai(rs.getInt(4));
                dsl.add(loai);
            }
            return dsl;
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean themLoai(LoaiSP loai) {
        try {
            String sql = "INSERT INTO loaisanpham(TenLSP, Mota, TrangThai) "
                    + "values (?, ?, 1)";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, loai.getTenLoai());
            pre.setString(2, loai.getMota());
            int x = pre.executeUpdate();
            return x > 0 ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(LoaiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean xoaLoai(int maLoai) {
        try {
            String sql = "DELETE FROM loaisanpham "
                    + "WHERE MaLSP=" + maLoai;
            Statement st = MyConnect.conn.createStatement();
            int x = st.executeUpdate(sql);
            return x > 0 ? true : false;
        } catch (SQLException e) {
        }
        return false;
    }

    public boolean suaLoai(int maLoai, String ten, String Mota) {
        try {
            String sql = "UPDATE loaisanpham "
                    + "SET TenLSP=?,"
                    + "SET Mota=?,"
                    + "WHERE MaLSP=" + maLoai;
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, ten);
            pre.setString(2, Mota);

            int x = pre.executeUpdate();
            return x > 0 ? true : false;
        } catch (SQLException e) {
        }
        return false;
    }

}
