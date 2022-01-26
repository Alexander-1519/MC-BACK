package com.ryhnik.exception;

public enum Code {

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
