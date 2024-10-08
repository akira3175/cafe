package com.core.cafe_shop_maven.VALIDATOR;

import com.core.cafe_shop_maven.BUS.KhuyenMaiBUS;
import com.core.cafe_shop_maven.DAO.KhuyenMaiDAO;
import com.core.cafe_shop_maven.DTO.KhachHang;
import com.core.cafe_shop_maven.DTO.KhuyenMai;

public class PromotionValidator {
    private KhuyenMai khuyenMai;
    private boolean isValid;
    private String message;

    public PromotionValidator(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public PromotionValidator(String ID, String name, String percent, String condition, String dateBegin, String dateEnd) {
        int ID_Int = Integer.parseInt(ID);
        int condition_Int = Integer.parseInt(condition);
        int percent_Int = Integer.parseInt(percent);
        khuyenMai = new KhuyenMai(ID_Int, name, percent_Int, condition_Int, dateBegin, dateEnd);
    }

    public String getMessage() {
        return message;
    }

    public boolean isCanAdd() {
        validateForAdd();
        return isValid;
    }

    public boolean isCanEdit() {
        validateForEdit();
        return isValid;
    }

    private boolean validateForAdd() {
        if (isNameExist()) {
            return false;
        }
        if (isPercentUnder0()) {
            return false;
        }
        if (isPercentMoreThan100()) {
            return false;
        }
        if (isConditionIsNegative()) {
            return false;
        }
        return true;
    }

    private boolean validateForEdit() {
        if (isPercentUnder0()) {
            return false;
        }
        if (isPercentMoreThan100()) {
            return false;
        }
        if (isConditionIsNegative()) {
            return false;
        }
        if (!isEdited()) {
            return false;
        }
        return true;
    }

    private boolean isEdited() {
        KhuyenMai khuyenMaiExist = KhuyenMaiDAO.getInstance().getKhuyenMai(khuyenMai.getMaKM());
        if (!khuyenMaiExist.getTenKM().equals(khuyenMai.getTenKM())) {
            isValid = true;
        } else if (khuyenMaiExist.getDieuKien() != khuyenMai.getDieuKien()) {
            isValid = true;
        } else if (khuyenMaiExist.getMaKM() != khuyenMai.getMaKM()) {
            isValid = true;
        } else if (!khuyenMaiExist.getNgayBD().equals(khuyenMai.getNgayBD())) {
            isValid = true;
        } else if (!khuyenMaiExist.getNgayKT().equals(khuyenMai.getNgayKT())) {
            isValid = true;
        } else {
            isValid = false;
        }
        if (isValid) {
            message = "Dữ liệu được chỉnh sửa!";
        } else {
            message = "Dữ liệu chưa được chỉnh sửa!";
        }
        return isValid;
    }

    private boolean isPercentUnder0() {
        if (khuyenMai.getPhanTramKM() < 0) {
            isValid = false;
            message = "Phần trăm giảm dưới 0!";
        } else {
            isValid = true;
        }
        return !isValid;
    }

    private boolean isPercentMoreThan100() {
        if (khuyenMai.getPhanTramKM() > 100) {
            isValid = false;
            message = "Phần trăm giảm trên 100!";
        } else {
            isValid = true;
        }
        return !isValid;
    }

    private boolean isConditionIsNegative() {
        if (khuyenMai.getDieuKien() < 0) {
            isValid = false;
            message = "Điều kiện giảm phải là số dương!";
        } else {
            isValid = true;
        }
        return !isValid;
    }

    private boolean isNameExist() {
        if (KhuyenMaiDAO.getInstance().tenMaCoTonTaiKhong(khuyenMai.getTenKM())) {
            isValid = false;
            message = "Mã khuyến mãi đã tồn tại!";
        } else {
            isValid = true;
        }
        return !isValid;
    }
}
