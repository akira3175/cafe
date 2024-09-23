/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.core.cafe_shop_maven.DTO;

/**
 *
 * @author Asus
 */
public class PriceSanPhamDTO {
    private int maSP;
    private int maPN;
    private int donGia;
    
    public PriceSanPhamDTO(int maSP, int maPN, int donGia) {
        this.maSP = maSP;
        this.maPN = maPN;
        this.donGia = donGia;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getMaPN() {
        return maPN;
    }

    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }
}
