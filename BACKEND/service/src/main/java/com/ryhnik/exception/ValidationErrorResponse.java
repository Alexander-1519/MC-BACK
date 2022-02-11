package com.ryhnik.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ValidationErrorResponse {

    private final String id;
    private final String message;
    private final Integer code;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final List<ValidationErrorField> errors;

    public ValidationErrorResponse(String id, String message, Integer code, List<ValidationErrorField> errors) {
        this.id = id;
        this.message = message;
        this.code = code;
        this.errors = errors;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<ValidationErrorField> getErrors() {
        return errors;
    }
}
