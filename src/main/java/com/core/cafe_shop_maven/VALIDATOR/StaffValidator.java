package com.core.cafe_shop_maven.VALIDATOR;

import com.core.cafe_shop_maven.CustomFunctions.AddressValidator;
import com.core.cafe_shop_maven.CustomFunctions.NameValidator;
import com.core.cafe_shop_maven.CustomFunctions.PhoneNumberValidator;
import com.core.cafe_shop_maven.DAO.*;
import com.core.cafe_shop_maven.DTO.*;

public class StaffValidator {
    private final NhanVien nhanVien;
    private boolean isValid;
    private String message;

    public StaffValidator(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public StaffValidator(String idNV,String name, String numberphone, String address, String datebirth) {
        nhanVien = new NhanVien(Integer.parseInt(idNV), name, datebirth, address, numberphone, 0);
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
        if (isNumberPhoneStaffExist()) {
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
        if (isNumberPhoneStaffExist()) {
            return false;
        }
        return true;
    }

    private boolean isNumberPhoneStaffExist() {
        isValid = !NhanVienDAO.getInstance().kiemTraSoDienThoaiCoTonTaiKhong(nhanVien.getSdt());
        message = "Số điện thoại đã tồn tại!";
        return !isValid;
    }

    private boolean isNameValid() {
        NameValidator nameValidator = new NameValidator(nhanVien.getTen());
        isValid = nameValidator.isValid();
        message = nameValidator.getMessage();
        return isValid;
    }

    private boolean isAddressValid() {
        AddressValidator addressValidator = new AddressValidator(nhanVien.getDiaChi());
        isValid = addressValidator.isValid();
        message = addressValidator.getMessage();
        return isValid;
    }

    private boolean isNumberphoneValid() {
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator(nhanVien.getSdt());
        isValid = phoneNumberValidator.isValid();
        message = phoneNumberValidator.getMessage();
        return isValid;
    }

    private boolean isEdited() {
        NhanVien nhanVienExist = NhanVienDAO.getInstance().getNhanVien(nhanVien.getMaNV());
        if (!nhanVienExist.getDiaChi().equals(nhanVien.getDiaChi())) {
            isValid = true;
        } else if (!nhanVienExist.getSdt().equals(nhanVien.getSdt())) {
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
}
