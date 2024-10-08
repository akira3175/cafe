package com.core.cafe_shop_maven.VALIDATOR;

import java.util.regex.Pattern;

public class UsernameValidator {
    private String username;
    private boolean isValid;
    private String message;
    private final int MIN_LENGTH = 3;
    private final int MAX_LENGTH = 16;

    public UsernameValidator(String username) {
        this.username = username;
        this.isValid = validator();
    }

    public String getMessage() {
        return message;
    }

    public boolean isValid() {
        return isValid;
    }

    private boolean validator() {
        if (!isEnoughLength()) {
            message = "Username phải có độ dài từ " + MIN_LENGTH + " đến " + MAX_LENGTH + " ký tự.";
            return false;
        }

        if (!isHaveCharacterAndNumberOnly()) {
            message = "Username chỉ được chứa chữ cái và số.";
            return false;
        }

        message = "Username hợp lệ.";
        return true;
    }

    private boolean isEnoughLength() {
        return username.length() >= MIN_LENGTH && username.length() <= MAX_LENGTH;
    }

    private boolean isHaveCharacterAndNumberOnly() {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        return pattern.matcher(username).matches();
    }
}
