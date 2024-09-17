package com.core.cafe_shop_maven.DAO;

import com.core.cafe_shop_maven.BUS.TaiKhoanBUS;
import com.core.cafe_shop_maven.DTO.TaiKhoan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DangNhapDAO {

    private static DangNhapDAO instance;

    private DangNhapDAO() {
    }

    public static DangNhapDAO getInstance() {
        if (instance == null) {
            instance = new DangNhapDAO();
        }
        return instance;
    }

    public TaiKhoan dangNhap(TaiKhoan tk) {
        try {
            String sql = "SELECT * FROM taikhoan WHERE TenTaiKhoan=? AND MatKhau=? AND TrangThai=1";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, tk.getTenTK());
            pre.setString(2, tk.getMatKhau());
            ResultSet rs = pre.executeQuery();
            TaiKhoan tkLogin = null;
            if (rs.next()) {
                tkLogin = tk;
                tkLogin.setMaTK(rs.getInt("MaTK"));
                tkLogin.setQuyen(TaiKhoanBUS.getInstance().getQuyenTheoMa(rs.getInt("MaTK") + ""));
            }
            return tkLogin;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tk;
    }
}