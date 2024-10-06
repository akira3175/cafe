package com.core.cafe_shop_maven.CustomFunctions;

public class PhoneNumberValidator {
    private final String numberPhone;
    private final boolean isValid;
    private String message;

    public PhoneNumberValidator(String numberPhone) {
        this.numberPhone = numberPhone;
        isValid = validate();
    }

    private boolean validate() {
        if (!isNumberHaveTenNumber()) {
            message = "Số điện thoại không hợp lệ!";
            return false;
        } else if (!isNumberNotNull()) {
            message = "Số điện thoại không được trống!";
            return false;
        } else {
            message = "Số điện thoại hợp lệ!";
            return true;
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public String getMessage() {
        return message;
    }

    private boolean isNumberHaveTenNumber() {
        String regex = "^0\\d{9}$";
        return numberPhone.matches(regex);
    }

    private boolean isNumberNotNull() {
        return !numberPhone.isEmpty();
    }
}