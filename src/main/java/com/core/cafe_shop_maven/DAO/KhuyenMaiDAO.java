package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.DTO.KhuyenMai;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public KhuyenMai getKhuyenMai(int maKM) {
        try {
            String sql = "SELECT * FROM khuyenmai WHERE MaKM=?";
            PreparedStatement ps = MyConnect.conn.prepareStatement(sql);
            ps.setInt(1, maKM);
            ResultSet rs = ps.executeQuery();
            KhuyenMai km = new KhuyenMai();
            while (rs.next()) {
                km.setMaKM(rs.getInt(1));
                km.setTenKM(rs.getString(2));
                km.setDieuKien(rs.getInt(3));
                km.setNgayBD(rs.getString(4));
                km.setPhanTramKM(rs.getInt(5));
                km.setNgayKT(rs.getString(6));
            }
            return km;
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

    public KhuyenMai getKhuyenMaiHopLe(int tongTien) {
        KhuyenMai km = null; // Khởi tạo biến km bên ngoài try-catch
        try {
            System.out.println(">>> TongTien = " + tongTien);
            String sql = "SELECT MaKM, TenKM, MAX(PhanTramKM) FROM khuyenmai WHERE ? >= DieuKienKM GROUP BY MaKM, TenKM ORDER BY PhanTramKM DESC LIMIT 1";
            PreparedStatement pst = MyConnect.conn.prepareStatement(sql);
            pst.setInt(1, tongTien);
            ResultSet rs = pst.executeQuery(); // Chỉ gọi executeQuery() không cần tham số sql

            if (rs.next()) { // Chỉ cần kiểm tra một lần
                km = new KhuyenMai();
                km.setMaKM(rs.getInt(1));
                km.setTenKM(rs.getString(2));
                km.setPhanTramKM(rs.getInt(3));
            }
        } catch (Exception error) {
            System.out.println(">>> KhuyenMaiDAO -> getKhuyenMaiHopLe = " + error);
        }
        return km;
    }

    public boolean tenMaCoTonTaiKhong(String tenKM) {
        try {
            String sql = "SELECT COUNT(*) FROM khuyenmai WHERE TenKM=?";
            PreparedStatement ps = MyConnect.conn.prepareStatement(sql);
            ps.setString(1, tenKM);
            ResultSet rs = ps.executeQuery();
            rs.next();
            boolean exists = rs.getInt(1) > 0;
            rs.close();
            ps.close();
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
