package com.ryhnik.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class MasterClubException extends RuntimeException{

    private final String id = generateExceptionId();

    private final Code code;

    public MasterClubException(Code code) {
        this.code = code;
    }

    public MasterClubException(String message, Code code) {
        super(message);
        this.code = code;
    }

    public MasterClubException(String message, Code code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public static String generateExceptionId() {
        return "err|" + UUID.randomUUID();
    }

    public String getId() {
        return id;
    }

    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    public Code getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "id='" + id + "'" +
                ", status=" + getStatus() +
                ", code=" + code;
    }
}
