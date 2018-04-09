package com.society.config.validate;

import com.society.config.exception.ErrorCode;

import java.util.ResourceBundle;

public class CustomValidator {
    public static String getMessage(ErrorCode errorCode) {
        if (errorCode == null) {
            return null;
        }
        String key = errorCode.getClass().getSimpleName() + "__" + errorCode;
        ResourceBundle bundle = ResourceBundle.getBundle("exceptions");
        return bundle.getString(key);
    }

}
 