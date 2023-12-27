package com.finalproject.bttd.password;

import java.util.regex.Pattern;

import static com.finalproject.bttd.password.PassPattern.PASSWORD_PATTERN;

public class PasswordValidator {


    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        return pattern.matcher(password).matches();
    }
}
