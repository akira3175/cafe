package com.core.cafe_shop_maven.VALIDATOR;

import com.core.cafe_shop_maven.CustomFunctions.AddressValidator;
import com.core.cafe_shop_maven.CustomFunctions.NameValidator;
import com.core.cafe_shop_maven.CustomFunctions.PhoneNumberValidator;
import com.core.cafe_shop_maven.DAO.*;
import com.core.cafe_shop_maven.DTO.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

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
        return validateForAdd();
    }

    public boolean isCanEdit() {
        return validateForEdit();
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
        if (isNumberPhoneStaffExist(nhanVien.getMaNV())) {
            return false;
        }
        return true;
    }

    private boolean validateForAdd() {
        if (!isNameValid()) {
            return false;
        }
        if (!isDateValid()) {
            return false;
        }
        if (!isOver18()) {
            return false;
        }
        if (isOver65()) {
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

    private boolean isNumberPhoneStaffExist(int IDStaff) {
        isValid = !NhanVienDAO.getInstance().kiemTraSoDienThoaiCoTonTaiKhong(IDStaff, nhanVien.getSdt());
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

   private boolean isDateValid() {
       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
       sdf.setLenient(false);
       try {
           sdf.parse(nhanVien.getNgaySinh());
           isValid = true;
           message = "Ngày sinh đúng định dạng.";
           String pattern = "\\d{2}/\\d{2}/\\d{4}";
           if (!nhanVien.getNgaySinh().matches(pattern)) {
               isValid = false;
               message = "Ngày sinh không đúng định dạng!";
           }
       } catch (Exception ex) {
           isValid = false;
           message = "Ngày sinh không đúng định dạng!";
       }
       return isValid;
   }

   private boolean isOver18() {
       LocalDate ngaySinhLocalDate = LocalDate.parse(nhanVien.getNgaySinh(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
       LocalDate ngayHienTai = LocalDate.now();
       Period age = Period.between(ngaySinhLocalDate, ngayHienTai);
       if (age.getYears() >= 18) {
           isValid = true;
           message = "Nhân viên đủ 18 tuổi.";
       } else {
           isValid = false;
           message = "Nhân viên dưới 18 tuổi!";
       }
       return isValid;
   }

    private boolean isOver65() {
        LocalDate ngaySinhLocalDate = LocalDate.parse(nhanVien.getNgaySinh(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate ngayHienTai = LocalDate.now();
        Period age = Period.between(ngaySinhLocalDate, ngayHienTai);
        if (age.getYears() > 65) {
            isValid = true;
            message = "Nhân viên quá 65 tuổi.";
        } else {
            isValid = false;
        }
        return isValid;
    }
}
