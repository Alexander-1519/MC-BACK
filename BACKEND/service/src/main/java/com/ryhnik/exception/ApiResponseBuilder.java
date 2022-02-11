package com.ryhnik.exception;

import java.util.ArrayList;
import java.util.List;

public class ApiResponseBuilder {

    private final String id;

    private String message;

    private Integer code;

    private List<String> errors = new ArrayList<>();

    public ApiResponseBuilder(String id) {
        this.id = id;
    }

    public static ApiResponseBuilder builder(String id) {
        return new ApiResponseBuilder(id);
    }

    public ApiResponseBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiResponseBuilder withCode(Integer code) {
        this.code = code;
        return this;
    }

    public ApiResponseBuilder withError(String error) {
        this.errors.add(error);
        return this;
    }

    public ApiResponseBuilder withErrors(List<String> errors) {
        this.errors = errors;
        return this;
    }

    public ApiErrorResponse build() {
        return new ApiErrorResponse(id, message, code, errors);
    }
}
