/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.core.cafe_shop_maven.GUI;

import com.core.cafe_shop_maven.BUS.DangNhapBUS;
import com.core.cafe_shop_maven.DTO.TaiKhoan;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;
import com.core.cafe_shop_maven.CustomFunctions.LooknFeel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;

/**
 *
 * @author Admin
 */
public class DangNhapGUI extends javax.swing.JFrame {

    /**
     * Creates new form FormDangNhap
     */
    public DangNhapGUI() {
        initComponents();
        setup();
    }

    private void setup() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        xuLyTaiKhoanDaGhiNho();
        addEvents();
        setVisible(true);
        setTitle("Đăng nhập");
        LooknFeel.init(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnForgot = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtUser = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        ckbRemember = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ĐĂNG NHẬP");
        jLabel1.setPreferredSize(new java.awt.Dimension(146, 80));
        getContentPane().add(jLabel1, java.awt.BorderLayout.PAGE_START);

        btnForgot.setText("Quên mật khẩu?");
        getContentPane().add(btnForgot, java.awt.BorderLayout.PAGE_END);

        txtUser.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên đăng nhập"));

        txtPassword.setBorder(javax.swing.BorderFactory.createTitledBorder("Mật khẩu"));

        btnLogin.setText("Xác nhận");

        ckbRemember.setText("Ghi nhớ đăng nhập");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtUser)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ckbRemember, javax.swing.GroupLayout.DEFAULT_SIZE, 340,
                                                Short.MAX_VALUE))
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ckbRemember, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE)));

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void xuLyTaiKhoanDaGhiNho() {
        DangNhapBUS dangNhapBUS = new DangNhapBUS();
        String line = dangNhapBUS.getTaiKhoanGhiNho();
        try {
            String[] arr = line.split(" | ");
            ckbRemember.setSelected(true);
            txtUser.setText(arr[0]);
            txtPassword.setText(arr[2]);
            txtUser.requestFocus();
        } catch (Exception e) {
            txtUser.setText("");
            txtPassword.setText("");
            txtUser.requestFocus();
            e.printStackTrace();
        }
    }

    private void addEvents() {
        txtUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtPassword.requestFocus();
            }
        });
        txtPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyDangNhap();
            }
        });
        btnForgot.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyQuenMatKhau();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        btnLogin.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyDangNhap();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void xuLyThoat() {
        System.exit(0);
    }

    private void xuLyQuenMatKhau() {
        new Dialog("Xin liên hệ quản trị hay quản lý để giải quyết!", Dialog.INFO_DIALOG);
    }

    private void xuLyDangNhap() {
        DangNhapBUS dangNhapBUS = new DangNhapBUS();
        TaiKhoan tk = dangNhapBUS.getTaiKhoanDangNhap(txtUser.getText(),
                txtPassword.getText(), ckbRemember.isSelected());
        if (tk != null) {
            try {
                this.dispose();
                MainQuanLyGUI gui = new MainQuanLyGUI();
                this.dispose();
                gui.showWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnForgot;
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox ckbRemember;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
