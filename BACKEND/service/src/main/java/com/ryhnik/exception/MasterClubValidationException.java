package com.ryhnik.exception;

public class MasterClubValidationException extends MasterClubException{

    public MasterClubValidationException() {
        super(Code.VALIDATION_FAILED);
    }

    public MasterClubValidationException(String message) {
        super(message, Code.VALIDATION_FAILED);
    }

    public MasterClubValidationException(String message, Throwable cause) {
        super(message, Code.VALIDATION_FAILED, cause);
    }
}
