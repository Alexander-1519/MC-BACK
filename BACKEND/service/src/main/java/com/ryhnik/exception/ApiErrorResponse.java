package com.ryhnik.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorResponse {

    private final String id;

    private final String message;

    private final Integer code;

    private final LocalDateTime timestamp = LocalDateTime.now();

    private final List<String> errors;

    public ApiErrorResponse(String id, String message, Integer code, List<String> errors) {
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

    public List<String> getErrors() {
        return errors;
    }
}
