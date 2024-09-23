/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.core.cafe_shop_maven.DAO;

import function.FuncTionCheckInput;
import java.sql.PreparedStatement;

/**
 *
 * @author Asus
 */
public class PriceSanPhamDAO {
    private static PriceSanPhamDAO instance;

    public static PriceSanPhamDAO getInstance() {
        if (instance == null) {
            instance = new PriceSanPhamDAO();
        }
        return instance;
    }
    
    public boolean addMaSanPhamKhiSanPhamMoiDuocThemVaoDB(String maSP) {
        try {
            if(FuncTionCheckInput.getInstance().isNumeric(maSP)) {
                String sql = "INSERT INTO pricesanpham(MaSP, MaPN, DonGia) VALUE (?, 0, 0)";
                PreparedStatement pst = MyConnect.conn.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(maSP));
                pst.execute();
                return true;
            } else return false;
                         
        } catch (Exception e){
            System.out.println(">>>PriceSanPhamDAO khong them duoc maSP moi!" + e);
            return false;
        }
    }
    
    public boolean updateMaPNVaDonGiaLoHangTiepTheoTrongDB() {
        
        return true;
    }
    
    public boolean getSoLuongVaDonGiaLoHangTiepTheoTrongDB(String maSP) {
        
        return true;
    }
}
