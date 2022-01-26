package com.ryhnik.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends MasterClubException {

    public EntityNotFoundException(Code code) {
        super(code);
    }

    public EntityNotFoundException(String message, Code code) {
        super(message, code);
    }

    public EntityNotFoundException(String message, Code code, Throwable cause) {
        super(message, code, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
