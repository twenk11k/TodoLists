package com.twenk11k.todolists.common;

import java.util.regex.Pattern;

public class EmailValidator {


    private static final Pattern EMAIL_VALIDATION_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


    public static boolean isEmailValid(String email){
        return email != null && EMAIL_VALIDATION_PATTERN.matcher(email).matches();
    }


}
