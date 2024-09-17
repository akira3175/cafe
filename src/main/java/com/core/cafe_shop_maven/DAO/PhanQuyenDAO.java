package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.DTO.PhanQuyen;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PhanQuyenDAO {
    private static PhanQuyenDAO instance;

    public static PhanQuyenDAO getInstance() {
        if (instance == null) {
            instance = new PhanQuyenDAO();
        }
        return instance;
    }

    private PhanQuyenDAO() {
    }

    private String[] chitietquyen;

    public ArrayList<PhanQuyen> getListQuyen() {
        try {
            String sql = "SELECT * FROM phanquyen WHERE TrangThai = 1";
            Statement st = MyConnect.conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ArrayList<PhanQuyen> dspq = new ArrayList<>();
            while (rs.next()) {
                PhanQuyen phanQuyen = new PhanQuyen();
                phanQuyen.setMaQuyen(rs.getInt(1));
                phanQuyen.setQuyen(rs.getString(2));

                chitietquyen = rs.getString(3).split(" ");

                phanQuyen.setNhapHang(0);
                phanQuyen.setQlSanPham(0);
                phanQuyen.setQlNhanVien(0);
                phanQuyen.setQlKhachHang(0);
                phanQuyen.setThongKe(0);

                for (String ctq : chitietquyen) {
                    if ("NhapHang".equals(ctq))
                        phanQuyen.setNhapHang(1);

                    if ("QLSanPham".equals(ctq))
                        phanQuyen.setQlSanPham(1);

                    if ("QLNhanVien".equals(ctq))
                        phanQuyen.setQlNhanVien(1);

                    if ("QLKhachHang".equals(ctq))
                        phanQuyen.setQlKhachHang(1);

                    if ("ThongKe".equals(ctq))
                        phanQuyen.setThongKe(1);
                }
                dspq.add(phanQuyen);
            }
            return dspq;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PhanQuyen getQuyen(String quyen) {
        try {
            String sql = "SELECT * FROM PhanQuyen WHERE TenQuyen='" + quyen + "'";
            Statement st = MyConnect.conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                PhanQuyen phanQuyen = new PhanQuyen();

                phanQuyen.setMaQuyen(rs.getInt(1));
                phanQuyen.setQuyen(rs.getString(2));

                chitietquyen = rs.getString(3).split(" ");

                phanQuyen.setNhapHang(0);
                phanQuyen.setQlSanPham(0);
                phanQuyen.setQlNhanVien(0);
                phanQuyen.setQlKhachHang(0);
                phanQuyen.setThongKe(0);

                for (String ctq : chitietquyen) {
                    if ("NhapHang".equals(ctq))
                        phanQuyen.setNhapHang(1);

                    if ("QLSanPham".equals(ctq))
                        phanQuyen.setQlSanPham(1);

                    if ("QLNhanVien".equals(ctq))
                        phanQuyen.setQlNhanVien(1);

                    if ("QLKhachHang".equals(ctq))
                        phanQuyen.setQlKhachHang(1);

                    if ("ThongKe".equals(ctq))
                        phanQuyen.setThongKe(1);
                }

                return phanQuyen;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getMaQuyen(String quyen) {
        try {
            String sql = "SELECT * FROM PhanQuyen WHERE TenQuyen='" + quyen + "'";
            Statement st = MyConnect.conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("MaQuyen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean suaQuyen(PhanQuyen phanQuyen) {
        try {
            String sql = "UPDATE phanquyen SET ChiTietQuyen = ? WHERE TenQuyen=?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);

            String ctq = "";
            if (phanQuyen.getNhapHang() == 1)
                ctq += "NhapHang ";

            if (phanQuyen.getQlSanPham() == 1)
                ctq += "QLSanPham ";

            if (phanQuyen.getQlNhanVien() == 1)
                ctq += "QLNhanVien ";

            if (phanQuyen.getQlKhachHang() == 1)
                ctq += "QLKhachHang ";

            if (phanQuyen.getThongKe() == 1)
                ctq += "ThongKe ";

            pre.setString(1, ctq);
            pre.setString(2, phanQuyen.getQuyen());
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean themQuyen(PhanQuyen phanQuyen) {
        try {
            String sql = "INSERT INTO phanquyen (TenQuyen, ChiTietQuyen, TrangThai) "
                    + "VALUES (?, ?, 1)";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, phanQuyen.getQuyen());

            String ctq = "";
            if (phanQuyen.getNhapHang() == 1)
                ctq += "NhapHang ";

            if (phanQuyen.getQlSanPham() == 1)
                ctq += "QLSanPham ";

            if (phanQuyen.getQlNhanVien() == 1)
                ctq += "QLNhanVien ";

            if (phanQuyen.getQlKhachHang() == 1)
                ctq += "QLKhachHang ";

            if (phanQuyen.getThongKe() == 1)
                ctq += "ThongKe ";

            pre.setString(2, ctq);
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaQuyen(String phanQuyen) {
        try {
            String sql = "UPDATE phanquyen "
                    + "SET TrangThai=0 "
                    + "WHERE TenQuyen='" + phanQuyen + "'";
            Statement st = MyConnect.conn.createStatement();
            return st.executeUpdate(sql) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean kiemTraMaQuyenCoTaiKhoanNaoKhong(int maQuyen) {
        try {
            String sql = "SELECT COUNT(*) > 0 FROM quyentaikhoan WHERE MaQuyen = ?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setInt(1, maQuyen);

            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
