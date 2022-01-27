package com.ryhnik.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ExceptionBuilder {

    private final Code code;
    private String message;
    private Throwable cause;

    public ExceptionBuilder(Code code) {
        this.code = code;
    }

    public MasterClubException build(Class<? extends MasterClubException> clazz) {
        try {
            Constructor<? extends MasterClubException> c = clazz.getConstructor(String.class, Code.class, Throwable.class);
            return c.newInstance(message, code, cause);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Failed to construct ClinEcoException", e);
        }
    }

    public static ExceptionBuilder builder(Code code) {
        return new ExceptionBuilder(code);
    }

    public ExceptionBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ExceptionBuilder withMessage(String message, Object... args) {
        this.message = String.format(message, args);
        return this;
    }

    public ExceptionBuilder withCause(Throwable cause) {
        this.cause = cause;
        return this;
    }
}
