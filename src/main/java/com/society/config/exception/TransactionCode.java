package com.society.config.exception;

public enum TransactionCode implements ErrorCode {
    REGISTER_FAILED(251),
    LOGIN_FAILED(252),
    USER_NOT_FOUND(253),
    UNAUTHORIZED_ACCESS(254),
    SUCCESS_LOGIN(253);
    private final int codeId;

    TransactionCode(int codeId) {
        this.codeId = codeId;
    }

    @Override
    public int getCodeId() {
        return codeId;
    }

}
