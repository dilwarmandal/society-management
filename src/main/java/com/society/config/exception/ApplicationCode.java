package com.society.config.exception;

public enum ApplicationCode implements ErrorCode {
    COUNTRY(1000), STATE(1001), CITY(1002), USER_TYPE(1020), USER_STATUS(1021), USER_ROLE(1022),
    GENDER(1023), TITLE(1043), ADDRESS_TYPE(1063), TRANSACTION_MODES(1030), TRANSACTION_TYPES(1026),
    TRANSACTION_CREDIT_SELECTION(1027),
    TRANSACTION_DEBIT_SELECTION(1028);
    int codeId;

    ApplicationCode(int codeId) {
        this.codeId = codeId;
    }

    public int getCodeId() {
        return codeId;
    }
}
