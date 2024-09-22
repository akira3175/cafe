package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.DTO.SanPham;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SanPhamDAO {
    private static SanPhamDAO instance;

    public static SanPhamDAO getInstance() {
        if (instance == null) {
            instance = new SanPhamDAO();
        }
        return instance;
    }

    private SanPhamDAO() {
    }

    public ArrayList<SanPham> getListSanPham() {
        try {
            String sql = "SELECT * FROM sanpham";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            ArrayList<SanPham> dssp = new ArrayList<>();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt(1));
                sp.setTenSP(rs.getString(2));
                sp.setSoLuong(rs.getInt(3));
                sp.setDonGia(rs.getInt(4));
                sp.setPhanTramLoi(rs.getInt(5));
                sp.setHinhAnh(rs.getString(6));
                sp.setTrangThai(rs.getInt(7));
                sp.setMaLoai(rs.getInt(8));
                sp.setMaPN(rs.getInt(9));
                dssp.add(sp);
            }
            return dssp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SanPham getSanPham(int ma) {
        try {
            String sql = "SELECT * FROM sanpham WHERE MaSP=?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setInt(1, ma);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt(1));
                sp.setTenSP(rs.getString(2));
                sp.setSoLuong(rs.getInt(3));
                sp.setDonGia(rs.getInt(4));
                sp.setPhanTramLoi(rs.getInt(5));
                sp.setHinhAnh(rs.getString(6));
                sp.setTrangThai(rs.getInt(7));
                sp.setMaLoai(rs.getInt(8));
                sp.setMaPN(rs.getInt(9));
                return sp;
            }
        } catch (SQLException e) {
        }

        return null;
    }

    public ArrayList<SanPham> getSanPhamTheoLoai(int maLoai) {
        try {
            String sql = "SELECT * FROM sanpham WHERE loaiSanPham_MaLSP=?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setInt(1, maLoai);
            ResultSet rs = pre.executeQuery();
            ArrayList<SanPham> dssp = new ArrayList<>();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt(1));
                sp.setTenSP(rs.getString(2));
                sp.setSoLuong(rs.getInt(3));
                sp.setDonGia(rs.getInt(4));
                sp.setPhanTramLoi(rs.getInt(5));
                sp.setHinhAnh(rs.getString(6));
                sp.setTrangThai(rs.getInt(7));
                sp.setMaLoai(rs.getInt(8));
                sp.setMaPN(rs.getInt(9));
                dssp.add(sp);
            }
            return dssp;
        } catch (SQLException e) {
        }

        return null;
    }

    public String getAnh(int ma) {
        try {
            String sql = "SELECT HinhAnh FROM sanpham WHERE MaSP=?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setInt(1, ma);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getString("HinhAnh");
            }
        } catch (SQLException e) {
        }
        return "";
    }

    public void capNhatSoLuongSP(int ma, int soLuongMat) {
        SanPham sp = getSanPham(ma);
        int soLuong = sp.getSoLuong();
        sp.setSoLuong(soLuong + soLuongMat);
        try {
            String sql = "UPDATE sanpham SET SoLuong=? WHERE MaSP=" + ma;
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setInt(1, sp.getSoLuong());
            pre.executeUpdate();
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }

    // Trung -> fix them column MaPN
    public boolean themSanPham(SanPham sp) {
        try {
            String sql = "INSERT INTO sanpham(TenSP, SoLuong, DonGia, phantramloi, HinhAnh, TrangThai, loaiSanPham_MaLSP) "
                    + "VALUES (?, ?, ?, ?, ?, 1, ?, 0)";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);

            pre.setString(1, sp.getTenSP());
            pre.setInt(2, sp.getSoLuong());
            pre.setInt(3, sp.getDonGia());
            pre.setInt(4, sp.getPhanTramLoi());
            pre.setString(5, sp.getHinhAnh());          
            pre.setInt(6, sp.getMaLoai());
            pre.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(">>> SanPhamDAO -> themSanPham -> " + e.getMessage());
        }
        return false;
    }

    public boolean xoaToanBoSP() {
        try {
            String sql = "DELETE FROM sanpham;";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.execute();
            return true;
        } catch (SQLException e) {
        }
        return false;
    }

    public boolean nhapSanPhamTuExcel(SanPham sp) {
        try {
            String sql = "INSERT INTO sanpham(TenSP, SoLuong, DonGia, phantramloi, HinhAnh, TrangThai, loaiSanPham_MaLSP) "
                    + "VALUES (?, ?, ?, ?, ?, 1, ?, 0)";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, sp.getTenSP());
            pre.setInt(2, sp.getSoLuong());
            pre.setInt(3, sp.getDonGia());
            pre.setInt(4, sp.getPhanTramLoi());
            pre.setString(5, sp.getHinhAnh());          
            pre.setInt(6, sp.getMaLoai());
            pre.execute();
            return true;
        } catch (SQLException e) {
        }
        return false;
    }

    public boolean xoaSanPham(int maSP) {
        try {
            String sql = "UPDATE sanpham "
                    + "SET TrangThai = 0 "
                    + "WHERE MaSP=" + maSP;
            System.out.println("xoa san pham: " + sql);
            Statement st = MyConnect.conn.createStatement();
            st.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean suaSanPham(SanPham sp) {
        try {
            String sql = "UPDATE sanpham SET "
                    + "TenSP=?, "
                    + "SoLuong=?, "
                    + "DonGia=?, "
                    + "HinhAnh=?, "
                    + "TrangThai=?, "
                    + "loaisanpham_MaLSP=?, "
                    + "phantramloi= ? "
                    + "WHERE MaSP=?";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            System.out.println("sua san pham: " + sp.toString());
            pre.setString(1, sp.getTenSP());
            pre.setInt(2, sp.getSoLuong());
            //
            pre.setInt(3, sp.getDonGia());
            pre.setString(4, sp.getHinhAnh());

            pre.setInt(5, 1);
            pre.setInt(6, sp.getMaLoai());
            pre.setInt(7, sp.getPhanTramLoi());
            pre.setInt(8, sp.getMaSP());

            pre.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Trung -> lay ma san pham moi nhat vua duoc them
    public String getMaSanPhamMoi() {
        try {
            String sql = "SELECT MAX(MaSP) FROM sanpham";
            PreparedStatement pst = MyConnect.conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(); // Sử dụng executeQuery() thay vì execute()

            if (rs.next()) { // Di chuyển con trỏ tới dòng kết quả đầu tiên
                return rs.getInt(1) + ""; // Chuyển đổi int thành String
            }
        } catch (Exception e) {
            System.out.println(">>> SanPhamDAO getMaSanPhamMoi " + e);
        }
        return null;
    }
    
    // Trung -> add function
    public boolean updateThongTinSanPhamLoTiepTheo(int maSP, int maPN, int soLuong, int donGia) {
        try {
            String sql = "UPDATE sanpham SET SoLuong = ?, DonGia = ?, MaPN = ? WHERE MaSP = ?";
            // Chuẩn bị câu lệnh với kết nối tới cơ sở dữ liệu
            PreparedStatement pst = MyConnect.conn.prepareStatement(sql);
            // Thiết lập giá trị cho các tham số trong câu lệnh SQL
            pst.setInt(1, soLuong);  // Cập nhật số lượng
            pst.setDouble(2, donGia);  // Cập nhật đơn giá
            pst.setInt(3, maPN);  // Cập nhật mã phiếu nhập
            pst.setInt(4, maSP);  // Điều kiện dựa trên mã sản phẩm
            // Thực thi câu lệnh
            int rowsAffected = pst.executeUpdate();
            // Kiểm tra xem có bao nhiêu dòng đã bị thay đổi
            if (rowsAffected > 0) {
                System.out.println("Cập nhật sản phẩm thành công!");
                return true;
            } else {
                System.out.println("Không tìm thấy sản phẩm với MaSP = " + maSP);
                return false;
            }
        } catch (SQLException e) {
            System.out.println(">>> SanPhamDAO -> updateThongTinSanPhamLoTiepTheo -> " + e.getMessage());
            return false;
        }
    }
}
