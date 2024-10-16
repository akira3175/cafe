package com.core.cafe_shop_maven.VALIDATOR;

import com.core.cafe_shop_maven.CustomFunctions.AddressValidator;
import com.core.cafe_shop_maven.CustomFunctions.NameValidator;
import com.core.cafe_shop_maven.CustomFunctions.PhoneNumberValidator;
import com.core.cafe_shop_maven.DAO.KhachHangDAO;
import com.core.cafe_shop_maven.DTO.KhachHang;

public class CustomerValidator {
    private KhachHang khachHang;
    private boolean isValid;
    private String message;

    public CustomerValidator(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public CustomerValidator(String idKH,String name, String address, String numberphone) {
        khachHang = new KhachHang(Integer.parseInt(idKH), name, address, numberphone);
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

    public boolean isCanDelete() {
        validateForDelete();
        return isValid;
    }

    private boolean validateForEdit() {
        if (!isAddressValid()) {
            return false;
        }
        if (!isNumberphoneValid()) {
            return false;
        }
        if (!isEdited()) {
            return false;
        }
        if (isNumberPhoneCustomerExist(khachHang.getMaKH())) {
            return false;
        }
        return true;
    }

    private boolean validateForAdd() {
        if (!isNameValid()) {
            return false;
        }
        if (!isAddressValid()) {
            return false;
        }
        if (!isNumberphoneValid()) {
            return false;
        }
        if (isNumberPhoneCustomerExist()) {
            return false;
        }
        return true;
    }

    private boolean validateForDelete() {
        if (isCustomerHaveInvoice()) {
            return false;
        }
        return true;
    }

    private boolean isNumberPhoneCustomerExist() {
        isValid = !KhachHangDAO.getInstance().kiemTraSoDienThoaiCoTonTaiKhong(khachHang.getSdt());
        message = "Số điện thoại đã tồn tại!";
        return !isValid;
    }

    private boolean isNumberPhoneCustomerExist(int IDCustomer) {
        isValid = !KhachHangDAO.getInstance().kiemTraSoDienThoaiCoTonTaiKhong(IDCustomer, khachHang.getSdt());
        message = "Số điện thoại đã tồn tại!";
        return !isValid;
    }

    private boolean isNameValid() {
        NameValidator nameValidator = new NameValidator(khachHang.getTen());
        isValid = nameValidator.isValid();
        message = nameValidator.getMessage();
        return isValid;
    }

    private boolean isAddressValid() {
        AddressValidator addressValidator = new AddressValidator(khachHang.getDiaChi());
        isValid = addressValidator.isValid();
        message = addressValidator.getMessage();
        return isValid;
    }

    private boolean isNumberphoneValid() {
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator(khachHang.getSdt());
        isValid = phoneNumberValidator.isValid();
        message = phoneNumberValidator.getMessage();
        return isValid;
    }

    private boolean isEdited() {
        KhachHang khachHangExist = KhachHangDAO.getInstance().getKhachHang(khachHang.getMaKH());
        if (!khachHangExist.getDiaChi().equals(khachHang.getDiaChi())) {
            isValid = true;
        } else if (!khachHangExist.getSdt().equals(khachHang.getSdt())) {
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

    private boolean isCustomerHaveInvoice() {
        isValid = !KhachHangDAO.getInstance().kiemTraKhachHangCoHoaDonKhong(khachHang.getMaKH()+"");
        message = "Khách hàng này này đã có hóa đơn nên không được xóa!";
        return !isValid;
    }
}
