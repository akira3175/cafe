/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.core.cafe_shop_maven;

import com.core.cafe_shop_maven.DAO.MyConnect;
import com.core.cafe_shop_maven.GUI.DangNhapGUI;
import com.formdev.flatlaf.FlatLightLaf;

/**
 *
 * @author truon
 */
public class Cafe_shop_maven {

    public static void main(String[] args) {
        new MyConnect();

        changLNF("FlatLaf Light");
        new DangNhapGUI();
    }

    public static void changLNF(String nameLNF) {
        FlatLightLaf.setup();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (nameLNF.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
        }
    }
}
