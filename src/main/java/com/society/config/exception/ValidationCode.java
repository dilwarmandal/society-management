package com.society.config.exception;

public enum ValidationCode implements ErrorCode {
    VALUE_REQUIRED(201),
    INVALID_FORMAT(202),
    VALUE_TOO_SHORT(203),
    VALUE_TOO_LONGS(204),
    COUNTRIES_FETCH_ERROR(205),
    STATES_FETCH_ERROR(206),
    CITIES_FETCH_ERROR(207),
    TITLES_FETCH_ERROR(208),
    USER_ID_IS_REQUIRED(209),
    PASSWORD_IS_REQUIRED(210),
    EMAIL_IS_REQUIRED(211),
    CONFIRM_PASSWORD_IS_REQUIRED(212),
    TITLE_IS_REQUIRED(213),
    FIRST_NAME_IS_REQUIRED(214),
    LAST_NAME_IS_REQUIRED(215),
    GENDER_IS_REQUIRED(216),
    BIRTH_DATE_IS_REQUIRED(217),
    PHONE_NO_IS_REQUIRED(218),
    ADDRESS_1_IS_REQUIRED(219),
    ADDRESS_2_IS_REQUIRED(220),
    COUNTRY_IS_REQUIRED(221),
    STATE_IS_REQUIRED(222),
    CITY_IS_REQUIRED(223),
    ZIP_IS_REQUIRED(224),
    USER_NAME_IS_REQUIRED(225),
    USER_NAME_SIZE(226),
    USER_NAME_EXIST(227),
    PASSWORD_SIZE(228),
    MEMBER_ALREADY_EXIST(229),
    EMAIL_IS_ALREADY_TAKEN(230),
    TRANSACTION_MODE_REQUIRED(235),
    TRANSACTION_SELECTION_REQUIRED(236),
    TRANSACTION_TYPE_REQUIRED(237),
    AMOUNT_NOT_AVAILABLE_AS_CASH(238),
    AMOUNT_NOT_AVAILABLE_IN_BANK(239),
    AMOUNT_ZERO_NOT_ALLOWED(240),
    TRANSACTION_DATE_NOT_ALLOWED_AFTER_CURRENT_DATE(241),
    REGISTRATION_NOT_ALLOWED(242);

    private final int codeId;

    ValidationCode(int codeId) {
        this.codeId = codeId;
    }

    @Override
    public int getCodeId() {
        return codeId;
    }
}