package com.ryhnik.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationResponseBuilder {

    private final String id;
    private String message;
    private Integer code;
    private List<ValidationErrorField> errors = new ArrayList<>();

    public ValidationResponseBuilder(String id) {
        this.id = id;
    }

    public static ValidationResponseBuilder builder(String id) {
        return new ValidationResponseBuilder(id);
    }

    public ValidationResponseBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ValidationResponseBuilder withCode(Integer code) {
        this.code = code;
        return this;
    }

    public ValidationResponseBuilder withErrors(List<ValidationErrorField> errors) {
        this.errors = errors;
        return this;
    }

    public ValidationErrorResponse build() {
        return new ValidationErrorResponse(id, message, code, errors);
    }
}
