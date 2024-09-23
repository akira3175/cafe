/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.PriceSanPhamDAO;
import function.FuncTionCheckInput;

/**
 *
 * @author Asus
 */
public class PriceSanPhamBUS {
    private static PriceSanPhamBUS instance;
    
    public static PriceSanPhamBUS getInstance() {
        if(instance == null)
            instance = new PriceSanPhamBUS();
        return instance;
    }
    
    public boolean addMaSanPhamWhenNewSanPhamIsAdded(String maSP) {
        if(FuncTionCheckInput.getInstance().isNumeric(maSP)) 
            return PriceSanPhamDAO.getInstance().addMaSanPhamKhiSanPhamMoiDuocThemVaoDB(maSP);
        else return false;
    }  
    
    public boolean loadMaPNVaDonGiaKhiDaBanHetLoHienTai() {
        
        return true;
    }
    
    public boolean updateSoLuongVaDonGiaLoTiepTheoLenBangSanPham() {
        
        return true;
    }
}
