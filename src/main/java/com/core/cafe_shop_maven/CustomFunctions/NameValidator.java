package com.core.cafe_shop_maven.CustomFunctions;

public class NameValidator {
    private final String name;
    private final boolean isValid;
    private String message;

    public NameValidator(String name) {
        this.name = name;
        isValid = validate();
    }

    private boolean validate() {
        if (isEmpty()) {
            message = "Tên không được để trống!";
            return false;
        } else if (isHaveSpecialChacracterOrNumber()) {
            message = "Tên không được chứa số hay kí tự đặc biệt!";
            return false;
        } else {
            message = "Tên hợp lệ!";
            return true;
        }
    }

    private boolean isEmpty() {
        return (name == null || name.trim().isEmpty());
    }

    private boolean isHaveSpecialChacracterOrNumber() {
        String regex = "^[a-zA-ZÀ-ỹ\\s]+$";
        return !name.matches(regex);
    }

    public boolean isValid() {
        return isValid;
    }

    public String getMessage() {
        return message;
    }
}
