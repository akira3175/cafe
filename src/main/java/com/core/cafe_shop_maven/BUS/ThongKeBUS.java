/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.ThongKeDAO;
import com.core.cafe_shop_maven.DTO.ThongKe;

import java.util.ArrayList;

/**
 * @author User
 */
public class ThongKeBUS {

    public ThongKeDAO thongKeDAO;
    private ArrayList<Double> doanhThuThang;

    private static ThongKeBUS instance;

    public static ThongKeBUS getInstance() {
        if (instance == null)
            instance = new ThongKeBUS();
        return instance;
    }

    private ThongKeBUS() {
        thongKeDAO = ThongKeDAO.getInstance();
        doanhThuThang = new ArrayList<>();
    }

    public ThongKe thongKe(int nam) {
        return thongKeDAO.getThongKe(nam);
    }

    public double getDoanhThuThang(int thang, int nam) {
        return thongKeDAO.getDoanhThuThang(thang, nam);
    }
}
