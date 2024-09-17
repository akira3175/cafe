package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.DTO.NhaCungCap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NhaCungCapDAO {
    private static NhaCungCapDAO instance;

    public static NhaCungCapDAO getInstance() {
        if (instance == null) {
            instance = new NhaCungCapDAO();
        }
        return instance;
    }

    private NhaCungCapDAO() {
    }

    public ArrayList<NhaCungCap> getListNhaCungCap() {
        try {
            ArrayList<NhaCungCap> dsncc = new ArrayList<>();
            String sql = "SELECT * FROM nhacungcap";
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(rs.getInt(1));
                ncc.setTenNCC(rs.getString(2));
                ncc.setDiaChi(rs.getString(3));
                ncc.setDienThoai(rs.getString(4));
                ncc.setFax(rs.getString(5));
                ncc.setTrangThai(rs.getInt(6));
                dsncc.add(ncc);
            }
            return dsncc;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public NhaCungCap getNhaCungCap(int maNCC) {
        NhaCungCap ncc = null;
        try {
            String sql = "SELECT * FROM nhacungcap WHERE MaNCC=" + maNCC;
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ncc = new NhaCungCap();
                ncc.setMaNCC(rs.getInt(1));
                ncc.setTenNCC(rs.getString(2));
                ncc.setDiaChi(rs.getString(3));
                ncc.setDienThoai(rs.getString(4));
                ncc.setFax(rs.getString(5));
                ncc.setTrangThai(rs.getInt(6));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return ncc;
    }

    public boolean addNCC(NhaCungCap ncc) {
        boolean result = false;
        try {
            String sql = "INSERT INTO nhacungcap (TenNCC, DiaChi, SDT, Fax, TrangThai) VALUES (?,?,?,?,'1')";
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setString(1, ncc.getTenNCC());
            prep.setString(2, ncc.getDiaChi());
            prep.setString(3, ncc.getDienThoai());
            prep.setString(4, ncc.getFax());
            result = prep.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return result;
    }

    public boolean updateNCC(NhaCungCap ncc) {
        boolean result = false;
        try {
            String sql = "UPDATE nhacungcap SET TenNCC=?, DiaChi=?, SDT=?, Fax=? WHERE TrangThai = 1 AND MaNCC=?";
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setString(1, ncc.getTenNCC());
            prep.setString(2, ncc.getDiaChi());
            prep.setString(3, ncc.getDienThoai());
            prep.setString(4, ncc.getFax());

            prep.setInt(5, ncc.getMaNCC());
            result = prep.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return result;
    }

    public boolean deleteNCC(int maNCC) {
        boolean result = false;
        try {
            String sql = "UPDATE nhacungcap "
                    + "SET TrangThai = 0"
                    + "WHERE MaNCC=" + maNCC;
            Statement stmt = MyConnect.conn.createStatement();
            result = stmt.executeUpdate(sql) > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return result;
    }
}
