package com.core.cafe_shop_maven.CustomFunctions;

public class AddressValidator {
    private final String address;
    private final boolean isValid;
    private String message;

    public AddressValidator(String address) {
        this.address = address;
        isValid = validate();
    }

    private boolean validate() {
        if (isEmpty()) {
            message = "Địa chỉ không được để trống!";
            return false;
        } else {
            message = "Địa chỉ hợp lệ!";
            return true;
        }
    }

    private boolean isEmpty() {
        return (address == null || address.trim().isEmpty());
    }

    public boolean isValid() {
        return isValid;
    }

    public String getMessage() {
        return message;
    }
}
