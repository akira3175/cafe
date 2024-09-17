package com.core.cafe_shop_maven.GUI;

import com.core.cafe_shop_maven.BUS.LoaiBUS;
import com.core.cafe_shop_maven.BUS.SanPhamBUS;
import com.core.cafe_shop_maven.DTO.LoaiSP;
import com.core.cafe_shop_maven.DTO.SanPham;

import static com.core.cafe_shop_maven.Cafe_shop_maven.changLNF;

import com.core.cafe_shop_maven.CustomFunctions.XuLyFileExcel;
import com.core.cafe_shop_maven.CustomFunctions.Dialog;
import com.core.cafe_shop_maven.CustomFunctions.FileChooser;
import com.core.cafe_shop_maven.CustomFunctions.Table;
import com.core.cafe_shop_maven.CustomFunctions.TransparentPanel;
import com.core.cafe_shop_maven.DAO.SanPhamDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class PnQuanLySanPhamGUI extends JPanel {

    public PnQuanLySanPhamGUI() {
        changLNF("Windows");
        addControlsSanPham();
        addEventsSanPham();
    }

    SanPhamBUS spBUS = SanPhamBUS.getInstance();
    LoaiBUS loaiBUS = LoaiBUS.getInstance();
    final Color colorPanel = new Color(247, 247, 247);
    Table tblSanPham;
    DefaultTableModel dtmSanPham;
    JTextField txtMa, txtTen, txtsoLuong, txtdonGia, txtTimKiem;
    JComboBox<String> cmbLoai;
    JButton btnThem, btnSua, btnXoa, btnTim, btnChonAnh, btnReset, btnXuatExcel, btnNhapExcel;
    JLabel lblAnhSP;

    private void addControlsSanPham() {
        Font font = new Font("Tahoma", Font.PLAIN, 20);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(colorPanel);

        int w = 1030;
        int h = 844;

        JPanel pnTitle = new TransparentPanel();
        JLabel lblTitle = new JLabel("<html><h1>QUẢN LÝ SẢN PHẨM</h1></html>");
        btnReset = new JButton(new ImageIcon("src/main/resources/image/Refresh-icon.png"));
        btnReset.setPreferredSize(new Dimension(40, 40));
        pnTitle.add(lblTitle);
        pnTitle.add(btnReset);
        this.add(pnTitle);

        JPanel pnThongTin = new TransparentPanel();
        pnThongTin.setLayout(new BoxLayout(pnThongTin, BoxLayout.X_AXIS));

        // ================PANEL INPUT=========
        JPanel pnTextField = new TransparentPanel();
        pnTextField.setLayout(new BoxLayout(pnTextField, BoxLayout.Y_AXIS));
        JLabel lblMa, lblTen, lblLoai, lblSoLuong, lblDonViTinh, lblDonGia;

        lblMa = new JLabel("Mã SP");
        lblTen = new JLabel("Tên SP");
        lblLoai = new JLabel("Loại");
        lblSoLuong = new JLabel("Số lượng   ");
        lblDonGia = new JLabel("Đơn giá");

        txtMa = new JTextField(15);
        txtMa.setEditable(false);
        txtTen = new JTextField(15);
        cmbLoai = new JComboBox<String>();
        txtsoLuong = new JTextField(15);
        txtsoLuong.setEditable(false);
        txtdonGia = new JTextField(15);
        txtdonGia.setEditable(false);

        JPanel pnMa = new TransparentPanel();
        lblMa.setFont(font);
        txtMa.setFont(font);
        pnMa.add(lblMa);
        pnMa.add(txtMa);
        pnTextField.add(pnMa);

        JPanel pnTen = new TransparentPanel();
        lblTen.setFont(font);
        txtTen.setFont(font);
        pnTen.add(lblTen);
        pnTen.add(txtTen);
        pnTextField.add(pnTen);

        JPanel pnLoai = new TransparentPanel();
        lblLoai.setFont(font);
        cmbLoai.setFont(font);
        cmbLoai.setPreferredSize(txtMa.getPreferredSize());
        pnLoai.add(lblLoai);
        pnLoai.add(cmbLoai);
        pnTextField.add(pnLoai);

        JPanel pnSoLuong = new TransparentPanel();
        lblSoLuong.setFont(font);
        txtsoLuong.setFont(font);
        pnSoLuong.add(lblSoLuong);
        pnSoLuong.add(txtsoLuong);
        pnTextField.add(pnSoLuong);

        JPanel pnDonGia = new TransparentPanel();
        lblDonGia.setFont(font);
        txtdonGia.setFont(font);
        pnDonGia.add(lblDonGia);
        pnDonGia.add(txtdonGia);
        pnTextField.add(pnDonGia);

        Dimension lblSize = lblSoLuong.getPreferredSize();
        lblMa.setPreferredSize(lblSize);
        lblTen.setPreferredSize(lblSize);
        lblLoai.setPreferredSize(lblSize);
        lblSoLuong.setPreferredSize(lblSize);
        lblDonGia.setPreferredSize(lblSize);

        pnThongTin.add(pnTextField);

        // =================PANEL ẢNH==========
        JPanel pnAnh = new TransparentPanel();
        pnAnh.setLayout(new BoxLayout(pnAnh, BoxLayout.Y_AXIS));

        JPanel pnChuaAnh = new TransparentPanel();
        pnChuaAnh.setPreferredSize(new Dimension((int) pnAnh.getPreferredSize().getWidth(), 250));
        lblAnhSP = new JLabel();
        lblAnhSP.setPreferredSize(new Dimension(200, 200));
        lblAnhSP.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblAnhSP.setIcon(getAnhSP(""));
        pnChuaAnh.add(lblAnhSP);
        pnAnh.add(pnChuaAnh);

        JPanel pnButtonAnh = new TransparentPanel();
        pnButtonAnh.setPreferredSize(new Dimension(
                (int) pnChuaAnh.getPreferredSize().getHeight(), 40));
        btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.setFont(font);
        pnButtonAnh.add(btnChonAnh);
        pnChuaAnh.add(pnButtonAnh);

        pnThongTin.add(pnAnh);
        this.add(pnThongTin);

        JPanel pnButton = new TransparentPanel();

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Lưu");
        btnXoa = new JButton("Xoá");
        btnTim = new JButton("Tìm kiếm");
        btnXuatExcel = new JButton("Xuất");
        btnNhapExcel = new JButton("Nhập");

        Font fontButton = new Font("Tahoma", Font.PLAIN, 16);
        btnThem.setFont(fontButton);
        btnSua.setFont(fontButton);
        btnXoa.setFont(fontButton);
        btnTim.setFont(fontButton);
        btnXuatExcel.setFont(fontButton);
        btnNhapExcel.setFont(fontButton);

        btnThem.setIcon(new ImageIcon("src/main/resources/image/add-icon.png"));
        btnSua.setIcon(new ImageIcon("src/main/resources/image/Pencil-icon.png"));
        btnXoa.setIcon(new ImageIcon("src/main/resources/image/delete-icon.png"));
        btnTim.setIcon(new ImageIcon("src/main/resources/image/Search-icon.png"));
        btnXuatExcel.setIcon(new ImageIcon("src/main/resources/image/excel-icon.png"));
        btnNhapExcel.setIcon(new ImageIcon("src/main/resources/image/excel-icon.png"));

        JPanel pnTimKiem = new TransparentPanel();
        JLabel lblTimKiem = new JLabel("Từ khoá tìm");
        lblTimKiem.setFont(font);
        txtTimKiem = new JTextField(20);
        txtTimKiem.setFont(font);
        pnTimKiem.add(lblTimKiem);
        pnTimKiem.add(txtTimKiem);
        this.add(pnTimKiem);

        pnButton.add(btnThem);
        pnButton.add(btnSua);
        pnButton.add(btnXoa);
        pnButton.add(btnTim);
        pnButton.add(btnXuatExcel);
        pnButton.add(btnNhapExcel);

        Dimension btnSize = btnTim.getPreferredSize();
        btnThem.setPreferredSize(btnSize);
        btnSua.setPreferredSize(btnSize);
        btnXoa.setPreferredSize(btnSize);
        btnTim.setPreferredSize(btnSize);
        btnXuatExcel.setPreferredSize(btnSize);
        btnNhapExcel.setPreferredSize(btnSize);

        this.add(pnButton);

        // ============PANEL BẢNG===========
        JPanel pnTable = new TransparentPanel(new BorderLayout());
        // ====================Bảng hàng hoá====================
        // <editor-fold defaultstate="collapsed" desc="Bảng sản phẩm">
        dtmSanPham = new DefaultTableModel();
        dtmSanPham.addColumn("Mã SP");
        dtmSanPham.addColumn("Tên SP");
        dtmSanPham.addColumn("Loại SP");
        dtmSanPham.addColumn("Đơn giá");
        dtmSanPham.addColumn("Số lượng");
        dtmSanPham.addColumn("");

        tblSanPham = new Table(dtmSanPham);
        tblSanPham.setDefaultEditor(Object.class, null);
        tblSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblSanPham.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblSanPham.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblSanPham.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        TableColumnModel columnModelBanHang = tblSanPham.getColumnModel();
        columnModelBanHang.getColumn(0).setPreferredWidth(77);
        columnModelBanHang.getColumn(1).setPreferredWidth(282);
        columnModelBanHang.getColumn(2).setPreferredWidth(120);
        columnModelBanHang.getColumn(3).setPreferredWidth(85);
        columnModelBanHang.getColumn(4).setPreferredWidth(138);

        columnModelBanHang.getColumn(5).setMinWidth(0);
        columnModelBanHang.getColumn(5).setMaxWidth(0);
        columnModelBanHang.getColumn(5).setWidth(0);

        JScrollPane scrTblSanPham = new JScrollPane(tblSanPham);
        // </editor-fold>
        pnTable.add(scrTblSanPham, BorderLayout.CENTER);
        this.add(pnTable);

        loadDataCmbLoai();
        loadDataLenBangSanPham();
    }

    private void addEventsSanPham() {
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAnh("");
                loadDataLenBangSanPham();
                txtMa.setText("");
                txtTen.setText("");
                txtdonGia.setText("");
                txtsoLuong.setText("");
                cmbLoai.setSelectedIndex(0);
            }
        });

        tblSanPham.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyClickTblSanPham();
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

        cmbLoai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyThemLoai();
            }
        });

        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyThemSanPham();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLySuaSanPham();
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXoaSanPham();
            }
        });

        btnChonAnh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyChonAnh();
            }
        });

        btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiem();
            }
        });

        txtTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiem();
            }
        });
        btnXuatExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXuatFileExcel();
            }
        });
        btnNhapExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyNhapFileExcel();
            }
        });
    }

    private void xuLyNhapFileExcel() {
        Dialog dlg = new Dialog("Dữ liệu cũ sẽ bị xoá, tiếp tục?", Dialog.WARNING_DIALOG);
        if (dlg.getAction() != Dialog.OK_OPTION) {
            return;
        }

        XuLyFileExcel nhapFile = new XuLyFileExcel();
        nhapFile.nhapExcel(tblSanPham);

        SanPhamDAO spDAO = SanPhamDAO.getInstance();
        spDAO.xoaToanBoSP();

        int row = tblSanPham.getRowCount();
        for (int i = 0; i < row; i++) {
            String ten = tblSanPham.getValueAt(i, 1) + "";
            String loai = tblSanPham.getValueAt(i, 2) + "";
            String donGia = tblSanPham.getValueAt(i, 3) + "";
            String soLuong = tblSanPham.getValueAt(i, 4) + "";
            String anh = tblSanPham.getValueAt(i, 5) + "";

            spBUS.nhapSanPhamTuExcel(ten, loai, soLuong, anh, donGia);
        }
    }

    private void xuLyXuatFileExcel() {
        XuLyFileExcel xuatFile = new XuLyFileExcel();
        xuatFile.xuatExcel(tblSanPham);
    }

    private void loadAnh(String anh) {
        lblAnhSP.setIcon(getAnhSP(anh));
    }

    private void xuLyClickTblSanPham() {
        int row = tblSanPham.getSelectedRow();
        if (row > -1) {
            String ma = tblSanPham.getValueAt(row, 0) + "";
            String ten = tblSanPham.getValueAt(row, 1) + "";
            String loai = tblSanPham.getValueAt(row, 2) + "";
            String donGia = tblSanPham.getValueAt(row, 3) + "";
            String soLuong = tblSanPham.getValueAt(row, 4) + "";
            String anh = tblSanPham.getValueAt(row, 5) + "";

            txtMa.setText(ma);
            txtTen.setText(ten);
            txtdonGia.setText(donGia);
            txtsoLuong.setText(soLuong);

            int flag = 0;
            for (int i = 0; i < cmbLoai.getItemCount(); i++) {
                if (cmbLoai.getItemAt(i).contains(loai)) {
                    flag = i;
                    break;
                }
            }
            cmbLoai.setSelectedIndex(flag);
            loadAnh("src/main/resources/image/SanPham/" + anh);
        }
    }

    private void loadDataLenBangSanPham() {
        spBUS.docListSanPham();
        dtmSanPham.setRowCount(0);

        ArrayList<SanPham> dssp = spBUS.getListSanPham();

        DecimalFormat dcf = new DecimalFormat("###,###");

        for (SanPham sp : dssp) {
            if (sp.getTrangThai() == 0)
                continue;
            Vector vec = new Vector();
            vec.add(sp.getMaSP());
            vec.add(sp.getTenSP());
            String tenLoai = loaiBUS.getTenLoai(sp.getMaLoai());
            vec.add(tenLoai);
            vec.add(dcf.format(sp.getDonGia()));
            vec.add(dcf.format(sp.getSoLuong()));
            vec.add(sp.getHinhAnh());
            dtmSanPham.addRow(vec);
        }
    }

    private void loadDataCmbLoai() {
        loaiBUS.docDanhSachLoai();
        cmbLoai.removeAllItems();

        ArrayList<LoaiSP> dsl = loaiBUS.getDanhSachLoai();
        cmbLoai.addItem("0 - Chọn loại");
        for (LoaiSP loai : dsl) {
            cmbLoai.addItem(loai.getMaLoai() + " - " + loai.getTenLoai());
        }
        cmbLoai.addItem("Khác...");
    }

    private void xuLyThemLoai() {
        int x = cmbLoai.getSelectedIndex();
        if (x == cmbLoai.getItemCount() - 1) {
            DlgQuanLyLoai loaiGUI = new DlgQuanLyLoai();
            loaiGUI.setVisible(true);
            loadDataCmbLoai();
        }
    }

    private void xuLyThemSanPham() {
        String anh = fileAnhSP.getName();
        System.out.println(fileAnhSP.getName());
        boolean flag = spBUS.themSanPham(txtTen.getText(),
                cmbLoai.getSelectedItem() + "",
                txtsoLuong.getText(),
                anh,
                txtdonGia.getText());
        spBUS.docListSanPham();
        loadDataLenBangSanPham();
        luuFileAnh();
    }

    File fileAnhSP;

    private void xuLySuaSanPham() {
        String anh = fileAnhSP.getName();
        boolean flag = spBUS.suaSanPham(txtMa.getText(),
                txtTen.getText(),
                cmbLoai.getSelectedItem() + "",
                txtsoLuong.getText(),
                anh,
                txtdonGia.getText());
        spBUS.docListSanPham();
        loadDataLenBangSanPham();
        luuFileAnh();
    }

    private void xuLyXoaSanPham() {
        if (!txtsoLuong.getText().equals("0")) {
            Dialog dlg1 = new Dialog("Không thể xóa!", Dialog.ERROR_DIALOG);
            return;
        }
        Dialog dlg = new Dialog("Bạn có chắc chắn muốn xoá?", Dialog.WARNING_DIALOG);
        if (dlg.OK_OPTION == dlg.getAction()) {
            boolean flag = spBUS.xoaSanPham(txtMa.getText());
            if (flag) {
                loadDataLenBangSanPham();
            }
        }
    }

    private void luuFileAnh() {
        BufferedImage bImage = null;
        try {
            File initialImage = new File(fileAnhSP.getPath());
            bImage = ImageIO.read(initialImage);

            ImageIO.write(bImage, "png", new File("src/main/resources/image/SanPham/" + fileAnhSP.getName()));

        } catch (IOException e) {
            System.out.println("Exception occured :" + e.getMessage());
        }
    }

    private void xuLyChonAnh() {
        JFileChooser fileChooser = new FileChooser("src/main/resources/image/SanPham/");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Tệp hình ảnh", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileAnhSP = fileChooser.getSelectedFile();
            lblAnhSP.setIcon(getAnhSP(fileAnhSP.getPath()));
        }
    }

    private ImageIcon getAnhSP(String src) {
        src = src.trim().equals("") ? "default.png" : src;
        // Xử lý ảnh
        BufferedImage img = null;
        File fileImg = new File(src);

        if (!fileImg.exists()) {
            src = "default.png";
            fileImg = new File("src/main/resources/image/SanPham/" + src);
        }

        try {
            img = ImageIO.read(fileImg);
            fileAnhSP = new File(src);
        } catch (IOException e) {
            fileAnhSP = new File("imgs/anhthe/avatar.jpg");
        }

        if (img != null) {
            Image dimg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            return new ImageIcon(dimg);
        }

        return null;
    }

    private void xuLyTimKiem() {
        String ten = txtTimKiem.getText().toLowerCase();
        dtmSanPham.setRowCount(0);
        ArrayList<SanPham> dssp = null;
        dssp = spBUS.getSanPhamTheoTen(ten);
        DecimalFormat dcf = new DecimalFormat("###,###");
        for (SanPham sp : dssp) {
            Vector vec = new Vector();
            vec.add(sp.getMaSP());
            vec.add(sp.getTenSP());
            String tenLoai = loaiBUS.getTenLoai(sp.getMaLoai());
            vec.add(tenLoai);
            vec.add(dcf.format(sp.getDonGia()));
            vec.add(dcf.format(sp.getSoLuong()));
            vec.add(sp.getHinhAnh());
            dtmSanPham.addRow(vec);
        }
        Dialog dlg = new Dialog("Số kết quả tìm được: " + dssp.size(), Dialog.INFO_DIALOG);
    }
}
