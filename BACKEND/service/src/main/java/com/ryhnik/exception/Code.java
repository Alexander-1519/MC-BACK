package com.ryhnik.exception;

public enum Code {

    VALIDATION_FAILED(101),

    USER_NOT_FOUND(200),
    USER_EXCEPTION(201),

    REGION_NOT_FOUND(210),
    REGION_EXCEPTION(211),

    ROLE_NOT_FOUND(220),
    ROLE_EXCEPTION(221),

    EMAIL_NOT_FOUND(230),
    EMAIL_EXCEPTION(231),

    MAINTENANCE_NOT_FOUND(240),
    MAINTENANCE_EXCEPTION(241),

    MASTER_NOT_FOUND(250),
    MASTER_EXCEPTION(251),

    CITY_NOT_FOUND(260),
    CITY_EXCEPTION(261),

    MAINTENANCE_DATE_NOT_FOUND(270),
    MAINTENANCE_DATE_EXCEPTION(271),

    UNEXPECTED(500),
    SYSTEM_ERROR(501),
    CONVERTING_FILE_ERROR(502);

    private final Integer codeIntValue;

    Code(Integer code) {
        this.codeIntValue = code;
    }

    public Integer getIntValue() {
        return codeIntValue;
    }
}
