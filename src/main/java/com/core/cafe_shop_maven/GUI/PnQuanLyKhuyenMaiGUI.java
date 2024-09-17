package com.core.cafe_shop_maven.GUI;

import com.core.cafe_shop_maven.BUS.KhuyenMaiBUS;
import com.core.cafe_shop_maven.DTO.KhuyenMai;
import com.core.cafe_shop_maven.Cafe_shop_maven;
import com.core.cafe_shop_maven.CustomFunctions.Table;
import com.core.cafe_shop_maven.CustomFunctions.TransparentPanel;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class PnQuanLyKhuyenMaiGUI extends JPanel {

    public PnQuanLyKhuyenMaiGUI() {
        Cafe_shop_maven.changLNF("Windows");
        addControls();
        addEvents();
    }

    private KhuyenMaiBUS KhuyenMaiBUS = com.core.cafe_shop_maven.BUS.KhuyenMaiBUS.getInstance();
    final Color colorPanel = new Color(247, 247, 247);

    JButton btnReset, btnThem, btnSua;
    JTextField txtMa, txtTen, txtPhanTram, txtDieuKien;
    Table tblKhuyenMai;
    DefaultTableModel dtmKhuyenMai;
    JDateChooser dateBD, dateKT;

    private void addControls() {
        Font font = new Font("Tahoma", Font.PLAIN, 16);
        this.setLayout(new BorderLayout());
        this.setBackground(colorPanel);

        int w = 1030;
        int h = 844;

        // =================MAIN PANEL=================
        // =====TITLE====
        JPanel pnMain = new TransparentPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));

        JPanel pnTitle = new TransparentPanel();
        JLabel lblTitle = new JLabel("<html><h1>QUẢN LÝ MÃ KHUYẾN MÃI</h1></html>");
        btnReset = new JButton(new ImageIcon("src/main/resources/image/Refresh-icon.png"));
        btnReset.setFocusPainted(false);
        btnReset.setPreferredSize(new Dimension(40, 40));
        pnTitle.add(lblTitle);
        pnTitle.add(btnReset);
        pnMain.add(pnTitle);

        // ========TEXTFIELD=========
        JPanel pnTextField = new TransparentPanel();
        pnTextField.setLayout(new BoxLayout(pnTextField, BoxLayout.Y_AXIS));

        JLabel lblMa, lblTen, lblPhanTram, lblDieuKien, lblNgayBD, lblNgayKT;
        lblMa = new JLabel("Mã Khuyến mãi");
        lblTen = new JLabel("Tên chương trình");
        lblPhanTram = new JLabel("Phần trăm giảm");
        lblDieuKien = new JLabel("Điều kiện (>x)");
        lblNgayBD = new JLabel("Ngày bắt đầu");
        lblNgayKT = new JLabel("Ngày kết thúc");

        lblMa.setFont(font);
        lblTen.setFont(font);
        lblPhanTram.setFont(font);
        lblDieuKien.setFont(font);
        lblNgayBD.setFont(font);
        lblNgayKT.setFont(font);

        txtMa = new JTextField(20);
        txtTen = new JTextField(20);
        txtPhanTram = new JTextField(20);
        txtDieuKien = new JTextField(20);
        dateBD = new JDateChooser();
        dateBD.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        dateKT = new JDateChooser();
        dateKT.setDateFormatString("yyyy-MM-dd HH:mm:ss");

        txtMa.setEditable(false);
        dateBD.getCalendarButton().setPreferredSize(new Dimension(32, 32));
        dateBD.getCalendarButton().setIcon(new ImageIcon("src/main/resources/image/icons8_calendar_25_20px.png"));
        dateKT.getCalendarButton().setPreferredSize(dateBD.getCalendarButton().getPreferredSize());
        dateKT.getCalendarButton().setIcon(dateBD.getCalendarButton().getIcon());

        txtMa.setFont(font);
        txtTen.setFont(font);
        txtPhanTram.setFont(font);
        txtDieuKien.setFont(font);
        dateBD.setFont(font);
        dateKT.setFont(font);

        JPanel pnMa = new TransparentPanel();
        pnMa.add(lblMa);
        pnMa.add(txtMa);
        pnTextField.add(pnMa);

        JPanel pnTen = new TransparentPanel();
        pnTen.add(lblTen);
        pnTen.add(txtTen);
        pnTextField.add(pnTen);

        JPanel pnPhanTram = new TransparentPanel();
        pnPhanTram.add(lblPhanTram);
        pnPhanTram.add(txtPhanTram);
        pnTextField.add(pnPhanTram);

        JPanel pnDieuKien = new TransparentPanel();
        pnDieuKien.add(lblDieuKien);
        pnDieuKien.add(txtDieuKien);
        pnTextField.add(pnDieuKien);

        JPanel pnNgayBD = new TransparentPanel();
        pnNgayBD.add(lblNgayBD);
        pnNgayBD.add(dateBD);
        pnTextField.add(pnNgayBD);

        JPanel pnNgayKT = new TransparentPanel();
        pnNgayKT.add(lblNgayKT);
        pnNgayKT.add(dateKT);
        pnTextField.add(pnNgayKT);

        pnMain.add(pnTextField);

        Dimension lblSize = lblTen.getPreferredSize();
        lblMa.setPreferredSize(lblSize);
        lblTen.setPreferredSize(lblSize);
        lblPhanTram.setPreferredSize(lblSize);
        lblDieuKien.setPreferredSize(lblSize);
        lblNgayBD.setPreferredSize(lblSize);
        lblNgayKT.setPreferredSize(lblSize);
        dateBD.setPreferredSize(txtDieuKien.getPreferredSize());
        dateKT.setPreferredSize(txtDieuKien.getPreferredSize());

        // ==========BUTTON PANEL===============
        JPanel pnButton = new TransparentPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnThem.setFont(font);
        btnSua.setFont(font);
        btnThem.setIcon(new ImageIcon("src/main/resources/image/add-icon.png"));
        btnSua.setIcon(new ImageIcon("src/main/resources/image/Pencil-icon.png"));
        pnButton.add(btnThem);
        pnButton.add(btnSua);
        pnMain.add(pnButton);
        btnSua.setPreferredSize(btnThem.getPreferredSize());

        // ======================TABLE======================
        JPanel pnTable = new TransparentPanel(new BorderLayout());
        dtmKhuyenMai = new DefaultTableModel();
        dtmKhuyenMai.addColumn("Mã KM");
        dtmKhuyenMai.addColumn("Chương trình");
        dtmKhuyenMai.addColumn("Phần trăm KM");
        dtmKhuyenMai.addColumn("Điều kiện");
        dtmKhuyenMai.addColumn("Ngày bắt đầu");
        dtmKhuyenMai.addColumn("Ngày kết thúc");
        dtmKhuyenMai.addColumn("Tình trạng");

        tblKhuyenMai = new Table(dtmKhuyenMai);
        tblKhuyenMai.setDefaultEditor(Object.class, null);
        tblKhuyenMai.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrTblKhuyenMai = new JScrollPane(tblKhuyenMai);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblKhuyenMai.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblKhuyenMai.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tblKhuyenMai.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tblKhuyenMai.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);

        TableColumnModel columnModelBanHang = tblKhuyenMai.getColumnModel();
        columnModelBanHang.getColumn(0).setPreferredWidth(24);
        columnModelBanHang.getColumn(1).setPreferredWidth(189);
        columnModelBanHang.getColumn(2).setPreferredWidth(66);
        columnModelBanHang.getColumn(3).setPreferredWidth(56);
        columnModelBanHang.getColumn(4).setPreferredWidth(81);
        columnModelBanHang.getColumn(5).setPreferredWidth(81);
        columnModelBanHang.getColumn(6).setPreferredWidth(92);

        pnTable.add(scrTblKhuyenMai, BorderLayout.CENTER);
        pnMain.add(pnTable);

        this.add(pnMain, BorderLayout.CENTER);

        loadDataTblKhuyenMai();
    }

    private void addEvents() {
        tblKhuyenMai.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyClickTblKhuyenMai();
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

        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyThemKhuyenMai();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLySuaKhuyenMai();
            }
        });
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DecimalFormat dcf = new DecimalFormat(">###,###");

    private void loadDataTblKhuyenMai() {
        dtmKhuyenMai.setRowCount(0);
        KhuyenMaiBUS.docDanhSach();
        ArrayList<KhuyenMai> dsg = KhuyenMaiBUS.getDanhSachKhuyenMai();

        Date ngayBD, ngayKT;

        for (KhuyenMai km : dsg) {
            Vector vec = new Vector();
            vec.add(km.getMaKM());
            vec.add(km.getTenKM());
            vec.add(km.getPhanTramKM());
            vec.add(dcf.format(km.getDieuKien()));
            vec.add(km.getNgayBD());
            vec.add(km.getNgayKT());

            Date now = new Date();

            try {
                ngayBD = sdf.parse(km.getNgayBD());
                ngayKT = sdf.parse(km.getNgayKT());
                if (ngayBD.before(now) && ngayKT.after(now)) {
                    vec.add("Còn hiệu lực");
                } else {
                    vec.add("Hết hiệu lực");
                }

            } catch (Exception ex) {
                vec.add("Không xác định");
            }
            dtmKhuyenMai.addRow(vec);
        }
    }

    private void xuLyClickTblKhuyenMai() {
        int row = tblKhuyenMai.getSelectedRow();
        if (row > -1) {
            String ma = tblKhuyenMai.getValueAt(row, 0) + "";
            String ten = tblKhuyenMai.getValueAt(row, 1) + "";
            String phanTram = tblKhuyenMai.getValueAt(row, 2) + "";
            String dieuKien = tblKhuyenMai.getValueAt(row, 3) + "";
            String start = tblKhuyenMai.getValueAt(row, 4) + "";
            String end = tblKhuyenMai.getValueAt(row, 5) + "";

            dieuKien = dieuKien.replace(">", "");
            dieuKien = dieuKien.replace(",", "");
            java.util.Date ngayBD = new Date();
            java.util.Date ngayKT = new Date();
            txtMa.setText(ma);
            txtTen.setText(ten);
            txtPhanTram.setText(phanTram);
            txtDieuKien.setText(dieuKien);

            try {
                ngayBD = sdf.parse(start);
                ngayKT = sdf.parse(end);
                dateBD.setDate(ngayBD);
                dateKT.setDate(ngayKT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void xuLyThemKhuyenMai() {
        boolean flag = KhuyenMaiBUS.themKhuyenMai(txtTen.getText(), txtPhanTram.getText(), txtDieuKien.getText(),
                sdf.format(dateBD.getDate()), sdf.format(dateKT.getDate()));
        if (flag)
            loadDataTblKhuyenMai();
    }

    private void xuLySuaKhuyenMai() {
        boolean flag = KhuyenMaiBUS.suaKhuyenMai(txtMa.getText(), txtTen.getText(), txtPhanTram.getText(),
                txtDieuKien.getText(), sdf.format(dateBD.getDate()), sdf.format(dateKT.getDate()));
        if (flag)
            loadDataTblKhuyenMai();
    }
}
