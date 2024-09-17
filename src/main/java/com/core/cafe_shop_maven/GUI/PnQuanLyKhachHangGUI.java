package com.core.cafe_shop_maven.GUI;

import com.core.cafe_shop_maven.BUS.KhachHangBUS;
import com.core.cafe_shop_maven.DTO.KhachHang;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;
import com.core.cafe_shop_maven.CustomFunctions.Table;
import com.core.cafe_shop_maven.CustomFunctions.TransparentPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import static com.core.cafe_shop_maven.Cafe_shop_maven.changLNF;

public class PnQuanLyKhachHangGUI extends JPanel {

    public PnQuanLyKhachHangGUI() {
        changLNF("Windows");
        addControls();
        addEvents();
    }

    private KhachHangBUS khachHangBUS = KhachHangBUS.getInstance();

    final Color colorPanel = new Color(247, 247, 247);
    JButton btnReset;
    JTextField txtMa, txtTen, txtDiaChi, txtSDT, txtTukhoa;
    JButton btnThem, btnSua, btnXoa;
    Table tblKhachHang;
    DefaultTableModel dtmKhachHang;

    private void addControls() {
        Font font = new Font("Tahoma", Font.PLAIN, 20);

        this.setLayout(new BorderLayout());
        this.setBackground(colorPanel);
        int w = 1030;
        int h = 844;

        /*
         * =========================================================================
         * PANEL KHÁCH HÀNG
         * =========================================================================
         */
        JPanel pnKhachHang = new TransparentPanel();
        pnKhachHang.setLayout(new BoxLayout(pnKhachHang, BoxLayout.Y_AXIS));

        JPanel pnTopKH = new TransparentPanel();
        pnTopKH.setLayout(new BoxLayout(pnTopKH, BoxLayout.Y_AXIS));

        JPanel pnTitle = new TransparentPanel();
        JLabel lblTitle = new JLabel("<html><h1>QUẢN LÝ KHÁCH HÀNG</h1></html>");
        btnReset = new JButton(new ImageIcon("src/main/resources/image/Refresh-icon.png"));
        btnReset.setPreferredSize(new Dimension(40, 40));
        pnTitle.add(lblTitle);
        pnTitle.add(btnReset);
        pnTopKH.add(pnTitle);

        // ======PANEL TEXT FIELD=======
        JPanel pnTextField = new TransparentPanel();
        pnTextField.setLayout(new BoxLayout(pnTextField, BoxLayout.Y_AXIS));

        JLabel lblMa, lblTen, lblDiaChi, lblSDT;
        lblMa = new JLabel("Mã Khách hàng");
        lblTen = new JLabel("Tên");
        lblDiaChi = new JLabel("Địa chỉ");
        lblSDT = new JLabel("Số điện thoại");

        lblMa.setFont(font);
        lblTen.setFont(font);
        lblDiaChi.setFont(font);
        lblSDT.setFont(font);

        txtMa = new JTextField(20);
        txtMa.setEditable(false);
        txtTen = new JTextField(20);
        txtDiaChi = new JTextField(20);
        txtSDT = new JTextField(20);

        txtMa.setFont(font);
        txtTen.setFont(font);
        txtDiaChi.setFont(font);
        txtSDT.setFont(font);

        JPanel pnMa = new TransparentPanel();
        pnMa.add(lblMa);
        pnMa.add(txtMa);
        pnTextField.add(pnMa);

        JPanel pnTen = new TransparentPanel();
        pnTen.add(lblTen);
        pnTen.add(txtTen);
        pnTextField.add(pnTen);

        JPanel pnDiaChi = new TransparentPanel();
        pnDiaChi.add(lblDiaChi);
        pnDiaChi.add(txtDiaChi);
        pnTextField.add(pnDiaChi);

        JPanel pnSDT = new TransparentPanel();
        pnSDT.add(lblSDT);
        pnSDT.add(txtSDT);
        pnTextField.add(pnSDT);

        Dimension lblSize = lblMa.getPreferredSize();
        lblMa.setPreferredSize(lblSize);
        lblTen.setPreferredSize(lblSize);
        lblDiaChi.setPreferredSize(lblSize);
        lblSDT.setPreferredSize(lblSize);

        pnTopKH.add(pnTextField);
        pnKhachHang.add(pnTopKH);

        // ===============PANEL BUTTON=============
        JPanel pnButton = new TransparentPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Lưu");
        btnXoa = new JButton("Xoá");

        btnThem.setIcon(new ImageIcon("src/main/resources/image/add-icon.png"));
        btnSua.setIcon(new ImageIcon("src/main/resources/image/Pencil-icon.png"));
        btnXoa.setIcon(new ImageIcon("src/main/resources/image/delete-icon.png"));
        Font fontButton = new Font("Tahoma", Font.PLAIN, 16);

        btnThem.setFont(fontButton);
        btnSua.setFont(fontButton);
        btnXoa.setFont(fontButton);
        pnKhachHang.add(pnButton);

        pnButton.add(btnThem);
        pnButton.add(btnSua);
        pnButton.add(btnXoa);

        btnThem.setIcon(new ImageIcon("src/main/resources/image/add-icon.png"));
        btnSua.setIcon(new ImageIcon("src/main/resources/image/Pencil-icon.png"));
        btnXoa.setIcon(new ImageIcon("src/main/resources/image/delete-icon.png"));
        Dimension btnSize = btnThem.getPreferredSize();
        btnThem.setPreferredSize(btnSize);
        btnSua.setPreferredSize(btnSize);
        btnXoa.setPreferredSize(btnSize);

        // ====PANEL SEARCH=====
        JPanel pnTimKiem = new TransparentPanel();
        JLabel lblTimKiem = new JLabel("Từ khoá tìm");
        lblTimKiem.setFont(font);
        txtTukhoa = new JTextField(20);
        txtTukhoa.setFont(font);
        pnTimKiem.add(lblTimKiem);
        pnTimKiem.add(txtTukhoa);
        pnKhachHang.add(pnTimKiem);

        // =========================TABLE=====================
        dtmKhachHang = new DefaultTableModel();
        dtmKhachHang.addColumn("Mã KH");
        dtmKhachHang.addColumn("Tên");
        dtmKhachHang.addColumn("Địa chỉ");
        dtmKhachHang.addColumn("SĐT");

        tblKhachHang = new Table(dtmKhachHang);
        tblKhachHang.setDefaultEditor(Object.class, null);
        tblKhachHang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrtblKhachHang = new JScrollPane(tblKhachHang);

        this.add(pnKhachHang, BorderLayout.NORTH);
        this.add(scrtblKhachHang, BorderLayout.CENTER);

        loadDataLenTableKhachHang();
    }

