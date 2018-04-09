package com.society.config.exception;

public class ErrorHandler {
    static int errorCode;
    static String errorDesc;
    private static ErrorHandler instance = null;

    public ErrorHandler() {
        errorCode = 0;
    }

    public static ErrorHandler getInstance() {
        if (instance == null) {
            instance = new ErrorHandler();
        }
        return instance;
    }

    public static boolean isError() {
        if (errorCode == 0) {
            return false;
        } else {
            return true;
        }
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }


}
