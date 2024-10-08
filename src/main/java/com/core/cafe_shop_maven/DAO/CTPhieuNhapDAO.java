package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.DTO.CTPhieuNhap;
import com.core.cafe_shop_maven.DTO.SanPham;
import function.FuncTionCheckInput;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CTPhieuNhapDAO {

    private static CTPhieuNhapDAO instance;

    public static CTPhieuNhapDAO getInstance() {
        if (instance == null) {
            instance = new CTPhieuNhapDAO();
        }
        return instance;
    }

    private CTPhieuNhapDAO() {
    }

    public ArrayList<CTPhieuNhap> getListCTPhieuNhap() {
        ArrayList<CTPhieuNhap> dsctpn = new ArrayList<>();
        try {
            String sql = "SELECT * FROM chitietphieunhap";
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CTPhieuNhap ctpn = new CTPhieuNhap();
                ctpn.setSoLuong(rs.getInt(1));
                ctpn.setDonGia(rs.getInt(2));
                ctpn.setMaPN(rs.getInt(3));
                ctpn.setMaSP(rs.getInt(4));
                dsctpn.add(ctpn);
            }
        } catch (SQLException ex) {
            return null;
        }
        return dsctpn;
    }

    public ArrayList<CTPhieuNhap> getListCTPhieuNhapTheoMaPN(int maPN) {
        ArrayList<CTPhieuNhap> dsctpn = new ArrayList<>();
        try {
            String sql = "SELECT * FROM chitietphieunhap WHERE MaPN=" + maPN;
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CTPhieuNhap ctpn = new CTPhieuNhap();
                ctpn.setSoLuong(rs.getInt(1));
                ctpn.setDonGia(rs.getInt(2));
                ctpn.setMaPN(rs.getInt(3));
                ctpn.setMaSP(rs.getInt(4));
                dsctpn.add(ctpn);
            }
        } catch (SQLException ex) {
            return null;
        }
        return dsctpn;
    }

    public ArrayList<CTPhieuNhap> getListCTPhieuNhapTheoMaSP(int maSP) {
        ArrayList<CTPhieuNhap> dsctpn = new ArrayList<>();
        try {
            String sql = "SELECT * FROM chitietphieunhap WHERE MaSP=" + maSP;
            Statement stmt = MyConnect.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CTPhieuNhap ctpn = new CTPhieuNhap();
                ctpn.setSoLuong(rs.getInt(1));
                ctpn.setDonGia(rs.getInt(2));
                ctpn.setMaPN(rs.getInt(3));
                ctpn.setMaSP(rs.getInt(4));
                dsctpn.add(ctpn);
            }
        } catch (SQLException ex) {
            return null;
        }
        return dsctpn;
    }
    // Trung -> fix quy trinh them chi tiet phieu nhap
    public boolean addCTPhieuNhap(CTPhieuNhap ctpn) {
        boolean result = false;
        try {
            // Phải Update số lượng SP trong kho
//            String sqlUpdateSP = "UPDATE sanpham SET SoLuong = SoLuong + ? WHERE MaSP = ?";
//            PreparedStatement pre = MyConnect.conn.prepareCall(sqlUpdateSP);
//            pre.setInt(1, ctpn.getSoLuong());
//            pre.setInt(2, ctpn.getMaSP());
//            pre.executeUpdate();

            // add property trangthai
            String sql = "INSERT INTO chitietphieunhap VALUES(?,?,?,?,?)";
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setInt(1, ctpn.getSoLuong());
            prep.setInt(2, ctpn.getDonGia());
            prep.setInt(3, ctpn.getMaPN());
            prep.setInt(4, ctpn.getMaSP());
            prep.setString(5, "InStock");
            result = prep.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return result;
    }

    public boolean deleteCTPhieuNhap(int maPN) {
        boolean result = false;
        try {
            String sql = "DELETE FROM chitietphieunhap WHERE MaPN=" + maPN;
            Statement stmt = MyConnect.conn.createStatement();
            result = stmt.executeUpdate(sql) > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean deleteCTPhieuNhap(int maPN, int maSP) {
        boolean result = false;
        try {
            String sql = "DELETE FROM chitietphieunhap WHERE MaPN=" + maPN + " AND MaSP=" + maSP;
            Statement stmt = MyConnect.conn.createStatement();
            result = stmt.executeUpdate(sql) > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean updateCTPhieuNhap(int maPN, CTPhieuNhap ctpn) {
        boolean result = false;
        try {
            String sql = "UPDATE chitietphieunhap SET MaPN=?, MaSP=?, SoLuong=?, DonGia=? WHERE MaPN=?";
            PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
            prep.setInt(1, ctpn.getMaPN());
            prep.setInt(2, ctpn.getMaSP());
            prep.setInt(3, ctpn.getSoLuong());
            prep.setInt(4, ctpn.getDonGia());
            prep.setInt(5, maPN);
            result = prep.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }
    // Trung -> add func to update price sanpham khi số lượng bán về 0
    public boolean updatePriceOfSanPham(String maSPInput) {
        try {
            if(FuncTionCheckInput.getInstance().isNumeric(maSPInput)) {
                int maSP = Integer.parseInt(maSPInput);
                String sql = "SELECT TOP 1 * FROM chitietphieunhap WHERE MaSP = ?, TrangThai = ?";
                PreparedStatement prep = MyConnect.conn.prepareStatement(sql);
                prep.setInt(1, maSP);
                prep.setString(2, "InStock");
                ResultSet rs = prep.executeQuery(); // No need to pass the SQL string again

                CTPhieuNhap ctpn = new CTPhieuNhap();
                while (rs.next()) {
                    ctpn.setSoLuong(rs.getInt(1));
                    ctpn.setDonGia(rs.getInt(2));
                    ctpn.setMaPN(rs.getInt(3));
                    ctpn.setMaSP(rs.getInt(4));
                    ctpn.setTrangThai(rs.getString(5));
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            return false;
        }
    }
    
    // Trung -> add function 
    public int tongSoLuongNhapCuaSanPhamById(int maSP) {
        try {
            String sql = "SELECT SUM(SoLuong) FROM chitietphieunhap WHERE MaSP = ?";
            PreparedStatement pst = MyConnect.conn.prepareStatement(sql);
            pst.setInt(1, maSP);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else return -1;
        } catch (Exception e) {
            System.out.println(">>> CTPhieuNhapDAO tinhSoLuongTonKhoCuaSanPhamById " + e);
            return -1;
        }
    }
    
    // Trung -> add function lấy lô hàng đầu tiên của sản phẩm mới 
    public Object getLoHangDauTien(int maSP, int maPN) {
        try {
            String sql = "SELECT * FROM chitietphieunhap WHERE MaSP = ? ORDER BY MaPN ASC LIMIT 1";
            PreparedStatement pst = MyConnect.conn.prepareStatement(sql);
            pst.setInt(1, maSP);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                SanPham sp = new SanPham();
                sp.setSoLuong(rs.getInt(1));
                sp.setDonGia(rs.getInt(2));
                sp.setMaPN(rs.getInt(3));
                sp.setMaSP(rs.getInt(4));
                return sp;
            } else {
                System.out.println("Khong tim thay lo hang dau tien " + "maSP=" + maSP + " maPN=" + maPN + "!");
                return null;
            }
        } catch (Exception e) {
            System.out.println(">>> CTPhieuNhapDAO -> getLoHangDauTien = " + e);
            return null;
        }
    }
    
    // Trung -> add function lấy lô hàng tiếp theo của sản phẩm
    public Object getLoHangTiepTheo(int maSP, int maPN) {
        try {
            // Cap nhat trang thai cua lo hang da het
            String sql1 = "UPDATE chitietphieunhap SET TrangThai = ? WHERE MaSP = ? AND MaPN = ?";
            PreparedStatement pst1 = MyConnect.conn.prepareStatement(sql1);
            pst1.setString(1, "OutStock");
            pst1.setInt(2, maSP);
            pst1.setInt(3, maPN);
            int rowsUpdated = pst1.executeUpdate();
            
            // Lay lo hang tiep theo cua san pham
            String sql2 = "SELECT * FROM chitietphieunhap WHERE MaSP = ? AND TrangThai = ? ORDER BY MaPN ASC LIMIT 1";
            PreparedStatement pst2 = MyConnect.conn.prepareStatement(sql2);
            pst2.setInt(1, maSP);
            pst2.setString(2, "InStock");
            ResultSet rs = pst2.executeQuery();
            if(rs.next()) {
                SanPham sp = new SanPham();
                sp.setSoLuong(rs.getInt(1));
                sp.setDonGia(rs.getInt(2));
                sp.setMaPN(rs.getInt(3));
                sp.setMaSP(rs.getInt(4));
                return sp;
            } else {
                System.out.println("Khong tim thay lo hang " + maPN + "!");
                return null;
            }
        } catch (Exception e) {
            System.out.println(">>> CTPhieuNhapDAO -> getLoHangTiepTheo = " + e);
            return null;
        }
    }
}
