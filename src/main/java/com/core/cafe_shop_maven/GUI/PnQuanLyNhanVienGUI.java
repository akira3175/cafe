package com.core.cafe_shop_maven.GUI;

import com.core.cafe_shop_maven.BUS.DangNhapBUS;
import com.core.cafe_shop_maven.CustomFunctions.XuLyFileExcel;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;
import com.core.cafe_shop_maven.CustomFunctions.TransparentPanel;
import com.core.cafe_shop_maven.CustomFunctions.Table;
import com.core.cafe_shop_maven.CustomFunctions.ImagePanel;
import static com.core.cafe_shop_maven.Cafe_shop_maven.changLNF;

import com.core.cafe_shop_maven.BUS.NhanVienBUS;
import com.core.cafe_shop_maven.BUS.PhanQuyenBUS;
import com.core.cafe_shop_maven.BUS.TaiKhoanBUS;
import com.core.cafe_shop_maven.DAO.NhanVienDAO;
import com.core.cafe_shop_maven.DAO.PhanQuyenDAO;
import com.core.cafe_shop_maven.DTO.NhanVien;
import com.core.cafe_shop_maven.DTO.PhanQuyen;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PnQuanLyNhanVienGUI extends JPanel {

    public PnQuanLyNhanVienGUI() {
        changLNF("Windows");
        addControlsNhanVien();
        addEventsNhanVien();
        addEventsPhanQuyen();
    }

    private PhanQuyenBUS phanQuyenBUS = PhanQuyenBUS.getInstance();
    private NhanVienBUS nhanVienBUS = NhanVienBUS.getInstance();

    JLabel lblTabbedNhanVien, lblTabbedQuyen;
    final ImageIcon tabbedSelected = new ImageIcon("src/main/resources/image/ManagerUI/tabbed-btn--selected.png");
    final ImageIcon tabbedDefault = new ImageIcon("src/main/resources/image/ManagerUI/tabbed-btn.png");
    final Color colorPanel = new Color(247, 247, 247);
    CardLayout cardNhanVienGroup = new CardLayout();
    JPanel pnCardTabNhanVien;
    JTextField txtMaNV, txtTen, txtNgaySinh, txtSDT, txtTimNV, txtDiaChi;
    Table tblNhanVien;
    DefaultTableModel dtmNhanVien;
    JButton btnReset, btnThemNV, btnSuaNV, btnXoaNV, btnTimNV, btnCapTaiKhoan, btnResetMatKhau, btnXoaTaiKhoan,
            btnXuatExcel, btnNhapExcel;

    private void addControlsNhanVien() {
        this.setLayout(new BorderLayout());
        this.setBackground(colorPanel);
        int w = 1030;
        int h = 844;
        /*
         * =========================================================================
         * PANEL TABBED
         * =========================================================================
         */
        JPanel pnTop = new TransparentPanel();
        // <editor-fold defaultstate="collapsed" desc="Panel Tab Nhân viên & Quyền">
        Font font = new Font("", Font.PLAIN, 20);
        pnTop.setPreferredSize(new Dimension(w, 41));
        pnTop.setLayout(null);
        pnTop.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

        lblTabbedNhanVien = new JLabel("Nhân viên");
        lblTabbedNhanVien.setHorizontalTextPosition(JLabel.CENTER);
        lblTabbedNhanVien.setVerticalTextPosition(JLabel.CENTER);
        lblTabbedNhanVien.setIcon(tabbedSelected);
        lblTabbedNhanVien.setBounds(2, 2, 140, 37);
        lblTabbedNhanVien.setFont(font);
        lblTabbedNhanVien.setForeground(Color.white);
        lblTabbedNhanVien.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblTabbedQuyen = new JLabel("Quyền");
        lblTabbedQuyen.setHorizontalTextPosition(JLabel.CENTER);
        lblTabbedQuyen.setVerticalTextPosition(JLabel.CENTER);
        lblTabbedQuyen.setIcon(tabbedDefault);
        lblTabbedQuyen.setBounds(143, 2, 140, 37);
        lblTabbedQuyen.setFont(font);
        lblTabbedQuyen.setForeground(Color.white);
        lblTabbedQuyen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pnTop.add(lblTabbedNhanVien);
        pnTop.add(lblTabbedQuyen);
        // </editor-fold>
        this.add(pnTop, BorderLayout.NORTH);

        /*
         * =========================================================================
         * PANEL NHÂN VIÊN
         * =========================================================================
         */
        JPanel pnNhanVien = new TransparentPanel();
        pnNhanVien.setLayout(new BoxLayout(pnNhanVien, BoxLayout.Y_AXIS));

        JPanel pnTopNV = new TransparentPanel();
        pnTopNV.setLayout(new BoxLayout(pnTopNV, BoxLayout.Y_AXIS));

        JPanel pnTitle = new TransparentPanel();
        JLabel lblTitle = new JLabel("<html><h1>QUẢN LÝ NHÂN VIÊN</h1></html>");
        btnReset = new JButton(new ImageIcon("src/main/resources/image/Refresh-icon.png"));
        btnReset.setPreferredSize(new Dimension(40, 40));
        pnTitle.add(lblTitle);
        pnTitle.add(btnReset);
        pnTopNV.add(pnTitle);
        pnTopNV.setBackground(Color.DARK_GRAY);
        // ==========
        JPanel pnText = new TransparentPanel();
        pnText.setLayout(new BoxLayout(pnText, BoxLayout.Y_AXIS));

        txtMaNV = new JTextField(25);
        txtMaNV.setEditable(false);
        txtTen = new JTextField(25);
        txtNgaySinh = new JTextField(25);
        txtDiaChi = new JTextField(25);
        txtSDT = new JTextField(25);

        txtMaNV.setFont(font);
        txtTen.setFont(font);
        txtNgaySinh.setFont(font);
        txtDiaChi.setFont(font);
        txtSDT.setFont(font);

        JLabel lblMa, lblHo, lblTen, lblDiaChi, lblChucVu;

        lblMa = new JLabel("Mã Nhân viên");
        lblHo = new JLabel("Tên");
        lblTen = new JLabel("Ngày sinh");
        lblDiaChi = new JLabel("Địa chỉ");
        lblChucVu = new JLabel("SĐT");

        lblMa.setFont(font);
        lblHo.setFont(font);
        lblTen.setFont(font);
        lblDiaChi.setFont(font);
        lblChucVu.setFont(font);

        JPanel pnMa = new TransparentPanel();
        pnMa.add(lblMa);
        pnMa.add(txtMaNV);
        pnText.add(pnMa);

        JPanel pnHo = new TransparentPanel();
        pnHo.add(lblHo);
        pnHo.add(txtTen);
        pnText.add(pnHo);

        JPanel pnTen = new TransparentPanel();
        pnTen.add(lblTen);
        pnTen.add(txtNgaySinh);
        pnText.add(pnTen);

        JPanel pnGioiTinh = new TransparentPanel();
        pnGioiTinh.add(lblDiaChi);
        pnGioiTinh.add(txtDiaChi);
        pnText.add(pnGioiTinh);

        JPanel pnChucVu = new TransparentPanel();
        pnChucVu.add(lblChucVu);
        pnChucVu.add(txtSDT);
        pnText.add(pnChucVu);

        Dimension lblSize = lblMa.getPreferredSize();
        lblMa.setPreferredSize(lblSize);
        lblHo.setPreferredSize(lblSize);
        lblTen.setPreferredSize(lblSize);
        lblDiaChi.setPreferredSize(lblSize);
        lblChucVu.setPreferredSize(lblSize);
        txtDiaChi.setPreferredSize(txtSDT.getPreferredSize());

        pnTopNV.add(pnText);

        // ==========
        JPanel pnTimNV = new TransparentPanel();
        JLabel lblTim = new JLabel("Từ khoá tìm");
        lblTim.setFont(font);
        txtTimNV = new JTextField(25);
        txtTimNV.setFont(font);
        pnTimNV.add(lblTim);
        pnTimNV.add(txtTimNV);
        pnTopNV.add(pnTimNV);
        lblTim.setPreferredSize(lblSize);
        // ==========
        JPanel pnButton = new TransparentPanel();

        btnThemNV = new JButton("Thêm");
        btnSuaNV = new JButton("Lưu");
        btnXoaNV = new JButton("Xoá");
        btnTimNV = new JButton("Tìm kiếm");
        btnXuatExcel = new JButton("Xuất");
        btnNhapExcel = new JButton("Nhập");

        Font fontButton = new Font("Tahoma", Font.PLAIN, 16);
        btnThemNV.setFont(fontButton);
        btnSuaNV.setFont(fontButton);
        btnXoaNV.setFont(fontButton);
        btnTimNV.setFont(fontButton);
        btnXuatExcel.setFont(fontButton);
        btnNhapExcel.setFont(fontButton);

        btnThemNV.setIcon(new ImageIcon("src/main/resources/image/add-icon.png"));
        btnSuaNV.setIcon(new ImageIcon("src/main/resources/image/Pencil-icon.png"));
        btnXoaNV.setIcon(new ImageIcon("src/main/resources/image/delete-icon.png"));
        btnTimNV.setIcon(new ImageIcon("src/main/resources/image/Search-icon.png"));
        btnXuatExcel.setIcon(new ImageIcon("src/main/resources/image/excel-icon.png"));
        btnNhapExcel.setIcon(new ImageIcon("src/main/resources/image/excel-icon.png"));

        pnButton.add(btnThemNV);
        pnButton.add(btnSuaNV);
        pnButton.add(btnXoaNV);
        pnButton.add(btnTimNV);
        pnButton.add(btnXuatExcel);
        pnButton.add(btnNhapExcel);

        Dimension btnSize = btnTimNV.getPreferredSize();
        btnThemNV.setPreferredSize(btnSize);
        btnSuaNV.setPreferredSize(btnSize);
        btnXoaNV.setPreferredSize(btnSize);
        btnTimNV.setPreferredSize(btnSize);
        btnXuatExcel.setPreferredSize(btnSize);
        btnNhapExcel.setPreferredSize(btnSize);

        JPanel pnButton2 = new TransparentPanel();
        btnCapTaiKhoan = new JButton("Cấp tài khoản");
        btnResetMatKhau = new JButton("Mật khẩu/Quyền");
        btnXoaTaiKhoan = new JButton("Khoá tài khoản");
        btnCapTaiKhoan.setIcon(new ImageIcon("src/main/resources/image/icons8_man_with_key_32px.png"));
        btnResetMatKhau.setIcon(new ImageIcon("src/main/resources/image/icons8_password_reset_32px.png"));
        btnXoaTaiKhoan.setIcon(new ImageIcon("src/main/resources/image/icons8_denied_32px.png"));
        btnCapTaiKhoan.setFont(fontButton);
        btnResetMatKhau.setFont(fontButton);
        btnXoaTaiKhoan.setFont(fontButton);
        pnButton2.add(btnCapTaiKhoan);
        pnButton2.add(btnResetMatKhau);
        pnButton2.add(btnXoaTaiKhoan);

        pnNhanVien.add(pnTopNV);
        pnNhanVien.add(pnButton);
        pnNhanVien.add(pnButton2);
        // ===================TABLE NHÂN VIÊN=====================
        JPanel pnTableNhanVien = new TransparentPanel();
        pnTableNhanVien.setLayout(new BorderLayout());
        dtmNhanVien = new DefaultTableModel();
        dtmNhanVien.addColumn("Mã NV");
        dtmNhanVien.addColumn("Tên");
        dtmNhanVien.addColumn("Ngày sinh");
        dtmNhanVien.addColumn("Địa chỉ");
        dtmNhanVien.addColumn("SĐT");
        dtmNhanVien.addColumn("Tài khoản");
        tblNhanVien = new Table(dtmNhanVien);

        tblNhanVien.setDefaultEditor(Object.class, null);
        tblNhanVien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrTblNhanVien = new JScrollPane(tblNhanVien);
        pnTableNhanVien.add(scrTblNhanVien, BorderLayout.CENTER);
        pnNhanVien.add(scrTblNhanVien);
        /*
         * =========================================================================
         * PANEL QUYỀN
         * =========================================================================
         */
        JPanel pnPhanQuyen = new TransparentPanel();
        pnPhanQuyen.setLayout(new BoxLayout(pnPhanQuyen, BoxLayout.Y_AXIS));

        JPanel pnTitlePhanQuyen = new TransparentPanel();
        JLabel lblTitlePhanQuyen = new JLabel("<html><h1>Quản lý phân quyền</h1></html>");
        pnTitlePhanQuyen.add(lblTitlePhanQuyen);
        pnPhanQuyen.add(pnTitlePhanQuyen);

        JPanel pnCmbQuyen = new TransparentPanel();
        JLabel lblCmbQuyen = new JLabel("<html><b>Nhóm quyền:</b></html>");
        lblCmbQuyen.setFont(font);
        cmbQuyen = new JComboBox<String>();
        cmbQuyen.setFont(font);
        pnCmbQuyen.add(lblCmbQuyen);
        pnCmbQuyen.add(cmbQuyen);
        pnPhanQuyen.add(pnCmbQuyen);

        JPanel pnCheckNhapHang = new TransparentPanel();
        ckbNhapHang = new JCheckBox("Quản lý nhập hàng.");
        ckbNhapHang.setFont(font);
        pnCheckNhapHang.add(ckbNhapHang);
        pnPhanQuyen.add(pnCheckNhapHang);

        JPanel pnCheckQLSanPham = new TransparentPanel();
        ckbQLSanPham = new JCheckBox("Quản lý sản phẩm.");
        ckbQLSanPham.setFont(font);
        pnCheckQLSanPham.add(ckbQLSanPham);
        pnPhanQuyen.add(pnCheckQLSanPham);

        JPanel pnCheckQLNhanVien = new TransparentPanel();
        ckbQLNhanVien = new JCheckBox("Quản lý nhân viên.");
        ckbQLNhanVien.setFont(font);
        pnCheckQLNhanVien.add(ckbQLNhanVien);
        pnPhanQuyen.add(pnCheckQLNhanVien);

        JPanel pnCheckQLKhachHang = new TransparentPanel();
        ckbQLKhachHang = new JCheckBox("Quản lý khách hàng.");
        ckbQLKhachHang.setFont(font);
        pnCheckQLKhachHang.add(ckbQLKhachHang);
        pnPhanQuyen.add(pnCheckQLKhachHang);

        JPanel pnCheckQLThongKe = new TransparentPanel();
        ckbThongKe = new JCheckBox("Quản lý thống kê.");
        ckbThongKe.setFont(font);
        pnCheckQLThongKe.add(ckbThongKe);
        pnPhanQuyen.add(pnCheckQLThongKe);

        Dimension ckbSize = ckbQLKhachHang.getPreferredSize();
        cmbQuyen.setPreferredSize(ckbSize);
        ckbNhapHang.setPreferredSize(ckbSize);
        ckbQLSanPham.setPreferredSize(ckbSize);
        ckbQLNhanVien.setPreferredSize(ckbSize);
        ckbQLKhachHang.setPreferredSize(ckbSize);
        ckbThongKe.setPreferredSize(ckbSize);

        JPanel pnButtonQuyen = new TransparentPanel();
        btnThemQuyen = new JButton("Thêm quyền");
        btnSuaQuyen = new JButton("Sửa quyền");
        btnXoaQuyen = new JButton("Xoá quyền");
        btnThemQuyen.setFont(font);
        btnSuaQuyen.setFont(font);
        btnXoaQuyen.setFont(font);
        btnThemQuyen.setIcon(new ImageIcon("src/main/resources/image/add-icon.png"));
        btnSuaQuyen.setIcon(new ImageIcon("src/main/resources/image/Pencil-icon.png"));
        btnXoaQuyen.setIcon(new ImageIcon("src/main/resources/image/delete-icon.png"));
        pnButtonQuyen.add(btnThemQuyen);
        pnButtonQuyen.add(btnSuaQuyen);
        pnButtonQuyen.add(btnXoaQuyen);
        btnSuaQuyen.setPreferredSize(btnThemQuyen.getPreferredSize());
        btnXoaQuyen.setPreferredSize(btnThemQuyen.getPreferredSize());
        pnPhanQuyen.add(pnButtonQuyen);

        // ========================
        pnCardTabNhanVien = new JPanel(cardNhanVienGroup);
        pnCardTabNhanVien.add(pnNhanVien, "1");
        pnCardTabNhanVien.add(pnPhanQuyen, "2");
        this.add(pnCardTabNhanVien);

        if (!checkAdmin()) {
            lblTabbedQuyen.setVisible(false);
        }
        loadDataTblNhanVien(null);
        loadDataCmbQuyen();
        turnOffButtonSuaQuyen();
        turnOffButtonXoaQuyen();
    }

    JComboBox<String> cmbQuyen;
    JCheckBox ckbNhapHang, ckbQLSanPham, ckbQLNhanVien, ckbQLKhachHang, ckbThongKe;
    JButton btnSuaQuyen, btnThemQuyen, btnXoaQuyen;

    private void addEventsNhanVien() {
        lblTabbedNhanVien.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblTabbedNhanVien.setIcon(tabbedSelected);
                lblTabbedQuyen.setIcon(tabbedDefault);
                cardNhanVienGroup.show(pnCardTabNhanVien, "1");
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

        lblTabbedQuyen.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblTabbedQuyen.setIcon(tabbedSelected);
                lblTabbedNhanVien.setIcon(tabbedDefault);
                cardNhanVienGroup.show(pnCardTabNhanVien, "2");
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

        tblNhanVien.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyClickTblNhanVien();
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

        txtTimNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiemNhanVien();
            }
        });

        btnTimNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiemNhanVien();
            }
        });

        btnThemNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyThemNhanVien();
            }
        });

        btnSuaNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLySuaNhanVien();
            }
        });

        btnXoaNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXoaNhanVien();
            }
        });

        btnXuatExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXuatExcel();
            }
        });

        btnNhapExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyNhapExcel();
            }
        });

        btnCapTaiKhoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyCapTaiKhoan();
            }
        });

        btnResetMatKhau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyResetMatKhau();
            }
        });

        btnXoaTaiKhoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyKhoaTaiKhoan();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataTblNhanVien(null);
                txtMaNV.setText("");
                txtTen.setText("");
                txtNgaySinh.setText("");
                txtSDT.setText("");
                txtTimNV.setText("");
                txtDiaChi.setText("");
            }
        });
    }

    private void addEventsPhanQuyen() {
        cmbQuyen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyHienThiChiTietQuyen();
            }
        });
        btnThemQuyen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyThemQuyen();
            }
        });
        btnSuaQuyen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLySuaQuyen();
            }
        });
        btnXoaQuyen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXoaQuyen();
            }
        });
    }

    private void xuLyXoaQuyen() {
        if (cmbQuyen.getSelectedIndex() < 1) {
            new Dialog("Chưa chọn nhóm quyền để xoá!", Dialog.ERROR_DIALOG);
            return;
        }
        String tenQuyen = cmbQuyen.getSelectedItem() + "";
        if (phanQuyenBUS.kiemTraMaQuyenCoTaiKhoanNaoKhong(tenQuyen)) {
            new Dialog("Có tài khoản mang quyền này!", Dialog.ERROR_DIALOG);
            return;
        }
        Dialog dlg = new Dialog("Bạn có chắc chắn muốn xoá?", Dialog.WARNING_DIALOG);
        if (dlg.getAction() == Dialog.CANCEL_OPTION) {
            return;
        }
        boolean flag = phanQuyenBUS.xoaQuyen(tenQuyen);
        if (flag) {
            loadDataCmbQuyen();
        }
    }

    private void xuLyThemQuyen() {
        String tenQuyen = JOptionPane.showInputDialog("Nhập tên quyền");

        boolean flag = phanQuyenBUS.themQuyen(tenQuyen);
        if (flag) {
            loadDataCmbQuyen();
        }
    }

    private void xuLySuaQuyen() {
        if (cmbQuyen.getSelectedIndex() < 1) {
            new Dialog("Chưa chọn nhóm quyền để sửa!", Dialog.ERROR_DIALOG);
            return;
        }
        String tenQuyen = cmbQuyen.getSelectedItem() + "";
        int nhapHang = ckbNhapHang.isSelected() ? 1 : 0;
        int sanPham = ckbQLSanPham.isSelected() ? 1 : 0;
        int nhanVien = ckbQLNhanVien.isSelected() ? 1 : 0;
        int khachHang = ckbQLKhachHang.isSelected() ? 1 : 0;
        int thongKe = ckbThongKe.isSelected() ? 1 : 0;

        boolean flag = phanQuyenBUS.suaQuyen(0, tenQuyen, nhapHang, sanPham, nhanVien, khachHang, thongKe);
        if (flag) {
            loadDataCmbQuyen();
        }
    }

    private void xuLyHienThiChiTietQuyen() {
        ArrayList<PhanQuyen> dsq = phanQuyenBUS.getListQuyen();
        PhanQuyen phanQuyen = new PhanQuyen();
        boolean found = false;
        for (PhanQuyen pq : dsq) {
            if (pq.getQuyen().equals(cmbQuyen.getSelectedItem())) {
                found = true;
                phanQuyen.setQuyen(pq.getQuyen());
                phanQuyen.setNhapHang(pq.getNhapHang());
                phanQuyen.setQlSanPham(pq.getQlSanPham());
                phanQuyen.setQlNhanVien(pq.getQlNhanVien());
                phanQuyen.setQlKhachHang(pq.getQlKhachHang());
                phanQuyen.setThongKe(pq.getThongKe());
                turnOffButtonThemQuyen();
                if (checkQuyenAdmin(phanQuyen)) {
                    turnOffButtonSuaQuyen();
                    turnOffButtonXoaQuyen();
                } else if (checkQuyenQuanLy(phanQuyen)) {
                    turnOffButtonXoaQuyen();
                    turnOnButtonSuaQuyen();
                } else {
                    turnOnButtonXoaQuyen();
                    turnOnButtonSuaQuyen();
                }
                break;
            }
        }
        if (found == false) {
            turnOnButtonThemQuyen();
            turnOffButtonXoaQuyen();
            turnOffButtonSuaQuyen();
        }
        ckbNhapHang.setSelected(false);
        ckbQLSanPham.setSelected(false);
        ckbQLNhanVien.setSelected(false);
        ckbQLKhachHang.setSelected(false);
        ckbThongKe.setSelected(false);
        if (phanQuyen.getNhapHang() == 1) {
            ckbNhapHang.setSelected(true);
        }
        if (phanQuyen.getQlSanPham() == 1) {
            ckbQLSanPham.setSelected(true);
        }
        if (phanQuyen.getQlNhanVien() == 1) {
            ckbQLNhanVien.setSelected(true);
        }
        if (phanQuyen.getQlKhachHang() == 1) {
            ckbQLKhachHang.setSelected(true);
        }
        if (phanQuyen.getThongKe() == 1) {
            ckbThongKe.setSelected(true);
        }
    }

    private Boolean checkQuyenAdmin(PhanQuyen phanQuyen) {
        if (phanQuyen.getQuyen().equals("Quản trị")) {
            return true;
        }
        return false;
    }

    private Boolean checkQuyenQuanLy(PhanQuyen phanQuyen) {
        if (phanQuyen.getQuyen().equals("Quản lý")) {
            return true;
        }
        return false;
    }

    private void loadDataCmbQuyen() {
        phanQuyenBUS.docDanhSachQuyen();
        ArrayList<PhanQuyen> dsq = phanQuyenBUS.getListQuyen();
        cmbQuyen.removeAllItems();
        cmbQuyen.addItem("Chọn quyền");
        for (PhanQuyen pq : dsq) {
            cmbQuyen.addItem(pq.getQuyen());
        }
    }

    private void xuLyResetMatKhau() {
        String maNV = txtMaNV.getText();
        if (maNV.trim().equals("")) {
            new Dialog("Hãy chọn nhân viên!", Dialog.ERROR_DIALOG);
            return;
        }
        DlgQuyen_MatKhau dialog = new DlgQuyen_MatKhau(maNV);
        dialog.setVisible(true);
    }

    private void xuLyCapTaiKhoan() {
        if (txtMaNV.getText().trim().equals("")) {
            new Dialog("Hãy chọn nhân viên!", Dialog.ERROR_DIALOG);
            return;
        }
        DlgCapTaiKhoan dialog = new DlgCapTaiKhoan(txtMaNV.getText());
        dialog.setVisible(true);
        loadDataTblNhanVien(null);
    }

    private void xuLyKhoaTaiKhoan() {
        TaiKhoanBUS taiKhoanBUS = TaiKhoanBUS.getInstance();
        NhanVienDAO nhanVienDAO = NhanVienDAO.getInstance();
        int maTK = nhanVienDAO.getNhanVien(Integer.parseInt(txtMaNV.getText())).getMaTK();
        if (checkMyself(maTK)) {
            new Dialog("Không thể khóa tài khoản bản thân!", Dialog.ERROR_DIALOG);
            return;
        }
        if (checkQuanTri()) {
            if (checkAdminTheoMa(maTK)) {
                new Dialog("Không thể khóa tài khoản quản trị!", Dialog.ERROR_DIALOG);
                return;
            }
            if (checkQuanLyTheoMa(maTK)) {
                new Dialog("Không thể khóa tài khoản quản lý!", Dialog.ERROR_DIALOG);
                return;
            }
        } else if (!checkAdmin()) {
            if (checkAdminTheoMa(maTK)) {
                new Dialog("Không thể khóa tài khoản quản trị!", Dialog.ERROR_DIALOG);
                return;
            }
            if (checkQuanLyTheoMa(maTK)) {
                new Dialog("Không thể khóa tài khoản quản lý!", Dialog.ERROR_DIALOG);
                return;
            }
        }
        taiKhoanBUS.khoaTaiKhoan(maTK);
        loadDataTblNhanVien(null);
    }



    private void xuLyNhapExcel() {
        Dialog dlg = new Dialog("Dữ liệu cũ sẽ bị xoá, tiếp tục?", Dialog.WARNING_DIALOG);
        if (dlg.getAction() != Dialog.OK_OPTION) {
            return;
        }

        XuLyFileExcel nhapExcel = new XuLyFileExcel();
        nhapExcel.nhapExcel(tblNhanVien);

        int row = tblNhanVien.getRowCount();
        for (int i = 0; i < row; i++) {
            String ho = tblNhanVien.getValueAt(i, 1) + "";
            String ten = tblNhanVien.getValueAt(i, 2) + "";
            String gioiTinh = tblNhanVien.getValueAt(i, 3) + "";
            String chucVu = tblNhanVien.getValueAt(i, 4) + "";

            nhanVienBUS.nhapExcel(ho, ten, gioiTinh, chucVu);

        }
    }

    private void xuLyXuatExcel() {
        XuLyFileExcel xuatExcel = new XuLyFileExcel();
        xuatExcel.xuatExcel(tblNhanVien);
    }

    private void xuLyXoaNhanVien() {
        String ma = txtMaNV.getText();
        boolean flag = nhanVienBUS.xoaNhanVien(ma);
        if (flag) {
            nhanVienBUS.docDanhSach();
            loadDataTblNhanVien(null);
        }
    }

    private void xuLySuaNhanVien() {
        if (txtDiaChi.getText().length() == 0) {
            new Dialog("Hãy nhập địa chỉ!", Dialog.ERROR_DIALOG);
            return;
        }
        String ma = txtMaNV.getText();
        String ten = txtTen.getText();
        String ngaySinh = txtNgaySinh.getText();
        String diaChi = txtDiaChi.getText();
        String sdt = txtSDT.getText();
        if (nhanVienBUS.updateNhanVien(ma, ten, ngaySinh, diaChi, sdt)) {
            nhanVienBUS.docDanhSach();
            loadDataTblNhanVien(null);
        }
    }

    private void xuLyThemNhanVien() {
        if (txtDiaChi.getText().length() == 0) {
            new Dialog("Hãy nhập địa chỉ!", Dialog.ERROR_DIALOG);
            return;
        }
        String ten = txtTen.getText();
        String ngaySinh = txtNgaySinh.getText();
        String diaChi = txtDiaChi.getText();
        String sdt = txtSDT.getText();
        if (nhanVienBUS.themNhanVien(ten, ngaySinh, diaChi, sdt)) {
            nhanVienBUS.docDanhSach();
            loadDataTblNhanVien(null);
        }
    }

    private void xuLyTimKiemNhanVien() {
        ArrayList<NhanVien> dsnv = nhanVienBUS.timNhanVien(txtTimNV.getText());
        dtmNhanVien.setRowCount(0);
        loadDataTblNhanVien(dsnv);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private void xuLyClickTblNhanVien() {
        int row = tblNhanVien.getSelectedRow();
        if (row > -1) {
            txtMaNV.setText(tblNhanVien.getValueAt(row, 0) + "");
            txtTen.setText(tblNhanVien.getValueAt(row, 1) + "");
            Date d;
            try {
                d = sdf.parse(tblNhanVien.getValueAt(row, 2) + "");
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                txtNgaySinh.setText(sdf.format(d));
            } catch (Exception ex) {
                d = new Date();
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                txtNgaySinh.setText(sdf.format(d));
            }
            txtSDT.setText(tblNhanVien.getValueAt(row, 4) + "");
            txtDiaChi.setText(tblNhanVien.getValueAt(row, 3) + "");
            if (tblNhanVien.getValueAt(row, 5).equals("Khả dụng")) {
                turnOffButtonCapTaiKhoan();
                turnOnButtonKhoaTaiKhoan();
            } else {
                turnOnButtonCapTaiKhoan();
                turnOffButtonKhoaTaiKhoan();
            }
            turnOffButtonThemNhanVien();
            turnOnButtonSuaNhanVien();
            turnOffTxtNgaySinhNV();
            turnOffTxtTenNV();
        }
    }

    private void loadDataTblNhanVien(ArrayList<NhanVien> dsnv) {
        dtmNhanVien.setRowCount(0);
        if (dsnv == null)

            dsnv = nhanVienBUS.getDanhSachNhanVien();

        for (NhanVien nv : dsnv) {
            Vector vec = new Vector();
            vec.add(nv.getMaNV());
            vec.add(nv.getTen());
            Date d;
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                d = sdf.parse(nv.getNgaySinh());
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                vec.add(sdf.format(d));
            } catch (Exception ex) {
                d = new Date();
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                vec.add(sdf.format(d));
            }
            vec.add(nv.getDiaChi());
            vec.add(nv.getSdt());
            int trangThai = taiKhoanBUS.getTrangThai(nv.getMaTK() + "");

            if (nv.getMaTK() == -1) {
                vec.add("Không khả dụng");
            } else if (trangThai == 0) {
                vec.add("Bị khoá");
            } else if (trangThai == 1) {
                vec.add("Khả dụng");
            }

            dtmNhanVien.addRow(vec);
            turnOnButtonCapTaiKhoan();
            turnOnButtonThemNhanVien();
            turnOffButtonSuaNhanVien();
            turnOffButtonXoaNhanVien();
            turnOnTxtNgaySinhNV();
            turnOnTxtTenNV();
        }
    }

    private Boolean checkAdmin() {
        int maTK = DangNhapBUS.taiKhoanLogin.getMaTK();
        String tenQuyen = taiKhoanBUS.getQuyenTheoMa(maTK+"");
        if (tenQuyen.equals("Quản trị")) {
            return true;
        }
        return false;
    }

    private Boolean checkQuanTri() {
        int maTK = DangNhapBUS.taiKhoanLogin.getMaTK();
        String tenQuyen = taiKhoanBUS.getQuyenTheoMa(maTK+"");
        if (tenQuyen.equals("Quản lý")) {
            return true;
        }
        return false;
    }

    private Boolean checkMyself(int maTKsub) {
        int maTK = DangNhapBUS.taiKhoanLogin.getMaTK();
        return maTK == maTKsub;
    }

    private Boolean checkAdminTheoMa(int maTK) {
        String tenQuyen = taiKhoanBUS.getQuyenTheoMa(maTK+"");
        if (tenQuyen.equals("Quản trị")) {
            return true;
        }
        return false;
    }

    private Boolean checkQuanLyTheoMa(int maTK) {
        String tenQuyen = taiKhoanBUS.getQuyenTheoMa(maTK+"");
        System.out.println(tenQuyen);
        if (tenQuyen.equals("Quản lý")) {
            return true;
        }
        return false;
    }

    private void turnOnTxtTenNV() {
        txtTen.setEditable(true);
    }

    private void turnOffTxtTenNV() {
        txtTen.setEditable(false);
    }

    private void turnOnTxtNgaySinhNV() {
        txtNgaySinh.setEditable(true);
    }

    private void turnOffTxtNgaySinhNV() {
        txtNgaySinh.setEditable(false);
    }

    private void turnOnButtonCapTaiKhoan() {
        btnCapTaiKhoan.setEnabled(true);
    }

    private void turnOffButtonCapTaiKhoan() {
        btnCapTaiKhoan.setEnabled(false);
    }

    private void turnOnButtonThemNhanVien() {
        btnThemNV.setEnabled(true);
    }

    private void turnOffButtonThemNhanVien() {
        btnThemNV.setEnabled(false);
    }

    private void turnOnButtonSuaNhanVien() {
        btnSuaNV.setEnabled(true);
    }

    private void turnOffButtonSuaNhanVien() {
        btnSuaNV.setEnabled(false);
    }

    private void turnOnButtonXoaNhanVien() {
        btnXoaNV.setEnabled(true);
    }

    private void turnOffButtonXoaNhanVien() {
        btnXoaNV.setEnabled(false);
    }

    private void turnOnButtonKhoaTaiKhoan() {
        btnXoaTaiKhoan.setEnabled(true);
    }

    private void turnOffButtonKhoaTaiKhoan() {
        btnXoaTaiKhoan.setEnabled(false);
    }

    private void turnOnButtonXoaQuyen() {
        btnXoaQuyen.setEnabled(true);
    }

    private void turnOffButtonXoaQuyen() {
        btnXoaQuyen.setEnabled(false);
    }

    private void turnOnButtonSuaQuyen() {
        btnSuaQuyen.setEnabled(true);
    }

    private void turnOffButtonSuaQuyen() {
        btnSuaQuyen.setEnabled(false);
    }

    private void turnOnButtonThemQuyen() {
        btnThemQuyen.setEnabled(true);
    }

    private void turnOffButtonThemQuyen() {
        btnThemQuyen.setEnabled(false);
    }

    TaiKhoanBUS taiKhoanBUS = TaiKhoanBUS.getInstance();

}
