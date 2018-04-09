package com.society.config.exception;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class SystemException extends Exception {
    public static final long serialVersionUID = 1L;

    public static ErrorCode errorCode;
    public static ErrorHandler errorHandler = ErrorHandler.getInstance();
    private final Map<String, Object> properties = new TreeMap<String, Object>();

    public SystemException(ErrorCode errorCode) {
        SystemException.errorCode = errorCode;
        int erroCode = errorCode.getCodeId();
        errorHandler.setErrorCode(erroCode);
        errorHandler.setErrorDesc(getErrorText(errorCode));
    }

    public SystemException(String message, ErrorCode errorCode) {
        super(message);
        SystemException.errorCode = errorCode;
    }

    public SystemException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        SystemException.errorCode = errorCode;
    }

    public SystemException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        SystemException.errorCode = errorCode;
    }

    public static SystemException wrap(Throwable exception) {
        return wrap(exception, null);
    }

    public static SystemException wrap(Throwable exception, ErrorCode errorCode) {
        if (exception instanceof SystemException) {
            SystemException se = (SystemException) exception;
            if (errorCode != null && errorCode != se.getErrorCode()) {
                return new SystemException(exception.getMessage(), exception, errorCode);
            }
            return se;
        } else {
            return new SystemException(exception.getMessage(), exception, errorCode);
        }
    }

    public static String getErrorText(ErrorCode errorCode) {
        if (errorCode == null) {
            return null;
        }
        String key = errorCode.getClass().getSimpleName() + "__" + errorCode;
        ResourceBundle bundle = ResourceBundle.getBundle("exceptions");
        return bundle.getString(key);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public SystemException setProperty(String name, Object value) {
        properties.put(name, value);
        return this;
    }
}