    private void addEvents() {
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataLenTableKhachHang();
                txtMa.setText("");
                txtTen.setText("");
                txtDiaChi.setText("");
                txtSDT.setText("");
                txtTukhoa.setText("");
            }
        });

        tblKhachHang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyClicktblKhachHang();
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

        txtTukhoa.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                xuLyLiveSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                xuLyLiveSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                xuLyLiveSearch();
            }
        });

        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyThemKhachHang();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLySuaKhachHang();
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXoaKhachHang();
            }
        });
    }

    private void loadDataLenTableKhachHang() {
        khachHangBUS.docDanhSach();
        ArrayList<KhachHang> dskh = khachHangBUS.getListKhachHang();
        loadDataLenTableKhachHang(dskh);
        turnOffButtonSua();
        turnOnButtonThem();
    }

    private void loadDataLenTableKhachHang(ArrayList<KhachHang> dskh) {
        dtmKhachHang.setRowCount(0);
        for (KhachHang kh : dskh) {
            Vector vec = new Vector();
            vec.add(kh.getMaKH());
            vec.add(kh.getTen());
            vec.add(kh.getDiaChi());
            vec.add(kh.getSdt());
            dtmKhachHang.addRow(vec);
        }
    }

    private void xuLyClicktblKhachHang() {
        int row = tblKhachHang.getSelectedRow();
        if (row > -1) {
            txtMa.setText(tblKhachHang.getValueAt(row, 0) + "");
            txtTen.setText(tblKhachHang.getValueAt(row, 1) + "");
            txtDiaChi.setText(tblKhachHang.getValueAt(row, 2) + "");
            txtSDT.setText(tblKhachHang.getValueAt(row, 3) + "");
        }
        turnOnButtonSua();
        turnOffButtonThem();
    }

    private void xuLyLiveSearch() {
        ArrayList<KhachHang> dskh = khachHangBUS.timKiemKhachHang(txtTukhoa.getText());
        loadDataLenTableKhachHang(dskh);
    }

    private void xuLyThemKhachHang() {
        if (khachHangBUS.themKhachHang(txtTen.getText(), txtDiaChi.getText(), txtSDT.getText()))
            btnReset.doClick();
    }

    private void xuLySuaKhachHang() {
        if (khachHangBUS.suaKhachHang(txtMa.getText(), txtTen.getText(), txtDiaChi.getText(), txtSDT.getText()))
            btnReset.doClick();
    }

    private void xuLyXoaKhachHang() {
        if (khachHangBUS.xoaKhachHang(txtMa.getText()))
            btnReset.doClick();
    }

    private void turnOnTxtTen() {
        txtTen.setEditable(true);
    }

    private void turnOffTxtTen() {
        txtTen.setEditable(false);
    }

    private void turnOnButtonThem() {
        btnThem.setEnabled(true);
    }

    private void turnOffButtonThem() {
        btnThem.setEnabled(false);
    }

    private void turnOnButtonSua() {
        btnSua.setEnabled(true);
    }

    private void turnOffButtonSua() {
        btnSua.setEnabled(false);
    }
}
