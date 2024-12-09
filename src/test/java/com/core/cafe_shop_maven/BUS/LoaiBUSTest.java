/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.core.cafe_shop_maven.BUS;

import com.core.cafe_shop_maven.DAO.MyConnect;
import com.core.cafe_shop_maven.DTO.LoaiSP;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

/**
 *
 * @author Asus
 */
public class LoaiBUSTest {
    private static  LoaiBUS loaiBus;

    /**
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
        MyConnect.initConnection(); // Khởi tạo kết nối trong mỗi bài kiểm thử
        loaiBus = LoaiBUS.getInstance();
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    
//    @BeforeAll
//    public static void setUpClass() {
//    }
//    
//    @AfterAll
//    public static void tearDownClass() {
//    }
//    
//    @BeforeEach
//    public void setUp() {
//    }
    
//    @AfterEach
//    public void tearDown() {
//    }

    /**
     * Test of themLoai method, of class LoaiBUS.
     */
    @org.junit.jupiter.api.Test
    public void testThemLoai() {
        System.out.println("Test themLoai");
        String tenLoai = "Drink";
        String MoTa = "Tat ca loai do uong";
        boolean expResult = true;
        boolean result = loaiBus.themLoai(tenLoai, MoTa);
        assertEquals(expResult, result);
    }
    
    @org.junit.jupiter.api.Test
    public void testThemLoaiVoiTenRong() {
        System.out.println("Test themLoai voi ten rong");
        String tenLoai = "";
        String MoTa = "Tat ca loai do uong";
        boolean expResult = false;
        boolean result = loaiBus.themLoai(tenLoai, MoTa);
        assertEquals(expResult, result);
    }
    
    @org.junit.jupiter.api.Test
    public void testThemLoaiVoiMotaRong() {
        System.out.println("Test themLoai voi mo ta rong");
        String tenLoai = "Drink";
        String MoTa = "";
        boolean expResult = true;
        boolean result = loaiBus.themLoai(tenLoai, MoTa);
        assertEquals(expResult, result);
    }
    
    @org.junit.jupiter.api.Test
    public void testThemLoaiVoiMotaRongVoiCacTruongDeuRong() {
        System.out.println("Test themLoai voi cac truong deu rong");
        String tenLoai = "";
        String MoTa = "";
        boolean expResult = false;
        boolean result = loaiBus.themLoai(tenLoai, MoTa);
        assertEquals(expResult, result);
    }

    /**
     * Test of suaLoai method, of class LoaiBUS.
     */
    @org.junit.jupiter.api.Test
    public void testSuaLoaiVoiTenVaMoTaHopLe() {
        System.out.println("suaLoai voi ten va moTa hop le");
        String ma = "7";
        String ten = "Coffee";
        String moTa = "Coffee rat tuyet";
        boolean expResult = true;
        boolean result = loaiBus.suaLoai(ma, ten, moTa);
        assertEquals(expResult, result);
    }
    
    @org.junit.jupiter.api.Test
    public void testSuaLoaiVoiTenDeTrong() {
        System.out.println("suaLoai voi ten de trong");
        String ma = "7";
        String ten = "";
        String moTa = "Coffee rat tuyet";
        boolean expResult = false;
        boolean result = loaiBus.suaLoai(ma, ten, moTa);
        assertEquals(expResult, result);
    }
    
    @org.junit.jupiter.api.Test
    public void testSuaLoaiVoiMoTaDeTrong() {
        System.out.println("suaLoai voi moTa de trong");
        String ma = "7";
        String ten = "Coffee";
        String moTa = "";
        boolean expResult = true;
        boolean result = loaiBus.suaLoai(ma, ten, moTa);
        assertEquals(expResult, result);
    }
    
    @org.junit.jupiter.api.Test
    public void testSuaLoaiVoiTenVaMoTaDeTrong() {
        System.out.println("suaLoai voi ten va moTa de trong");
        String ma = "7";
        String ten = "";
        String moTa = "";
        boolean expResult = false;
        boolean result = loaiBus.suaLoai(ma, ten, moTa);
        assertEquals(expResult, result);
    }
}
