package com.core.cafe_shop_maven.GUI;

import com.core.cafe_shop_maven.BUS.KhuyenMaiBUS;
import com.core.cafe_shop_maven.DTO.KhuyenMai;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;
import com.core.cafe_shop_maven.CustomFunctions.Table;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class DlgKM extends JDialog {

    public static KhuyenMai kmTimDuoc = null;
    private KhuyenMaiBUS KhuyenMaiBUS = com.core.cafe_shop_maven.BUS.KhuyenMaiBUS.getInstance();
    private int tongTien = 0;

    public DlgKM(int tongTien) {
        this.tongTien = tongTien;
        addControls();
        addEvents();

        this.setSize(750, 500);
        this.setModal(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private JTextField txtTuKhoa;
    private JTable tblMaGiam;
    private DefaultTableModel dtmKM;
    private JButton btnChon, btnThoat;

    private void addControls() {
        Container con = getContentPane();
        con.setLayout(new BorderLayout());

        Font font = new Font("Tahoma", Font.PLAIN, 16);
        JPanel pnTop = new JPanel();
        JLabel lblTuKhoa = new JLabel("Từ khoá tìm");
        txtTuKhoa = new JTextField(20);
        lblTuKhoa.setFont(font);
        txtTuKhoa.setFont(font);
        pnTop.add(lblTuKhoa);
        pnTop.add(txtTuKhoa);
        con.add(pnTop, BorderLayout.NORTH);

        JPanel pnTable = new JPanel();
        pnTable.setLayout(new BorderLayout());
        dtmKM = new DefaultTableModel();
        dtmKM.addColumn("Mã");
        dtmKM.addColumn("Chương trình");
        dtmKM.addColumn("% KM");
        dtmKM.addColumn("Điều kiện");
        dtmKM.addColumn("Bắt đầu");
        dtmKM.addColumn("Kết thúc");
        dtmKM.addColumn("Trạng thái");
        tblMaGiam = new Table(dtmKM);
        JScrollPane scrMaGiam = new JScrollPane(tblMaGiam);
        pnTable.add(scrMaGiam, BorderLayout.CENTER);
        con.add(pnTable, BorderLayout.CENTER);

        JPanel pnButton = new JPanel();
        btnChon = new JButton("Chọn");
        btnThoat = new JButton("Thoát");
        btnChon.setFont(font);
        btnThoat.setFont(font);
        pnButton.add(btnChon);
        pnButton.add(btnThoat);
        con.add(pnButton, BorderLayout.SOUTH);

        TableColumnModel columnModelBanHang = tblMaGiam.getColumnModel();
        columnModelBanHang.getColumn(0).setPreferredWidth(56);
        columnModelBanHang.getColumn(1).setPreferredWidth(213);
        columnModelBanHang.getColumn(2).setPreferredWidth(30);
        columnModelBanHang.getColumn(3).setPreferredWidth(62);
        columnModelBanHang.getColumn(4).setPreferredWidth(58);
        columnModelBanHang.getColumn(5).setPreferredWidth(61);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tblMaGiam.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tblMaGiam.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        btnChon.setPreferredSize(new Dimension(120, 40));
        btnThoat.setPreferredSize(btnChon.getPreferredSize());

        loadDataLenTable();
    }

    private void addEvents() {
        txtTuKhoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataLenTable(txtTuKhoa.getText());
            }
        });

        btnChon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyChonMaGiam();
            }
        });

        btnThoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyThoat();
            }
        });
    }

    private void xuLyChonMaGiam() {
        int row = tblMaGiam.getSelectedRow();
        if (row > -1) {
            if (tblMaGiam.getValueAt(row, 6).equals("Không hiệu lực")) {
                new Dialog("Khuyến mãi này đã hết hiệu lực!", Dialog.ERROR_DIALOG);
                loadDataLenTable();
                return;
            }
            int ma = Integer.parseInt(tblMaGiam.getValueAt(row, 0) + "");
            String ten = tblMaGiam.getValueAt(row, 1) + "";
            int phanTram = Integer.parseInt(tblMaGiam.getValueAt(row, 2) + "");
            String dieuKienst = tblMaGiam.getValueAt(row, 3) + "";
            dieuKienst = dieuKienst.replace(">", "");
            dieuKienst = dieuKienst.replace(",", "");
            int dieuKien = Integer.parseInt(dieuKienst);

            if (dieuKien > tongTien) {
                new Dialog("Không đủ điều kiện áp dụng khuyến mãi này!", Dialog.ERROR_DIALOG);
                return;
            }

            String ngayBD = tblMaGiam.getValueAt(row, 4) + "";
            String ngayKT = tblMaGiam.getValueAt(row, 5) + "";

            kmTimDuoc = new KhuyenMai();
            kmTimDuoc.setMaKM(ma);
            kmTimDuoc.setTenKM(ten);
            kmTimDuoc.setPhanTramKM(phanTram);
            kmTimDuoc.setDieuKien(dieuKien);
            kmTimDuoc.setNgayBD(ngayBD);
            kmTimDuoc.setNgayKT(ngayKT);
        }
        xuLyThoat();
    }

    private void xuLyThoat() {
        dispose();
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DecimalFormat dcf = new DecimalFormat(">###,###");

    private void loadDataLenTable() {
        dtmKM.setRowCount(0);
        KhuyenMaiBUS.docDanhSach();
        ArrayList<KhuyenMai> dskm = KhuyenMaiBUS.getDanhSachKhuyenMai();
        Date ngayBDdt, ngayKTdt;
        for (KhuyenMai km : dskm) {
            Vector vec = new Vector();
            vec.add(km.getMaKM());
            vec.add(km.getTenKM());
            vec.add(km.getPhanTramKM());
            vec.add(dcf.format(km.getDieuKien()));
            vec.add(km.getNgayBD());
            vec.add(km.getNgayKT());

            Date now = new Date();

            try {
                ngayBDdt = sdf.parse(km.getNgayBD());
                ngayKTdt = sdf.parse(km.getNgayKT());
                if (ngayBDdt.before(now) && ngayKTdt.after(now)) {
                    vec.add("Còn hiệu lực");
                } else {
                    vec.add("Hết hiệu lực");
                }
            } catch (Exception ex) {
                vec.add("Không xác định");
            }
            dtmKM.addRow(vec);
        }
    }

    private void loadDataLenTable(String tuKhoa) {
        TableColumnModel columnModelBanHang = tblMaGiam.getColumnModel();

    }

}
